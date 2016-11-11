package com.wxxiaomi.ming.electricbicycle.core.presenter.impl;

import com.wxxiaomi.ming.electricbicycle.ConstantValue;
import com.wxxiaomi.ming.electricbicycle.core.base.BasePreImpl;
import com.wxxiaomi.ming.electricbicycle.core.presenter.WelcomePre;
import com.wxxiaomi.ming.electricbicycle.core.ui.WelcomeView;
import com.wxxiaomi.ming.electricbicycle.core.ui.activity.LoginActivity;
import com.wxxiaomi.ming.electricbicycle.core.ui.activity.RegisterOneAct;

/**
 * Created by 12262 on 2016/10/28.
 */

public class WelcomePresenterImpl extends BasePreImpl<WelcomeView> implements WelcomePre<WelcomeView> {
    @Override
    public void init() {
        mView.showBtn();
    }

    @Override
    public void onRegisterClick() {
        if(ConstantValue.openScan){

        }else{
            mView.runActivity(RegisterOneAct.class,null,false);
        }
    }

    @Override
    public void onLoginClick() {
        mView.runActivity(LoginActivity.class,null,false);
    }
}
