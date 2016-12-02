package com.wxxiaomi.ming.electricbicycle.core.ui.presenter;


import android.support.design.widget.TextInputLayout;

import com.wxxiaomi.ming.electricbicycle.core.ui.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.core.ui.base.BaseView;


/**
 * Created by 12262 on 2016/6/5.
 */
public interface LoginPresenter<V extends BaseView> extends BasePre<V> {

    void onLoginBtnClick(TextInputLayout til_username, TextInputLayout til_password);
    //void LoginFromSerer(String username,String password);
    void onDebugBtnClick();
}
