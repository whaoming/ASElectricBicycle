package com.wxxiaomi.ming.electricbicycle.net.interceptor;

import com.wxxiaomi.ming.common.net.ServerException;
import com.wxxiaomi.ming.common.net.cons.Result;

import rx.functions.Func1;

/**
 * Created by Mr.W on 2017/2/14.
 * E-maiil：122627018@qq.com
 * github：https://github.com/whaoming
 * TODO:解析服务器返回的数据格式，对客户端的错误进行解析
 */
public class ResultParseInterceptor <T> implements Func1<Result<T>, T> {
    @Override
    public T call(Result<T> httpResult) {
        if(httpResult==null){
            throw new ServerException(404, "获取结构为空");
        }else if(httpResult.state!=200){
            throw new ServerException(httpResult.state,httpResult.error);
        }
        return httpResult.infos;
    }
}
