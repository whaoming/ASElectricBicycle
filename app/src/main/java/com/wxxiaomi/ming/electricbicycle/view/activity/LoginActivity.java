package com.wxxiaomi.ming.electricbicycle.view.activity;

import java.util.List;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.wxxiaomi.ming.electricbicycle.AppManager;
import com.wxxiaomi.ming.electricbicycle.GlobalParams;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.bean.User;
import com.wxxiaomi.ming.electricbicycle.bean.format.InitUserInfo;
import com.wxxiaomi.ming.electricbicycle.bean.format.Login;
import com.wxxiaomi.ming.electricbicycle.bean.format.common.ReceiceData;
import com.wxxiaomi.ming.electricbicycle.dao.impl.UserDaoImpl;
import com.wxxiaomi.ming.electricbicycle.engine.UserEngineImpl;
import com.wxxiaomi.ming.electricbicycle.engine.common.ResultByGetDataListener;
import com.wxxiaomi.ming.electricbicycle.util.MyUtils;
import com.wxxiaomi.ming.electricbicycle.view.activity.base.BaseActivity;


/**
 * 登陆页面
 * 
 * @author Mr.W
 * 
 */
public class LoginActivity extends BaseActivity {

	private TextInputLayout til_username;
	private TextInputLayout til_password;
	private Button btn_ok;
	private UserEngineImpl engine;
	private Toolbar toolbar;

	@Override
	protected void initView() {
		setContentView(R.layout.activity_login);
		AppManager.getAppManager().addActivity(this);
		til_username = (TextInputLayout) findViewById(R.id.til_username);
		til_password = (TextInputLayout) findViewById(R.id.til_password);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(this);
		toolbar = (Toolbar) this.findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setHomeButtonEnabled(true); // 设置返回键可用
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	protected void initData() {
		Log.i("wang","log测试中文");
		engine = new UserEngineImpl(ct);
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

	/**
	 * 处理点击事件
	 */
	@Override
	protected void processClick(View v) {
		switch (v.getId()) {
		case R.id.btn_ok:

			String username = til_username.getEditText().getText().toString()
					.trim();
			String password = til_password.getEditText().getText().toString()
					.trim();
			if (checkFormat(til_username) && checkFormat(til_password)) {
				showLoading1Dialog("正在登陆");
				LoginFromServer(username, password);
				return;
			} else {
				 closeLoading1Dialog();
			}
			break;
		default:
			break;
		}

	}

	/**
	 * 连接服务器，执行登录操作
	 * 
	 * @param username
	 * @param password
	 */
	private void LoginFromServer(final String username, final String password) {
		engine.Login(username, password, true,
				new ResultByGetDataListener<Login>() {

					@Override
					public void success(ReceiceData<Login> result) {

						if (result.state == 200) {
							// SharePrefUtil.saveString(ct, "username", value)

							// //登录成功
							GlobalParams.user = result.infos.userInfo;
							GlobalParams.username = GlobalParams.user.username;
							LoginFromEM(result.infos.userInfo);
						} else {
							closeLoading1Dialog();
							showErrorDialog("登录失败" + result.error);
						}
					}

					@Override
					public void error(String error) {
						closeLoading1Dialog();
						showErrorDialog("登录失败，连接不上服务器");
					}
				});

	}

	/**
	 * 登陆em服务器
	 * 
	 * @param userInfo
	 */
	protected void LoginFromEM(final User userInfo) {
		Log.i("wang", "登陆em服务器中");
		setLoadingContent("正在登陆聊天服务器");
		EMClient.getInstance().login(userInfo.username, userInfo.password,
				new EMCallBack() {
					@Override
					public void onSuccess() {
						// ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
						EMClient.getInstance().groupManager().loadAllGroups();
						EMClient.getInstance().chatManager()
								.loadAllConversations();

						Log.i("wang", "登陆em服务器成功");
						EMClient.getInstance().updateCurrentUserNick(
								userInfo.username);
						
						AfterLoginCheck(userInfo);

					}

					@Override
					public void onProgress(int progress, String status) {
						// Log.d(TAG, "login: onProgress");
					}

					@Override
					public void onError(final int code, final String message) {
						Log.i("wang", "login: onError: " + code + "---message="
								+ message);
						closeLoading1Dialog();
						showErrorDialog("登陆聊天服务器失败，请尝试重新登陆");
					}
				});
	}

	/**
	 * 检查本地是否有此账号相关的信息 如果有就取出 如果没有就连接服务器获取
	 */
	protected void AfterLoginCheck(final User userInfo) {
		
		//检查好友情况
		/**
		 * 先从em获取好友列表,然后校对数据库
		 * 多的就删除,少的就从服务器获取并存到数据库
		 */

		new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					Log.i("wang", "正在檢查好友情況");
					List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
					Log.i("wang", "从em服务器获取的好友个数:"+usernames.size());
					final UserDaoImpl impl = new UserDaoImpl(ct);
					List<String> checkFriendList = impl.checkFriendList(usernames);
					Log.i("wang", "检查数据库后返回的需要更新的好友个数:"+checkFriendList.size());
					engine.getUserInfoByEmnameList(checkFriendList, new ResultByGetDataListener<InitUserInfo>() {

						@Override
						public void success(ReceiceData<InitUserInfo> result) {
							impl.updateFriendList(result.infos.friendList);
							GlobalParams.user = userInfo;
							Intent intent = new Intent(LoginActivity.this,
									HomeActivity2.class);
							startActivity(intent);
							closeLoading1Dialog();
							finish();
						}

						@Override
						public void error(String error) {
							// TODO Auto-generated method stub

						}
					});
				} catch (HyphenateException e) {
					Log.i("wang","從em服務器獲取好友列表出錯");
					e.printStackTrace();
				}
			}
		}).start();

