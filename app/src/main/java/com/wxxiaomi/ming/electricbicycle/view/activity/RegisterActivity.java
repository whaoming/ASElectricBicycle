package com.wxxiaomi.ming.electricbicycle.view.activity;//package com.wxxiaomi.electricbicycle.view.activity;
//
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//import com.wxxiaomi.electricbicycle.GlobalParams;
//import com.wxxiaomi.electricbicycle.R;
//import com.wxxiaomi.electricbicycle.bean.format.Register;
//import com.wxxiaomi.electricbicycle.bean.format.common.ReceiceData;
//import com.wxxiaomi.electricbicycle.engine.UserEngineImpl;
//import com.wxxiaomi.electricbicycle.view.activity.base.BaseActivity;
//
//public class RegisterActivity extends BaseActivity {
//
//	private EditText et_username;
//	private EditText et_password;
//	private EditText et_name;
//	private Button btn_ok;
//	
//	@Override
//	protected void initView() {
//		setContentView(R.layout.activity_register);
//		et_username = (EditText) findViewById(R.id.et_username);
//		et_password = (EditText) findViewById(R.id.et_password);
//		et_name = (EditText) findViewById(R.id.et_name);
//		btn_ok = (Button) findViewById(R.id.btn_ok);
//		btn_ok.setOnClickListener(this);
//	}
//
//	@Override
//	protected void initData() {
//		
//	}
//
//	@Override
//	protected void processClick(View v) {
//		switch (v.getId()) {
//		case R.id.btn_ok:
//			showLoadingDialog("正在注册..");
//			//确定按钮
//			String username = et_username.getText().toString().trim();
//			String password = et_password.getText().toString().trim();
//			String name = et_name.getText().toString().trim();
//			RegisterFromServer(username,password,name);
//			
//			break;
//
//		default:
//			break;
//		}
//		
//	}
//
//	private void RegisterFromServer(final String username, final String password,
//			final String name) {
//		new AsyncTask<String, Void, ReceiceData<Register>>() {
//			@Override
//			protected ReceiceData<Register> doInBackground(String... params) {
//				UserEngineImpl engine = new UserEngineImpl();
//				return engine.Register(username, password, name);
//			}
//
//			@Override
//			protected void onPostExecute(ReceiceData<Register> result) {
//				closeLoadingDialog();
//				if (result!=null) {
//					if(result.state == 200){
//						//登录成功
//						GlobalParams.user = result.infos.userInfo;
//						Intent intent = new Intent(ct,HomeActivity2.class);
//						startActivity(intent);
//						finish();
//					}else{
////						Log.i("wang", "登录失败，错误信息："+result.error);
//						showMsgDialog("注册失败"+result.error);
//					}
//				} else {
//					showMsgDialog("注册失败，连接不上服务器");
////					Log.i("wang", "登录失败，连接不上服务器");
//				}
//			}
//		}.execute();
//		
//	}
//
//}
