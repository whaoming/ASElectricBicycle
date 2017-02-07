package com.wxxiaomi.ming.electricbicycle.common.rx;

import android.content.Context;

import com.wxxiaomi.ming.common.net.ApiException;
import com.wxxiaomi.ming.electricbicycle.ui.weight.custom.SimplexToast;

/**
 * Created by Administrator on 2017/1/3.
 */

public abstract class ToastObserver2<T> extends MyObserver<T>{
    private Context context;
    public ToastObserver2(Context context){
        this.context = context;
    }

    @Override
    protected void onError(ApiException ex) {
//        Snackbar.make(view,ex.getDisplayMessage(),Snackbar.LENGTH_SHORT).show();
        SimplexToast.show(context,ex.getDisplayMessage());
    }

    @Override
    public void onCompleted() {
    }
}
