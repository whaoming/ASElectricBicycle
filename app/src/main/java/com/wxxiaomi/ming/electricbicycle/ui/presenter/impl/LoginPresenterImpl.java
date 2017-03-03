package com.wxxiaomi.ming.electricbicycle.ui.presenter.impl;

import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.wxxiaomi.ming.common.util.AppManager;
import com.wxxiaomi.ming.common.util.CommonUtil;
import com.wxxiaomi.ming.common.util.TDevice;
import com.wxxiaomi.ming.common.weight.DialogHelper;
import com.wxxiaomi.ming.electricbicycle.ConstantValue;
import com.wxxiaomi.ming.electricbicycle.EBApplication;
import com.wxxiaomi.ming.electricbicycle.db.FriendDao;
import com.wxxiaomi.ming.electricbicycle.db.UserDao;
import com.wxxiaomi.ming.electricbicycle.db.bean.User;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.db.bean.format.LoginResponseBean;
import com.wxxiaomi.ming.electricbicycle.db.impl.FriendDaoImpl2;
import com.wxxiaomi.ming.electricbicycle.db.impl.UserDaoImpl;
import com.wxxiaomi.ming.electricbicycle.im.ImHelper1;
import com.wxxiaomi.ming.electricbicycle.manager.Account;
import com.wxxiaomi.ming.electricbicycle.manager.UserFunctionProvider;
import com.wxxiaomi.ming.electricbicycle.net.HttpMethods;
import com.wxxiaomi.ming.electricbicycle.ui.activity.RegisterActivity;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.base.BasePreImpl;
import com.wxxiaomi.ming.electricbicycle.ui.activity.HomeActivity;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.LoginPresenter;
import com.wxxiaomi.ming.electricbicycle.common.rx.ProgressObserver;
import com.wxxiaomi.ming.electricbicycle.ui.activity.view.LoginView;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;


/**
 * Created by 12262 on 2016/6/5.
 * 登陆页面的控制器
 */
public class LoginPresenterImpl extends BasePreImpl<LoginView> implements LoginPresenter<LoginView> {
//    UniqueUtil util;
private UserDao userDao;

    @Override
    public void init() {
//        util = new UniqueUtil(mView.getContext());
        userDao = new UserDaoImpl(mView.getContext());
    }

    private boolean checkFormat(TextInputLayout strLayout) {
        assert strLayout.getEditText() != null;
        String str = strLayout.getEditText().getText().toString().trim();
        if ("".equals(str)) {
            strLayout.setError("不能为空");
            return false;
        } else if (str.contains(" ")) {
            strLayout.setError("出现非法字符");
            return false;
        } else if (CommonUtil.checkChainse(str)) {
            strLayout.setError("不能包含中文");
            return false;
        } else if (str.length() < 6) {
            strLayout.setError("长度小于6");
            return false;
        } else {
            strLayout.setEnabled(false);
            return true;
        }
    }


    @Override
    public void onLoginBtnClick(TextInputLayout til_username, TextInputLayout til_password) {
//        AlertDialog dialog = DialogHelper.getSelectDialog(mView.getContext(), "", new String[]{"asd", "ddede"}, "",new DialogInterface.OnClickListener(){
//
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Log.d("wang","i:"+i);
//            }
//        }).create();
//        DialogHelper.showBottomDialog(dialog);

        if (checkFormat(til_username) && checkFormat(til_password)) {
            assert til_username.getEditText() != null;
            String username = til_username.getEditText().getText().toString()
                    .trim();
            String password = til_password.getEditText().getText().toString()
                    .trim();
//            sendRequest(username, password);

        }
    }

    private void sendRequest(String username, String password) {
//        Log.i("wang","进行登录");
        String uniqueID = TDevice.getUniqueID(EBApplication.applicationContext);
//        UserFunctionProvider.getInstance().HandLogin(username, password,ConstantValue.isEMOpen,uniqueID)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new ProgressObserver<Integer>(mView.getContext()) {
//                    @Override
//                    public void onNext(Integer integer) {
//                        Log.i("wang","登录成功");
//                        AppManager.getAppManager().finishActivity(RegisterActivity.class);
//                        mView.runActivity(HomeActivity.class, null, true);
//                    }
//                });
//        final LoginResponseBean[] temp = new LoginResponseBean[1];
        userDao.Login(username, password,uniqueID)
                .flatMap(new Func1<LoginResponseBean, Observable<LoginResponseBean>>() {
                    @Override
                    public Observable<LoginResponseBean> call(LoginResponseBean loginResponseBean) {
                        String currentEmUser = ImHelper1.getInstance().getCurrentEmUser();
                        if(loginResponseBean.user.username.equals(currentEmUser)){
                            Log.i("wang","环信已经登录");
                            return Observable.just(loginResponseBean);
                        }
                        ImHelper1.getInstance().logout();
//                        Log.i("wang","在登录环信之前先检查当前登录的用户："+currentEmUser);
                        Observable<Boolean> loginEm = ImHelper1.getInstance().LoginFromEm(loginResponseBean.user.username, loginResponseBean.user.password);
                        Observable<LoginResponseBean> zip = Observable.zip(loginEm, Observable.just(loginResponseBean), new Func2<Boolean, LoginResponseBean, LoginResponseBean>() {
                            @Override
                            public LoginResponseBean call(Boolean aBoolean, LoginResponseBean loginResponseBean) {
                                if(aBoolean){
                                    Log.i("wang","登录环信成功");
                                    return loginResponseBean;
                                }
                                return null;
                            }
                        });
                        return zip;
                    }
                })
                .subscribe(new ProgressObserver<LoginResponseBean>(mView.getContext()) {
                    @Override
                    public void onNext(LoginResponseBean loginResponseBean) {
                        if (loginResponseBean!=null) {
                            Log.i("wang","登录成功");
                            Account.updateUserCache(loginResponseBean.user);
                            FriendDao dao2 = new FriendDaoImpl2(mView.getContext());
                            dao2.updateFriendsList(loginResponseBean.friendList);
                            ImHelper1.getInstance().setFriends(loginResponseBean.friendList);


                            AppManager.getAppManager().finishActivity(RegisterActivity.class);
                             mView.runActivity(HomeActivity.class, null, true);
                        }else{
                            Log.i("wang","登录失败");
                        }

                    }
                });
    }

    @Override
    public void onDebugBtnClick() {
        sendRequest("122627018", "987987987");
    }

    public void loginEM(String username,String password){
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
                          boolean isEmOpen, String uniqueNum){
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
