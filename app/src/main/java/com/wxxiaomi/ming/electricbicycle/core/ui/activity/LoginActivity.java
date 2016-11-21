package com.wxxiaomi.ming.electricbicycle.core.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.core.base.BaseActivity;
import com.wxxiaomi.ming.electricbicycle.core.presenter.LoginPresenter;
import com.wxxiaomi.ming.electricbicycle.core.presenter.impl.LoginPresenterImpl;
import com.wxxiaomi.ming.electricbicycle.core.ui.LoginView;
import com.wxxiaomi.ming.electricbicycle.support.util.MyUtils;


/**
 * 登陆页面
 * 
 * @author Mr.W
 * 
 */
public class LoginActivity extends BaseActivity<LoginView,LoginPresenter> implements LoginView<LoginPresenter> {

	private TextInputLayout til_username;
	private TextInputLayout til_password;
	private Toolbar toolbar;
	private TextView tv_debug;

	@Override
	protected void initView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_login);
		til_username = (TextInputLayout) findViewById(R.id.til_username);
		til_password = (TextInputLayout) findViewById(R.id.til_password);
		Button btn_ok = (Button) findViewById(R.id.btn_ok);
		assert btn_ok != null;
		btn_ok.setOnClickListener(this);
		toolbar = (Toolbar) this.findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		assert getSupportActionBar() != null;
		getSupportActionBar().setHomeButtonEnabled(true); // 设置返回键可用
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		assert til_username.getEditText() != null;
//		til_username.getEditText().addTextChangedListener(new TextWatcher() {
//
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before,
//									  int count) {
//				til_username.setError("");
//				til_username.setEnabled(false);
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//										  int after) {
//			}
//
//			@Override
//			public void afterTextChanged(Editable s) {
//			}
//		});
//		assert til_password.getEditText() != null;
//		til_password.getEditText().addTextChangedListener(new TextWatcher() {
//
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before,
//									  int count) {
//				// TODO Auto-generated method stub
//				til_password.setError("");
//				til_password.setEnabled(false);
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//										  int after) {
//
//			}
//
//			@Override
//			public void afterTextChanged(Editable s) {
//			}
//		});
		tv_debug = (TextView) findViewById(R.id.tv_debug);
		tv_debug.setOnClickListener(this);
	}

	@Override
	public LoginPresenter getPresenter() {
		return new LoginPresenterImpl();
	}



	@Override
	protected void onDestroy() {
		super.onDestroy();
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn_ok:
				presenter.onLoginBtnClick(til_username,til_password);
				break;
			case R.id.tv_debug:
				presenter.onDebugBtnClick();
		}
	}





	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
