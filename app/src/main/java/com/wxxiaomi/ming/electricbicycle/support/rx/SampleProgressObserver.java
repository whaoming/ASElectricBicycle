package com.wxxiaomi.ming.electricbicycle.support.rx;


import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.api.exception.ApiException;
import com.wxxiaomi.ming.electricbicycle.view.custom.LoadingDialog;

/**
 * Created by 12262 on 2016/6/5.
 */
public abstract class SampleProgressObserver<T> extends MyObserver<T>{

    private LoadingDialog dialog;
    private AlertDialog msgDialog;
    private Context context;


    public SampleProgressObserver(Context context) {
        dialog = new LoadingDialog(context).builder().setMessage("正在加载中");
        this.context = context;
    }

    @Override
    public void onStart() {
        dialog.show();
        super.onStart();
    }

    @Override
    protected void onError(ApiException ex) {
        Log.i("wang","SampleProgressObserver-onError-"+ex.getDisplayMessage());
        ex.printStackTrace();;
        dialog.dismiss();
        msgDialog = new AlertDialog.Builder(context, R.style.MingDialog).setMessage(ex.getDisplayMessage()).setPositiveButton("确定", null).create();
        msgDialog.show();
    }

    @Override
    public void onCompleted() {
        dialog.dismiss();
    }

}
