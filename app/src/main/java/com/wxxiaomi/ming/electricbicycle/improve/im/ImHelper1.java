package com.wxxiaomi.ming.electricbicycle.improve.im;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.exceptions.HyphenateException;
import com.wxxiaomi.ming.electricbicycle.api.exp.ApiException;
import com.wxxiaomi.ming.electricbicycle.api.exp.ERROR;
import com.wxxiaomi.ming.electricbicycle.improve.im.operator.ContactAddOperator;
import com.wxxiaomi.ming.electricbicycle.improve.im.operator.InviteGetOperator;
import com.wxxiaomi.ming.electricbicycle.improve.im.provider.MySettingProvider;
import com.wxxiaomi.ming.electricbicycle.improve.im.provider.MyUserProvider;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.improve.im.notice.NoticeBean;
import com.wxxiaomi.ming.electricbicycle.ui.activity.ContactActivity;
import com.wxxiaomi.ming.electricbicycle.ui.activity.HomeActivity;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Mr.W on 2016/12/19.
 * E-maiil：122627018@qq.com
 * github：https://github.com/whaoming
 */
public class ImHelper1 implements Contract.IService {

    private static final String TAG = ImHelper1.class.getName();

    public static final String NOTICE_MESSAGE_RECEIVE = TAG+"_messgae_receive";
    public static final String EXTRA_BEAN = TAG+"_extra_notice";

    public static final String ACTION_INVITE = "action_invite";
    public static final String ACTION_INVITE_AGREE = "action_invite_agree";


    private ImHelper1() {
    }

    public static ImHelper1 INSTANCE;

    public static ImHelper1 getInstance() {
        if (INSTANCE == null) {
            synchronized (ImHelper1.class) {
                INSTANCE = new ImHelper1();
            }
        }
        return INSTANCE;
    }

    private Context appContext;
    private EaseUI easeUI;
    private EMConnectionListener connectionListener;
    private LocalBroadcastManager broadcastManager;
    private MyUserProvider myUserProvider;

    public void init(Context context) {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        easeUI = EaseUI.getInstance();
        try {
            Log.i("wang","环信初始化的线程："+Thread.currentThread().getName());
            if (easeUI.init(context, options)) {
                Log.i("wang","环信初始化成功");
                appContext = context;

//                EMClient.getInstance().lo
                easeUI.setSettingsProvider(new MySettingProvider());
                setEaseUIProviders();
                setGlobalListeners();
                broadcastManager = LocalBroadcastManager.getInstance(appContext);
                initDbDao();
            } else {
                Log.i("wang", "初始化环信失败");
            }
        }catch (Exception e){
            Log.i("wang", "初始化环信失败");
            e.printStackTrace();;
        }
    }

    public void setFriends(List<UserCommonInfo> usrs){
        myUserProvider.putUsers(usrs);
    }


    public boolean isMyFriend(String emname){
        return myUserProvider.getUser(emname)!=null;
    }

