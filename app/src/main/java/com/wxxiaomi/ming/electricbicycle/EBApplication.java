package com.wxxiaomi.ming.electricbicycle;


import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.wxxiaomi.ming.electricbicycle.improve.common.AppContext;
import com.wxxiaomi.ming.electricbicycle.improve.im.ImHelper1;
import com.wxxiaomi.ming.electricbicycle.service.AccountHelper;
import com.wxxiaomi.ming.electricbicycle.service.UserFunctionProvider;
import com.wxxiaomi.ming.electricbicycle.support.cache.CacheManager;

import java.util.List;

/**
 * 程序入口
 *
 * @author Administrator
 */
public class EBApplication extends AppContext {
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
//        PreferenceManager.init(getApplicationContext());
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        CacheManager.init(this);
        sRefWatcher = LeakCanary.install(this);
    }

    public static EBApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


}
