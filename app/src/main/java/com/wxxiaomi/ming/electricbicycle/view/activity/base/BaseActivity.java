package com.wxxiaomi.ming.electricbicycle.view.activity.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.view.custom.LoadingDialog;


public abstract class BaseActivity extends AppCompatActivity implements
		OnClickListener {

	protected Context ct;
	protected View loadingView;
	protected ImageButton rightBtn;
	protected TextView leftImgBtn;
	protected ImageButton rightImgBtn;
	protected TextView titleTv;
	protected TextView rightbutton;
	protected UIHandler handler = new UIHandler(Looper.getMainLooper());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		handler.send
		super.onCreate(savedInstanceState);
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		ct = this;
		setHandler();
		initView();
		initData();
		
	}
	
	  private void setHandler() {
	        handler.setHandler(new IHandler() {
	            public void handleMessage(Message msg) {
	                handler(msg);//有消息就提交给子类实现的方法
	            }
	        });     
	    }
	  //让子类处理消息
	    

	protected void handler(Message msg) {
		switch (msg.what) {
		case 1:
			showLoadingDialog((String)msg.obj);
			break;
		case 2:
			closeLoadingDialog();
			break;
		case 3:
			showMsgDialog((String)msg.obj);
			break;
		case 4:
			setloadingViewContent((String)msg.obj);
			break;
		default:
			break;
		}
	}
	
	
	
	protected void showLoading2Dialog(Activity ct,String content){
		this.ct = ct;
		Message msg = new Message();
		msg.what = 1;
		msg.obj = content;
		handler.sendMessage(msg);
	}
	
	protected void showLoading1Dialog(String content){
		Message msg = new Message();
		msg.what = 1;
		msg.obj = content;
		handler.sendMessage(msg);
	}
	protected void closeLoading1Dialog(){
		Message msg = new Message();
		msg.what = 2;
		handler.sendMessage(msg);
	}
	
	protected void showErrorDialog(String error){
		Message msg = new Message();
		msg.what = 3;
		msg.obj = error;
//		handler(msg);
		handler.sendMessage(msg);
	}
	protected void setLoadingContent(String content){
		Message msg = new Message();
		msg.what = 4;
		msg.obj = content;
//		handler(msg);
		handler.sendMessage(msg);
	}

	protected void initTitleBar() {
	}


	@Override
	public void onClick(View v) {
		processClick(v);
	}
	AlertDialog msgDialog ;
	private  void showMsgDialog(String content){
		msgDialog = new AlertDialog.Builder(ct, R.style.MingDialog).setMessage(content).setPositiveButton("确定", null).create();
		msgDialog.show();
	}
	
//	private void closeMsgDialog(){
//		if(msgDialog != null){
//			msgDialog.dismiss();
//		}
//	}
	
	LoadingDialog dialog;
	/**
	 * 显示加载dialog
	 * @param content  加载dialog显示的内容
	 */
	private void showLoadingDialog(String content){
		Log.i("wang", "name="+ct.getApplicationInfo().className);
		dialog = new LoadingDialog(ct).builder().setMessage(content);
		dialog.show();
	}
	
	/**
	 * 关闭加载dialog
	 */
	private void closeLoadingDialog(){
		if(dialog != null){
			Log.i("wang", "closeLoadingDialog2");
			dialog.dismiss();
		}
	}
	
	private void setloadingViewContent(String content){
		if(dialog != null){
			dialog.setMessage(content);
			
		}
	}

	
	protected abstract void initView();

	protected abstract void initData();

	protected abstract void processClick(View v);


	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	
	

	
}
