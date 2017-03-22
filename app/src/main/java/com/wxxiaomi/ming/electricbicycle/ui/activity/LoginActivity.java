package com.wxxiaomi.ming.electricbicycle.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wxxiaomi.ming.common.widget.ClearableEditTextWithIcon;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.ui.activity.base.MvpActivity;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.LoginPresenter;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.impl.LoginPresenterImpl;
import com.wxxiaomi.ming.electricbicycle.ui.activity.view.LoginView;


/**
 * 登陆页面
 * @author Mr.W
 */
public class LoginActivity extends MvpActivity<LoginView,LoginPresenter> implements LoginView {

	private ClearableEditTextWithIcon et_login_usr;
	private ClearableEditTextWithIcon et_login_pwd;
	private Toolbar toolbar;
	private TextView tv_debug;
	private TextView tv_register;

	@Override
	protected void initView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_login2);
		et_login_usr = (ClearableEditTextWithIcon) findViewById(R.id.et_login_usr);
		et_login_pwd = (ClearableEditTextWithIcon) findViewById(R.id.et_login_pwd);
		et_login_usr.setIconResource(R.mipmap.user_account_icon);
		et_login_pwd.setIconResource(R.mipmap.user_pwd_lock_icon);
		TextView btn_ok = (TextView) findViewById(R.id.btn_ok);
		assert btn_ok != null;
		btn_ok.setOnClickListener(this);
		toolbar = (Toolbar) this.findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		tv_register = (TextView) findViewById(R.id.tv_register);
		tv_register.setOnClickListener(this);
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
				String username =  et_login_usr.getEditableText().toString().toLowerCase();
				String password =  et_login_pwd.getEditableText().toString().toLowerCase();
				presenter.onLoginBtnClick(username,password);
				break;
			case R.id.tv_debug:
				presenter.onDebugBtnClick();
			case R.id.tv_register:
				Log.i("wang","reister has click");
				break;
		}
	}

	@Override
	public EditText getEditText() {
		return et_login_usr;
	}
}
