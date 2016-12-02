package com.wxxiaomi.ming.electricbicycle.core.ui.presenter;

import com.wxxiaomi.ming.electricbicycle.core.ui.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.core.ui.base.BaseView;

/**
 * Created by 12262 on 2016/10/28.
 */

public interface WelcomePre<V extends BaseView> extends BasePre<V> {
    void onRegisterClick();
    void onLoginClick();
}
