package com.wxxiaomi.ming.common.net;

import android.util.Log;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mr.W on 2017/2/5.
 * E-maiil：122627018@qq.com
 * github：https://github.com/122627018
 */

public class RetrofitHelper {
    private static RetrofitHelper instance;
    private static String baseURL = "http:/192.168.1.106:8080/";

    private Retrofit mRetrofit;
//    private static Gson mGson;
//    public List<Interceptor> interceptors = new ArrayList<>();

    public void init(String baseURL, List<Interceptor> interceptors){
        instance.baseURL = baseURL;
//        instance.interceptors = is;
        if (baseURL == null) {
            throw new UnsupportedOperationException("baseURL没有初始化");
        }
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    String text = URLDecoder.decode(message, "utf-8");
                    Log.d("OKHttp", text);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.d("OKHttp", message);
                }
            }
        });
//        if (BuildConfig.LOG_ENABLE) {
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        } else {
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
//        }

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.;
        builder.readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .
        addInterceptor(new AESInterceptor());
        for(Interceptor i : interceptors){
            builder.addInterceptor(i);
        }
        OkHttpClient okHttpClient =builder.build();

                mRetrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    /**
     * 获取RetrofitHelper单例对象
     *
     * @return RetrofitHelper单例对象
     */
    public static synchronized RetrofitHelper getInstance() {
        if (instance == null) {
            instance = new RetrofitHelper();
        }
        return instance;
    }

    private RetrofitHelper() {

//        mRetrofit.
//        mGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    /**
     * 创建服务接口实例
     *
     * @param clazz 服务接口的class对象
     * @param <T>   服务接口
     * @return 服务接口实例
     */
    public <T> T createService(Class<T> clazz) {
        if (mRetrofit == null) {
            return null;
        }
        return mRetrofit.create(clazz);
    }

    /**
     * 将参数转为json字符串
     *
     * @param params 请求参数
     * @return json字符串
     */
//    public static String getParamJSON(Object params) {
//        RequestData requestData = new RequestData();
//        requestData.setClient(Constant.DEVICE_TYPE);
//        requestData.setData(params);
//        return mGson.toJson(requestData);
//    }
}
