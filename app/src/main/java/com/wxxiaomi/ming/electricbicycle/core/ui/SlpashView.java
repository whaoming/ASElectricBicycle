package com.wxxiaomi.ming.electricbicycle.core.ui;


import com.wxxiaomi.ming.electricbicycle.core.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.core.base.BaseView;


/**
 * Created by 12262 on 2016/5/28.
 */
public interface SlpashView<T extends BasePre> extends BaseView<T> {
    /**
     * 跳转到注册页面
     */
    void runRegisterAct();

    /**
     * 跳转到欢迎页面
     */
    void runWelcomeAct();

}
