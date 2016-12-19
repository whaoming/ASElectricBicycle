//package com.wxxiaomi.ming.electricbicycle.core.presenter.impl;
//
//import com.wxxiaomi.ming.electricbicycle.core.presenter.base.BasePreImpl;
//import com.wxxiaomi.ming.electricbicycle.core.presenter.SplashPre;
//import com.wxxiaomi.ming.electricbicycle.core.activity.view.SlpashView;
//import com.wxxiaomi.ming.electricbicycle.common.util.SharePrefUtil;
//
///**
// * Created by 12262 on 2016/5/28.
// */
//public class SplashImpl extends BasePreImpl<SlpashView> implements SplashPre<SlpashView> {
//
//
//
//    @Override
//    public void loadConfig() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                long start = System.currentTimeMillis();
//                long costTime = System.currentTimeMillis() - start;
//                if (2000 - costTime > 0) {
//                    try {
//                        Thread.sleep(2000 - costTime);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                // 是否是第一次使用此程序
//                boolean isFirstRun = SharePrefUtil.getBoolean(
//                        mView.getContext(), "firstRun", true);
//                if (isFirstRun) {
//                    // 第一次使用
////                    Intent intent = ne
//                    mView.runWelcomeAct();
//                    SharePrefUtil.saveBoolean(mView.getContext(), "firstGo",
//                            false);
////                    finish();
//                } else {
//                    // 检测本地是否得到账号
//                    boolean isRemUser = SharePrefUtil.getBoolean(
//                            mView.getContext(), "isRemUser", false);
//                    if (isRemUser) {
//                        // 本地有记住账号,实现登录功能
//                        /**
//                         * 1.取出账号密码进行服务器登陆 2.当服务器登陆成功后进行EM登陆
//                         * 3.当EM登陆成功，检查本地是否有此用户相关信息
//                         * 4.如果有，直接进入主界面；如果没有，连接服务器做初始化操作
//                         */
//                    } else {
//                        // 检测不到本地账号的话就去注册页面(里面有登录功能)
//                        mView.runRegisterAct();
////                        startActivity(intent);
////                        finish();
//                    }
//
//                }
//
//            }
//        }).start();
//    }
//
//    @Override
//    public void init() {
//
//    }
//
//
//
//
//}
