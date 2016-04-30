package com.wxxiaomi.ming.electricbicycle.view.custom;




import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.wxxiaomi.ming.electricbicycle.R;

public class EditableDialog {

	private AlertDialog dialog;
	private Context ct;
	private EditText et_et;
	private PositiveButtonOnClick  lis;

	public EditableDialog(Context ct) {
		super();
		this.ct = ct;
	}

	@SuppressLint("InflateParams")
	public EditableDialog builder() {
		View view = LayoutInflater.from(ct).inflate(R.layout.dialog_editable,
				null);

		et_et = (EditText) view.findViewById(R.id.et_et);
		dialog = new AlertDialog.Builder(ct, R.style.MingDialog).setView(view).setNegativeButton("cancel",null)
				.setPositiveButton("ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						lis.onClick(dialog,et_et.getText().toString().trim());
//						dismiss();
					}
				}).create();
		return this;
	}

	public EditableDialog setOnPositiveButtonClick(PositiveButtonOnClick lis){
		this.lis  = lis;
		return this;
	}

	public interface PositiveButtonOnClick{
		void onClick(DialogInterface dialog,String content);
	}

	public EditableDialog setTitle(String title){
		dialog.setTitle(title);
		return this;
	}

	public EditableDialog setHint(String hint){
		et_et.setHint(hint);
		return this;
	}
	
	public EditableDialog show(){
		dialog.show();
		return this;
	}
	public void dismiss(){
		dialog.dismiss();
	}
	public EditableDialog setMessage(String msg){
		dialog.setMessage(msg);
//		dialog.
		return this;
	}
}
