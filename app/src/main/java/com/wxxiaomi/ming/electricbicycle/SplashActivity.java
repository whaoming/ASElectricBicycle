package com.wxxiaomi.ming.electricbicycle;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.wxxiaomi.ming.electricbicycle.util.SharePrefUtil;
import com.wxxiaomi.ming.electricbicycle.view.activity.RegisterOneActivity;
import com.wxxiaomi.ming.electricbicycle.view.activity.WelcomeActivity;

/**
 * 入口activity
 * 
 * @author Mr.W
 * 
 */
public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		AppManager.getAppManager().addActivity(this);
		init();
	}

	// https://github.com/122627018/ElectricBicycle.git

	/**
	 * 初始化各类参数 决定程序往哪里走
	 */
	private void init() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				long start = System.currentTimeMillis();
				long costTime = System.currentTimeMillis() - start;
				if (2000 - costTime > 0) {
					try {
						Thread.sleep(2000 - costTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// 是否是第一次使用此程序
				boolean isFirstRun = SharePrefUtil.getBoolean(
						SplashActivity.this, "firstRun", true);
				if (isFirstRun) {
					// 第一次使用
					Intent intent = new Intent(SplashActivity.this,
							WelcomeActivity.class);
					startActivity(intent);
					SharePrefUtil.saveBoolean(SplashActivity.this, "firstGo",
							false);
					finish();
				} else {
					// 检测本地是否得到账号
					boolean isRemUser = SharePrefUtil.getBoolean(
							SplashActivity.this, "isRemUser", false);
					if (isRemUser) {
						// 本地有记住账号,实现登录功能
						/**
						 * 1.取出账号密码进行服务器登陆 2.当服务器登陆成功后进行EM登陆
						 * 3.当EM登陆成功，检查本地是否有此用户相关信息
						 * 4.如果有，直接进入主界面；如果没有，连接服务器做初始化操作
						 */
					} else {
						// 检测不到本地账号的话就去注册页面(里面有登录功能)
						Intent intent = new Intent(SplashActivity.this,
								RegisterOneActivity.class);
						startActivity(intent);
						finish();
					}

				}

			}
		}).start();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		AppManager.getAppManager().finishActivity(this);
		super.onDestroy();
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
