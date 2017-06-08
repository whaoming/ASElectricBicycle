package com.wxxiaomi.ming.electricbicycle.ui.presenter.impl;

import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.widget.EditText;

import com.wxxiaomi.ming.common.net.ApiException;
import com.wxxiaomi.ming.common.util.AppManager;
import com.wxxiaomi.ming.common.util.CommonUtil;
import com.wxxiaomi.ming.common.util.TDevice;
import com.wxxiaomi.ming.electricbicycle.EBApplication;
import com.wxxiaomi.ming.electricbicycle.common.rx.MyObserver;
import com.wxxiaomi.ming.electricbicycle.db.FriendDao;
import com.wxxiaomi.ming.electricbicycle.db.UserDao;
import com.wxxiaomi.ming.electricbicycle.db.bean.format.LoginResponseBean;
import com.wxxiaomi.ming.electricbicycle.db.impl.FriendDaoImpl2;
import com.wxxiaomi.ming.electricbicycle.db.impl.UserDaoImpl;
import com.wxxiaomi.ming.electricbicycle.im.ImHelper1;
import com.wxxiaomi.ming.electricbicycle.manager.Account;
import com.wxxiaomi.ming.electricbicycle.net.HttpMethods;
import com.wxxiaomi.ming.electricbicycle.ui.activity.RegisterActivity;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.base.BasePreImpl;
import com.wxxiaomi.ming.electricbicycle.ui.activity.HomeActivity;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.LoginPresenter;
import com.wxxiaomi.ming.electricbicycle.common.rx.ProgressObserver;
import com.wxxiaomi.ming.electricbicycle.ui.activity.view.LoginView;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;


/**
 * Created by 12262 on 2016/6/5.
 * 登陆页面的控制器
 */
public class LoginPresenterImpl extends BasePreImpl<LoginView> implements LoginPresenter<LoginView> {
    private UserDao userDao;

    @Override
    public void init() {

    }

    private boolean checkFormat(EditText strLayout) {
//        assert strLayout.getEditText() != null;
//        String str = strLayout.getText().toString().trim();
//        if ("".equals(str)) {
//            strLayout.setError("不能为空");
//            return false;
//        } else if (str.contains(" ")) {
//            strLayout.setError("出现非法字符");
//            return false;
//        } else if (CommonUtil.checkChainse(str)) {
//            strLayout.setError("不能包含中文");
//            return false;
//        } else if (str.length() < 6) {
//            strLayout.setError("长度小于6");
//            return false;
//        } else {
//            strLayout.setEnabled(false);
//            return true;
//        }
        return true;
    }


    @Override
    public void onLoginBtnClick(String username, String password) {
        Log.d("wang", "来");

//        if (checkFormat(til_username) && checkFormat(til_password)) {
//            String username = til_username.getText().toString()
//                    .trim();
//            String password = til_password.getText().toString()
//                    .trim();
        sendRequest(username, password);
//        }
    }

