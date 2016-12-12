package com.wxxiaomi.ming.electricbicycle.ui.presenter.base;

/**
 * Created by 12262 on 2016/10/18.
 * 初级接口
 */
public interface BasePre<T> {

    void init();
    void onViewResume();
    void attach(T mview);
    void onViewDestory();
}
