package com.wxxiaomi.ming.electricbicycle.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import com.wxxiaomi.ming.electricbicycle.AppManager;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.bean.Bicycle;
import com.wxxiaomi.ming.electricbicycle.view.activity.base.BaseActivity;


public class BicycleWelcomeInfoActivity extends BaseActivity {

	private Button btn_bund;
	private Bicycle bike;

	@Override
	protected void initView() {
		setContentView(R.layout.activity_bicycle_welcome);
		AppManager.getAppManager().addActivity(this);
		btn_bund = (Button) findViewById(R.id.btn_bund);
		btn_bund.setOnClickListener(this);
		bike = (Bicycle) getIntent().getExtras().get("value");
	}

	@Override
	protected void initData() {
		bike = (Bicycle) getIntent().getExtras().get("value");
//		Log.i("wang", "weclcome中bikeid=" + bike.id);
		if(bike.isbund == 1){
			btn_bund.setBackgroundColor(Color.GRAY);
//			btn_bund.setClickable(false);
			btn_bund.setTextColor(Color.BLACK);
			btn_bund.setText("本车已被绑定");
		}
	}

	@Override
	protected void processClick(View v) {
		switch (v.getId()) {
		case R.id.btn_bund:
			if(bike.isbund == 1){
				showErrorDialog("本车已经被绑定");
			}else{
				Intent intent = new Intent(ct, RegisterOneActivity.class);
				intent.putExtra("carid", bike.id);
				startActivity(intent);
			}
			
			// finish();
			break;

		default:
			break;
		}

	}

	@Override
	protected void onDestroy() {
		AppManager.getAppManager().finishActivity(this);
		super.onDestroy();

	}

}
