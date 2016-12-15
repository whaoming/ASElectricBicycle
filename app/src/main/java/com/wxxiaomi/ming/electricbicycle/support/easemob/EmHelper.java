//package com.wxxiaomi.ming.electricbicycle.support.easemob;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//
//import com.hyphenate.EMConnectionListener;
//import com.hyphenate.EMContactListener;
//import com.hyphenate.EMError;
//import com.hyphenate.chat.EMClient;
//import com.hyphenate.chat.EMOptions;
//import com.hyphenate.easeui.controller.EaseUI;
//import com.wxxiaomi.ming.electricbicycle.dao.bean.InviteMessage;
//import com.wxxiaomi.ming.electricbicycle.dao.db.InviteMessgeDao;
//import com.wxxiaomi.ming.electricbicycle.dao.db.impl.InviteMessgeDaoImpl;
//import com.wxxiaomi.ming.electricbicycle.dao.db.impl.InviteMessgeDaoImpl2;
//import com.wxxiaomi.ming.electricbicycle.support.easemob.provider.MyUserProvider;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by Administrator on 2016/12/14.
// */
//
//public class EmHelper {
//
//
//    private InviteMessgeDao inviteMessgeDao;
//
//    private EmHelper(){};
//    public static EmHelper INSTANCE;
//    public static EmHelper getInstance(){
//        if(INSTANCE==null){
//            synchronized (EmHelper.class){
//                INSTANCE = new EmHelper();
//            }
//        }
//        return INSTANCE;
//    }
//
//    private Context appContext;
//    private EaseUI easeUI;
//    private EMConnectionListener connectionListener;
//    public void init(Context context) {
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
//        inviteMessgeDao = new InviteMessgeDaoImpl2(appContext);
//    }
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
//    public List<InviteMessage> getInviteMsgList(){
//        return inviteMessgeDao.getMessagesList();
//    }
//
//    /**
//     * 获取未读的消息总数(消息数+好友邀请数)
//     * @return
//     */
//    public int getAllUnreadCount(){
//        return inviteMessgeDao.getUnreadNotifyCount()
//                +EMClient.getInstance().chatManager().getUnreadMsgsCount();
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
//}
