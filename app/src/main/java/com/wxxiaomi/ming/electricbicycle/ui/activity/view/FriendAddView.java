package com.wxxiaomi.ming.electricbicycle.ui.activity.view;

import android.view.View;

import com.wxxiaomi.ming.electricbicycle.ui.presenter.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.ui.activity.base.BaseView;

import com.wxxiaomi.ming.electricbicycle.ui.weight.pulltorefresh.recycleview.PullToRefreshRecyclerView;

/**
 * Created by 12262 on 2016/6/15.
 */
public interface FriendAddView<T extends BasePre> extends BaseView {
//    void setListAdaper(NearFriendRecommendAdapter1 adapter);
    PullToRefreshRecyclerView getListView();
    View getHeader();
    void setHeaderText(String text);
}