    private void setEaseUIProviders() {
        myUserProvider = new MyUserProvider();
        easeUI.getInstance().setUserProfileProvider(myUserProvider);
        easeUI.getNotifier().setNotificationInfoProvider(new EaseNotifier.EaseNotificationInfoProvider() {

            @Override
            public String getTitle(EMMessage message) {
                //you can update title here
                return "我是尼玛的标题";
            }

            @Override
            public int getSmallIcon(EMMessage message) {
                //you can update icon here
                return 0;
            }

            @Override
            public String getDisplayedText(EMMessage message) {
                // be used on notification bar, different text according the message type.
                String ticker = EaseCommonUtils.getMessageDigest(message, appContext);
                if(message.getType() == EMMessage.Type.TXT){
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                }
                EaseUser user = null;
                if(user != null){
                    if(EaseAtMessageHelper.get().isAtMeMsg(message)){
                        return String.format("asdsad", user.getNick());
                    }
                    return user.getNick() + ": " + ticker;
                }else{
                    if(EaseAtMessageHelper.get().isAtMeMsg(message)){
                        return String.format("asdsad", message.getFrom());
                    }
                    return message.getFrom() + ": " + ticker;
                }
            }

            @Override
            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
                // here you can customize the text.
                // return fromUsersNum + "contacts send " + messageNum + "messages to you";
                return null;
            }

            @Override
            public Intent getLaunchIntent(EMMessage message) {
                // you can set what activity you want display when user click the notification
//                Intent intent = new Intent(appContext, ChatActivity.class);
//                // open calling activity if there is call
//                if(isVideoCalling){
//                    intent = new Intent(appContext, VideoCallActivity.class);
//                }else if(isVoiceCalling){
//                    intent = new Intent(appContext, VoiceCallActivity.class);
//                }else{
//                    EMMessage.ChatType chatType = message.getChatType();
//                    if (chatType == EMMessage.ChatType.Chat) { // single chat message
//                        intent.putExtra("userId", message.getFrom());
//                        intent.putExtra("chatType", Constant.CHATTYPE_SINGLE);
//                    } else { // group chat message
//                        // message.getTo() is the group id
//                        intent.putExtra("userId", message.getTo());
//                        if(chatType == EMMessage.ChatType.GroupChat){
//                            intent.putExtra("chatType", Constant.CHATTYPE_GROUP);
//                        }else{
//                            intent.putExtra("chatType", Constant.CHATTYPE_CHATROOM);
//                        }
//
//                    }
//                }
                Intent intent = new Intent(appContext, ContactActivity.class);

                return intent;
            }
        });
    }

    private void initDbDao() {
    }

    /**
     * 设置关于Em的全局监听
     */
    private void setGlobalListeners() {
        //与服务器的连接相关的监听
        connectionListener = new EMConnectionListener() {
            @Override
            public void onDisconnected(int error) {
                // 这里就是处理各种用户账号异常事件(比如异地登陆)
                if (error == EMError.USER_REMOVED) {
                    //账号被删除
                    onUserException(EmConstant.ACCOUNT_REMOVED);
                } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    //账号他处登陆
                    onUserException(EmConstant.ACCOUNT_CONFLICT);
                } else if (error == EMError.SERVER_SERVICE_RESTRICTED) {
                    //不知道是什么错误
                    onUserException(EmConstant.ACCOUNT_FORBIDDEN);
                }
            }

            //这里处理例如联系人同步事件
            @Override
            public void onConnected() {

                // in case group and contact were already synced, we supposed to notify sdk we are ready to receive the events
//                if (isGroupsSyncedWithServer && isContactsSyncedWithServer) {
//                    EMLog.d(TAG, "group and contact already synced with servre");
//                } else {
//                    if (!isGroupsSyncedWithServer) {
//                        asyncFetchGroupsFromServer(null);
//                    }
//
//                    if (!isContactsSyncedWithServer) {
//                        asyncFetchContactsFromServer(null);
//                    }
//
//                    if (!isBlackListSyncedWithServer) {
//                        asyncFetchBlackListFromServer(null);
//                    }
//                }
            }
        };
        EMClient.getInstance().addConnectionListener(connectionListener);
        //还有别人视频你的这种类似的通知都在这里注册
        //通知栏的消息通知也是在这里注册，这里先省略
        registerGroupAndContactListener();
        registerMessageListener();
    }

    /**
     * 发生与账号有关的错误
     */
    private void onUserException(String exception) {
        Log.i("wang","onUserException,exception:"+exception);
        Intent intent = new Intent(appContext, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(exception, true);
        appContext.startActivity(intent);
    }

    private void registerMessageListener() {
        EMClient.getInstance().chatManager()
                .addMessageListener(new EMMessageListener() {
                    @Override
                    public void onMessageReceived(List<EMMessage> list) {
                        for (EMMessage message : list) {
                            getNotifier().onNewMsg(message);
                        }
                        notifyMsgListener();
                    }

                    @Override
                    public void onCmdMessageReceived(List<EMMessage> list) {
//                        notifyMsgListener();
                        for(EMMessage msg : list){
                            Log.i("wang","透传消息："+msg.getBody().toString());
                        }
                    }

                    @Override
                    public void onMessageReadAckReceived(List<EMMessage> list) {
                        notifyMsgListener();
                    }

                    @Override
                    public void onMessageDeliveryAckReceived(List<EMMessage> list) {
                        notifyMsgListener();
                    }

                    @Override
                    public void onMessageChanged(EMMessage emMessage, Object o) {
                        notifyMsgListener();
                    }
                });
    }

    private EaseNotifier getNotifier() {
        return easeUI.getNotifier();
    }

    private void notifyMsgListener() {
        try {
            Intent intent = new Intent(NOTICE_MESSAGE_RECEIVE);
            NoticeBean n = new NoticeBean();
            n.setMessage(1);
            NoticeBean notice = NoticeBean.getInstance(appContext)
                    .add(n)
                    .save(appContext);
            intent.putExtra(EXTRA_BEAN, notice);
            broadcastManager.sendBroadcast(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 注册一些跟联系人有关的监听
     */
    public void registerGroupAndContactListener() {
        EMClient.getInstance().contactManager().setContactListener(new MyContactListener());
    }


    /**
     * 登录em服务器
     *
     * @param username
     * @param password
     * @return
     */
    public Observable<Boolean> LoginFromEm(final String username, final String password) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber) {
                EMClient.getInstance().login(username, password,
                        new EMCallBack() {
                            @Override
                            public void onSuccess() {
                                // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
                                try {
                                    EMClient.getInstance().groupManager().loadAllGroups();
                                    EMClient.getInstance().chatManager()
                                            .loadAllConversations();
                                    EMClient.getInstance().updateCurrentUserNick(
                                            username);
                                    subscriber.onNext(true);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    ApiException ex = new ApiException(e, 333);
                                    ex.setDisplayMessage("em驱动器登录失败");
                                    subscriber.onError(ex);
                                }
                            }

                            @Override
                            public void onProgress(int progress, String status) {
                                // Log.d(TAG, "login: onProgress");
                            }

                            @Override
                            public void onError(final int code, final String message) {
                                Log.i("wang", "登录em login: onError(错误): " + code + "---message="
                                        + message);
                                ApiException apiException = new ApiException(new Exception(), code);
                                apiException.setDisplayMessage(message);
                                subscriber.onError(apiException);
                            }
                        });
            }
        });
    }

    public EaseUI.EaseUserProfileProvider getEmUserProvider(){
        return easeUI.getUserProfileProvider();
    }

    /**
     * 发起好友申请
     *
     * @param emname
     * @param content
     * @return
     */
    public Observable<Boolean> addContact(final String emname, final String content) {
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
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                ;

    }

    /**
     * 从em服务器拉取好友列表
     *
     * @return
     */
    public Observable<List<String>> getContactFromEm() {
        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                try {
                    List<String> allContactsFromServer = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    Log.i("wang", "从em服务器获取的好友个数:" + allContactsFromServer.size());
                    subscriber.onNext(allContactsFromServer);
                } catch (Exception e) {
                    ApiException apiEx = new ApiException(e, ERROR.EM_GETCONTACT_ERROR);
                    apiEx.setDisplayMessage("获取好友失败");
                    subscriber.onError(apiEx);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }

    /**
     * 同意好友申请
     *
     * @param emname
     * @return
     */
    public Observable<Boolean> agreeInvite(final String emname) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    EMClient.getInstance().contactManager().acceptInvitation(emname);
//                    UserFunctionProvider.getInstance().getUserInfoByEname(emname)
//                            .subscribe(new Action1<UserCommonInfo>() {
//                                @Override
//                                public void call(UserCommonInfo userCommonInfo) {
//                                    Log.i("wang", "EmEngine->agreeInvite->userCommonInfo：" + userCommonInfo);
//                                }
//                            });
                    //还要将此好友添加到数据库中
                    // 但是发送好友请求的时候不是应该就已经存在本地了吗
                    //此时又有一个问题了，那不是应该当你点进去它的那个userInfo页面就已经获取了它的信息了吗
                    subscriber.onNext(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    ApiException apiException = new ApiException(e, ERROR.EM_AGREEINVITE_ERROR);
                    apiException.setDisplayMessage("同意好友申请失败");
                    subscriber.onError(apiException);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public void logout(){
        EMClient.getInstance().logout(true);
    }

    /**
     * 当联系人发生变动的时候这里会发生回调
     * 所以这里需要做一些事件
     * 类似于说当有先联系人添加的时候，需要发送到服务器取得他的信息
     * 最后发送广播
     */
    public class MyContactListener implements EMContactListener {

        @Override
        public void onContactAdded(String username) {
            ContactAddOperator operator = new ContactAddOperator(username,ImHelper1.this,0);
            operator.run();
        }

        @Override
        public void onContactDeleted(String username) {
        }

        @Override
        public void onContactInvited(String username, String reason) {
//            Log.i("wang","收到好友邀请");
            InviteGetOperator operator = new InviteGetOperator(username,reason, ImHelper1.this,0);
            operator.run();
        }

        @Override
        public void onContactAgreed(String username) {
        }

        @Override
        public void onContactRefused(String username) {
        }
    }

    @Override
    public void notifyMsg(String action) {
        if(ACTION_INVITE.equals(action)){
            getNotifier().vibrateAndPlayTone(null);
//            broadcastManager.sendBroadcast(new Intent(EmConstant.ACTION_CONTACT_CHANAGED));
            Intent intent = new Intent(NOTICE_MESSAGE_RECEIVE);
            NoticeBean n = new NoticeBean();
            n.setInvite(1);
            NoticeBean notice = NoticeBean.getInstance(appContext)
                    .add(n)
                    .save(appContext);
            intent.putExtra(EXTRA_BEAN, notice);
            broadcastManager.sendBroadcast(intent);
        }else if(ACTION_INVITE_AGREE.equals(action)){
            //同意添加某好友
            broadcastManager.sendBroadcast(new Intent(EmConstant.ACTION_CONTACT_CHANAGED));
        }
    }

}
