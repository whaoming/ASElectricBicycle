package com.wxxiaomi.ming.electricbicycle;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.wxxiaomi.ming.electricbicycle.api.HttpMethods;
import com.wxxiaomi.ming.electricbicycle.api.exception.ApiException;
import com.wxxiaomi.ming.electricbicycle.bean.User;
import com.wxxiaomi.ming.electricbicycle.bean.format.InitUserInfo;
import com.wxxiaomi.ming.electricbicycle.bean.format.Login;
import com.wxxiaomi.ming.electricbicycle.bean.format.Register;
import com.wxxiaomi.ming.electricbicycle.dao.impl.InviteMessgeDaoImpl2;
import com.wxxiaomi.ming.electricbicycle.dao.impl.UserDaoImpl;
import com.wxxiaomi.ming.electricbicycle.dao.impl.UserDaoImpl2;
import com.wxxiaomi.ming.electricbicycle.support.rx.MyObserver;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {


    public ApplicationTest() {
        super(Application.class);


    }

    public void testInitMapSdk(){
        Log.i("wang","testInitMapSdk()");
//        SDKInitializer.initialize(getApplication().getApplicationContext());
    }

    public void testLoginFromServer(){
        HttpMethods.getInstance().login("122627018", "987987987")
        .subscribe(new MyObserver<Login>() {
            @Override
            protected void onError(ApiException ex) {
                Log.i("wang",ex.getDisplayMessage());
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onNext(Login login) {
                Log.i("wang","login success");
            }
        })
        ;
    }

    public void testGetFriendListFromDatabase(){
        GlobalParams.username = "122627018";
        List<User.UserCommonInfo> friendList = UserDaoImpl2.getInstance().getFriendList();
        Log.i("wang","friendList.size="+friendList.size());

    }

    public void testGetUserInfoBtEmname(){
        Observable<Integer> integerObservable = UserDaoImpl2.getInstance().getUserCommonInfoByEmname("emdemo3")
                .flatMap(new Func1<InitUserInfo, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(InitUserInfo initUserInfo) {
                        Log.i("wang", initUserInfo.friendList.get(0).toString());
                        return Observable.just(1);
                    }
                });
        Observable<Integer> objectObservable2 = InviteMessgeDaoImpl2.getInstance().getUnreadNotifyCount();
        Observable.merge(integerObservable,objectObservable2)
                .subscribe(new MyObserver<Integer>() {
                    @Override
                    protected void onError(ApiException ex) {

                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }
                });
    }

    public void testGetTempUser(){

    }

    public void  testGetUserByNameFromServer(){
        EBApplication.applicationContext = getContext();
        UserDaoImpl2.getInstance().getUserCommonInfoByName("王先生")
                .subscribe(new MyObserver<InitUserInfo>() {
                    @Override
                    protected void onError(ApiException ex) {
                        Log.i("wang",ex.getDisplayMessage());
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(InitUserInfo initUserInfo) {
                        Log.i("wang",initUserInfo.friendList.get(0).toString());
                    }
                });
    }

    public void testRegister(){
        Log.i("wang","testRegister");
        EBApplication.applicationContext = getContext();
//        UserDaoImpl2.getInstance().registerUser("201610290803","987987987")
        HttpMethods.getInstance().registerUser("201610290837","987987987")
                .subscribe(new Observer<Register>() {

                    @Override
                    public void onCompleted() {
                        Log.i("wang","onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("wang","发生错误拉:"+e.toString());
                    }

                    @Override
                    public void onNext(Register register) {
                        Log.i("wang","onNext:"+register.toString());
                    }
                });
    }
}