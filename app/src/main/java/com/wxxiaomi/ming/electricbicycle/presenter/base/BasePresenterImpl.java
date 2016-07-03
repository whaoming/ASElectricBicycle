package com.wxxiaomi.ming.electricbicycle.presenter.base;

/**
 * Created by 12262 on 2016/6/15.
 */
public abstract class BasePresenterImpl<T> {
    public T mView;

    public void attach(T mView) {
        this.mView = mView;
    }

    public void dettach() {
        mView = null;
    }

    public void onResume(){

    }
}
