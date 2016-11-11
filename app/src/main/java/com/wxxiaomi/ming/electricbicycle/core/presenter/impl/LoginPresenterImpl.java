package com.wxxiaomi.ming.electricbicycle.core.presenter.impl;

import android.support.design.widget.TextInputLayout;

import com.wxxiaomi.ming.electricbicycle.ConstantValue;
import com.wxxiaomi.ming.electricbicycle.GlobalParams;
import com.wxxiaomi.ming.electricbicycle.bean.User;
import com.wxxiaomi.ming.electricbicycle.bean.format.Login;
import com.wxxiaomi.ming.electricbicycle.core.base.BasePreImpl;
import com.wxxiaomi.ming.electricbicycle.core.ui.activity.HomeActivity;
import com.wxxiaomi.ming.electricbicycle.dao.impl.UserDaoImpl2;
import com.wxxiaomi.ming.electricbicycle.model.impl.EmEngine;
import com.wxxiaomi.ming.electricbicycle.core.presenter.LoginPresenter;
import com.wxxiaomi.ming.electricbicycle.support.GlobalManager;
import com.wxxiaomi.ming.electricbicycle.support.rx.SampleProgressObserver;
import com.wxxiaomi.ming.electricbicycle.core.ui.LoginView;
import com.wxxiaomi.ming.electricbicycle.support.util.MyUtils;

import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

/**
 * Created by 12262 on 2016/6/5.
 */
public class LoginPresenterImpl extends BasePreImpl<LoginView> implements LoginPresenter<LoginView> {
    private User tempUser;

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
        Observable<Login> flag = UserDaoImpl2.getInstance().Login(username, password);
        if (ConstantValue.isEMOpen) {
            flag
                    //登录到em服务器
                    .flatMap(new Func1<Login, Observable<Boolean>>() {
                        @Override
                        public Observable<Boolean> call(Login login) {
                            tempUser = login.userInfo;
                            return EmEngine.getInstance().LoginFromEm(login.userInfo.username, login.userInfo.password);
                        }
                    })
                    //更新好友列表
                    .flatMap(new Func1<Boolean, Observable<Integer>>() {
                        @Override
                        public Observable<Integer> call(Boolean aBoolean) {
                            return EmEngine.getInstance().updateFriend();
                        }
                    })
                    .subscribe(new SampleProgressObserver<Integer>(mView.getContext()) {
                        @Override
                        public void onNext(Integer flag) {
//                            GlobalParams.user = tempUser;
                            GlobalManager.getInstance().savaUser(tempUser);
                            mView.runActivity(HomeActivity.class, null, true);
                        }
                    })
            ;
        } else {
            flag.subscribe(new SampleProgressObserver<Login>(mView.getContext()) {
                @Override
                public void onNext(Login login) {
                    GlobalManager.getInstance().savaUser(login.userInfo);
                    mView.runActivity(HomeActivity.class, null, true);
                }
            });
        }
    }

    @Override
    public void onDebugBtnClick() {
        sendRequest("122627018","987987987");
    }
}
