package com.wxxiaomi.ming.electricbicycle.ui.presenter;

import com.wxxiaomi.ming.electricbicycle.ui.presenter.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.ui.activity.base.BaseView;

/**
 * Created by 12262 on 2016/6/9.
 */
public interface ContactPresenter<V extends BaseView> extends BasePre<V> {
    void initDrawerData();
    void onAddFriendBtnClick();


    void onDrawClick();
}
