package com.wxxiaomi.ming.electricbicycle.api;

import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.ConstantValue;
import com.wxxiaomi.ming.electricbicycle.api.exception.ExceptionEngine;
import com.wxxiaomi.ming.electricbicycle.api.exception.ServerException;
import com.wxxiaomi.ming.electricbicycle.api.service.DemoService;
import com.wxxiaomi.ming.electricbicycle.bean.format.InitUserInfo;
import com.wxxiaomi.ming.electricbicycle.bean.format.Login;
import com.wxxiaomi.ming.electricbicycle.bean.format.NearByPerson;
import com.wxxiaomi.ming.electricbicycle.bean.format.Register;
import com.wxxiaomi.ming.electricbicycle.bean.format.common.Result;


import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 12262 on 2016/5/31.
 * 3333333
 */
public class HttpMethods {
    public static final String BASE_URL = ConstantValue.SERVER_URL;

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private DemoService demoService;

    //构造方法私有
    private HttpMethods() {

        //手动创建一个OkHttpClient并设置超时时间
//        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
//        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        if(ConstantValue.isDeBug){
            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor
                    (new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }else{
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }

        demoService = retrofit.create(DemoService.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 用于获取豆瓣电影Top250的数据
     */
    public Observable<InitUserInfo> getTopMovie(String username, String password) {

        return demoService.initUserInfo(username, password)
                .map(new ServerResultFunc<InitUserInfo>())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends InitUserInfo>>() {
                    @Override
                    public Observable<? extends InitUserInfo> call(Throwable throwable) {
                        return Observable.error(ExceptionEngine.handleException(throwable));
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
//                .subscribe()

    }

    public Observable<Login> login(String username, String password) {
        return demoService.readBaidu(username, password)
                .map(new ServerResultFunc<Login>())
                .onErrorResumeNext(new HttpResultFunc<Login>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<InitUserInfo> getUserListByEmList(List<String> emnamelist) {
        String temp = "";
        for (String e : emnamelist) {
            temp += e + "<>";
        }
//        Observable.create()
        return demoService.getUserListByEmList(temp)
                .map(new ServerResultFunc<InitUserInfo>())
                .onErrorResumeNext(new HttpResultFunc<InitUserInfo>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<InitUserInfo> getUserCommonInfoByEmname(String emname) {
        emname = emname + "<>";
        return demoService.getUserListByEmList(emname)
                .map(new ServerResultFunc<InitUserInfo>())
                .onErrorResumeNext(new HttpResultFunc<InitUserInfo>());
    }

    public Observable<NearByPerson> getNearByFromServer(int userid, double latitude, double longitude) {
        return demoService.getNearByFromServer(userid, latitude, longitude)
                .map(new ServerResultFunc<NearByPerson>())
                .onErrorResumeNext(new HttpResultFunc<NearByPerson>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<InitUserInfo> getUserCommonInfoByName(String name) {
        return demoService.getUserCommonInfoByName(name)
                .map(new ServerResultFunc<InitUserInfo>())
                .onErrorResumeNext(new HttpResultFunc<InitUserInfo>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Register> registerUser(String username, String password) {
        Log.i("wang","HttpMethods->registerUser");
        return demoService.registerUser(username, password)
                .map(new ServerResultFunc<Register>())
                .onErrorResumeNext(new HttpResultFunc<Register>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> upLoadHead(String fileName, RequestBody imgs){
        return demoService.uploadImage(fileName,imgs)
                .map(new ServerResultFunc<String>())
                .onErrorResumeNext(new HttpResultFunc<String>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



    private class ServerResultFunc<T> implements Func1<Result<T>, T> {
        @Override
        public T call(Result<T> httpResult) {
            Log.i("wang","HttpMethods->ServerResultFunc,httpResult==null?"+(httpResult==null));
//            Log.i("wang","httpResult.toString()="+httpResult.toString());
            if(httpResult==null){
                throw new ServerException(404, "获取结构为空");
            }
           else if (httpResult.state != 200) {
                throw new ServerException(httpResult.state, httpResult.error);
            }
            return httpResult.infos;
        }
    }

    private class HttpResultFunc<T> implements Func1<Throwable, Observable<T>> {
        @Override
        public Observable<T> call(Throwable throwable) {
            Log.i("wang","HttpMethod发现异常拉"+throwable.toString());
            throwable.printStackTrace();
            return Observable.error(ExceptionEngine.handleException(throwable));
        }
    }



}
