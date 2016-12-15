package com.wxxiaomi.ming.electricbicycle;


import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.baidu.mapapi.SDKInitializer;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.wxxiaomi.ming.electricbicycle.support.easemob.EmHelper2;

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
        EmHelper2.getInstance().init(this);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        sRefWatcher = LeakCanary.install(this);
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
