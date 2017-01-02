package com.wxxiaomi.ming.electricbicycle.improve.im.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
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
import com.wxxiaomi.ming.electricbicycle.bridge.easemob.provider.MySettingProvider;
import com.wxxiaomi.ming.electricbicycle.bridge.easemob.provider.MyUserProvider;
import com.wxxiaomi.ming.electricbicycle.improve.im.Contract;
import com.wxxiaomi.ming.electricbicycle.improve.im.DemoSe;
import com.wxxiaomi.ming.electricbicycle.ui.activity.ContactActivity;

import java.util.List;

import rx.Observable;


/**
 * Created by Mr.W on 2017/1/2.
 * E-maiil：122627018@qq.com
 * github：https://github.com/122627018
 * im的处理服务
 */

public class ImService extends Service implements Contract.IService{
    private final static String TAG = ImService.class.getName();
    private final static String ACTION_INIT = "action_init";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
//    public class LocalBinder extends Binder {
//        ImService getService() {
//            return ImService.this;
//        }
//    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static void startInitImModule(Context context){
        Intent intent = new Intent(context, ImService.class);
        intent.setAction(ACTION_INIT);
        context.startService(intent);
    }

    public static Observable<Boolean> Login(String username,String password){
        return Observable.create(new DemoSe(username,password));
    }

//    public static void startGetContact(doSome doSome){
//        try {
//            List<String> allContactsFromServer = EMClient.getInstance().contactManager().getAllContactsFromServer();
//            doSome.returnData(allContactsFromServer);
//        } catch (HyphenateException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void startLogin(final String username,final String password,final doSome doSome){
//        EMClient.getInstance().login(username, password,
//                new EMCallBack() {
//                    @Override
//                    public void onSuccess() {
//                        // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
//                        try {
//                            EMClient.getInstance().groupManager().loadAllGroups();
//                            EMClient.getInstance().chatManager()
//                                    .loadAllConversations();
//                            EMClient.getInstance().updateCurrentUserNick(
//                                    username);
////                            subscriber.onNext(true);
//                            doSome.returnData(true);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            ApiException ex = new ApiException(e, 333);
//                            ex.setDisplayMessage("em驱动器登录失败");
////                            subscriber.onError(ex);
//                        }
//                    }
//
//                    @Override
//                    public void onProgress(int progress, String status) {
//                        // Log.d(TAG, "login: onProgress");
//                    }
//
//                    @Override
//                    public void onError(final int code, final String message) {
//                        Log.i("wang", "登录em login: onError(错误): " + code + "---message="
//                                + message);
//                        ApiException apiException = new ApiException(new Exception(), code);
//                        apiException.setDisplayMessage(message);
////                        subscriber.onError(apiException);
//                    }
//                });
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return super.onStartCommand(null, flags, startId);
        }
        String action = intent.getAction();
        if (action != null) {
            handleAction(action, intent);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void handleAction(String action, Intent intent) {
        if(ACTION_INIT.equals(action)){
            init();
        }
    }
    private EaseUI easeUI;
    private void init() {
        Log.i("wang","eminit函数");
        try {
            EMOptions options = new EMOptions();
            // 默认添加好友时，是不需要验证的，改成需要验证
            options.setAcceptInvitationAlways(false);
            if (EaseUI.getInstance().init(getApplicationContext(), options)) {
//            appContext = context;
                easeUI = EaseUI.getInstance();
                easeUI.setSettingsProvider(new MySettingProvider());
                setEaseUIProviders();
                setGlobalListeners();
                Log.i("wang", "em初始化成功");
            }else{
                Log.i("wang", "em初始化失败");
            }
        }catch (Exception e){
            Log.i("wang", "em初始化失败");
            e.printStackTrace();
        }
    }

    private void setGlobalListeners() {
        EMConnectionListener connectionListener = new EMConnectionListener() {
            @Override
            public void onDisconnected(int error) {
                // 这里就是处理各种用户账号异常事件(比如异地登陆)
                if (error == EMError.USER_REMOVED) {
                    //账号被删除
//                    onUserException(EmConstant.ACCOUNT_REMOVED);
                } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    //账号他处登陆
//                    onUserException(EmConstant.ACCOUNT_CONFLICT);
                } else if (error == EMError.SERVER_SERVICE_RESTRICTED) {
                    //不知道是什么错误
//                    onUserException(EmConstant.ACCOUNT_FORBIDDEN);
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
//        registerGroupAndContactListener();
        registerMessageListener();
    }

    private void registerMessageListener() {
    }

    private void setEaseUIProviders() {
        easeUI.getInstance().setUserProfileProvider(new MyUserProvider());
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
                String ticker = EaseCommonUtils.getMessageDigest(message, getApplicationContext());
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
                Intent intent = new Intent(getApplicationContext(), ContactActivity.class);

                return intent;
            }
        });
    }

//    public interface doSome<T>{
//        void returnData(T data);
//    }
}
