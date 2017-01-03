package com.wxxiaomi.ming.electricbicycle;


import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.baidu.mapapi.SDKInitializer;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.wxxiaomi.ming.electricbicycle.improve.im.ImHelper1;
import com.wxxiaomi.ming.electricbicycle.service.AccountHelper;
import com.wxxiaomi.ming.electricbicycle.service.PreferenceManager;
import com.wxxiaomi.ming.electricbicycle.service.UserFunctionProvider;

/**
 * 程序入口
 *
 * @author Administrator
 */
public class EBApplication extends Application {
    public static Context applicationContext;
    private static EBApplication instance;
    public  static RefWatcher sRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        instance = this;
        SDKInitializer.initialize(getApplicationContext());
        ImHelper1.getInstance().init(this);
        AccountHelper.init(this);
        PreferenceManager.init(getApplicationContext());
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        sRefWatcher = LeakCanary.install(this);
//        new Thread(){
//            @Override
//            public void run() {
//                ImHelper1.getInstance().setFriends(UserFunctionProvider.getInstance().getEFriends());
//                super.run();
//            }
//        }.start();
    }

//    private void initEM() {
//        EMOptions options = new EMOptions();
//        // 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(false);
//        // 初始化
//        try {
//            EaseUI.getInstance().init(getApplicationContext(), options);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }

    public static EBApplication getInstance() {
        return instance;
    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


}
