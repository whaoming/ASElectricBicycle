package com.wxxiaomi.ming.electricbicycle.net.provider;

import rx.Observable;

/**
 * Created by Mr.W on 2017/2/14.
 * E-maiil：122627018@qq.com
 * github：https://github.com/122627018
 */

public interface TokenProvider {
    Observable<String> getToken();
}
