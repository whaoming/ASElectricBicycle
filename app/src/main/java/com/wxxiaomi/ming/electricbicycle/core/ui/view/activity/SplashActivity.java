//package com.wxxiaomi.ming.electricbicycle.core.ui.view.activity;
//
//
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//
//import com.wxxiaomi.ming.electricbicycle.common.util.AppManager;
//import com.wxxiaomi.ming.electricbicycle.R;
//import com.wxxiaomi.ming.electricbicycle.core.ui.base.BaseActivity;
//import com.wxxiaomi.ming.electricbicycle.core.ui.presenter.SplashPre;
//import com.wxxiaomi.ming.electricbicycle.core.ui.presenter.impl.SplashImpl;
//import com.wxxiaomi.ming.electricbicycle.core.ui.view.SlpashView;
//
//
///**
// * 入口activity
// *
// * @author Mr.W
// *
// */
//public class SplashActivity extends BaseActivity<SlpashView,SplashPre> implements SlpashView<SplashPre>{
//
//	@Override
//	public void initView(Bundle savedInstanceState) {
//		setContentView(R.layout.activity_main);
//		AppManager.getAppManager().addActivity(this);
//		presenter.loadConfig();
//	}
//
//	@Override
//	public SplashPre getPresenter() {
//		return new SplashImpl();
//	}
//
//
//
//
//	@Override
//	protected void onDestroy() {
//		// TODO Auto-generated method stub
//		AppManager.getAppManager().finishActivity(this);
//		super.onDestroy();
//
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
//
//	@Override
//	public void runRegisterAct() {
////		Intent intent = new Intent(this,RegisterOneAct.class);
////		startActivity(intent);
//	}
//
//	@Override
//	public void runWelcomeAct() {
////		Intent intent = new Intent(this,WelcomeActivity.class);
////		startActivity(intent);
//	}
//
//	@Override
//	public void onClick(View v) {
//
//	}
//}
