package com.wxxiaomi.ming.electricbicycle.service;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.wxxiaomi.ming.electricbicycle.db.bean.User;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.improve.im.ImHelper1;
import com.wxxiaomi.ming.electricbicycle.improve.common.SharedPreferencesHelper;

/**
 * Created by Administrator on 2017/1/3.
 */

public class AccountHelper {
    private User user;
    private Application application;
    private static AccountHelper instances;

    private AccountHelper(Application application) {
        this.application = application;
    }

    public static void init(Application application) {
        instances = new AccountHelper(application);
    }

    public static boolean isLogin() {
//        Log.i("wang","getUserId():"+getUserId());
        return getUserId() > 0 && !TextUtils.isEmpty(getCookie());
    }

    public static long getUserId() {
        return getUser().id;
    }

    public synchronized static User getUser() {
        if (instances == null) {
//            TLog.error("AccountHelper instances is null, you need call init() method.");
            return new User();
        }
        if (instances.user == null)
            instances.user = SharedPreferencesHelper.loadFormSource(instances.application, User.class);
        if (instances.user == null)
            instances.user = new User();
        return instances.user;
    }

    public static String getCookie() {
        String cookie = getUser().longToken;
        return cookie == null ? "" : cookie;
    }

    public static void updateUserCache(User user) {
        if (user == null)
            return;
        // 保留Cookie信息
//        if (TextUtils.isEmpty(user.shortToken) && instances.user != user)
//            user.setCookie(instances.user.getCookie());
        if(!TextUtils.isEmpty(instances.user.shortToken)){
            user.shortToken = instances.user.shortToken;
        }
        if(!TextUtils.isEmpty(instances.user.longToken)){
            user.longToken = instances.user.longToken;
        }
        instances.user = user;
        SharedPreferencesHelper.save(instances.application, user);
    }

    public static boolean isShortCookieEmpty(){
        return TextUtils.isEmpty(getUser().shortToken);
    }

    public static String getShortCookie(){
        return instances.user.shortToken;
    }

    public static String getLongCookie(){
        return instances.user.longToken;
    }

    public static void updateSCookie(String scookie){
        if (!TextUtils.isEmpty(scookie)){
            instances.user.shortToken = scookie;
            updateUserCache(instances.user);
        }
    }

    public static void updateLCookie(String longToken){
        if (!TextUtils.isEmpty(longToken)){
            instances.user.longToken = longToken;
            updateUserCache(instances.user);
        }
    }

    public static UserCommonInfo getAccountInfo(){
        return  instances.user.userCommonInfo;
    }

    public static void logout(final View view, final Runnable runnable){
        //清除缓存
        instances.user = null;
        SharedPreferencesHelper.remove(instances.application, User.class);
        ImHelper1.getInstance().logout();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.removeCallbacks(this);
                User user = SharedPreferencesHelper.load(instances.application, User.class);
                // 判断当前用户信息是否清理成功
                if (user == null || user.id <= 0) {
//                    clearAndPostBroadcast(instances.application);
                    runnable.run();
                } else {
                    view.postDelayed(this, 200);
                }
            }
        }, 200);
    }



}
