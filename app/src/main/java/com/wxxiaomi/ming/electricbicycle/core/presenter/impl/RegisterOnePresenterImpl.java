package com.wxxiaomi.ming.electricbicycle.core.presenter.impl;

import android.os.Bundle;
import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.ConstantValue;
import com.wxxiaomi.ming.electricbicycle.GlobalParams;
import com.wxxiaomi.ming.electricbicycle.bean.format.Register;
import com.wxxiaomi.ming.electricbicycle.core.MainActivity;
import com.wxxiaomi.ming.electricbicycle.core.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.core.base.BasePreImpl;
import com.wxxiaomi.ming.electricbicycle.dao.impl.UserDaoImpl2;
import com.wxxiaomi.ming.electricbicycle.core.presenter.RegisterOnePresenter;
import com.wxxiaomi.ming.electricbicycle.support.GlobalManager;
import com.wxxiaomi.ming.electricbicycle.support.rx.SampleProgressObserver;
import com.wxxiaomi.ming.electricbicycle.core.ui.activity.RegisterTwoAct;
import com.wxxiaomi.ming.electricbicycle.core.ui.RegisterOneView;

import rx.Observable;


/**
 * Created by 12262 on 2016/6/25.
 */
public class RegisterOnePresenterImpl extends BasePreImpl<RegisterOneView> implements RegisterOnePresenter<RegisterOneView>{
    private int carid;

    @Override
    public void init() {
        if(ConstantValue.openScan) {
            carid = mView.getIntentData().getIntExtra("carid", 0);
        }
    }

    @Override
    public void onLoginClick(String username, String password) {
        UserDaoImpl2.getInstance().registerUser(username,password)
                .subscribe(new SampleProgressObserver<Register>(mView.getContext()) {
                    @Override
                    public void onNext(Register register) {
                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("userInfo", register.userInfo);
//                        bundle.putInt("carid", carid);
//                        mView.runActivity(RegisterTwoAct.class,bundle,true);
                        Log.i("wang","userinfo="+register.userInfo.toString());
//                        GlobalParams.user = register.userInfo;
                        GlobalManager.getInstance().savaUser(register.userInfo);
                        mView.runActivity(MainActivity.class,null,false);
                    }
                });

//        UserDaoImpl2.getInstance().registerUser(username, password)
//                .su
    }
}
