package com.wxxiaomi.ming.electricbicycle.core.presenter;

import com.wxxiaomi.ming.electricbicycle.core.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.core.base.BaseView;


/**
 * Created by 12262 on 2016/7/2.
 */
public interface UserInfoPresenter<V extends BaseView> extends BasePre<V> {
    void onAddBtnClick();
}
