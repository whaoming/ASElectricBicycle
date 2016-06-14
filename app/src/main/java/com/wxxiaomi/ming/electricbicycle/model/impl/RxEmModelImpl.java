package com.wxxiaomi.ming.electricbicycle.model.impl;

import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.wxxiaomi.ming.electricbicycle.api.exception.ApiException;
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
import rx.functions.Func1;
import rx.functions.Func3;

/**
 * Created by 12262 on 2016/6/6.
 * em
 */
public class RxEmModelImpl {
    private RxEmModelImpl() {
        EmDaoImpl.getInstance().init();
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final RxEmModelImpl INSTANCE = new RxEmModelImpl();
    }

    //获取单例
    public static RxEmModelImpl getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Observable<Integer> handleEMListener() {
        Observable<EmEvent<String>> emEventObservable = EmDaoImpl.getInstance().addEmContactObserver();
        Observable<EmEvent<List<EMMessage>>> emEventObservable1 = EmDaoImpl.getInstance().addMessageListener();
        return Observable.merge(emEventObservable,emEventObservable1)
                .flatMap(new Func1<EmEvent<? extends Object>, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(EmEvent<? extends Object> emEvent) {
//                        Log.i("wang","handleEMListener()");
                        return handleEmEvent(emEvent);
                    }
                });
    }

    public Observable<Integer> handleEmEvent(final EmEvent<? extends Object> emEvent){
        Log.i("wang","handleEmEvent，type："+emEvent.getType());
        switch (emEvent.getType()){
            case EmEvent.ONCONNTACTAGREE:
                //好友请求被同意
                //1.先从服务器获取用户公共信息
                //2.然后存到本地数据库
                Observable<Integer> objectObservable1 = UserDaoImpl2.getInstance().getUserCommonInfoByEmname(emEvent.getUsername())
                        .flatMap(new Func1<InitUserInfo, Observable<Integer>>() {
                            @Override
                            public Observable<Integer> call(InitUserInfo userCommonInfo) {
                                List<User.UserCommonInfo> list = new ArrayList<User.UserCommonInfo>();
                                list.add(userCommonInfo.friendList.get(0));
                                return UserDaoImpl2.getInstance().updateFriendList(list);
                            }
                        });
                return objectObservable1;
            case EmEvent.ONCONTACTREFUSED:
                //好友请求被拒绝
                break;
            case EmEvent.ONCONTACTINVITED:
                // 收到好友邀请
                Log.i("wang","EmDaoImpl.EmEvent.ONCONTACTINVITED:");
                final InviteMessage msg = new InviteMessage();
                msg.setFrom(emEvent.getUsername());
                msg.setTime(System.currentTimeMillis());
                msg.setReason((String)emEvent.getReason());
                //存储信息
                Observable<Integer> integerObservable = InviteMessgeDaoImpl2.getInstance().saveMessage(msg);
                //增加未读消息数量
                Observable<Integer> objectObservable2 = InviteMessgeDaoImpl2.getInstance().getUnreadNotifyCount()
                        .flatMap(new Func1<Integer, Observable<Integer>>() {
                            @Override
                            public Observable<Integer> call(Integer integer) {
                                return InviteMessgeDaoImpl2.getInstance().saveUnreadMessageCount(integer+1);
                            }
                        });
                //从服务器获取用户公共信息并存到本地服务器
                final Observable<Integer> objectObservable = UserDaoImpl2.getInstance().getUserCommonInfoByEmname(emEvent.getUsername())
                        .flatMap(new Func1<InitUserInfo, Observable<Integer>>() {
                            @Override
                            public Observable<Integer> call(InitUserInfo initUserInfo) {
                                return UserDaoImpl2.getInstance().savaPerson(initUserInfo.friendList.get(0));
                            }
                        });
                Observable<Integer> integerObservable1 = UserDaoImpl2.getInstance().isTempUserExist(emEvent.getUsername())
                        .flatMap(new Func1<Boolean, Observable<Integer>>() {
                            @Override
                            public Observable<Integer> call(Boolean aBoolean) {
                                if (aBoolean) {
                                    return Observable.just(1);
                                } else {
                                    return objectObservable;
                                }
                            }
                        });


                Observable<Integer> merge = Observable.zip(integerObservable, integerObservable1, objectObservable2, new Func3<Integer, Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer, Integer integer2, Integer integer3) {
                        return 1;
                    }
                });
                return merge;
            case EmEvent.ONCONTACTDELETED:
                break;
            case EmEvent.ONCONTACTADDED:
                break;
            case EmEvent.onMessageReceived:
                Log.i("wang","case EmDaoImpl.EmEvent.onMessageReceived");
                //有多少条直接回复数字就行
                return Observable.create(new Observable.OnSubscribe<Integer>() {

                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        List<EMMessage> msgs = (List<EMMessage>) emEvent.getReason();
                        subscriber.onNext(0);
                    }
                });
            case EmEvent.onCmdMessageReceived:
                break;
            case EmEvent.onMessageReadAckReceived:
                break;
            case EmEvent.onMessageDeliveryAckReceived:
                break;
            case EmEvent.onMessageChanged:
                break;
        }
        return Observable.just(0);
    }

    public Observable<Integer> getUnreadInviteCountTotal() {
        return InviteMessgeDaoImpl2.getInstance().getUnreadNotifyCount();
    }

    public Observable<Integer> getUnreadMsgCount(){
       return getUnreadInviteCountTotal()
                .flatMap(new Func1<Integer, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Integer count) {
                        int unreadMsgsCount = EMClient.getInstance().chatManager().getUnreadMsgsCount();
                        return Observable.just(unreadMsgsCount+count);
                    }
                });
    }

    public void logout(){
        EmManager.getInstance().logout();
    }

    public Observable<Integer> agreeInvite(final User.UserCommonInfo userInfo){
        return EmDaoImpl.getInstance().agreeInvite(userInfo.emname)
                .flatMap(new Func1<Boolean, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Boolean aBoolean) {
                        List<User.UserCommonInfo> list  = new ArrayList<User.UserCommonInfo>();
                        list.add(userInfo);
                       return  UserDaoImpl2.getInstance().updateFriendList(list);
                    }
                });
    }



}
