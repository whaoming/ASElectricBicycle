package com.wxxiaomi.ming.electricbicycle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.presenter.LoginPresenter;
import com.wxxiaomi.ming.electricbicycle.presenter.impl.LoginPresenterImpl;
import com.wxxiaomi.ming.electricbicycle.ui.base.BaseActivity;
import com.wxxiaomi.ming.electricbicycle.ui.view.LoginView;
import com.wxxiaomi.ming.electricbicycle.util.MyUtils;


/**
 * 登陆页面
 * 
 * @author Mr.W
 * 
 */
public class LoginActivity1 extends BaseActivity<LoginPresenter> implements LoginView {

	private TextInputLayout til_username;
	private TextInputLayout til_password;
	private Toolbar toolbar;

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

	}

	@Override
	protected void initData() {
		assert til_username.getEditText() != null;
		til_username.getEditText().addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
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
				// checkFormat(til_username);
			}
		});
		assert til_password.getEditText() != null;
		til_password.getEditText().addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
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
				// checkFormat(til_password);
			}
		});
	}

	@Override
	protected LoginPresenter initPre() {
		return new LoginPresenterImpl(this);
	}



	@Override
	protected void onDestroy() {
		super.onDestroy();
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn_ok:
				assert til_username.getEditText() != null;
				String username = til_username.getEditText().getText().toString()
						.trim();
				assert til_password.getEditText() != null;
				String password = til_password.getEditText().getText().toString()
						.trim();
				if (checkFormat(til_username) && checkFormat(til_password)) {
					presenter.LoginFromSerer(username,password);
				} else {
//					closeLoading1Dialog();
				}
		}
	}
	@Override
	public void runMainAct() {
		Intent intent = new Intent(LoginActivity1.this,
				HomeActivity3.class);
		startActivity(intent);
	}


	private boolean checkFormat(TextInputLayout strLayout) {
		assert strLayout.getEditText() != null;
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
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
