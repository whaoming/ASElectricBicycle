package com.wxxiaomi.ming.electricbicycle;


import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.wxxiaomi.ming.electricbicycle.improve.im.ImHelper1;
import com.wxxiaomi.ming.electricbicycle.service.AccountHelper;
import com.wxxiaomi.ming.electricbicycle.service.PreferenceManager;
import com.wxxiaomi.ming.electricbicycle.service.UserFunctionProvider;

import java.util.List;

/**
 * 程序入口
 *
 * @author Administrator
 */
public class EBApplication extends Application {
    public static Context applicationContext;
    private static EBApplication instance;
    public  static RefWatcher sRefWatcher;
    private static boolean isInit = false;

    @Override
    public void onCreate() {
//        super.onCreate();
        Log.i("wang","onCreate");
//        OsU
//        if(!isInit){
            applicationContext = this;
            instance = this;
//        String processName = getProcessName(this, android.os.Process.myPid());
//        if (processName != null) {
//            boolean defaultProcess = processName.equals(Constants.REAL_PACKAGE_NAME);
//            if (processName.contains(":baidu")) {
                SDKInitializer.initialize(this);
//            }else{
                ImHelper1.getInstance().init(EBApplication.this);


                AccountHelper.init(this);
                PreferenceManager.init(getApplicationContext());
                if (LeakCanary.isInAnalyzerProcess(this)) {
                    return;
                }
                sRefWatcher = LeakCanary.install(this);
//            }
//        }

//            isInit = !isInit;
//        }

//        new Thread(){
//            @Override
//            public void run() {
//                ImHelper1.getInstance().setFriends(UserFunctionProvider.getInstance().getEFriends());
//                super.run();
//            }
//        }.start();
    }

    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }
    private void init(){

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
