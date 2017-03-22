package com.wxxiaomi.ming.electricbicycle.net;

import com.wxxiaomi.ming.common.net.cons.Result;
import com.wxxiaomi.ming.electricbicycle.net.interceptor.ErrorInterceptor;
import com.wxxiaomi.ming.electricbicycle.net.interceptor.ResultParseInterceptor;
import com.wxxiaomi.ming.electricbicycle.net.interceptor.TokenExpireInterceptor;
import com.wxxiaomi.ming.electricbicycle.net.provider.TokenProvider;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Mr.W on 2017/2/14.
 * E-maiil：122627018@qq.com
 * github：https://github.com/whaoming
 * TODO: 按照创建者模式的思想，把一个访问服务器的操作规格化
 */
public class Direct2 {
   public static<T> Observable<T> create(Observable<Result<T>> resurce,TokenProvider tokenProvider){
       return resurce
               .map(new ResultParseInterceptor<T>())

               .retryWhen(new TokenExpireInterceptor(tokenProvider))
               .onErrorResumeNext(new ErrorInterceptor<T>())
                .observeOn(AndroidSchedulers.mainThread())
               .subscribeOn(Schedulers.io());
   }
}
