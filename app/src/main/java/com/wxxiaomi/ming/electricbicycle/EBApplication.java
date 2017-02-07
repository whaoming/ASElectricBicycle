package com.wxxiaomi.ming.electricbicycle;


import android.app.ActivityManager;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.baidu.mapapi.SDKInitializer;
import com.wxxiaomi.ming.common.base.AppContext;
import com.wxxiaomi.ming.common.cache.CacheManager;
import com.wxxiaomi.ming.electricbicycle.im.ImHelper1;
import com.wxxiaomi.ming.electricbicycle.manager.AccountHelper;

import java.util.List;

/**
 * 程序入口
 *
 * @author Administrator
 */
public class EBApplication extends AppContext {
    public static Context applicationContext;
    private static EBApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        instance = this;
        String processName = getProcessName(this, android.os.Process.myPid());
        if(processName!=null && !processName.equalsIgnoreCase(getPackageName())){
            return;
        }
        SDKInitializer.initialize(getApplicationContext());
        ImHelper1.getInstance().init(this);
        AccountHelper.init(this);
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
        CacheManager.init(this);
//        sRefWatcher = LeakCanary.install(this);
    }

    public static EBApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningAppProcesses) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }


}
