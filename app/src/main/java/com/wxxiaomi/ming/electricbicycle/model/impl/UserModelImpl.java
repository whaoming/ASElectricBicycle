package com.wxxiaomi.ming.electricbicycle.model.impl;

import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.wxxiaomi.ming.electricbicycle.api.HttpMethods;
import com.wxxiaomi.ming.electricbicycle.api.exception.ApiException;
import com.wxxiaomi.ming.electricbicycle.api.exception.ERROR;
import com.wxxiaomi.ming.electricbicycle.bean.User;
import com.wxxiaomi.ming.electricbicycle.bean.format.InitUserInfo;
import com.wxxiaomi.ming.electricbicycle.bean.format.Login;
import com.wxxiaomi.ming.electricbicycle.bean.format.NearByPerson;
import com.wxxiaomi.ming.electricbicycle.dao.impl.EmDaoImpl;
import com.wxxiaomi.ming.electricbicycle.dao.impl.UserDaoImpl2;
import com.wxxiaomi.ming.electricbicycle.model.base.DAO;
import com.wxxiaomi.ming.electricbicycle.support.rx.MyObserver;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by 12262 on 2016/5/29.
 */
public class UserModelImpl implements DAO<User> {

    @Override
    public boolean cacheAll(List<User> list) {
        return false;
    }

    @Override
    public boolean clearCache() {
        return false;
    }

    @Override
    public void loadFromCache() {

    }

    @Override
    public void loadFromNet() {

    }

    public Observable<Login> Login(String username, String password) {
        return UserDaoImpl2.getInstance().Login(username, password);
    }

    public Observable<Integer> updateFriend() {
        return EmDaoImpl.getInstance().getContactFromEm()
                //核对数据库好友列表，多的删除，少的数据库获取
                .flatMap(new Func1<List<String>, Observable<List<String>>>() {
                    @Override
                    public Observable<List<String>> call(List<String> usernames) {
                        return UserDaoImpl2.getInstance().checkFriendList(usernames);
                    }
                })
                .flatMap(new Func1<List<String>, Observable<InitUserInfo>>() {
                    @Override
                    public Observable<InitUserInfo> call(List<String> missingUsernames) {
                        return UserDaoImpl2.getInstance().getUserListFromByEmList(missingUsernames);
                    }
                })
                .flatMap(new Func1<InitUserInfo, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(InitUserInfo initUserInfo) {
                        return UserDaoImpl2.getInstance().updateFriendList(initUserInfo.friendList);
                    }
                });
    }

    public Observable<Boolean> LoginFromEm(final String username, final String password) {
        return EmDaoImpl.getInstance().LoginFromEm(username, password);
    }

    public Observable<NearByPerson> getNearPeople(int userid, double latitude, double longitude) {
        return UserDaoImpl2.getInstance().getNearPeople(userid, latitude, longitude);
    }

    public Observable<Boolean> addEMListener() {
        Observable<EmEvent<String>> emEventObservable = EmDaoImpl.getInstance().addEmContactObserver();
        Observable<EmEvent<List<EMMessage>>> observable2 = EmDaoImpl.getInstance().addMessageListener();
        return Observable.merge(observable2, emEventObservable)
                .flatMap(new Func1<EmEvent<? extends Object>, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(EmEvent<? extends Object> emEvent) {
                        return Observable.just(true);
                    }
                });

    }

}
