package com.wxxiaomi.ming.electricbicycle.view.activity;


import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
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
import com.wxxiaomi.ming.electricbicycle.util.MyUtils;
import com.wxxiaomi.ming.electricbicycle.view.activity.base.BaseActivity;


/**
 * 注册页面一
 * 
 * @author Administrator
 * 
 */
public class RegisterOneActivity extends BaseActivity {

	private TextInputLayout til_username;
	private TextInputLayout til_password;

	/**
	 * 输入验证码之后确认按钮
	 */
	private Button btn_ok;
	private int carid;

	private UserEngineImpl engine;
	private Toolbar toolbar;
	private String username;
	private String password;

	@Override
	protected void initView() {
		setContentView(R.layout.activity_register_one);
		AppManager.getAppManager().addActivity(this);
		til_username = (TextInputLayout) findViewById(R.id.til_username);
		til_password = (TextInputLayout) findViewById(R.id.til_password);

		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(this);
		toolbar = (Toolbar) this.findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

	}

	@Override
	protected void initData() {
		engine = new UserEngineImpl(ct);
		carid = getIntent().getIntExtra("carid", 0);
		til_username.getEditText().addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				til_username.setError("");
				til_username.setEnabled(false);
				
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
		til_password.getEditText().addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				til_password.setError("");
				til_password.setEnabled(false);
				
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

	private boolean checkFormat(TextInputLayout strLayout) {
		String str = strLayout.getEditText().getText().toString().trim();
		if ("".equals(str)) {
			strLayout.setError("不能为空");
			return false;
		} else if (str.contains(" ")) {
			strLayout.setError("出现非法字符");
			return false;
		} else if (MyUtils.checkChainse(str)) {
			strLayout.setError("不能包含中文");
			return false;
		} else if (str.length() < 6) {
			strLayout.setError("长度小于6");
			return false;
		} else {
			strLayout.setEnabled(false);
			return true;
		}
	}

	@Override
	protected void processClick(View v) {
		switch (v.getId()) {
		case R.id.btn_ok:
			username = til_username.getEditText().getText().toString().trim();
			password = til_password.getEditText().getText().toString().trim();
			if (checkFormat(til_username)&&checkFormat(til_password)) {
				showLoading1Dialog("正在验证....");
				getPhoneCodeMessage(username, password);
			} 
			break;
		case R.id.btn_debug:

			Intent intent1 = new Intent(ct, HomeActivity2.class);
			startActivity(intent1);
			break;
		default:
			break;
		}

	}

	/**
	 * 连接服务器检测手机号是否已被注册 如果未被注册，则获取验证码
	 * 
	 * @param
	 *
	 */
	private void getPhoneCodeMessage(final String username,
			final String password) {
		engine.registerUser(username, password,
				new ResultByGetDataListener<Register>() {

					@Override
					public void success(ReceiceData<Register> result) {
						closeLoading1Dialog();
						if (result.state == 200) {
							// // 获取短信成功,也就是此手机号可以注册
							GlobalParams.user = result.infos.userInfo;
							GoToNext(result.infos.userInfo);
						} else {
							showErrorDialog(result.error);
						}

					}

					@Override
					public void error(String error) {
						closeLoading1Dialog();
						showErrorDialog("连接不上服务器");
					}
				});
	}

	protected void GoToNext(User userInfo) {
		Intent intent = new Intent(RegisterOneActivity.this,
				RegisterTwoActivity.class);
		Log.i("wang", "userInfo.tostring=" + userInfo.toString());
		intent.putExtra("userInfo", userInfo);
		intent.putExtra("carid", carid);
		startActivity(intent);
		finish();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		AppManager.getAppManager().finishActivity(this);
		super.onDestroy();

	}

}
