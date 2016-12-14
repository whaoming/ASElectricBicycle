//package com.wxxiaomi.ming.electricbicycle.support.easemob;
//
//import android.content.Context;
//import android.util.Log;
//
//import com.hyphenate.EMCallBack;
//import com.hyphenate.EMConnectionListener;
//import com.hyphenate.EMContactListener;
//import com.hyphenate.EMError;
//import com.hyphenate.EMMessageListener;
//import com.hyphenate.chat.EMClient;
//import com.hyphenate.chat.EMMessage;
//import com.hyphenate.chat.EMOptions;
//import com.hyphenate.easeui.controller.EaseUI;
//import com.hyphenate.exceptions.HyphenateException;
//import com.wxxiaomi.ming.electricbicycle.api.exception.ApiException;
//import com.wxxiaomi.ming.electricbicycle.api.exception.ERROR;
//import com.wxxiaomi.ming.electricbicycle.dao.bean.InviteMessage;
//import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo;
//import com.wxxiaomi.ming.electricbicycle.dao.db.UserService;
//import com.wxxiaomi.ming.electricbicycle.dao.db.impl.InviteMessgeDaoImpl;
//import com.wxxiaomi.ming.electricbicycle.dao.db.impl.InviteMessgeDaoImpl2;
//import com.wxxiaomi.ming.electricbicycle.support.easemob.listener.AllMsgListener;
//import com.wxxiaomi.ming.electricbicycle.support.easemob.listener.FriendMessageListener;
//import com.wxxiaomi.ming.electricbicycle.support.easemob.listener.InviteMessageListener;
//import com.wxxiaomi.ming.electricbicycle.support.easemob.ui.MyUserProvider;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import rx.Observable;
//import rx.Subscriber;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.functions.Action1;
//import rx.functions.Func1;
//import rx.functions.Func3;
//import rx.schedulers.Schedulers;
//
///**
// * Created by 12262 on 2016/6/6.
// * 总共需要接口：
// * 1.消息通知接口
// * 2.好友信息通知接口
// * 3.总的消息数通知接口(不需要数量)
// */
//public class EmEngine2 {
//
//
//
//
//
//    private EmEngine2() {
//    }
//    //在访问HttpMethods时创建单例
//    private static class SingletonHolder {
//        private static final EmEngine2 INSTANCE = new EmEngine2();
//    }
//    //获取单例
//    public static EmEngine2 getInstance() {
//        return SingletonHolder.INSTANCE;
//    }
//
//    private Context appContext;
//    private EaseUI easeUI;
//    private EMConnectionListener connectionListener;
//
//    /**
//     *初始化聊天系统
//     */
//    public void init(Context context) {
////
//        EMOptions options = new EMOptions();
//        // 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(false);
//        if (EaseUI.getInstance().init(context, options)) {
//            appContext = context;
//            easeUI = EaseUI.getInstance();
//            setEaseUIProviders();
//            setGlobalListeners();
//            initDbDao();
//        }
//    }
//
//    private void setEaseUIProviders() {
//        easeUI.getInstance().setUserProfileProvider(new MyUserProvider());
//    }
//
//    private void initDbDao() {
////        inviteMessgeDao = new InviteMessgeDaoImpl2(appContext);
//    }
//
//
//    /**
//     * 设置关于Em的全局监听
//     */
//    private void setGlobalListeners() {
//        //与服务器的连接相关的监听
//        connectionListener = new EMConnectionListener() {
//            @Override
//            public void onDisconnected(int error) {
////                EMLog.d("global listener", "onDisconnect" + error);
//                // 这里就是处理各种用户账号异常事件(比如异地登陆)
//                if (error == EMError.USER_REMOVED) {
////                    onUserException(Constant.ACCOUNT_REMOVED);
//                } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
////                    onUserException(Constant.ACCOUNT_CONFLICT);
//                } else if (error == EMError.SERVER_SERVICE_RESTRICTED) {
////                    onUserException(Constant.ACCOUNT_FORBIDDEN);
//                }
//            }
//
//            //这里处理例如联系人同步事件
//            @Override
//            public void onConnected() {
//
//                // in case group and contact were already synced, we supposed to notify sdk we are ready to receive the events
////                if (isGroupsSyncedWithServer && isContactsSyncedWithServer) {
////                    EMLog.d(TAG, "group and contact already synced with servre");
////                } else {
////                    if (!isGroupsSyncedWithServer) {
////                        asyncFetchGroupsFromServer(null);
////                    }
////
////                    if (!isContactsSyncedWithServer) {
////                        asyncFetchContactsFromServer(null);
////                    }
////
////                    if (!isBlackListSyncedWithServer) {
////                        asyncFetchBlackListFromServer(null);
////                    }
////                }
//            }
//        };
//        EMClient.getInstance().addConnectionListener(connectionListener);
//        //还有别人视频你的这种类似的通知都在这里注册
//        //通知栏的消息通知也是在这里注册，这里先省略
//        registerGroupAndContactListener();
//    }
//
//    /**
//     * 注册一些跟联系人有关的监听
//     */
//    public void registerGroupAndContactListener() {
//        EMClient.getInstance().contactManager().setContactListener(new MyContactListener());
//    }
//
//    /**
//     * 处理好友消息和邀请消息俩个接口的回调具体事件处理
//     * @param emEvent
//     */
//    public void handleEmEvent(final EmEvent<? extends Object> emEvent) {
//        switch (emEvent.getType()) {
//            case EmEvent.ONCONNTACTAGREE:
//                Log.i("wang","好友请求被同意");
//                //好友请求被同意
//                //1.先从服务器获取用户公共信息
//                //2.然后存到本地数据库
//                checkTempUser(emEvent.getUsername())
//                        .flatMap(new Func1<UserCommonInfo, Observable<Integer>>() {
//                            @Override
//                            public Observable<Integer> call(UserCommonInfo userCommonInfo) {
//                                List<UserCommonInfo> list = new ArrayList<>();
//                                list.add(userCommonInfo);
//                                return UserService.getInstance().InsertFriendList(list);
//                            }
//                        })
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//
//                    }
//                })
//                ;
//            case EmEvent.ONCONTACTREFUSED:
//                //好友请求被拒绝
//                break;
//            case EmEvent.ONCONTACTINVITED:
//                // 收到好友邀请
//                Log.i("wang", "EmEvent.ONCONTACTINVITED:");
//                final InviteMessage msg = new InviteMessage();
//                msg.setFrom(emEvent.getUsername());
//                msg.setTime(System.currentTimeMillis());
//                msg.setReason((String) emEvent.getReason());
//                //存储信息
//                Observable<Integer> integerObservable = InviteMessgeDaoImpl.getInstance().saveMessage(msg);
//                //增加未读消息数量
//                Observable<Integer> objectObservable2 = InviteMessgeDaoImpl.getInstance().getUnreadNotifyCount()
//                        .flatMap(new Func1<Integer, Observable<Integer>>() {
//                            @Override
//                            public Observable<Integer> call(Integer integer) {
//                                return InviteMessgeDaoImpl.getInstance().saveUnreadMessageCount(integer + 1);
//                            }
//                        });
//                //从服务器获取用户公共信息并存到本地服务器
//                Observable<UserCommonInfo> userCommonInfoObservable = UserService.getInstance().getUserInfoByEname((emEvent.getUsername()));
//
//                Observable.zip(integerObservable, userCommonInfoObservable, objectObservable2, new Func3<Integer, UserCommonInfo, Integer, UserCommonInfo>() {
//
//                    @Override
//                    public UserCommonInfo call(Integer integer, UserCommonInfo userCommonInfo, Integer integer2) {
//                        return userCommonInfo;
//                    }
//                })
//                        .subscribeOn(Schedulers.io())
//                        .unsubscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<UserCommonInfo>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                        Log.i("wang","在处理收到好友消息的过程中出错了");
//                    }
//
//                    @Override
//                    public void onNext(UserCommonInfo userCommonInfo) {
//                        if(inviteMsgLis != null){
//                            inviteMsgLis.InviteMsgReceive();
//                        }
//                        if(allMsgLis != null){
//                            allMsgLis.AllMsgReceive();
//                        }
//                    }
//                })
//                ;
//            case EmEvent.ONCONTACTDELETED:
//                break;
//            case EmEvent.ONCONTACTADDED:
//                break;
//            case EmEvent.onMessageReceived:
////                Log.i("wang", "case EmDaoImpl.EmEvent.onMessageReceived");
//                //有多少条直接回复数字就行
//                if(friendMsgLis != null){
//                    friendMsgLis.FriendMsgReceive();
//                }
//                if(allMsgLis != null){
//                    allMsgLis.AllMsgReceive();
//                }
//            case EmEvent.onCmdMessageReceived:
//                break;
//            case EmEvent.onMessageReadAckReceived:
//                break;
//            case EmEvent.onMessageDeliveryAckReceived:
//                break;
//            case EmEvent.onMessageChanged:
//                break;
//        }
//    }
//
//    /**
//     * 获取未读的邀请消息总数
//     * @return
//     */
//    public Observable<Integer> getUnreadInviteCountTotal() {
//        return InviteMessgeDaoImpl.getInstance().getUnreadNotifyCount();
//    }
//
//    /**
//     * 获取未读的邀请消息和未读的好友消息总数
//     * @return
//     */
//    public Observable<Integer> getUnreadMsgCount() {
//        return getUnreadInviteCountTotal()
//                .flatMap(new Func1<Integer, Observable<Integer>>() {
//                    @Override
//                    public Observable<Integer> call(Integer count) {
//                        int unreadMsgsCount = EMClient.getInstance().chatManager().getUnreadMsgsCount();
//                        return Observable.just(unreadMsgsCount + count);
//                    }
//                });
//    }
//
//    /**
//     * 登出
//     */
//    public void logout() {
//        EMClient.getInstance().logout(true);
//    }
//
//    /**
//     * 判断本地是否有临时用户，有就返回，没有就从服务器取
//     * 取了之后返回
//     * @param emname
//     * @return
//     */
//    public Observable<UserCommonInfo> checkTempUser(final String emname) {
//        return UserService.getInstance().getUserInfoByEname(emname);
//    }
//
//    /**
//     * 登录em服务器
//     * @param username
//     * @param password
//     * @return
//     */
//    public Observable<Boolean> LoginFromEm(final String username, final String password) {
//            return Observable.create(new Observable.OnSubscribe<Boolean>() {
//                @Override
//                public void call(final Subscriber<? super Boolean> subscriber) {
//                    EMClient.getInstance().login(username, password,
//                            new EMCallBack() {
//                                @Override
//                                public void onSuccess() {
//                                    // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
//                                    try {
//                                        EMClient.getInstance().groupManager().loadAllGroups();
//                                        EMClient.getInstance().chatManager()
//                                                .loadAllConversations();
//                                        Log.i("wang-em", "登陆em服务器成功");
//                                        EMClient.getInstance().updateCurrentUserNick(
//                                                username);
//                                        subscriber.onNext(true);
//                                    }catch (Exception e){
//                                        e.printStackTrace();
//                                        ApiException ex = new ApiException(e, 333);
//                                        ex.setDisplayMessage("em驱动器登录失败");
//                                        subscriber.onError(ex);
//                                    }
//                                }
//
//                                @Override
//                                public void onProgress(int progress, String status) {
//                                    // Log.d(TAG, "login: onProgress");
//                                }
//
//                                @Override
//                                public void onError(final int code, final String message) {
//                                    Log.i("wang", "登录em login: onError(错误): " + code + "---message="
//                                            + message);
//                                    ApiException apiException = new ApiException(new Exception(), code);
//                                    apiException.setDisplayMessage(message);
//                                    subscriber.onError(apiException);
//                                }
//                            });
//                }
//            });
//    }
//
//    /**
//     * 更新好友列表：
//     * 1.先从Em服务器获取好友列表
//     * 2.核对数据库，多的删除->UserDaoImpl2.getInstance().checkFriendList(usernames);
//     * 3.缺少的从服务器获取->调用UserDaoImpl2.getInstance().getUserListFromByEmList(missingUsernames);
//     * 4.存到数据库->UserDaoImpl2.getInstance().updateFriendList(initUserInfo.friendList);
//     * @return
//     */
////    public Observable<Integer> updateFriend() {
////        return getContactFromEm()
////                //核对数据库好友列表，多的删除，少的数据库获取
////                .flatMap(new Func1<List<String>, Observable<Integer>>() {
////                    @Override
////                    public Observable<Integer> call(List<String> usernames) {
////                        return UserService.getInstance().UpdateFriendList(usernames);
////                    }
////                });
//////
////    }
//
////    /**
////     * 设置邀请类的消息回调接口
////     * @param inviteMsgLis
////     */
////    public void setInviteMsgLis(InviteMessageListener inviteMsgLis) {
////        this.inviteMsgLis = inviteMsgLis;
////    }
////
////    /**
////     * 设置好友相关的消息回调接口
////     * @param friendMsgLis
////     */
////    public void setFriendMsgLis(FriendMessageListener friendMsgLis) {
////        this.friendMsgLis = friendMsgLis;
////    }
//
//    /**
//     * 设置所有类型的消息监听回调接口
//     * @param allMsgLis
//     */
//
////    public void setAllMsgLis(AllMsgListener allMsgLis) {
////        this.allMsgLis = allMsgLis;
////    }
//
//
//    /**
//     * 发起好友申请
//     * @param emname
//     * @param content
//     * @return
//     */
//    public Observable<Boolean> addContact(final String emname,final String content){
//        return Observable.create(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//                try {
//                    EMClient.getInstance().contactManager().addContact(emname, content);
//                    subscriber.onNext(true);
//                } catch (HyphenateException e) {
//                    e.printStackTrace();
//                    ApiException apiException = new ApiException(e, ERROR.EM_ADDCONNTACT_ERROR);
//                    subscriber.onError(apiException);
//                }
//            }
//        });
//
//    };
//    /**
//     * 从em服务器拉取好友列表
//     * @return
//     */
//    public Observable<List<String>> getContactFromEm() {
//        return Observable.create(new Observable.OnSubscribe<List<String>>() {
//            @Override
//            public void call(Subscriber<? super List<String>> subscriber) {
//                try {
//                    List<String> allContactsFromServer = EMClient.getInstance().contactManager().getAllContactsFromServer();
//                    Log.i("wang", "从em服务器获取的好友个数:"+allContactsFromServer.size());
//                    subscriber.onNext(allContactsFromServer);
//                } catch (Exception e) {
//                    ApiException apiEx = new ApiException(e, ERROR.EM_GETCONTACT_ERROR);
//                    apiEx.setDisplayMessage("获取好友失败");
//                    subscriber.onError(apiEx);
//                }
//            }
//        });
//    }
//
//    /**
//     * 同意好友申请
//     * @param emname
//     * @return
//     */
//    public Observable<Boolean> agreeInvite(final String emname){
//        return Observable.create(new Observable.OnSubscribe<Boolean>() {
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//                try{
//                    Log.i("wang","emengine同意好友申请,emname:"+emname);
//                    EMClient.getInstance().contactManager().acceptInvitation(emname);
//                    UserService.getInstance().getUserInfoByEname(emname)
//                            .subscribe(new Action1<UserCommonInfo>() {
//                                @Override
//                                public void call(UserCommonInfo userCommonInfo) {
//                                    Log.i("wang","EmEngine->agreeInvite->userCommonInfo："+userCommonInfo);
//                                }
//                            });
//                    //还要将此好友添加到数据库中
//                    // 但是发送好友请求的时候不是应该就已经存在本地了吗
//                    //此时又有一个问题了，那不是应该当你点进去它的那个userInfo页面就已经获取了它的信息了吗
//                    subscriber.onNext(true);
//                }catch (Exception e){
//                    e.printStackTrace();
//                    ApiException apiException = new ApiException(e, ERROR.EM_AGREEINVITE_ERROR);
//                    apiException.setDisplayMessage("同意好友申请失败");
//                    subscriber.onError(apiException);
//                }
//            }
//        })
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }
//
//
//    /**
//     * 获取全部邀请消息
//     * @return
//     */
//    public List<InviteMessage> getInviteMsgList(){
//        return InviteMessgeDaoImpl.getInstance().getMessagesList();
//    }
//
//    /**
//     * 当联系人发生变动的时候这里会发生回调
//     * 所以这里需要做一些事件
//     * 类似于说当有先联系人添加的时候，需要发送到服务器取得他的信息
//     * 最后发送广播
//     */
//    public class MyContactListener implements EMContactListener {
//
//        @Override
//        public void onContactAdded(String username) {
//            // save contact
////            Map<String, EaseUser> localUsers = getContactList();
////            Map<String, EaseUser> toAddUsers = new HashMap<String, EaseUser>();
////            EaseUser user = new EaseUser(username);
//
////            if (!localUsers.containsKey(username)) {
//////                userDao.saveContact(user);
////            }
////            toAddUsers.put(username, user);
////            localUsers.putAll(toAddUsers);
//
////            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_CONTACT_CHANAGED));
//        }
//
//        @Override
//        public void onContactDeleted(String username) {
////            Map<String, EaseUser> localUsers = DemoHelper.getInstance().getContactList();
////            localUsers.remove(username);
////            userDao.deleteContact(username);
////            inviteMessgeDao.deleteMessage(username);
////
////            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_CONTACT_CHANAGED));
//        }
//
//        @Override
//        public void onContactInvited(String username, String reason) {
////            List<InviteMessage> msgs = inviteMessgeDao.getMessagesList();
////
////            for (InviteMessage inviteMessage : msgs) {
////                if (inviteMessage.getGroupId() == null && inviteMessage.getFrom().equals(username)) {
////                    inviteMessgeDao.deleteMessage(username);
////                }
////            }
////            // save invitation as message
////            InviteMessage msg = new InviteMessage();
////            msg.setFrom(username);
////            msg.setTime(System.currentTimeMillis());
////            msg.setReason(reason);
////            Log.d(TAG, username + "apply to be your friend,reason: " + reason);
////            // set invitation status
////            msg.setStatus(InviteMesageStatus.BEINVITEED);
////            notifyNewInviteMessage(msg);
////            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_CONTACT_CHANAGED));
//        }
//
//        @Override
//        public void onContactAgreed(String username) {
////            List<InviteMessage> msgs = inviteMessgeDao.getMessagesList();
////            for (InviteMessage inviteMessage : msgs) {
////                if (inviteMessage.getFrom().equals(username)) {
////                    return;
////                }
////            }
////            // save invitation as message
////            InviteMessage msg = new InviteMessage();
////            msg.setFrom(username);
////            msg.setTime(System.currentTimeMillis());
////            Log.d(TAG, username + "accept your request");
////            msg.setStatus(InviteMesageStatus.BEAGREED);
////            notifyNewInviteMessage(msg);
////            broadcastManager.sendBroadcast(new Intent(Constant.ACTION_CONTACT_CHANAGED));
//        }
//
//        @Override
//        public void onContactRefused(String username) {
//            // your request was refused
//            Log.d(username, username + " refused to your request");
//        }
//    }
//
//}
