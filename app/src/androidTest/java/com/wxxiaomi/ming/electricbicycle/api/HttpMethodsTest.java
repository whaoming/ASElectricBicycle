package com.wxxiaomi.ming.electricbicycle.api;

import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.common.util.RxUnitTestTools;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.dao.db.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


/**
 * Created by Administrator on 2016/12/7.
 */
public class HttpMethodsTest {

    public void setUp() throws Exception {
        RxUnitTestTools.openRxTools();
    }
    @org.junit.Test
    public void testUpdateUserFriend(){
        Map<String,String> pars = new HashMap<>();
        pars.put("name","Mr.M");
        pars.put("head","asdasdsa");
        HttpMethods.getInstance().updateuserFriend(pars)
                .subscribe(new Action1<List<UserCommonInfo>>() {
                    @Override
                    public void call(List<UserCommonInfo> userCommonInfos) {

                    }
                });
    }
    @org.junit.Test
    public void testUpdateUserInfo(){
        Map<String,String> pars = new HashMap<>();
        UserCommonInfo info = new UserCommonInfo();
        info.head = "Im.head";
        info.emname = "asd";
        info.name = "asd";
        pars.put("name","Mr.M");
        pars.put("head","asdasdsa");
        HttpMethods.getInstance().updateUserInfo3(info)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("wang","result:"+s);
                    }
                });
    }

    @org.junit.Test
    public void getOption() throws Exception {
//       HttpMethods.getInstance().getOption(25)
//               .subscribe(new Observer<List<Option>>() {
//                   @Override
//                   public void onCompleted() {
//
//                   }
//
//                   @Override
//                   public void onError(Throwable e) {
//                        e.printStackTrace();;
//                   }
//
//                   @Override
//                   public void onNext(List<Option> options) {
//                       for(Option o : options){
//                           Log.i("wang",o.toString());
//                       }
//                   }
//               });
        UserService.getInstance().getUserOptions(25)
                .subscribe(new Observer<List<Option>>() {
                   @Override
                   public void onCompleted() {

                   }

                   @Override
                   public void onError(Throwable e) {
                        e.printStackTrace();;
                   }

                   @Override
                   public void onNext(List<Option> options) {
                       for(Option o : options){
                           Log.i("wang",o.toString());
                       }
                   }
               });
    }

}