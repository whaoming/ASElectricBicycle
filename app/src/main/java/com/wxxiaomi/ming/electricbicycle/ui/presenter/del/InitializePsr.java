package com.wxxiaomi.ming.electricbicycle.ui.presenter.del;

import com.wxxiaomi.ming.electricbicycle.ui.activity.base.BaseView;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.base.BasePre;

/**
 * 初始化相关的handler
 * @param <V>
 */
public interface InitializePsr<V extends BaseView> extends BasePre<V> {
    void autoLogin();
    void onLoginBtnClick(String username, String password);
    void onDebugBtnClick();
}