    private void sendRequest(String username, String password) {
        String uniqueID = TDevice.getUniqueID(EBApplication.applicationContext);
        ProgressObserver<LoginResponseBean> progressObserver = new ProgressObserver<LoginResponseBean>(mView.getContext()) {
            @Override
            protected void onError(ApiException ex) {
                super.onError(ex);
                mView.getEditText().requestFocus();
            }
            @Override
            public void onNext(LoginResponseBean loginResponseBean) {
                if (loginResponseBean != null) {
                    Account.updateUserCache(loginResponseBean.user);
                    userDao = new UserDaoImpl(mView.getContext());
                    FriendDao dao2 = new FriendDaoImpl2(mView.getContext());
                    dao2.updateFriendsList(loginResponseBean.friendList);
                    dao2.updateBlackList(loginResponseBean.blackList);
                    ImHelper1.getInstance().setFriends(loginResponseBean.friendList);
                    AppManager.getAppManager().finishActivity(RegisterActivity.class);
                    mView.runActivity(HomeActivity.class, null, true);
                } else {
                    Log.i("wang", "登录失败");
                }

            }
        };
        //登录到app服务器
        HttpMethods.getInstance().login(username, password, uniqueID)
                .flatMap(new Func1<LoginResponseBean, Observable<LoginResponseBean>>() {
                    @Override
                    public Observable<LoginResponseBean> call(LoginResponseBean loginResponseBean) {
                        ImHelper1.getInstance().logout();
                        //登录到环信服务器
                        Observable<Boolean> loginEm = ImHelper1.getInstance().LoginFromEm(
                                loginResponseBean.user.username, loginResponseBean.user.password);
                        Observable<LoginResponseBean> zip = Observable.zip(loginEm, Observable.just(loginResponseBean)
                                , new Func2<Boolean, LoginResponseBean, LoginResponseBean>() {
                            @Override
                            public LoginResponseBean call(Boolean aBoolean, LoginResponseBean loginResponseBean) {
                                if (aBoolean) {
                                    return loginResponseBean;
                                }
                                return null;
                            }
                        });
                        return zip;
                    }
                })
                .subscribe(progressObserver);
    }

    @Override
    public void onDebugBtnClick() {
        sendRequest("122627018", "987987987");
    }

    public void loginEM(String username, String password) {
//        UserFunctionProvider.getInstance().HandLogin()
//        ImService.Login(username,password)
//                .subscribe(new ProgressObserver<Boolean>(mView.getContext()) {
//                    @Override
//                    public void onNext(Boolean aBoolean) {
//                        Log.i("wang","登录em:"+aBoolean);
//                    }
//                });
//        ImHelper.getInstance().LoginFromEm(username,password)
//                .subscribe(new ProgressObserver<Boolean>(mView.getContext()) {
//                    @Override
//                    public void onNext(Boolean aBoolean) {
//                        Log.i("wang","登录em:"+aBoolean);
//                    }
//                });
//        return rx.Observable.create(new rx.Observable.OnSubscribe<Boolean>() {
//
//            @Override
//            public void call(Subscriber<? super Boolean> subscriber) {
//
//            }
//        });
//        ImService.startLogin(username, password, new ImService.doSome() {
//            @Override
//            public void returnData(Object data) {
//
//            }
//        });
    }

    public void handLogin(String username, String password,
                          boolean isEmOpen, String uniqueNum) {
//       final FriendDao dao2 = new FriendDaoImpl2(mView.getContext());
//         UserDao userDao = new UserDaoImpl(mView.getContext());
//        userDao.Login(username, password, uniqueNum)
//                .flatMap(new Func1<User, Observable<Boolean>>() {
//                    @Override
//                    public Observable<Boolean> call(User user) {
//                        return ImHelper1.getInstance().LoginFromEm(user.username, user.password);
//                    }
//                })
//                //从服务器获取好友列表
//                .flatMap(new Func1<Boolean, Observable<List<String>>>() {
//                    @Override
//                    public Observable<List<String>> call(Boolean aBoolean) {
//                        return ImHelper1.getInstance().getContactFromEm();
//                    }
//                })
//                //对比本地数据库，得出键值对
//                .map(new Func1<List<String>, String>() {
//                    @Override
//                    public String call(List<String> strings) {
//                        if (strings.size() == 0) {
//                            return "";
//                        }
//                        return dao2.getErrorFriend(strings);
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
//                        dao2.updateFriendsList(userCommonInfo2s);
//                        return userCommonInfo2s.size();
//                    }
//                })
//                //将好友列表加载到内存中
//                .map(new Func1<Integer, Integer>() {
//                    @Override
//                    public Integer call(Integer integer) {
////                        ImHelper.getInstance().openUserCache(getEFriends());
//                        ImHelper1.getInstance().setFriends(dao2.getEFriends());
//                        return integer;
//                    }
//                });
    }

}
