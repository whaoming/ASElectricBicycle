//package com.wxxiaomi.ming.electricbicycle.core.weight.em;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.util.Log;
//
//
//import com.hyphenate.EMConnectionListener;
//import com.hyphenate.EMContactListener;
//import com.hyphenate.EMError;
//import com.hyphenate.EMMessageListener;
//import com.hyphenate.chat.EMClient;
//import com.hyphenate.chat.EMMessage;
//import com.hyphenate.exceptions.HyphenateException;
//import com.wxxiaomi.ming.electricbicycle.EBApplication;
//import com.wxxiaomi.ming.electricbicycle.api.exception.ApiException;
//import com.wxxiaomi.ming.electricbicycle.bean.InviteMessage;
//import com.wxxiaomi.ming.electricbicycle.bean.User;
//import com.wxxiaomi.ming.electricbicycle.bean.UserCommonInfo;
//import com.wxxiaomi.ming.electricbicycle.bean.format.InitUserInfo;
//import com.wxxiaomi.ming.electricbicycle.bean.format.common.ReceiceData;
//import com.wxxiaomi.ming.electricbicycle.dao.impl.InviteMessgeDaoImpl;
//import com.wxxiaomi.ming.electricbicycle.dao.impl.TempUserDaoImpl;
//import com.wxxiaomi.ming.electricbicycle.dao.impl.UserDaoImpl;
//import com.wxxiaomi.ming.electricbicycle.core.weight.em.EmInterface.FriendReqDetailListener;
//import com.wxxiaomi.ming.electricbicycle.core.weight.em.EmInterface.FriendReqListener;
//import com.wxxiaomi.ming.electricbicycle.core.weight.em.EmInterface.MsgGetListener;
//import com.wxxiaomi.ming.electricbicycle.core.weight.em.EmInterface.RemoteLoginListener;
//import com.wxxiaomi.ming.electricbicycle.dao.impl.UserDaoImpl2;
//import com.wxxiaomi.ming.electricbicycle.support.rx.MyObserver;
//
//
//public class EmManager {
//
//	private static EmManager instance = null;
//
//	public static EmManager getInstance() {
//		if (instance == null) {
//			synchronized (EmManager.class) {
//				if (instance == null) {
//					instance = new EmManager();
//				}
//			}
//		}
//		return instance;
//	}
//
//	static EMMessageListener messageListener;
//
//	// listener
//	private static FriendReqDetailListener friendReqDetailListener;
//	private static FriendReqListener friendReqListener;
//	private static RemoteLoginListener remoteLoginListener;
//	private static MsgGetListener msgGetListener;
//	private InviteMessgeDaoImpl inviteMessgeDao;
//	private UserDaoImpl userDao;
//	private TempUserDaoImpl tempUserDao;
//
//	private EmManager() {
//		inviteMessgeDao = new InviteMessgeDaoImpl();
//		userDao = new UserDaoImpl(EBApplication.getInstance().getApplicationContext());
//		tempUserDao = new TempUserDaoImpl(EBApplication.getInstance().getApplicationContext());
//
//	};
//
//	// private static Context ct;
//	public void init() {
//		EMClient.getInstance().contactManager()
//				.setContactListener(new EMContactListener() {
//
//					@Override
//					public void onContactAgreed(final String username) {
//						// 好友请求被同意
//
//						UserDaoImpl2.getInstance().getUserCommonInfoByEmname(username)
//								.subscribe(new MyObserver<InitUserInfo>() {
//									@Override
//									protected void onError(ApiException ex) {
//
//									}
//
//									@Override
//									public void onCompleted() {
//
//									}
//
//									@Override
//									public void onNext(InitUserInfo initUserInfo) {
//										List<UserCommonInfo> list = new ArrayList<UserCommonInfo>();
//										list.add(initUserInfo.friendList.get(0));
//										userDao.updateFriendList(list);
//										if(friendReqListener!=null){
//											friendReqListener.friendStatChange();
//										}
//										if(friendReqDetailListener!=null){
//											friendReqDetailListener.agree(username);
//										}
//									}
//								});
//
//					}
//
//					@Override
//					public void onContactRefused(String username) {
//						// 好友请求被拒绝
//						Log.i("wang", "好友请求被拒绝");
//						friendReqListener.friendStatChange();
//						friendReqDetailListener.refuse(username);
//					}
//
//					@Override
//					public void onContactInvited(final String username,
//							final String reason) {
//						// 收到好友邀请
//						Log.i("wang", "EmManager收到好友邀请,username:"+username);
//						UserDaoImpl2.getInstance().getUserCommonInfoByEmname(username)
//								.subscribe(new MyObserver<InitUserInfo>() {
//									@Override
//									protected void onError(ApiException ex) {
//
//									}
//
//									@Override
//									public void onCompleted() {
//
//									}
//
//									@Override
//									public void onNext(InitUserInfo initUserInfo) {
//										//ReceiceData<InitUserInfo> result) {
//											InviteMessage msg = new InviteMessage();
//											msg.setFrom(username);
//											msg.setTime(System.currentTimeMillis());
//											msg.setReason(reason);
//											inviteMessgeDao.saveMessage(msg);
//											// 保存未读数，这里没有精确计算
////											inviteMessgeDao.saveUnreadMessageCount(inviteMessgeDao
////													.getUnreadNotifyCount() + 1);
//											tempUserDao.savaPerson(initUserInfo.friendList.get(0));
//											if(friendReqListener!=null){
//												friendReqListener.friendStatChange();
//											}
//											if(friendReqDetailListener!=null){
//												friendReqDetailListener.getFriend(username, reason);
//											}
//									}
//								});
//
//							//	});
//
//					}
//
//					@Override
//					public void onContactDeleted(String username) {
//						// 被删除时回调此方法
//						Log.i("wang", "被删除时回调此方法");
//						friendReqListener.friendStatChange();
//						friendReqDetailListener.deleted(username);
//					}
//
//					@Override
//					public void onContactAdded(String username) {
//						// 增加了联系人时回调此方法
//						Log.i("wang", "EmManager增加了联系人时回调此方法");
//						// InviteMessgeDao inviteMessgeDao = new
//						// InviteMessgeDao();
//						// Log.i("wang",
//						// "未读的系统消息数为:"+inviteMessgeDao.getUnreadMessagesCount());
//						//
//						// InviteMessage msg = new InviteMessage();
//						// msg.setFrom(username);
//						// msg.setTime(System.currentTimeMillis());
//						// msg.setReason("系统强制添加");
//						//
//						// inviteMessgeDao.saveMessage(msg);
//						// //保存未读数，这里没有精确计算
//						// inviteMessgeDao.saveUnreadMessageCount(1);
//						// Log.i("wang",
//						// "未读的系统消息数为:"+inviteMessgeDao.getUnreadMessagesCount());
//						friendReqListener.friendStatChange();
//						friendReqDetailListener.addContact(username);
//					}
//				});
//
//		messageListener = new EMMessageListener() {
//			@Override
//			public void onMessageReceived(List<EMMessage> messages) {
//				// 提示新消息
//				// Log.i("wang", "onMessageReceived");
//				for (EMMessage message : messages) {
//					Log.i("wang", "EmManager提示新消息:"
//							+ messages.get(0).toString());
//					msgGetListener.OnMsgReceive(message);
//				}
//
//			}
//
//			@Override
//			public void onCmdMessageReceived(List<EMMessage> messages) {
//			}
//
//			@Override
//			public void onMessageReadAckReceived(List<EMMessage> messages) {
//			}
//
//			@Override
//			public void onMessageDeliveryAckReceived(List<EMMessage> message) {
//			}
//
//			@Override
//			public void onMessageChanged(EMMessage message, Object change) {
//			}
//		};
//
//
//		EMClient.getInstance().chatManager()
//				.addMessageListener(messageListener);
//		//注册一个监听连接状态的listener
//		EMClient.getInstance().addConnectionListener(new MyConnectionListener());
//	}
//
//	//实现ConnectionListener接口
//	private class MyConnectionListener implements EMConnectionListener {
//		@Override
//		public void onConnected() {
//		}
//		@Override
//		public void onDisconnected(final int error) {
//					if(error == EMError.USER_REMOVED){
//						// 显示帐号已经被移除
//						remoteLoginListener.remoteLogin(RemoteLoginListener.USER_LOGIN_ANOTHER_DEVICE);
//					}else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
//						// 显示帐号在其他设备登陆
//						remoteLoginListener.remoteLogin(RemoteLoginListener.USER_REMOVED);
//					} else {
//						//if (NetUtils.hasNetwork(MainActivity.this))
//						//连接不到聊天服务器
//						//else
//						//当前网络不可用，请检查网络设置
//						remoteLoginListener.remoteLogin(RemoteLoginListener.ELSE);
//					}
//
//			}
//		}
//
//
//	public void registerFriendStatChangeListener(FriendReqListener jj) {
//		friendReqListener = jj;
//	}
//
//	public void registerFriendReqDetailListener(
//			FriendReqDetailListener friendReqListener1) {
//
//		friendReqDetailListener = friendReqListener1;
//	}
//
//	public void registerRemoteLoginListener(RemoteLoginListener remoteLoginListener){
//		this.remoteLoginListener = remoteLoginListener;
//	}
//
//	public void registerMsgReceiveListener(MsgGetListener msgGetListener1) {
//		// Log.i("wang", "注册了消息监听");
//		msgGetListener = msgGetListener1;
//	}
//
//	/**
//	 * 获取未读的好友邀请总数
//	 *
//	 * @return
//	 */
//	public int getUnreadInviteCountTotal() {
//		// InviteMessgeDao inviteMessgeDao = new InviteMessgeDao();
//		return inviteMessgeDao.getUnreadNotifyCount();
//	}
//
//	/**
//	 * 判断是否有未读消息
//	 *
//	 * @return
//	 */
//	public boolean isThereUnreadMsg() {
//		if (getUnreadInviteCountTotal() > 0) {
//			return true;
//		} else if (inviteMessgeDao.getUnreadNotifyCount() > 0) {
//			return true;
//		} else {
//			return false;
//		}
//
//	}
//
//	/**
//	 * 获取未读的好友发来的消息总数
//	 * @return
//	 */
//	public int getUnreadFMsgCount() {
//		return EMClient.getInstance().chatManager().getUnreadMsgsCount();
//	}
//
//	/**
//	 * 查看此emname是否为好友
//	 * @param emname
//	 */
//	public void isMyFriend(String emname){
//		boolean myFriend = userDao.isMyFriend(emname);
//	}
//
//	/**
//	 * 同意好友申请
//	 */
//	public void agreeInvite(UserCommonInfo userInfo, EmResultCallBack<String> lis){
//		try {
//			EMClient.getInstance().contactManager().acceptInvitation(userInfo.emname);
//			List<UserCommonInfo> list  = new ArrayList<UserCommonInfo>();
//			list.add(userInfo);
//			userDao.updateFriendList(list);
//			lis.success("添加好友成功");
//		} catch (HyphenateException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			lis.error("添加好友失败");
//		}
//	}
//
//	public List<InviteMessage> getInviteMsgList(){
//		InviteMessgeDaoImpl dao = new InviteMessgeDaoImpl();
//		return dao.getMessagesList();
//	}
//
//	/**
//	 * 发送好友申请
//	 * @param emname
//	 */
//	public void sendFriendInvite(String emname){
//
//	}
//
//	/**
//	 * 登陆
//	 */
//	public void login(){
//
//	}
//
//	/**
//	 * 注销
//	 */
//	public void logout(){
//		EMClient.getInstance().logout(true);
//	}
//
//	public interface EmResultCallBack<T>{
//		void success(T t);
//
//		void error(T t);
//	}
//
//}
