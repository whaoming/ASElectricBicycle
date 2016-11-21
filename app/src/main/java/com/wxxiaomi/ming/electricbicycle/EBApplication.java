package com.wxxiaomi.ming.electricbicycle;


import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.wxxiaomi.ming.electricbicycle.core.em.MyUserProvider;

/**
 * 程序入口
 *
 * @author Administrator
 */
public class EBApplication extends Application {
    public static Context applicationContext;
    private static EBApplication instance;

    @Override
    public void onCreate() {
        MultiDex.install(this);
        Log.i("wang", "进入application-oncreate()");
        applicationContext = this;
        instance = this;
        SDKInitializer.initialize(getApplicationContext());
        initEM();
        super.onCreate();

    }

    public static EBApplication getInstance() {
        return instance;
    }

    private void initEM() {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        // 初始化
        try {
            EaseUI.getInstance().init(this, options);
            Log.i("wang", "初始化em引擎成功");
        } catch (Exception e) {

            e.printStackTrace();
            Log.i("wang", "初始化em引擎失败");
        }
        EaseUI.getInstance().setUserProfileProvider(new MyUserProvider());

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


}
