package com.wxxiaomi.ming.electricbicycle.ui.activity.view;

import com.wxxiaomi.ming.electricbicycle.ui.presenter.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.ui.activity.base.BaseView;

import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter.NearFriendRecommendAdapter1;

/**
 * Created by 12262 on 2016/6/15.
 */
public interface FriendAddView<T extends BasePre> extends BaseView<T> {
    void setListAdaper(NearFriendRecommendAdapter1 adapter);
}
