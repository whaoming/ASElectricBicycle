package com.wxxiaomi.ming.electricbicycle.common.rx;

import com.wxxiaomi.ming.common.base.AppContext;
import com.wxxiaomi.ming.common.net.ApiException;

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
