package com.wxxiaomi.ming.electricbicycle.model.impl;

import android.util.Log;

import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;
import com.wxxiaomi.ming.electricbicycle.api.exception.ApiException;
import com.wxxiaomi.ming.electricbicycle.api.exception.ERROR;
import com.wxxiaomi.ming.electricbicycle.bean.InviteMessage;
import com.wxxiaomi.ming.electricbicycle.bean.User;
import com.wxxiaomi.ming.electricbicycle.bean.format.InitUserInfo;
import com.wxxiaomi.ming.electricbicycle.dao.impl.EmDaoImpl;
import com.wxxiaomi.ming.electricbicycle.dao.impl.InviteMessgeDaoImpl2;
import com.wxxiaomi.ming.electricbicycle.dao.impl.UserDaoImpl2;
import com.wxxiaomi.ming.electricbicycle.view.em.EmManager;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func3;

/**
 * Created by 12262 on 2016/6/6.
 * 总共需要接口：
 * 1.消息通知接口
 * 2.好友信息通知接口
 * 3.总的消息数通知接口(不需要数量)
 */
public class EmEngine {
    private EmEngine() {
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final EmEngine INSTANCE = new EmEngine();
    }

    //获取单例
    public static EmEngine getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     *初始化聊天系统
     */
    public void init() {
        EMClient.getInstance().contactManager()
                .setContactListener(new EMContactListener() {
                    @Override
                    public void onContactAgreed(final String username) {
                        handleEmEvent(new EmEvent<String>(EmEvent.ONCONNTACTAGREE,username,""));
                    }

                    @Override
                    public void onContactRefused(String username) {
                        handleEmEvent(new EmEvent<String>(EmEvent.ONCONTACTREFUSED,username,""));
                    }

                    @Override
                    public void onContactInvited(String username,
                                                 String reason) {
//
                        try {
//
                            handleEmEvent(new EmEvent<String>(EmEvent.ONCONTACTINVITED, username, reason));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onContactDeleted(String username) {
                        handleEmEvent(new EmEvent<String>(EmEvent.ONCONTACTDELETED,username,""));
                    }

                    @Override
                    public void onContactAdded(String username) {
                        handleEmEvent(new EmEvent<String>(EmEvent.ONCONTACTADDED,username,""));
                    }
                });

        EMClient.getInstance().chatManager()
                .addMessageListener(new EMMessageListener() {
                    @Override
                    public void onMessageReceived(List<EMMessage> list) {
                        Log.i("wang","EmDaoImpl底层收到新消息");
                        handleEmEvent(new EmEvent<List<EMMessage>>(EmEvent.onMessageReceived,"",list));
                    }

                    @Override
                    public void onCmdMessageReceived(List<EMMessage> list) {
                        handleEmEvent(new EmEvent<List<EMMessage>>(EmEvent.onCmdMessageReceived,"",list));
                    }

                    @Override
                    public void onMessageReadAckReceived(List<EMMessage> list) {
                        handleEmEvent(new EmEvent<List<EMMessage>>(EmEvent.onMessageReadAckReceived,"",list));
                    }

                    @Override
                    public void onMessageDeliveryAckReceived(List<EMMessage> list) {
                        handleEmEvent(new EmEvent<List<EMMessage>>(EmEvent.onMessageDeliveryAckReceived,"",list));
                    }

                    @Override
                    public void onMessageChanged(EMMessage emMessage, Object o) {
                        handleEmEvent(new EmEvent<List<EMMessage>>(EmEvent.onMessageChanged,"",null));
                    }
                });


    }

    public void handleEmEvent(final EmEvent<? extends Object> emEvent) {
        switch (emEvent.getType()) {
            case EmEvent.ONCONNTACTAGREE:
                //好友请求被同意
                //1.先从服务器获取用户公共信息
                //2.然后存到本地数据库
                checkTempUser(emEvent.getUsername())
                        .flatMap(new Func1<User.UserCommonInfo, Observable<Integer>>() {
                            @Override
                            public Observable<Integer> call(User.UserCommonInfo userCommonInfo) {
                                List<User.UserCommonInfo> list = new ArrayList<User.UserCommonInfo>();
                                list.add(userCommonInfo);
                                return UserDaoImpl2.getInstance().updateFriendList(list);
                            }
                        })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {

                    }
                })
                ;
            case EmEvent.ONCONTACTREFUSED:
                //好友请求被拒绝
                break;
            case EmEvent.ONCONTACTINVITED:
                // 收到好友邀请
                Log.i("wang", "EmEvent.ONCONTACTINVITED:");
                final InviteMessage msg = new InviteMessage();
                msg.setFrom(emEvent.getUsername());
                msg.setTime(System.currentTimeMillis());
                msg.setReason((String) emEvent.getReason());
                //存储信息
                Observable<Integer> integerObservable = InviteMessgeDaoImpl2.getInstance().saveMessage(msg);
                //增加未读消息数量
                Observable<Integer> objectObservable2 = InviteMessgeDaoImpl2.getInstance().getUnreadNotifyCount()
                        .flatMap(new Func1<Integer, Observable<Integer>>() {
                            @Override
                            public Observable<Integer> call(Integer integer) {
                                return InviteMessgeDaoImpl2.getInstance().saveUnreadMessageCount(integer + 1);
                            }
                        });
                //从服务器获取用户公共信息并存到本地服务器
                Observable<User.UserCommonInfo> userCommonInfoObservable = checkTempUser(emEvent.getUsername());
                Observable.zip(integerObservable, userCommonInfoObservable, objectObservable2, new Func3<Integer, User.UserCommonInfo, Integer, User.UserCommonInfo>() {

                    @Override
                    public User.UserCommonInfo call(Integer integer, User.UserCommonInfo userCommonInfo, Integer integer2) {
                        return userCommonInfo;
                    }
                })
                .subscribe(new Subscriber<User.UserCommonInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("wang","在处理收到好友消息的过程中出错了");
                    }

                    @Override
                    public void onNext(User.UserCommonInfo userCommonInfo) {
                        if(inviteMsgLis != null){
                            inviteMsgLis.InviteMsgReceive();
                        }
                        if(allMsgLis != null){
                            allMsgLis.AllMsgReceive();
                        }
                    }
                })
                ;
            case EmEvent.ONCONTACTDELETED:
                break;
            case EmEvent.ONCONTACTADDED:
                break;
            case EmEvent.onMessageReceived:
//                Log.i("wang", "case EmDaoImpl.EmEvent.onMessageReceived");
                //有多少条直接回复数字就行
                if(friendMsgLis != null){
                    friendMsgLis.FriendMsgReceive();
                }
                if(allMsgLis != null){
                    allMsgLis.AllMsgReceive();
                }
            case EmEvent.onCmdMessageReceived:
                break;
            case EmEvent.onMessageReadAckReceived:
                break;
            case EmEvent.onMessageDeliveryAckReceived:
                break;
            case EmEvent.onMessageChanged:
                break;
        }
    }

    public Observable<Integer> getUnreadInviteCountTotal() {
        return InviteMessgeDaoImpl2.getInstance().getUnreadNotifyCount();
    }

    public Observable<Integer> getUnreadMsgCount() {
        return getUnreadInviteCountTotal()
                .flatMap(new Func1<Integer, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Integer count) {
                        int unreadMsgsCount = EMClient.getInstance().chatManager().getUnreadMsgsCount();
                        return Observable.just(unreadMsgsCount + count);
                    }
                });
    }

    public void logout() {
        EmManager.getInstance().logout();
    }



    /**
     * 判断本地是否有临时用户，有就返回，没有就从服务器取得
     * @param emname
     * @return
     */
    public Observable<User.UserCommonInfo> checkTempUser(final String emname) {
        return UserDaoImpl2.getInstance().getTempuser(emname)
                .flatMap(new Func1<User.UserCommonInfo, Observable<User.UserCommonInfo>>() {
                    @Override
                    public Observable<User.UserCommonInfo> call(User.UserCommonInfo userCommonInfo) {
                        if (userCommonInfo != null) {
                            return Observable.just(userCommonInfo);
                        }else {
                            Log.i("wang","在未连接服务器获取之前临时用表无此用户信息:"+emname);
                            return UserDaoImpl2.getInstance().getUserCommonInfoByEmname(emname)
                                    .flatMap(new Func1<InitUserInfo, Observable<User.UserCommonInfo>>() {
                                        @Override
                                        public Observable<User.UserCommonInfo> call(InitUserInfo initUserInfo) {
                                            Observable<Integer> integerObservable = UserDaoImpl2.getInstance().savaPerson(initUserInfo.friendList.get(0));
                                            Observable<User.UserCommonInfo> just = Observable.just(initUserInfo.friendList.get(0));
                                            return Observable.zip(integerObservable, just, new Func2<Integer, User.UserCommonInfo, User.UserCommonInfo>() {
                                                @Override
                                                public User.UserCommonInfo call(Integer integer, User.UserCommonInfo userCommonInfo) {
                                                    return userCommonInfo;
                                                }
                                            });
                                        }
                                    });
                        }

                    }
                });
    }

    public Observable<Boolean> LoginFromEm(final String username, final String password) {
        return EmDaoImpl.getInstance().LoginFromEm(username, password);
    }

    public Observable<Integer> updateFriend() {
        return EmDaoImpl.getInstance().getContactFromEm()
                //核对数据库好友列表，多的删除，少的数据库获取
                .flatMap(new Func1<List<String>, Observable<List<String>>>() {
                    @Override
                    public Observable<List<String>> call(List<String> usernames) {
                        return UserDaoImpl2.getInstance().checkFriendList(usernames);
                    }
                })
                .flatMap(new Func1<List<String>, Observable<InitUserInfo>>() {
                    @Override
                    public Observable<InitUserInfo> call(List<String> missingUsernames) {
                        return UserDaoImpl2.getInstance().getUserListFromByEmList(missingUsernames);
                    }
                })
                .flatMap(new Func1<InitUserInfo, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(InitUserInfo initUserInfo) {
                        return UserDaoImpl2.getInstance().updateFriendList(initUserInfo.friendList);
                    }
                });
    }


    public interface InviteMessageListener{
        void InviteMsgReceive();
    }

    public interface FriendMessageListener{
        void FriendMsgReceive();
    }

    public interface AllMsgListener{
        void  AllMsgReceive();
    }

    private InviteMessageListener inviteMsgLis;
    private FriendMessageListener friendMsgLis;
    private AllMsgListener allMsgLis;

    public void setInviteMsgLis(InviteMessageListener inviteMsgLis) {
        this.inviteMsgLis = inviteMsgLis;
    }

    public void setFriendMsgLis(FriendMessageListener friendMsgLis) {
        this.friendMsgLis = friendMsgLis;
    }

    public void setAllMsgLis(AllMsgListener allMsgLis) {
        this.allMsgLis = allMsgLis;
    }

    public Observable<Boolean> addContact(final String emname,final String content){
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    EMClient.getInstance().contactManager().addContact(emname, content);
                    subscriber.onNext(true);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    ApiException apiException = new ApiException(e, ERROR.EM_ADDCONNTACT_ERROR);
                    subscriber.onError(apiException);
                }
            }
        });

    };
}
