package com.wxxiaomi.ming.electricbicycle.support.rx;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.wxxiaomi.ming.electricbicycle.api.exp.ApiException;
import com.wxxiaomi.ming.electricbicycle.improve.common.AppContext;

/**
 * Created by Administrator on 2017/1/3.
 */

public abstract class ToastObserver<T> extends MyObserver<T>{
//    private View view;
//    public ToastObserver(View view){
//        this.view = view;
//    }

    @Override
    protected void onError(ApiException ex) {
//        Snackbar.make(view,ex.getDisplayMessage(),Snackbar.LENGTH_SHORT).show();
        AppContext.showToast(ex.getDisplayMessage());
    }

    @Override
    public void onCompleted() {
    }
}
