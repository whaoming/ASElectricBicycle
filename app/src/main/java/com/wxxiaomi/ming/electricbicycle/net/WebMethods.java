package com.wxxiaomi.ming.electricbicycle.net;

import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.ConstantValue;
import com.wxxiaomi.ming.electricbicycle.net.service.ApiService;

import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 12262 on 2016/11/8.
 */

public class WebMethods {
    public static final String BASE_URL = ConstantValue.SERVER_URL;

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private ApiService demoService;

    //构造方法私有
    private WebMethods() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        demoService = retrofit.create(ApiService.class);
    }

    public Observable<String> sendPost(String url, Map<String, String> pars) {
        Log.i("wang","httpMethod->sendPost被调用了,url="+url);
        for(Map.Entry<String,String> item : pars.entrySet()){
            Log.i("wang","key:"+item.getKey()+"--value:"+item.getValue());
        }
        return demoService.sendPost(url,pars)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final WebMethods INSTANCE = new WebMethods();
    }

    //获取单例
    public static WebMethods getInstance() {
        return WebMethods.SingletonHolder.INSTANCE;
    }

    public Observable<String> listTopic(int start){
        return demoService.listTopic(start)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> sendget(String url,Map<String,String> pars){
        Log.i("wang","httpMethod->sendget被调用了,url="+url);
        for(Map.Entry<String,String> item : pars.entrySet()){
            Log.i("wang","key:"+item.getKey()+"--value:"+item.getValue());
        }
        return demoService.sendGet(url,pars)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
