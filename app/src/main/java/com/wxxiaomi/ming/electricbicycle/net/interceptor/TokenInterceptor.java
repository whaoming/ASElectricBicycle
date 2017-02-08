package com.wxxiaomi.ming.electricbicycle.net.interceptor;

import com.wxxiaomi.ming.electricbicycle.manager.AccountHelper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mr.W on 2017/2/6.
 * E-maiil：122627018@qq.com
 * github：https://github.com/122627018
 */

public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request newRequest;
        if(!AccountHelper.isShortCookieEmpty()){
            newRequest= chain.request().newBuilder()
                    .addHeader("token", AccountHelper.getShortCookie())
                    .build();
        }else{
            newRequest= chain.request().newBuilder()
                    .build();
        }
        Response response = chain.proceed(newRequest);
        if(response.header("token")!=null){
            AccountHelper.updateSCookie(response.header("token"));
        }
        String long_token = response.header("long_token");
        if(long_token!=null){
            AccountHelper.updateLCookie(long_token);
        }
        return response;
    }
}
