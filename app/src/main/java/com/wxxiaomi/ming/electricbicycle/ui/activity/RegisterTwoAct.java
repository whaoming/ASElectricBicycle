package com.wxxiaomi.ming.electricbicycle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wxxiaomi.ming.electricbicycle.presenter.callback.RegisterTwoPresenter;
import com.wxxiaomi.ming.electricbicycle.ui.base.BaseMvpActivity;
import com.wxxiaomi.ming.electricbicycle.ui.view.RegisterTwoView;

/**
 * Created by 12262 on 2016/6/26.
 */
public class RegisterTwoAct extends BaseMvpActivity<RegisterTwoView,RegisterTwoPresenter<RegisterTwoView>> implements RegisterTwoView{
    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected RegisterTwoPresenter<RegisterTwoView> initPre() {
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
