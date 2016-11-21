package com.wxxiaomi.ming.electricbicycle.core.weight.em;


import android.content.Context;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.model.EaseNotifier;

public class EMHelper {
	
	private static EMHelper instance = null;
	private EaseUI easeUI;
	
	/**
	 * init helper
	 * 
	 * @param context
	 *            application context
	 */
	public void init(Context context) {
//	    demoModel = new DemoModel(context);
	    EMOptions options = initChatOptions();
	    //options传null则使用默认的
		if (EaseUI.getInstance().init(context, options)) {
//		    appContext = context;
		    
		    //设为调试模式，打成正式包时，最好设为false，以免消耗额外的资源
		    EMClient.getInstance().setDebugMode(true);
		    //get easeui instance
		    easeUI = EaseUI.getInstance();
		    
		    //调用easeui的api设置providers
//		    setEaseUIProviders();
			//初始化PreferenceManager
//			PreferenceManager.init(context);
			//初始化用户管理类
//			getUserProfileManager().init(context);
			
			//设置全局监听
//			setGlobalListeners();
//			broadcastManager = LocalBroadcastManager.getInstance(appContext);
//	        initDbDao();
		}
	}
	
	/**
	 * 获取消息通知类
	 * @return
	 */
	public EaseNotifier getNotifier(){
	    return easeUI.getNotifier();
	}
	
	private EMOptions initChatOptions(){
//        Log.d(TAG, "init HuanXin Options");
        
        // 获取到EMChatOptions对象
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        // 设置是否需要已读回执
        options.setRequireAck(true);
        // 设置是否需要已送达回执
        options.setRequireDeliveryAck(false);
        // 设置从db初始化加载时, 每个conversation需要加载msg的个数
//        options.setNumberOfMessagesLoaded(1);
        
        //使用gcm和mipush时，把里面的参数替换成自己app申请的
        //设置google推送，需要的GCM的app可以设置此参数
        options.setGCMNumber("324169311137");
        //在小米手机上当app被kill时使用小米推送进行消息提示，同GCM一样不是必须的
        options.setMipushConfig("2882303761517426801", "5381742660801");
        
//        options.allowChatroomOwnerLeave(getModel().isChatroomOwnerLeaveAllowed());
//        options.setDeleteMessagesAsExitGroup(getModel().isDeleteMessagesAsExitGroup());
//        options.setAutoAcceptGroupInvitation(getModel().isAutoAcceptGroupInvitation());
        
        return options;
//        notifier.setNotificationInfoProvider(getNotificationListener());
    }

	/**
	 * 是否登录成功过
	 * 
	 * @return
	 */
	public boolean isLoggedIn() {
		return EMClient.getInstance().isLoggedInBefore();
	}

	public synchronized static EMHelper getInstance() {
		if (instance == null) {
			instance = new EMHelper();
		}
		return instance;
	}
}
