package com.wxxiaomi.ming.electricbicycle.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.widget.RelativeLayout;

import com.wxxiaomi.ming.electricbicycle.EBApplication;
import com.wxxiaomi.ming.electricbicycle.common.util.AppManager;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.improve.common.AppConfig;
import com.wxxiaomi.ming.electricbicycle.improve.common.AppContext;
import com.wxxiaomi.ming.electricbicycle.improve.im.ImHelper1;
import com.wxxiaomi.ming.electricbicycle.improve.im.notice.NoticeManager;
import com.wxxiaomi.ming.electricbicycle.improve.update.CheckUpdateManager;
import com.wxxiaomi.ming.electricbicycle.improve.update.Version;
import com.wxxiaomi.ming.electricbicycle.service.AccountHelper;
import com.wxxiaomi.ming.electricbicycle.support.cache.DiskCache;
import com.wxxiaomi.ming.electricbicycle.ui.activity.HomeActivity;
import com.wxxiaomi.ming.electricbicycle.ui.activity.RegisterActivity;
import com.wxxiaomi.ming.electricbicycle.service.UserFunctionProvider;
import com.wxxiaomi.ming.electricbicycle.bridge.aliyun.OssEngine;
import com.wxxiaomi.ming.electricbicycle.ui.weight.custom.SplashView;

import java.util.concurrent.CountDownLatch;

import rx.Observer;


/**
 * 入口activity
 *
 * @author Mr.W
 */
public class SplashActivity extends Activity {
    private boolean isLogin = false;
    private RelativeLayout content;

    final CountDownLatch order = new CountDownLatch(4);
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 5:
                    if(isLogin){
                        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    }else{
                        Intent intent = new Intent(SplashActivity.this, RegisterActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    }

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        content = (RelativeLayout) findViewById(R.id.content);
        AppManager.getAppManager().addActivity(this);
        String url = AppContext.get(AppConfig.SPLASH_IMG_URL, "");
        SplashView.showSplashView(this, 3, R.mipmap.ic_launcher, url, content, new SplashView.OnSplashViewActionListener() {
            @Override
            public void onSplashImageClick(String actionUrl) {

            }

            @Override
            public void onSplashViewDismiss(boolean initiativeDismiss) {
                Log.i("wang","onSplashViewDismiss");
                order.countDown();
            }
        });
        init();
    }

    /**
     * 初始化各类参数 决定程序往哪里走
     */
    private void init() {
        new Thread() {
            @Override
            public void run() {
                initModule();
            }
        }.start();
//        new Thread() {
//            @Override
//            public void run() {
//                sleepTime();
//            }
//        }.start();
        new Thread() {
            @Override
            public void run() {
                thisAutoLogin();

            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                checkUpdate();

            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                doFinalAction();
            }
        }.start();
    }

    private void checkUpdate() {
        //要是遇到网络异常这里会卡住
        CheckUpdateManager mng = new CheckUpdateManager(this,false);
        mng.setCaller(new CheckUpdateManager.RequestPermissions() {
            @Override
            public void call(boolean isOk,Version version) {
                if(isOk){
                    Log.i("wang","点击了确定更新");
                }
                order.countDown();
            }
        });
        mng.checkUpdate();
    }

    //初始化各个模块
    private void initModule() {
        MultiDex.install(EBApplication.applicationContext);
        DiskCache.getInstance().open(EBApplication.applicationContext);
        OssEngine.getInstance().initOssEngine(EBApplication.applicationContext);
        NoticeManager.init(EBApplication.applicationContext);
        order.countDown();
    }

    //休息一段时间
    private void sleepTime() {
        try {
            Thread.sleep(5000);
            order.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void thisAutoLogin() {
        boolean login = AccountHelper.isLogin();
        if(login){
            //允许登陆
                isLogin = true;
            ImHelper1.getInstance().setFriends(UserFunctionProvider.getInstance().getEFriends());
            }
        order.countDown();
    }



    private void doFinalAction() {
        try {
            order.await();
            handler.sendEmptyMessage(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();

    }

}
