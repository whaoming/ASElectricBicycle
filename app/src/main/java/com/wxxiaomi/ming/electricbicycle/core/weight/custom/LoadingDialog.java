package com.wxxiaomi.ming.electricbicycle.core.weight.custom;




import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.wxxiaomi.ming.electricbicycle.R;

public class LoadingDialog {

	private AlertDialog dialog;
	private Context ct;

	public LoadingDialog(Context ct) {
		super();
		this.ct = ct;
	}

	@SuppressLint("InflateParams")
	public LoadingDialog builder() {
		View view = LayoutInflater.from(ct).inflate(R.layout.dialog_loading,
				null);
		dialog = new AlertDialog.Builder(ct, R.style.MingDialog).setView(view).setCancelable(false).create();
		return this;
	}
	
	public LoadingDialog show(){
		dialog.show();
		return this;
	}
	public void dismiss(){
		dialog.dismiss();
	}
	public LoadingDialog setMessage(String msg){
		dialog.setMessage(msg);
//		dialog.
		return this;
	}
}
