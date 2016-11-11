package com.wxxiaomi.ming.electricbicycle.core.ui;

import com.wxxiaomi.ming.electricbicycle.core.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.core.base.BaseView;

import com.wxxiaomi.ming.electricbicycle.core.weight.adapter.NearFriendRecommendAdapter1;

/**
 * Created by 12262 on 2016/6/15.
 */
public interface FriendAddView<T extends BasePre> extends BaseView<T> {
    void setListAdaper(NearFriendRecommendAdapter1 adapter);
}
