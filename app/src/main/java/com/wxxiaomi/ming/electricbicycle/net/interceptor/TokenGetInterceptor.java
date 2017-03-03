package com.wxxiaomi.ming.electricbicycle.net.interceptor;

import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.manager.Account;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mr.W on 2017/2/6.
 * E-maiil：122627018@qq.com
 * github：https://github.com/whaoming
 * TODO: 拦截服务器返回的token并进行保存,并且在发起请求的时候自动为头部添加token
 */
public class TokenGetInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request newRequest;
        if(!Account.isShortCookieEmpty()){
            newRequest= chain.request().newBuilder()
                    .addHeader("token", Account.getShortCookie())
                    .build();
        }else{
            newRequest= chain.request().newBuilder()
                    .build();
        }
//        try {
            Response response = chain.proceed(newRequest);

        if(response.header("token")!=null){
            Log.i("wangwang","发现短的token");
            Account.updateSCookie(response.header("token"));
        }
        String long_token = response.header("long_token");
        if(long_token!=null){
            Log.i("wangwang","发现长的token");
            Account.updateLCookie(long_token);
        }
        return response;
//        }catch (Exception e){
//            Log.i("wang","TokenGetInterceptor中出错了");
//            throw new ServerException(999,"网络异常");
//        }
    }
}
