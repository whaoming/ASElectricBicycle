package com.wxxiaomi.ming.electricbicycle.view.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.wxxiaomi.ming.electricbicycle.AppManager;
import com.wxxiaomi.ming.electricbicycle.GlobalParams;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.bean.User;
import com.wxxiaomi.ming.electricbicycle.bean.format.Register;
import com.wxxiaomi.ming.electricbicycle.bean.format.common.ReceiceData;
import com.wxxiaomi.ming.electricbicycle.engine.UserEngineImpl;
import com.wxxiaomi.ming.electricbicycle.engine.common.ResultByGetDataListener;
import com.wxxiaomi.ming.electricbicycle.view.activity.base.BaseActivity;


public class RegisterTwoActivity extends BaseActivity {

//	private EditText et_name;
	private Button btn_ok;
	
	/**
	 * 上一个activity带过的phone
	 */
//	private String userid;
	private User userInfo;
	private int carid;
	private UserEngineImpl engine;
	private TextInputLayout til_name;
	private TextInputLayout til_description;
	
	@Override
	protected void initView() {
		setContentView(R.layout.activity_register_two);
		AppManager.getAppManager().addActivity(this);
		til_name = (TextInputLayout) findViewById(R.id.til_name);
		til_description = (TextInputLayout) findViewById(R.id.til_description);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(this);
		
	}

	@Override
	protected void initData() {
		engine = new UserEngineImpl(ct);
		carid = getIntent().getIntExtra("carid", 0);
		userInfo = (User) getIntent().getExtras().get("userInfo");
		til_name.getEditText().addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				til_name.setError("");
				til_name.setEnabled(false);
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		til_description.getEditText().addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				til_description.setError("");
				til_description.setEnabled(false);
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	protected void processClick(View v) {
		switch (v.getId()) {
		case R.id.btn_ok:
			showLoading1Dialog("正在注册");
			String name = til_name.getEditText().getText().toString();
			String description = til_description.getEditText().getText().toString();
			boolean checkParsResult = checkPars(name,description);
			if(checkParsResult){
//				连接服务器进行注册
				improveUserInfo(name,description,"",userInfo.id,userInfo.username);
			}
			break;

		default:
			break;
		}
		
	}



	private boolean checkPars(String name, String description) {
		if("".equals(name)){
			return false;
		}else if("".equals(description)){
			return false;
		}
		return true;
	}

	private void improveUserInfo(final String name, final String description,
			final String headUrl, int userid, String username) {
		engine.ImproveUserInfo(userid,username,name, description, headUrl, new ResultByGetDataListener<Register>() {
			
			@Override
			public void success(ReceiceData<Register> result) {
				if(result.state == 200){
					GlobalParams.user.userCommonInfo = result.infos.userInfo.userCommonInfo;
					Intent intent = new Intent(ct,HomeActivity2.class);
					if(carid == 0){
						closeLoading1Dialog();
						startActivity(intent);
						finish();
					}else{
						setLoadingContent("正在绑定车子");
						bundCar(carid,GlobalParams.user.id);
					}
//					
				}else{
//					showMsgDialog("注册失败"+result.error);
					showErrorDialog("注册失败"+result.error);
				}
				
			}
			
			@Override
			public void error(String error) {
//				showMsgDialog("注册失败，连接不上服务器");
				showErrorDialog("注册失败，连接不上服务器");
			}
		});
	}

	/**
	 * 绑定车子
	 * @param carid2
	 */
	protected void bundCar(int carid2, int userid) {
		engine.BundCar(userid, carid2, new ResultByGetDataListener<String>() {
			
			@Override
			public void success(ReceiceData<String> result) {
				closeLoading1Dialog();
				if(result.state == 200){
//					showMsgDialog("绑定成功");
					showErrorDialog("绑定成功");
					Intent intent = new Intent(ct,HomeActivity2.class);
					startActivity(intent);
					finish();
				}else{
					showErrorDialog("绑定失败");
				}
				
			}
			
			@Override
			public void error(String error) {
				showErrorDialog("连接不上服务器");

				
			}
		});
		
	}
	
	@Override
		protected void onDestroy() {
		AppManager.getAppManager().finishActivity(this);
			super.onDestroy();
			
		}

}
