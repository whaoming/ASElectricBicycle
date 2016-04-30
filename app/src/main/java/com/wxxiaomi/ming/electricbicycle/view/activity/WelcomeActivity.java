package com.wxxiaomi.ming.electricbicycle.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wxxiaomi.ming.electricbicycle.AppManager;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.bean.Bicycle;
import com.wxxiaomi.ming.electricbicycle.bean.format.common.ReceiceData;
import com.wxxiaomi.ming.electricbicycle.engine.BicycleEngineImpl;
import com.wxxiaomi.ming.electricbicycle.engine.common.ResultByGetDataListener;
import com.wxxiaomi.ming.electricbicycle.view.activity.base.BaseActivity;


public class WelcomeActivity extends BaseActivity {

	private final static int LOADCOMPLETE = 123;
	private LinearLayout ll_scan;
	private LinearLayout ll_login;
	private final static int SCANNIN_GREQUEST_CODE = 1;
	private BicycleEngineImpl engine;
	private TranslateAnimation mShowAction;
	private TextView tv_load;
	private LinearLayout ll_towbtn_view;

	@Override
	protected void initView() {
		setContentView(R.layout.activity_welcome2);
		AppManager.getAppManager().addActivity(this);
		ll_scan = (LinearLayout) findViewById(R.id.ll_scan);
		ll_login = (LinearLayout) findViewById(R.id.ll_login);
		tv_load = (TextView) findViewById(R.id.tv_load);
		ll_scan.setOnClickListener(this);
		ll_login.setOnClickListener(this);
		ll_towbtn_view = (LinearLayout) findViewById(R.id.ll_towbtn_view);
	}

	@Override
	protected void handler(Message msg) {
		super.handler(msg);
		switch (msg.what) {
		case LOADCOMPLETE:
			tv_load.setVisibility(View.GONE);
			showBtn();
			break;

		default:
			break;
		}
	}

	@Override
	protected void initData() {
		engine = new BicycleEngineImpl(ct);
		mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		mShowAction.setDuration(1000);
		mShowAction.setInterpolator(new OvershootInterpolator());
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
				Message msg = new Message();
				msg.what = LOADCOMPLETE;
				handler.sendMessage(msg);

			}
		}).start();

		
	}

	private void showBtn() {
//		ll_scan.clearAnimation();
//		ll_scan.setAnimation(mShowAction);
//		ll_scan.startAnimation(mShowAction);
//		ll_scan.setVisibility(View.VISIBLE);
//		ll_login.clearAnimation();
//		ll_login.setAnimation(mShowAction);
//		ll_login.startAnimation(mShowAction);
//		ll_login.setVisibility(View.VISIBLE);
		ll_towbtn_view.clearAnimation();
		ll_towbtn_view.setAnimation(mShowAction);
		ll_towbtn_view.startAnimation(mShowAction);
		ll_towbtn_view.setVisibility(View.VISIBLE);
	}

	@Override
	protected void processClick(View v) {
		switch (v.getId()) {
		case R.id.ll_scan:
			Intent intent = new Intent();
			intent.setClass(ct, ScanCodeActivity1.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			break;
		case R.id.ll_login:
			Intent intent2 = new Intent(ct, LoginActivity.class);
			startActivity(intent2);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				// 显示扫描到的内容
				// et_device_number.setText(bundle.getString("result"));
				String scanResult = bundle.getString("result");
				Log.i("wang", "扫描到的内容:" + scanResult);
				getBicycleInfo(scanResult);
			}
			break;

		}
	}

	/**
	 * 根据扫描到的连接获取车辆信息
	 * 
	 * @param scanResult
	 */
	private void getBicycleInfo(String scanResult) {
		try {
			int cardid = Integer.valueOf(scanResult);
			if ((cardid < 6)) {
				showLoading2Dialog(this, "正在获取车辆信息");
				// 连接服务器
				engine.getBicycleInfo(scanResult,
						new ResultByGetDataListener<Bicycle>() {

							@Override
							public void success(ReceiceData<Bicycle> result) {
								closeLoading1Dialog();
								if (result.state == 200) {
									Intent intent = new Intent(ct,
											BicycleWelcomeInfoActivity.class);
									intent.putExtra("value", result.infos);
									startActivity(intent);
								} else {
									showErrorDialog(result.error);
								}
							}

							@Override
							public void error(String error) {
								closeLoading1Dialog();
								showErrorDialog("连接服务器失败");
							}
						});
			} else {
				showErrorDialog("非法二维码");
			}
		} catch (Exception e) {
			showErrorDialog("非法二维码");
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		AppManager.getAppManager().finishActivity(this);
		super.onDestroy();

	}
}
