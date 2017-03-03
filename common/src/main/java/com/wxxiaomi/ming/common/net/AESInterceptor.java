package com.wxxiaomi.ming.common.net;

import android.text.TextUtils;
import android.util.Log;

import com.wxxiaomi.ming.common.base.AppContext;
import com.wxxiaomi.ming.common.security.aes.AESUtil;
import com.wxxiaomi.ming.common.security.rsa.RSAKeyProvider;
import com.wxxiaomi.ming.common.security.rsa.RSAUtils;

import java.io.IOException;
import java.security.interfaces.RSAPublicKey;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
* @author whaoming
* github：https://github.com/whaoming
* created at 2017/2/6 10:13
* Description:对表单提交的数据进行aes加密
*/
public class AESInterceptor implements Interceptor {
    public String key = "123456789aaaaaaa";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Log.i("wang","aes拦截器");
        try {
            Request newRequest = null;
            if (request.body() instanceof FormBody) {
                FormBody formBody = (FormBody) request.body();
                FormBody.Builder formBuilder = new FormBody.Builder();
                String keyMI = null;
                for (int i = 0; i < formBody.size(); i++) {
                    if (formBody.name(i).equals("param")) {
                        Log.i("wang","发现param");
                        String json = AESUtil.encrypt(formBody.value(i), key);
                        if (!TextUtils.isEmpty(json)) {
                            formBuilder.add("data", json);
                            RSAPublicKey pk = RSAKeyProvider.loadPublicKeyByStr(AppContext.getPublicKeyStore());
                            keyMI = RSAUtils.encryptByPublicKey(key,pk);
                            formBuilder.add("key",keyMI);
                        }
                    }else{
                        formBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                    }
                }
                FormBody newFormBody = formBuilder.build();
                Request.Builder builder = request.newBuilder();
                Log.i("wang","keyMI:"+keyMI);
                if(keyMI!=null){
                    builder.addHeader("key",keyMI);
                }
                newRequest = builder
                        .method(request.method(), newFormBody)
                        .removeHeader("Content-Length")
                        .addHeader("Content-Length", newFormBody.contentLength() + "")
                        .build();
            }
            Response response = chain.proceed(newRequest == null ? request : newRequest);
            String result = response.body().string();
            return response.newBuilder().body(ResponseBody.create(response.body().contentType(), result)).build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return chain.proceed(request);
    }
}
