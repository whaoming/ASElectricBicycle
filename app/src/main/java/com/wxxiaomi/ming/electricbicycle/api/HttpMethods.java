package com.wxxiaomi.ming.electricbicycle.api;

import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.ConstantValue;
import com.wxxiaomi.ming.electricbicycle.EBApplication;
import com.wxxiaomi.ming.electricbicycle.GlobalParams;
import com.wxxiaomi.ming.electricbicycle.api.exception.ExceptionEngine;
import com.wxxiaomi.ming.electricbicycle.api.exception.ServerException;
import com.wxxiaomi.ming.electricbicycle.api.service.DemoService;
import com.wxxiaomi.ming.electricbicycle.common.PreferenceManager;
import com.wxxiaomi.ming.electricbicycle.common.util.UniqueUtil;
import com.wxxiaomi.ming.electricbicycle.dao.bean.Option;
import com.wxxiaomi.ming.electricbicycle.dao.bean.OptionLogs;
import com.wxxiaomi.ming.electricbicycle.dao.bean.User;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo2;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserLocatInfo;

import com.wxxiaomi.ming.electricbicycle.dao.common.Result;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import rx.Subscriber;
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
                    Response response = chain.proceed(newRequest);

                    if(response.header("token")!=null){
                        Log.i("wang","发现瘦肉汤_token");
                            GlobalParams.token = response.header("token");
                        PreferenceManager.getInstance().savaShortToken(response.header("token"));
                    }
                    String long_token = response.header("long_token");
                    if(long_token!=null){
                        Log.i("wang","发现long_token");
//                        SharedPreferencesUtils.setParam(EBApplication.applicationContext,ConstantValue.LONGTOKEN,long_token);
                        PreferenceManager.getInstance().savaLongToken(long_token);
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
    public Observable<List<UserCommonInfo2>> getTopMovie(String username, String password) {
        return demoService.initUserInfo(username, password)
                .map(new ServerResultFunc<List<UserCommonInfo2>>())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends List<UserCommonInfo2>>>() {
                    @Override
                    public Observable<? extends List<UserCommonInfo2>> call(Throwable throwable) {
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

    public Observable<String> updateUserInfo(Map<String,String> pars){
        return demoService.updateUserInfo(pars)
                .map(new ServerResultFunc<String>())
//                .retryWhen(new TokenOutTime(3,1))
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends String>>() {
                    @Override
                    public Observable<? extends String> call(Throwable throwable) {
                        return Observable.error(ExceptionEngine.handleException(throwable));
                    }
                });

    }

    public Observable<String> updateUserInfo2(String name){
        return demoService.updateUserInfo2(name)
                .map(new ServerResultFunc<String>())
//                .retryWhen(new TokenOutTime(3,1))
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends String>>() {
                    @Override
                    public Observable<? extends String> call(Throwable throwable) {
                        return Observable.error(ExceptionEngine.handleException(throwable));
                    }
                });
    }

    public Observable<String> updateUserInfo3(UserCommonInfo2 name){
        return demoService.updateUserInfo3(name)
                .map(new ServerResultFunc<String>())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends String>>() {
                    @Override
                    public Observable<? extends String> call(Throwable throwable) {
                        return Observable.error(ExceptionEngine.handleException(throwable));
                    }
                });
    }

    public Observable<User> login(String username, String password,String num) {
        return demoService.readBaidu(username, password,num)
                .map(new ServerResultFunc<User>())
                .retryWhen(new TokenOutTime(3,1))
                .onErrorResumeNext(new HttpResultFunc<User>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }

    public Observable<List<UserCommonInfo2>> getUserListByEmList(List<String> emnamelist) {
        String temp = "";
        for (String e : emnamelist) {
            temp += e + "<>";
        }
//        Observable.create()
        return demoService.getUserListByEmList(temp)
                .map(new ServerResultFunc<List<UserCommonInfo2>>())
                .retryWhen(new TokenOutTime(3,1))
                .onErrorResumeNext(new HttpResultFunc<List<UserCommonInfo2>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }

    public Observable<List<UserCommonInfo2>> getUserCommonInfo2ByEmname(String emname) {
        emname = emname + "<>";
        return demoService.getUserListByEmList(emname)
                .map(new ServerResultFunc<List<UserCommonInfo2>>())
                .retryWhen(new TokenOutTime(3,1))
                .onErrorResumeNext(new HttpResultFunc<List<UserCommonInfo2>>());
    }

    public Observable<List<UserLocatInfo>> getNearByFromServer(int userid, double latitude, double longitude) {
        return demoService.getNearByFromServer(userid, latitude, longitude)
                .map(new ServerResultFunc<List<UserLocatInfo>>())
                .retryWhen(new TokenOutTime(3,1))
                .onErrorResumeNext(new HttpResultFunc<List<UserLocatInfo>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<UserCommonInfo2>> getUserCommonInfo2ByName(String name) {
        return demoService.getUserCommonInfoByName(name)
                .map(new ServerResultFunc<List<UserCommonInfo2>>())
                .retryWhen(new TokenOutTime(3,1))
                .onErrorResumeNext(new HttpResultFunc<List<UserCommonInfo2>>())
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
                .retryWhen(new TokenOutTime(3,1))
                .onErrorResumeNext(new HttpResultFunc<String>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<OptionLogs>> optionLogs(int userid){
        return demoService.optionLogs(userid)
                .map(new ServerResultFunc<List<OptionLogs>>())
                .retryWhen(new TokenOutTime(3,1))
                .onErrorResumeNext(new HttpResultFunc<List<OptionLogs>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Option>> getOption(int userid){
        return demoService.listOption(userid)
                .map(new ServerResultFunc<List<Option>>())
                .retryWhen(new TokenOutTime(3,1))
                .onErrorResumeNext(new HttpResultFunc<List<Option>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 向服务器发送长token，获取短token
     * 还要发送设备唯一id
     * @return
     */
    public Observable<String> Token_Long2Short(){
        UniqueUtil util = new UniqueUtil(EBApplication.applicationContext);
        String uniqueID = util.getUniqueID();
        String long_token = PreferenceManager.getInstance().getLongToken();
        return demoService.getSToken(long_token,uniqueID)
                .map(new ServerResultFunc<String>())
        .onErrorResumeNext(new HttpResultFunc<String>());
    }

    public Observable<List<UserCommonInfo2>> updateuserFriend(String friends){
        return demoService.updateUserFriend2(friends)
                .map(new ServerResultFunc<List<UserCommonInfo2>>())
                .onErrorResumeNext(new HttpResultFunc<List<UserCommonInfo2>>())
                .subscribeOn(Schedulers.io())
                ;
    }

    public Observable<List<UserCommonInfo2>> updateuserFriend2(String friends){
        return demoService.updateUserFriend3(friends)
                .map(new ServerResultFunc<List<UserCommonInfo2>>())
                .onErrorResumeNext(new HttpResultFunc<List<UserCommonInfo2>>())
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
                throw new ServerException(401, "短token过期，重新获取长token");
            }else if(httpResult.state == 402){
                throw new ServerException(402, "token过期,需要重新登陆");
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
                                return Token_Long2Short()
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
