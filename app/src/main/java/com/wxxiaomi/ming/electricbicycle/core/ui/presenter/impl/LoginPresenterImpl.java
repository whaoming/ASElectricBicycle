package com.wxxiaomi.ming.electricbicycle.core.ui.presenter.impl;

import android.support.design.widget.TextInputLayout;

import com.wxxiaomi.ming.electricbicycle.ConstantValue;
import com.wxxiaomi.ming.electricbicycle.api.HttpMethods;
import com.wxxiaomi.ming.electricbicycle.common.util.AppManager;
import com.wxxiaomi.ming.electricbicycle.core.ui.view.activity.RegisterActivity;
import com.wxxiaomi.ming.electricbicycle.core.ui.base.BasePreImpl;
import com.wxxiaomi.ming.electricbicycle.core.ui.view.activity.HomeActivity;
import com.wxxiaomi.ming.electricbicycle.core.ui.presenter.LoginPresenter;
import com.wxxiaomi.ming.electricbicycle.dao.UserService;
import com.wxxiaomi.ming.electricbicycle.common.rx.SampleProgressObserver;
import com.wxxiaomi.ming.electricbicycle.core.ui.view.LoginView;
import com.wxxiaomi.ming.electricbicycle.common.util.MyUtils;
import com.wxxiaomi.ming.electricbicycle.dao.bean.format.Login;

import rx.functions.Action1;


/**
 * Created by 12262 on 2016/6/5.
 * 登陆页面的控制器
 */
public class LoginPresenterImpl extends BasePreImpl<LoginView> implements LoginPresenter<LoginView> {

    @Override
    public void init() {

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
           sendRequest(username,password);

        }
    }

    private void sendRequest(String username, String password) {
        UserService.getInstance().Login(username, password,ConstantValue.isEMOpen)
                .subscribe(new SampleProgressObserver<Integer>(mView.getContext()) {
                    @Override
                    public void onNext(Integer integer) {
                        AppManager.getAppManager().finishActivity(RegisterActivity.class);
                        mView.runActivity(HomeActivity.class, null, true);
                    }
                });
//        HttpMethods.getInstance().login2(username, password)
//                .subscribe(new Action1<Login>() {
//                    @Override
//                    public void call(Login login) {
//
//                    }
//                });
    }

    @Override
    public void onDebugBtnClick() {
        sendRequest("122627018","987987987");
    }


}
