package com.wxxiaomi.ming.electricbicycle.core.presenter;

import com.wxxiaomi.ming.electricbicycle.core.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.core.base.BaseView;

/**
 * Created by 12262 on 2016/6/9.
 */
public interface ContactPresenter<V extends BaseView> extends BasePre<V> {
    void initDrawerData();
    void onAddFriendBtnClick();


    void onDrawClick();
}
