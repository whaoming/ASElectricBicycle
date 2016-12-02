package com.wxxiaomi.ming.electricbicycle.common.util;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

/**
 * Created by 12262 on 2016/10/29.
 */

public class RxUnitTestTools {
    private static boolean isInitRxTools = false;

    /**
     * 把异步变成同步，方便测试
     */
    public static void openRxTools() {
        if (isInitRxTools) {
            return;
        }
        isInitRxTools = true;

        RxAndroidSchedulersHook rxAndroidSchedulersHook = new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        };

        RxJavaSchedulersHook rxJavaSchedulersHook = new RxJavaSchedulersHook() {
            @Override
            public Scheduler getIOScheduler() {
                return Schedulers.immediate();
            }
        };

        // reset()不是必要，实践中发现不写reset()，偶尔会出错，所以写上保险^_^
        RxAndroidPlugins.getInstance().reset();
        RxAndroidPlugins.getInstance().registerSchedulersHook(rxAndroidSchedulersHook);
        //RxJavaPlugins.getInstance().reset();
        RxJavaPlugins.getInstance().registerSchedulersHook(rxJavaSchedulersHook);
    }
}
