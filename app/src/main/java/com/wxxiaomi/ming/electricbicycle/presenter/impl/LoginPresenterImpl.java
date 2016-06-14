package com.wxxiaomi.ming.electricbicycle.presenter.impl;

import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.GlobalParams;
import com.wxxiaomi.ming.electricbicycle.bean.User;
import com.wxxiaomi.ming.electricbicycle.bean.format.Login;
import com.wxxiaomi.ming.electricbicycle.model.impl.UserModelImpl;
import com.wxxiaomi.ming.electricbicycle.presenter.LoginPresenter;
import com.wxxiaomi.ming.electricbicycle.presenter.base.BasePresenter;
import com.wxxiaomi.ming.electricbicycle.support.rx.SampleProgressObserver;
import com.wxxiaomi.ming.electricbicycle.ui.view.LoginView;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by 12262 on 2016/6/5.
 */
public class LoginPresenterImpl extends BasePresenter<LoginView,UserModelImpl> implements LoginPresenter {
    private User tempUser;

    public LoginPresenterImpl(LoginView loginView) {
        super(loginView);
    }

    @Override
    public void LoginFromSerer(String username, String password) {
        model.Login(username,password)
                //登录到em服务器
                .flatMap(new Func1<Login, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(Login login) {
                        tempUser = login.userInfo;
                        return model.LoginFromEm(login.userInfo.username,login.userInfo.password);
                    }
                })
                //更新好友列表
                .flatMap(new Func1<Boolean, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Boolean aBoolean) {
                        return model.updateFriend();
                    }
                })
                .subscribe(new SampleProgressObserver<Integer>(view.getContext()) {
                    @Override
                    public void onNext(Integer login) {
                        Log.i("wang","presenter登录成功");
                        GlobalParams.user = tempUser;
                        view.runMainAct();
                    }
                })
        ;
    }

    @Override
    public void onViewCreate() {

    }

    @Override
    public void onViewDestory() {

    }
}
