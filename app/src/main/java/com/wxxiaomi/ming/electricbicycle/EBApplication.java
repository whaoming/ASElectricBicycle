package com.wxxiaomi.ming.electricbicycle;


import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;

/**
 * 程序入口
 * 
 * @author Administrator
 * 
 */
public class EBApplication extends Application {
	public static Context applicationContext;
	private static EBApplication instance;
	
	@Override
	public void onCreate() {
		MultiDex.install(this);
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i("wang", "进入application-oncreate()");
		// EMHelper.getInstance().init(this);
		 applicationContext = this;
		instance = this;

		 initEM();
		 SDKInitializer.initialize(this);
		
	}
	
	public static EBApplication getInstance() {
		return instance;
	}

	private void initEM() {
//		int pid = android.os.Process.myPid();
//		String processAppName = getAppName(pid);
//		// 如果app启用了远程的service，此application:onCreate会被调用2次
//		// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
//		// 默认的app会在以包名为默认的process name下运行，如果查到的process name不是app的process
//		// name就立即返回
//
//		if (processAppName == null
//				|| !processAppName.equalsIgnoreCase(this.getPackageName())) {
//			// "com.easemob.chatuidemo"为demo的包名，换到自己项目中要改成自己包名
//
//			// 则此application::onCreate 是被service 调用的，直接返回
//			return;
//		}

		EMOptions options = new EMOptions();
		
		// 默认添加好友时，是不需要验证的，改成需要验证
		options.setAcceptInvitationAlways(false);
		// 初始化
		EaseUI.getInstance().init(this,options);
//		EMClient.getInstance().init(this, options);
		// 在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//		EMClient.getInstance().setDebugMode(true);

	}
	
	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}
	
		

}
