package com.wxxiaomi.ming.electricbicycle.core;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.multidex.MultiDex;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.wxxiaomi.ming.electricbicycle.common.util.AppManager;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.common.util.SharePrefUtil;
import com.wxxiaomi.ming.electricbicycle.core.ui.view.activity.RegisterActivity;
import com.wxxiaomi.ming.electricbicycle.support.aliyun.OssEngine;
import com.wxxiaomi.ming.electricbicycle.support.cache.DiskCache;
import com.wxxiaomi.ming.electricbicycle.support.easemob.ui.MyUserProvider;

import java.util.concurrent.CountDownLatch;


/**
 * 入口activity
 * 
 * @author Mr.W
 * 
 */
public class SplashActivity extends Activity {

	private TextView tv_tv;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case 1:
					Log.i("wang","asd");
					tv_tv.setText(tv_tv.getText()+"\n-初始化百度地图完成\n-");
					Log.i("wang","初始化百度地图完成");
					break;
				case 2:
					tv_tv.setText(tv_tv.getText()+"初始化缓存系统完成\n-");
					Log.i("wang","初始化缓存系统完成");
					break;
				case 3:
					tv_tv.setText(tv_tv.getText()+"初始化Oss完成\n-");
					Log.i("wang","初始化Oss完成");
					break;
				case 4:
					tv_tv.setText(tv_tv.getText()+"初始化Ease完成\n");
					Log.i("wang","初始化Ease完成");
					break;
				case 5:
//					tv_tv.setText(tv_tv.getText()+"跳转");
					Intent intent = new Intent(SplashActivity.this, RegisterActivity.class);
					startActivity(intent);
					finish();
					break;
			}
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		AppManager.getAppManager().addActivity(this);
		tv_tv = (TextView) findViewById(R.id.tv_tv);
		init();
	}


	/**
	 * 初始化各类参数 决定程序往哪里走
	 */
	private void init() {
		final CountDownLatch order = new CountDownLatch(2);
		SDKInitializer.initialize(getApplicationContext());
		handler.sendEmptyMessage(1);
		new Thread(){
			@Override
			public void run() {
				MultiDex.install(getApplicationContext());
				DiskCache.getInstance().open(getApplicationContext());
				handler.sendEmptyMessage(2);
				OssEngine.getInstance().initOssEngine(getApplicationContext());
				handler.sendEmptyMessage(3);
				initEM();
				handler.sendEmptyMessage(4);
				order.countDown();
			}
		}.start();

		new Thread(){
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					order.countDown();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
		new Thread(){
			@Override
			public void run() {
				try {
					order.await();
					handler.sendEmptyMessage(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	private void initEM() {
		EMOptions options = new EMOptions();
		// 默认添加好友时，是不需要验证的，改成需要验证
		options.setAcceptInvitationAlways(false);
		// 初始化
		try {
			EaseUI.getInstance().init(getApplicationContext(), options);
		} catch (Exception e) {
			e.printStackTrace();
		}
		EaseUI.getInstance().setUserProfileProvider(new MyUserProvider());

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		AppManager.getAppManager().finishActivity(this);
		super.onDestroy();
	
	}

}