//		boolean isThereInfo = SharePrefUtil.getBoolean(ct, userInfo.username,
//				false);
//		if (false) {
//			// UserDaoImpl userEngine = new UserDaoImpl(ct);
//			// List<UserCommonInfo> friendList = userEngine.getFriendList();
//			GlobalParams.username = userInfo.username;
//			GlobalParams.user = userInfo;
//			// GlobalParams.friendList = friendList;
//			Intent intent = new Intent(LoginActivity.this, HomeActivity2.class);
//			closeLoading1Dialog();
//			startActivity(intent);
//			finish();
//		} else {
//			initUserInfoFromServer(userInfo);
//		}
		// 本地是否有该账号信息d
		// boolean isHasUserInfo = false;
		// 先默认没有该账号信息
		// showLoadingDialog("正在初始化账号相关信息");

	}

//	private void initUserInfoFromServer(final User userInfo) {
//		setLoadingContent("正在初始化帐号相关信息");
//		engine.initUserInfoData(userInfo.username, userInfo.password,
//				new ResultByGetDataListener<InitUserInfo>() {
//					@Override
//					public void success(ReceiceData<InitUserInfo> result) {
//						if (result.state == 200) {
//							closeLoading1Dialog();
//							// //登录成功
//							GlobalParams.user = userInfo;
//							UserDaoImpl userEngine = new UserDaoImpl(ct);
//							userEngine.saveFriendList(result.infos.friendList);
//							// GlobalParams.friendList =
//							// result.infos.friendList;
//							SharePrefUtil.saveBoolean(ct, userInfo.username,
//									true);
//							Intent intent = new Intent(LoginActivity.this,
//									HomeActivity2.class);
//							startActivity(intent);
//							finish();
//						} else {
//							closeLoading1Dialog();
//							GlobalParams.user = userInfo;
//							Intent intent = new Intent(LoginActivity.this,
//									HomeActivity2.class);
//							startActivity(intent);
//							finish();
//						}
//
//					}
//
//					@Override
//					public void error(String error) {
//						closeLoading1Dialog();
//						showErrorDialog("连接不上服务器");
//					}
//				});
//
//	}

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

	// /**
	// * 对输入的用户名和密码进行字符校验
	// *
	// * @param username
	// * @param password
	// * @return
	// */
	// private boolean checkInputString(String username, String password) {
	//
	// if ("".equals(username)) {
	// // 用户名不能为空
	// // showMsgDialog("用户名不能为空");
	// // showErrorDialog("用户名不能为空");
	// til_username.setError("用户名不能为空");
	//
	// return false;
	// } else if ("".equals(password)) {
	// // 密码不能为空
	// showErrorDialog("密码不能为空");
	// return false;
	// } else if (username.contains(" ")) {
	// // 用户名出现空格
	// showErrorDialog("用户名出现空格");
	// return false;
	// } else if (username.length() < 6) {
	// // 用户名长度少于6位
	// showErrorDialog("用户名长度少于6位");
	// return false;
	// } else if (password.length() < 6) {
	// // 密码少于6位
	// showErrorDialog("密码少于6位");
	// return false;
	//
	// } else {
	// return true;
	// }
	// }

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
