//package com.wxxiaomi.ming.electricbicycle.ui.presenter.del;
//
//import android.support.multidex.MultiDex;
//import android.util.Log;
//
//import com.wxxiaomi.ming.electricbicycle.EBApplication;
//import com.wxxiaomi.ming.electricbicycle.api.HttpMethods;
//import com.wxxiaomi.ming.electricbicycle.service.GlobalManager;
//import com.wxxiaomi.ming.electricbicycle.service.PreferenceManager;
//import com.wxxiaomi.ming.electricbicycle.common.rx.ProgressObserver;
//import com.wxxiaomi.ming.electricbicycle.common.util.AppManager;
//import com.wxxiaomi.ming.electricbicycle.common.util.UniqueUtil;
//import com.wxxiaomi.ming.electricbicycle.db.bean.User;
//import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;
//import com.wxxiaomi.ming.electricbicycle.db.AppDao;
//import com.wxxiaomi.ming.electricbicycle.db.FriendDao;
//import com.wxxiaomi.ming.electricbicycle.db.UserDao;
//import com.wxxiaomi.ming.electricbicycle.db.impl.AppDaoImpl;
//import com.wxxiaomi.ming.electricbicycle.db.impl.FriendDaoImpl2;
//import com.wxxiaomi.ming.electricbicycle.db.impl.UserDaoImpl;
//import com.wxxiaomi.ming.electricbicycle.common.aliyun.OssEngine;
////import com.wxxiaomi.ming.electricbicycle.common.cache.base.DiskCache;
//import com.wxxiaomi.ming.electricbicycle.ui.activity.RegisterActivity;
//import com.wxxiaomi.ming.electricbicycle.ui.activity.base.BaseView;
//import com.wxxiaomi.ming.electricbicycle.ui.presenter.base.BasePreImpl;
//
//import java.util.List;
//import java.util.concurrent.CountDownLatch;
//
//import rx.Observable;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.functions.Action1;
//import rx.functions.Func1;
//import rx.schedulers.Schedulers;
//
///**
// * Created by Administrator on 2016/12/19.
// */
//
//public class InitializePsrImpl extends BasePreImpl<BaseView> implements InitializePsr<BaseView> {
//    private boolean isLogin;
//    private CountDownLatch order = null;
//    private FriendDao friendDao2;
//    private UniqueUtil util;
//    private UserDao userDao;
//
//    @Override
//    public void init() {
//        friendDao2 = new FriendDaoImpl2(EBApplication.applicationContext);
//        util = new UniqueUtil(mView.getContext());
//        userDao = new UserDaoImpl(EBApplication.applicationContext);
//
//    }
//
//    @Override
//    public void autoLogin() {
//        order = new CountDownLatch(3);
//        new Thread() {
//            @Override
//            public void run() {
//                initModule();
//            }
//        }.start();
//        new Thread() {
//            @Override
//            public void run() {
//                sleepTime();
//            }
//        }.start();
//        new Thread() {
//            @Override
//            public void run() {
//                thisAutoLogin();
//            }
//        }.start();
//        new Thread() {
//            @Override
//            public void run() {
//                doFinalAction();
//            }
//        }.start();
//    }
//
//
//    @Override
//    public void onLoginBtnClick(String username, String password) {
//        String uniqueID = util.getUniqueID();
//        userDao.Login(username, password, uniqueID)
//                .flatMap(new Func1<User, Observable<Boolean>>() {
//                    @Override
//                    public Observable<Boolean> call(User user) {
//                        GlobalManager.getInstance().savaUser(user);
//                        //存到数据库
//                        AppDao dao = new AppDaoImpl(EBApplication.applicationContext);
//                        dao.savaUser(user);
//                        return ImHelper.getInstance().LoginFromEm(user.username, user.password);
//                    }
//                })
//                //从服务器获取好友列表
//                .flatMap(new Func1<Boolean, Observable<Integer>>() {
//                    @Override
//                    public Observable<Integer> call(Boolean aBoolean) {
//                        return updateLocal();
//                    }
//                })
////                //对比本地数据库，得出键值对
////                .map(new Func1<List<String>, String>() {
////                    @Override
////                    public String call(List<String> strings) {
////                        if (strings.size() == 0) {
////                            return "";
////                        }
////                        return friendDao2.getErrorFriend(strings);
////                    }
////                })
////                //链接服务器对比
////                .flatMap(new Func1<String, Observable<List<UserCommonInfo>>>() {
////                    @Override
////                    public Observable<List<UserCommonInfo>> call(String s) {
////                        return HttpMethods.getInstance().updateuserFriend2(s);
////                    }
////                })
////                //得到最新的好友列表
////                .map(new Func1<List<UserCommonInfo>, Integer>() {
////                    @Override
////                    public Integer call(List<UserCommonInfo> userCommonInfo2s) {
////                        friendDao2.updateFriendsList(userCommonInfo2s);
////                        return userCommonInfo2s.size();
////                    }
////                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new ProgressObserver<Integer>(mView.getContext()) {
//                    @Override
//                    public void onNext(Integer integer) {
//                        Log.i("wang", "更新了" + integer + "个好友");
//                        ImHelper.getInstance().openUserCache(friendDao2.getEFriends());
//                        AppManager.getAppManager().finishActivity(RegisterActivity.class);
////                        mView.runActivity(HomeActivity.class, null, true);
//                    }
//                });
//    }
//
//
//    @Override
//    public void onDebugBtnClick() {
//
//    }
//
//    //更新本地数据
//    public Observable<Integer> updateLocal(){
//       return  ImHelper.getInstance().getContactFromEm()
//                //对比本地数据库，得出键值对
//                .map(new Func1<List<String>, String>() {
//                    @Override
//                    public String call(List<String> strings) {
//                        if (strings.size() == 0) {
//                            return "";
//                        }
//                        return friendDao2.getErrorFriend(strings);
//                    }
//                })
//                //链接服务器对比
//                .flatMap(new Func1<String, Observable<List<UserCommonInfo>>>() {
//                    @Override
//                    public Observable<List<UserCommonInfo>> call(String s) {
//                        return HttpMethods.getInstance().updateuserFriend2(s);
//                    }
//                })
//                //得到最新的好友列表
//                .map(new Func1<List<UserCommonInfo>, Integer>() {
//                    @Override
//                    public Integer call(List<UserCommonInfo> userCommonInfo2s) {
//                        friendDao2.updateFriendsList(userCommonInfo2s);
//                        return userCommonInfo2s.size();
//                    }
//                });
//    }
//
//
//    //初始化各个模块
//    private void initModule() {
//        MultiDex.install(EBApplication.applicationContext);
////        DiskCache.getInstance().open(EBApplication.applicationContext);
//        OssEngine.getInstance().initOssEngine(EBApplication.applicationContext);
//        order.countDown();
//    }
//
//    //休息一段时间
//    private void sleepTime() {
//        try {
//            Thread.sleep(2000);
//            order.countDown();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void thisAutoLogin() {
//        String longToken = PreferenceManager.getInstance().getLongToken();
//        if (!"".equals(longToken)) {
//            updateLocal()
//                    .subscribe(new Action1<Integer>() {
//                        @Override
//                        public void call(Integer integer) {
//                            isLogin = true;
//                            initAfterLogin();
//                            Log.i("wang", "本地更新好友个数:" + integer);
//                            Log.i("wang", "自动登陆成功");
//                        }
//                    });
//
//        } else {
//            isLogin = false;
//            order.countDown();
//        }
//    }
//
//    private void initAfterLogin() {
//        ImHelper.getInstance().openUserCache(friendDao2.getEFriends());
//        order.countDown();
//    }
//
//    private void doFinalAction() {
//        try {
//            order.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}
