package com.wxxiaomi.ming.electricbicycle.core.presenter;


import com.wxxiaomi.ming.electricbicycle.core.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.core.base.BaseView;


/**
 * Created by 12262 on 2016/5/28.
 */
public interface SplashPre<V extends BaseView> extends BasePre<V> {
    void loadConfig();
}
