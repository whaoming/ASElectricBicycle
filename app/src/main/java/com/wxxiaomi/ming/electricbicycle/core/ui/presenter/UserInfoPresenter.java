package com.wxxiaomi.ming.electricbicycle.core.ui.presenter;

import com.wxxiaomi.ming.electricbicycle.core.ui.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.core.ui.base.BaseView;


/**
 * Created by 12262 on 2016/7/2.
 */
public interface UserInfoPresenter<V extends BaseView> extends BasePre<V> {
    void onAddBtnClick();
}
