package com.wxxiaomi.ming.electricbicycle.ui.presenter.impl;

import android.support.design.widget.TextInputLayout;

import com.wxxiaomi.ming.electricbicycle.ConstantValue;
import com.wxxiaomi.ming.electricbicycle.common.util.AppManager;
import com.wxxiaomi.ming.electricbicycle.common.util.UniqueUtil;
import com.wxxiaomi.ming.electricbicycle.service.UserFunctionProvider;
import com.wxxiaomi.ming.electricbicycle.ui.activity.RegisterActivity;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.base.BasePreImpl;
import com.wxxiaomi.ming.electricbicycle.ui.activity.HomeActivity;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.LoginPresenter;
import com.wxxiaomi.ming.electricbicycle.support.rx.ProgressObserver;
import com.wxxiaomi.ming.electricbicycle.ui.activity.view.LoginView;
import com.wxxiaomi.ming.electricbicycle.common.util.MyUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by 12262 on 2016/6/5.
 * 登陆页面的控制器
 */
public class LoginPresenterImpl extends BasePreImpl<LoginView> implements LoginPresenter<LoginView> {
    UniqueUtil util;

    @Override
    public void init() {
        util = new UniqueUtil(mView.getContext());
    }

    private boolean checkFormat(TextInputLayout strLayout) {
        assert strLayout.getEditText() != null;
        String str = strLayout.getEditText().getText().toString().trim();
        if ("".equals(str)) {
            strLayout.setError("不能为空");
            return false;
        } else if (str.contains(" ")) {
            strLayout.setError("出现非法字符");
            return false;
        } else if (MyUtils.checkChainse(str)) {
            strLayout.setError("不能包含中文");
            return false;
        } else if (str.length() < 6) {
            strLayout.setError("长度小于6");
            return false;
        } else {
            strLayout.setEnabled(false);
            return true;
        }
    }


    @Override
    public void onLoginBtnClick(TextInputLayout til_username, TextInputLayout til_password) {
        if (checkFormat(til_username) && checkFormat(til_password)) {
            assert til_username.getEditText() != null;
            String username = til_username.getEditText().getText().toString()
                    .trim();
            String password = til_password.getEditText().getText().toString()
                    .trim();
            sendRequest(username, password);

        }
    }

    private void sendRequest(String username, String password) {
        String uniqueID = util.getUniqueID();
        UserFunctionProvider.getInstance().HandLogin(username, password,ConstantValue.isEMOpen,uniqueID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressObserver<Integer>(mView.getContext()) {
                    @Override
                    public void onNext(Integer integer) {
                        AppManager.getAppManager().finishActivity(RegisterActivity.class);
                        mView.runActivity(HomeActivity.class, null, true);
                    }
                });
    }

    @Override
    public void onDebugBtnClick() {
        sendRequest("122627018", "987987987");
    }

    public void loginEM(String username,String password){
//        UserFunctionProvider.getInstance().HandLogin()
//        ImService.Login(username,password)
//                .subscribe(new ProgressObserver<Boolean>(mView.getContext()) {
//                    @Override
//                    public void onNext(Boolean aBoolean) {
//                        Log.i("wang","登录em:"+aBoolean);
//                    }
//                });
//        ImHelper.getInstance().LoginFromEm(username,password)
//                .subscribe(new ProgressObserver<Boolean>(mView.getContext()) {
//                    @Override
//                    public void onNext(Boolean aBoolean) {
//                        Log.i("wang","登录em:"+aBoolean);
//                    }
//                });
//        return rx.Observable.create(new rx.Observable.OnSubscribe<Boolean>() {
//
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//
//            }
//        });
//        ImService.startLogin(username, password, new ImService.doSome() {
//            @Override
//            public void returnData(Object data) {
//
//            }
//        });
    }


}
