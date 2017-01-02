package com.wxxiaomi.ming.electricbicycle.improve.im;

import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.wxxiaomi.ming.electricbicycle.api.exp.ApiException;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Mr.W on 2017/1/2.
 * E-maiil：122627018@qq.com
 * github：https://github.com/122627018
 */

public class DemoSe implements Observable.OnSubscribe<Boolean> {
    private String username;
    private String password;

    public DemoSe(String username,String password){
        this.username = username;
        this.password = password;
    }
    @Override
    public void call(final Subscriber<? super Boolean> subscriber) {
        Log.i("wang","username:"+username+",psd:"+password);
        EMClient.getInstance().login(username, password,
                new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
                        try {
                            EMClient.getInstance().groupManager().loadAllGroups();
                            EMClient.getInstance().chatManager()
                                    .loadAllConversations();
                            EMClient.getInstance().updateCurrentUserNick(
                                    username);
                            subscriber.onNext(true);
                            subscriber.onCompleted();
                        } catch (Exception e) {
                            e.printStackTrace();
                            ApiException ex = new ApiException(e, 333);
                            ex.setDisplayMessage("em驱动器登录失败");
                            subscriber.onError(ex);
                        }
                    }

                    @Override
                    public void onProgress(int progress, String status) {
                        // Log.d(TAG, "login: onProgress");
                    }

                    @Override
                    public void onError(final int code, final String message) {
                        Log.i("wang", "登录em login: onError(错误): " + code + "---message="
                                + message);
                        ApiException apiException = new ApiException(new Exception(), code);
                        apiException.setDisplayMessage(message);
                        subscriber.onError(apiException);
                    }
                });
    }
}
