package com.wxxiaomi.ming.electricbicycle.core.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wxxiaomi.ming.electricbicycle.core.base.BaseActivity;
import com.wxxiaomi.ming.electricbicycle.core.presenter.RegisterTwoPresenter;
import com.wxxiaomi.ming.electricbicycle.core.ui.RegisterTwoView;

/**
 * Created by 12262 on 2016/6/26.
 */
public class RegisterTwoAct extends BaseActivity<RegisterTwoView,RegisterTwoPresenter> implements RegisterTwoView<RegisterTwoPresenter>{
    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    public RegisterTwoPresenter getPresenter() {
        return null;
    }



    @Override
    public void onClick(View v) {

    }

    @Override
    public Intent getIntentData() {
        return null;
    }
}
