package com.wxxiaomi.ming.common.util;
import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;


public class AppManager {
	// Activity栈
		private static Stack<Activity> activityStack;
		// 单例模式
		private static AppManager instance;

		private AppManager() {
			//Glide.with(this).load("").into()
		}

		/**
		 * 单一实例
		 */
		public static AppManager getAppManager() {
			if (instance == null) {
				instance = new AppManager();
			}
			return instance;
		}

		/**
		 * 添加Activity到堆栈
		 */
		public void addActivity(Activity activity) {
			if (activityStack == null) {
				activityStack = new Stack<Activity>();
			}
			activityStack.add(activity);
		}

		/**
		 * 获取当前Activity（堆栈中最后一个压入的）
		 */
		public Activity currentActivity() {
			Activity activity = activityStack.lastElement();
			return activity;
		}

		/**
		 * 结束当前Activity（堆栈中最后一个压入的）
		 */
		public void finishActivity() {
			Activity activity = activityStack.lastElement();
			finishActivity(activity);
		}

		/**
		 * 结束指定的Activity
		 */
		public void finishActivity(Activity activity) {
			try {
				if (activity != null) {
					if (activityStack.contains(activity)) {
						activityStack.remove(activity);
						activity.finish();
					}
//				activity = null;
				}
			}catch (Exception e){
				Log.i("wang","结束activity的时候发生一点小错误");
			}
		}

		/**
		 * 结束指定类名的Activity
		 */
		public void finishActivity(Class<?> cls) {
			for (Activity activity : activityStack) {
				if (activity.getClass().equals(cls)) {
					finishActivity(activity);
				}
			}
		}

		/**
		 * 结束所有Activity
		 */
		public void finishAllActivity() {
			for (int i = 0; i < activityStack.size(); i++) {
				if (null != activityStack.get(i)) {
					activityStack.get(i).finish();
				}
			}
			activityStack.clear();
		}

	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity(Class<?> cls) {
		for (Activity activity : activityStack) {
			Log.i("wang","finishAllActivity");
			if (!activity.getClass().equals(cls)) {
				Log.i("wang","activity.getClass()："+activity.getClass());
				finishActivity(activity);
			}

		}
	}

		/**
		 * 退出应用程序
		 */
		public void AppExit(Context context) {
			try {
				finishAllActivity();
				ActivityManager activityMgr = (ActivityManager) context
						.getSystemService(Context.ACTIVITY_SERVICE);
				activityMgr.killBackgroundProcesses(context.getPackageName());
				System.exit(0);
			} catch (Exception e) {
			}
		}
	}
