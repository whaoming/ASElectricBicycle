package com.wxxiaomi.ming.electricbicycle.net.interceptor;

import com.wxxiaomi.ming.common.net.ServerException;
import com.wxxiaomi.ming.electricbicycle.net.provider.TokenProvider;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Mr.W on 2017/2/14.
 * E-maiil：122627018@qq.com
 * github：https://github.com/122627018
 * TODO: 短token过期的处理
 */
public class TokenExpireInterceptor implements Func1<Observable<? extends Throwable>, Observable<?>> {

    TokenProvider tokenProvider;

    public TokenExpireInterceptor(TokenProvider tokenProvider){
        this.tokenProvider = tokenProvider;
    }
    @Override
    public Observable<?> call(Observable<? extends Throwable> observable) {
        return observable.flatMap(new Func1<Throwable, Observable<?>>() {
            @Override
            public Observable<?> call(Throwable throwable) {
                if(throwable instanceof ServerException){
                    ServerException ex = (ServerException)throwable;
                    if(ex.getCode() == 304){
                            return tokenProvider.getToken();
                    }else if(ex.getCode()==402){
                        //登录标志确定过期

                    }
                }
                return Observable.error(throwable);
            }
        });
    }
}
