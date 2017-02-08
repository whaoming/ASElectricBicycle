//package com.wxxiaomi.ming.electricbicycle.core.presenter.impl;
//
//import android.os.Bundle;
//import android.util.Log;
//
//import com.wxxiaomi.ming.electricbicycle.ConstantValue;
//import com.wxxiaomi.ming.electricbicycle.dao.bean.format.Register;
//import com.wxxiaomi.ming.electricbicycle.core.MainActivity;
//import com.wxxiaomi.ming.electricbicycle.core.presenter.base.BasePreImpl;
//import com.wxxiaomi.ming.electricbicycle.core.presenter.RegisterOnePresenter;
//import com.wxxiaomi.ming.electricbicycle.dao.db.UserService;
//import com.wxxiaomi.ming.electricbicycle.common.GlobalManager;
//import com.wxxiaomi.ming.electricbicycle.common.rx.ProgressObserver;
//import com.wxxiaomi.ming.electricbicycle.core.activity.view.RegisterOneView;
//
//
///**
// * Created by 12262 on 2016/6/25.
// */
//public class RegisterOnePresenterImpl extends BasePreImpl<RegisterOneView> implements RegisterOnePresenter<RegisterOneView>{
//    private int carid;
//
//    @Override
//    public void init() {
//        if(ConstantValue.openScan) {
//            carid = mView.getIntentData().getIntExtra("carid", 0);
//        }
//    }
//
//    @Override
//    public void onLoginClick(String username, String password) {
//        UserService.getInstance().registerUser(username,password)
//                .subscribe(new ProgressObserver<Register>(mView.getContext()) {
//                    @Override
//                    public void onNext(Register register) {
//                        Bundle bundle = new Bundle();
////                        bundle.putSerializable("userInfo", register.userInfo);
////                        bundle.putInt("carid", carid);
////                        mView.runActivity(RegisterTwoAct.class,bundle,true);
//                        Log.i("wang","userinfo="+register.userInfo.toString());
////                        GlobalParams.user = register.userInfo;
//                        GlobalManager.getInstance().savaUser(register.userInfo);
//                        mView.runActivity(MainActivity.class,null,false);
//                    }
//                });
//
////        UserDaoImpl2.getInstance().registerUser(username, password)
////                .su
//    }
//}
