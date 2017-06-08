package com.wxxiaomi.ming.electricbicycle.net.interceptor;

import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.manager.Account;

import java.io.IOException;
import java.util.Map;

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

    private Map<String,String> headers = null;
    public TokenGetInterceptor(Map<String,String> headers){
        this.headers = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request newRequest;
        if (headers!=null || !Account.isShortCookieEmpty()) {
            Request.Builder builder = chain.request().newBuilder();
            if(headers!=null){
                for(Map.Entry<String,String> item : headers.entrySet()){
                    builder.addHeader(item.getKey(),item.getValue());
                }
            }
            if (!Account.isShortCookieEmpty()) {
                builder.addHeader("token", Account.getShortCookie());
            }
            newRequest = builder.build();
        } else {
            newRequest = chain.request().newBuilder()
                    .build();
        }
        Response response = chain.proceed(newRequest);
        if (response.header("token") != null) {
            Account.updateSCookie(response.header("token"));
        }
        String long_token = response.header("long_token");
        if (long_token != null) {
            Account.updateLCookie(long_token);
        }
        return response;
    }
}
