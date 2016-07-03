package com.wxxiaomi.ming.electricbicycle.presenter.callback;

import com.wxxiaomi.ming.electricbicycle.presenter.base.BasePresenter;

/**
 * Created by 12262 on 2016/6/25.
 */
public interface RegisterOnePresenter<T> extends BasePresenter<T> {
    void onLoginClick(String username,String password);
}
