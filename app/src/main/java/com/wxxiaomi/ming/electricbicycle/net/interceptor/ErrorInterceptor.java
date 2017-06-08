package com.wxxiaomi.ming.electricbicycle.net.interceptor;

import android.util.Log;

import com.wxxiaomi.ming.common.net.ExceptionProvider;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Mr.W on 2017/2/14.
 * E-maiil：122627018@qq.com
 * github：https://github.com/122627018
 * TODO: 异常解析的一个拦截器
 */
public class ErrorInterceptor<T> implements Func1<Throwable, Observable<T>> {
    @Override
    public Observable<T> call(Throwable throwable) {
        throwable.printStackTrace();
        return Observable.error(ExceptionProvider.handleException(throwable));
    }
}
