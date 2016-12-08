package com.wxxiaomi.ming.electricbicycle;


import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.wxxiaomi.ming.electricbicycle.support.easemob.ui.MyUserProvider;
import com.wxxiaomi.ming.electricbicycle.support.aliyun.OssEngine;
import com.wxxiaomi.ming.electricbicycle.support.cache.DiskCache;

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
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
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
