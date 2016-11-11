package com.wxxiaomi.ming.electricbicycle.core.presenter;

import com.wxxiaomi.ming.electricbicycle.core.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.core.base.BaseView;

/**
 * Created by 12262 on 2016/10/28.
 */

public interface WelcomePre<V extends BaseView> extends BasePre<V> {
    void onRegisterClick();
    void onLoginClick();
}
