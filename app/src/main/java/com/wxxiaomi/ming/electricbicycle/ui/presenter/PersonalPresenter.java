package com.wxxiaomi.ming.electricbicycle.ui.presenter;

import com.wxxiaomi.ming.electricbicycle.ui.presenter.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.ui.activity.base.BaseView;

/**
 * Created by 12262 on 2016/11/1.
 */

public interface PersonalPresenter<V extends BaseView> extends BasePre<V> {
    /**
     * 头像点击事件
     */
    void onHeadBrnClick();

    void onBackImgClick();

    void onSettingClick();

    void onEditClick();
}
