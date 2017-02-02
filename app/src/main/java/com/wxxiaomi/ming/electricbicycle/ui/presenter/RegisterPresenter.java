package com.wxxiaomi.ming.electricbicycle.ui.presenter;

import com.wxxiaomi.ming.electricbicycle.ui.presenter.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.ui.activity.base.BaseView;

/**
 * Created by Administrator on 2016/12/8.
 */

public interface RegisterPresenter<V extends BaseView> extends BasePre<V> {
    void onRegisterBtnClick(String username,String password);
}
