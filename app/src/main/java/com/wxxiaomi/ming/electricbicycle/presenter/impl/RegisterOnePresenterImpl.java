package com.wxxiaomi.ming.electricbicycle.presenter.impl;

import android.os.Bundle;

import com.wxxiaomi.ming.electricbicycle.bean.format.Register;
import com.wxxiaomi.ming.electricbicycle.dao.impl.UserDaoImpl2;
import com.wxxiaomi.ming.electricbicycle.presenter.base.BasePresenterImpl;
import com.wxxiaomi.ming.electricbicycle.presenter.callback.RegisterOnePresenter;
import com.wxxiaomi.ming.electricbicycle.support.rx.SampleProgressObserver;
import com.wxxiaomi.ming.electricbicycle.ui.view.RegisterOneView;
import com.wxxiaomi.ming.electricbicycle.view.activity.RegisterTwoActivity;

/**
 * Created by 12262 on 2016/6/25.
 */
public class RegisterOnePresenterImpl extends BasePresenterImpl<RegisterOneView> implements RegisterOnePresenter<RegisterOneView>{
    private int carid;
    @Override
    public void attach(RegisterOneView mView) {
        super.attach(mView);
        carid = mView.getIntentData().getIntExtra("carid", 0);
    }

    @Override
    public void onLoginClick(String username, String password) {
        UserDaoImpl2.getInstance().registerUser(username,password)
                .subscribe(new SampleProgressObserver<Register>(mView.getContext()) {
                    @Override
                    public void onNext(Register register) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userInfo", register.userInfo);
                        bundle.putInt("carid", carid);
                        mView.runActivity(RegisterTwoActivity.class,bundle,true);
                    }
                });
    }
}
