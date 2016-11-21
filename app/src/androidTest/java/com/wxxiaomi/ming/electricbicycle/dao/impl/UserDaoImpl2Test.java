package com.wxxiaomi.ming.electricbicycle.dao.impl;

import android.util.Log;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.wxxiaomi.ming.electricbicycle.EBApplication;
import com.wxxiaomi.ming.electricbicycle.api.exception.ApiException;
import com.wxxiaomi.ming.electricbicycle.bean.User;
import com.wxxiaomi.ming.electricbicycle.bean.format.InitUserInfo;
import com.wxxiaomi.ming.electricbicycle.bean.format.Login;
import com.wxxiaomi.ming.electricbicycle.bean.format.NearByPerson;
import com.wxxiaomi.ming.electricbicycle.bean.format.Register;
import com.wxxiaomi.ming.electricbicycle.model.impl.EmEngine;
import com.wxxiaomi.ming.electricbicycle.support.rx.MyObserver;
import com.wxxiaomi.ming.electricbicycle.support.util.RxUnitTestTools;

import junit.framework.TestCase;

/**
 * Created by 12262 on 2016/10/29.
 */
public class UserDaoImpl2Test extends TestCase {
    public void setUp() throws Exception {
        //super.setUp();
        RxUnitTestTools.openRxTools();
       // EBApplication.getInstance().onCreate();;
    }

    public void testRegisterUser() throws Exception {
        UserDaoImpl2.getInstance().registerUser("201610291001","987987987")
                .subscribe(new MyObserver<Register>() {
                    @Override
                    protected void onError(ApiException ex) {
                        Log.i("wang", "发生错误拉:" + ex.getDisplayMessage());
                    }

                    @Override
                    public void onCompleted() {
                        Log.i("wang", "onCompleted()");
                    }

                    @Override
                    public void onNext(Register register) {
                        Log.i("wang", "onNext:" + register.userInfo.toString());
                    }
                });
    }

    public void testLogin(){
        UserDaoImpl2.getInstance().Login("1226270189","987987987")
                .subscribe(new MyObserver<Login>() {
                    @Override
                    protected void onError(ApiException ex) {
                        Log.i("wang","错误："+ex.getDisplayMessage());
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(Login login) {
                        Log.i("wang","result:"+login.userInfo.toString());
                    }
                });
    }

    public void testLoginFromEm(){
//        EMOptions options = new EMOptions();
//        // 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(false);
//        // 初始化
//        try {
//            EaseUI.getInstance().init(EBApplication.getInstance(), options);
//            Log.i("wang", "初始化em引擎成功");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        EmEngine.getInstance().LoginFromEm("122627018","987987987")
                .subscribe(new MyObserver<Boolean>() {
                    @Override
                    protected void onError(ApiException ex) {
                        Log.i("wang","错误："+ex.getDisplayMessage());
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        Log.i("wang","result:"+aBoolean);
                    }
                });
    }


    public void testGetUserCommonInfoByEmname(){
        UserDaoImpl2.getInstance().getUserCommonInfoByEmname("1226270sha1838")
                .subscribe(new MyObserver<InitUserInfo>() {
                    @Override
                    protected void onError(ApiException ex) {
                        Log.i("wang","错误："+ex.getDisplayMessage());
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(InitUserInfo initUserInfo) {
                        if(initUserInfo.friendList.size()==0){
                            Log.i("wang","搜索不到这个好友");
                        }
                        for(User.UserCommonInfo item :initUserInfo.friendList){
                            Log.i("wang","result:"+item.toString());
                        }

                    }
                });
    }

    public void testGetUserByName(){
        UserDaoImpl2.getInstance().getUserCommonInfoByName("1226270sha1")
                .subscribe(new MyObserver<InitUserInfo>() {
                    @Override
                    protected void onError(ApiException ex) {
                        Log.i("wang","错误："+ex.getDisplayMessage());
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(InitUserInfo initUserInfo) {
                        if(initUserInfo.friendList.size()==0){
                            Log.i("wang","搜索不到这个好友");
                        }
                        for(User.UserCommonInfo item :initUserInfo.friendList){
                            Log.i("wang","result:"+item.toString());
                        }
                    }
                });
    }

    public void testGetNear(){
        UserDaoImpl2.getInstance().getNearPeople(25,23.5457440000,116.3705530000)
                .subscribe(new MyObserver<NearByPerson>() {
                    @Override
                    protected void onError(ApiException ex) {
                        Log.i("wang","错误："+ex.getDisplayMessage());
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(NearByPerson nearByPerson) {
//                        Log.i("wang",nearByPerson.)

                        for(NearByPerson.UserLocatInfo info :  nearByPerson.userLocatList){
                            Log.i("wang",info.toString());
                        }
                    }
                });
    }

    public void testUpLoadHead(){
        UserDaoImpl2.getInstance().upLoadHead("25","/storage/sdcard0/temp/1478072958862.jpg")
                .subscribe(new MyObserver<String>() {
                    @Override
                    protected void onError(ApiException ex) {
                        Log.i("wang","错误："+ex.getDisplayMessage());
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("wang","result="+s);
                    }
                });
    }

}