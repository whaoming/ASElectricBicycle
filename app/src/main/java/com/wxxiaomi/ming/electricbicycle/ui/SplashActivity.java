package com.wxxiaomi.ming.electricbicycle.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.multidex.MultiDex;

import com.wxxiaomi.ming.electricbicycle.EBApplication;
import com.wxxiaomi.ming.electricbicycle.common.util.AppManager;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.support.cache.DiskCache;
import com.wxxiaomi.ming.electricbicycle.ui.activity.HomeActivity;
import com.wxxiaomi.ming.electricbicycle.ui.activity.RegisterActivity;
import com.wxxiaomi.ming.electricbicycle.service.FunctionProvider;
import com.wxxiaomi.ming.electricbicycle.bridge.aliyun.OssEngine;

import java.util.concurrent.CountDownLatch;

import rx.Observer;


/**
 * 入口activity
 *
 * @author Mr.W
 */
public class SplashActivity extends Activity {
    private boolean isLogin = false;

    final CountDownLatch order = new CountDownLatch(3);
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
        AppManager.getAppManager().addActivity(this);
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
        new Thread() {
            @Override
            public void run() {
                sleepTime();
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                thisAutoLogin();

            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                doFinalAction();
            }
        }.start();
    }

    //初始化各个模块
    private void initModule() {
        MultiDex.install(EBApplication.applicationContext);
        DiskCache.getInstance().open(EBApplication.applicationContext);
        OssEngine.getInstance().initOssEngine(EBApplication.applicationContext);
        order.countDown();
    }

    //休息一段时间
    private void sleepTime() {
        try {
            Thread.sleep(2000);
            order.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void thisAutoLogin() {
        FunctionProvider.getInstance().AutoLogin()
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        isLogin = false;
                        order.countDown();
                    }

                    @Override
                    public void onNext(Integer integer) {
                        isLogin = true;
                        order.countDown();
                    }
                });
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
