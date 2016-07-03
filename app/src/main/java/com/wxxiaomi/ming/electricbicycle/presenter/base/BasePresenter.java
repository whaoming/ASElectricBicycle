package com.wxxiaomi.ming.electricbicycle.presenter.base;

/**
 * Created by 12262 on 2016/6/15.
 */
public interface BasePresenter<T> {
    void attach(T mView);
    void dettach();
    void onResume();

}
