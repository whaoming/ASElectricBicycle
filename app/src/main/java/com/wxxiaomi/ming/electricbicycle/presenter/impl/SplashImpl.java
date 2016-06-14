package com.wxxiaomi.ming.electricbicycle.presenter.impl;

import com.wxxiaomi.ming.electricbicycle.model.impl.UserModelImpl;
import com.wxxiaomi.ming.electricbicycle.presenter.SplashPre;
import com.wxxiaomi.ming.electricbicycle.presenter.base.BasePresenter;
import com.wxxiaomi.ming.electricbicycle.ui.view.SlpashView;
import com.wxxiaomi.ming.electricbicycle.util.SharePrefUtil;

/**
 * Created by 12262 on 2016/5/28.
 */
public class SplashImpl extends BasePresenter<SlpashView,UserModelImpl> implements SplashPre {

    public SplashImpl(SlpashView slpashView) {
        super(slpashView);
    }

    @Override
    public void onViewCreate() {
    }

    @Override
    public void onViewDestory() {

    }
    @Override
    public void loadConfig() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                long costTime = System.currentTimeMillis() - start;
                if (2000 - costTime > 0) {
                    try {
                        Thread.sleep(2000 - costTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 是否是第一次使用此程序
                boolean isFirstRun = SharePrefUtil.getBoolean(
                        view.getContext(), "firstRun", true);
                if (isFirstRun) {
                    // 第一次使用
//                    Intent intent = ne
                    view.runWelcomeAct();
                    SharePrefUtil.saveBoolean(view.getContext(), "firstGo",
                            false);
//                    finish();
                } else {
                    // 检测本地是否得到账号
                    boolean isRemUser = SharePrefUtil.getBoolean(
                            view.getContext(), "isRemUser", false);
                    if (isRemUser) {
                        // 本地有记住账号,实现登录功能
                        /**
                         * 1.取出账号密码进行服务器登陆 2.当服务器登陆成功后进行EM登陆
                         * 3.当EM登陆成功，检查本地是否有此用户相关信息
                         * 4.如果有，直接进入主界面；如果没有，连接服务器做初始化操作
                         */
                    } else {
                        // 检测不到本地账号的话就去注册页面(里面有登录功能)
                        view.runRegisterAct();
//                        startActivity(intent);
//                        finish();
                    }

                }

            }
        }).start();
    }
}
