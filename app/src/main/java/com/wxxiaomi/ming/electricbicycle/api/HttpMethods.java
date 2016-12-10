package com.wxxiaomi.ming.electricbicycle.api;

import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.ConstantValue;
import com.wxxiaomi.ming.electricbicycle.GlobalParams;
import com.wxxiaomi.ming.electricbicycle.api.exception.ExceptionEngine;
import com.wxxiaomi.ming.electricbicycle.api.exception.ServerException;
import com.wxxiaomi.ming.electricbicycle.api.service.DemoService;
import com.wxxiaomi.ming.electricbicycle.dao.bean.Option;
import com.wxxiaomi.ming.electricbicycle.dao.bean.OptionLogs;
import com.wxxiaomi.ming.electricbicycle.dao.bean.User;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserLocatInfo;

import com.wxxiaomi.ming.electricbicycle.dao.common.Result;


import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
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
 * 33333
 * 44444
 */
public class HttpMethods {
    public static final String BASE_URL = ConstantValue.SERVER_URL;

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private DemoService demoService;
    private boolean test = false;


    //构造方法私有
    private HttpMethods() {

        //手动创建一个OkHttpClient并设置超时时间
//        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
//        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        if(ConstantValue.isDeBug){
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //输出每次返回的json数据的字符串
            builder.addInterceptor
                    (new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
            builder.addInterceptor
                    (new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS));
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request newRequest = chain.request().newBuilder()
                            .addHeader("token",GlobalParams.token)
                            .build();
                    Log.i("wang","currentThread:"+Thread.currentThread().getName());
                    Response response = chain.proceed(newRequest);

                    if(response.header("token")!=null){
                        Log.i("wang","返回的头部中发现了token");
                        if(test){
                            GlobalParams.token = response.header("token");
                        }
                        test = !test;
                    }
                    return response;
                }
            });
            OkHttpClient okHttpClient = builder.build();
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
    public Observable<List<UserCommonInfo>> getTopMovie(String username, String password) {
        return demoService.initUserInfo(username, password)
                .map(new ServerResultFunc<List<UserCommonInfo>>())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends List<UserCommonInfo>>>() {
                    @Override
                    public Observable<? extends List<UserCommonInfo>> call(Throwable throwable) {
                        return Observable.error(ExceptionEngine.handleException(throwable));
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }

//    public Observable<Login> login2(String username,String password){
//        return demoService.readBaidu2(username, password)
//                .flatMap(new Func1<retrofit2.Response<Login>, Observable<Login>>() {
//                    @Override
//                    public Observable<Login> call(retrofit2.Response<Login> loginResponse) {
//                        String token = loginResponse.headers().get("token");
//                        Log.i("wang","服务器响应头发现token："+token);
//                        return Observable.just(loginResponse.body());
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                ;
//    }

    public Observable<User> login(String username, String password) {
        return demoService.readBaidu(username, password)
                .map(new ServerResultFunc<User>())
                .retryWhen(new TokenOutTime(3,1))
                .onErrorResumeNext(new HttpResultFunc<User>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }

    public Observable<List<UserCommonInfo>> getUserListByEmList(List<String> emnamelist) {
        String temp = "";
        for (String e : emnamelist) {
            temp += e + "<>";
        }
//        Observable.create()
        return demoService.getUserListByEmList(temp)
                .map(new ServerResultFunc<List<UserCommonInfo>>())
                .retryWhen(new TokenOutTime(3,1))
                .onErrorResumeNext(new HttpResultFunc<List<UserCommonInfo>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }

    public Observable<List<UserCommonInfo>> getUserCommonInfoByEmname(String emname) {
        emname = emname + "<>";
        return demoService.getUserListByEmList(emname)
                .map(new ServerResultFunc<List<UserCommonInfo>>())
                .onErrorResumeNext(new HttpResultFunc<List<UserCommonInfo>>());
    }

    public Observable<List<UserLocatInfo>> getNearByFromServer(int userid, double latitude, double longitude) {
        return demoService.getNearByFromServer(userid, latitude, longitude)
                .map(new ServerResultFunc<List<UserLocatInfo>>())
                .onErrorResumeNext(new HttpResultFunc<List<UserLocatInfo>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<UserCommonInfo>> getUserCommonInfoByName(String name) {
        return demoService.getUserCommonInfoByName(name)
                .map(new ServerResultFunc<List<UserCommonInfo>>())
                .onErrorResumeNext(new HttpResultFunc<List<UserCommonInfo>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

//    public Observable<Register> registerUser(String username, String password) {
//        Log.i("wang","HttpMethods->registerUser");
//        return demoService.registerUser(username, password)
//                .map(new ServerResultFunc<Register>())
//                .onErrorResumeNext(new HttpResultFunc<Register>())
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }

    public Observable<String> upLoadHead(String fileName, RequestBody imgs){
        return demoService.uploadImage(fileName,imgs)
                .map(new ServerResultFunc<String>())
                .onErrorResumeNext(new HttpResultFunc<String>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<OptionLogs>> optionLogs(int userid){
        return demoService.optionLogs(userid)
                .map(new ServerResultFunc<List<OptionLogs>>())
                .onErrorResumeNext(new HttpResultFunc<List<OptionLogs>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Option>> getOption(int userid){
        return demoService.listOption(userid)
                .map(new ServerResultFunc<List<Option>>())
                .onErrorResumeNext(new HttpResultFunc<List<Option>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> demodemo(){
        return login("122627018","987987987")
                .flatMap(new Func1<User, Observable<String>>() {
                    @Override
                    public Observable<String> call(User login) {
                        Log.i("wang","再次登录成功");
                        return Observable.just("asd");
                    }
                })
                .subscribeOn(Schedulers.io())
                ;
    }



    private class ServerResultFunc<T> implements Func1<Result<T>, T> {
        @Override
        public T call(Result<T> httpResult) {
            Log.i("wang","HttpMethods->ServerResultFunc,httpResult==null?"+(httpResult==null));
//            Log.i("wang","httpResult.toString()="+httpResult.toString());
            if(httpResult==null){
                throw new ServerException(404, "获取结构为空");
            }else if(httpResult.state == 401){

                throw new ServerException(401, "token过期");
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

    private class TokenOutTime implements  Func1<Observable<? extends Throwable>, Observable<?>>{
        private  int maxRetries;
        private  int retryDelayMillis;
        private int retryCount = 0;

        public TokenOutTime(int maxRetries, int retryDelayMillis){
            this.maxRetries = maxRetries;
            this.retryDelayMillis = retryDelayMillis;
        }
        @Override
        public Observable<?> call(Observable<? extends Throwable> observable) {
            return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                @Override
                public Observable<?> call(Throwable throwable) {
                    Log.i("wang","throwable instanceof ServerException:"+(throwable instanceof ServerException ));
                    if(throwable instanceof ServerException){
                        ServerException ex = (ServerException)throwable;
                        if(ex.getCode() == 401){
                            Log.i("wang","httpResult.state == 401,发现token过期");
                            //重新获取token，并返回这个
                            if (++retryCount <= maxRetries) {
                                Log.i("wang","正在准备发送重新获取token的请求");
                                return demodemo()
                                        ;
                            }
                        }
                    }
                    return Observable.error(throwable);
                }
            });
        }
    }





}
