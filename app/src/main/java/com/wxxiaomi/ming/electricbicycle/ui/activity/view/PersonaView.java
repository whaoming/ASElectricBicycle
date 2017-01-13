package com.wxxiaomi.ming.electricbicycle.ui.activity.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.ui.activity.base.BaseView;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pulltorefresh.recycleview.PullToRefreshRecyclerView;


/**
 * Created by 12262 on 2016/6/21.
 */
public interface PersonaView<T extends BasePre> extends BaseView {
    Intent getIntent();
    void setViewData(UserCommonInfo info);
    void setBtnView(Drawable drawable);
//    void setAdapter(RecyclerView.Adapter adapter);
    ImageView getHeadView();

    PullToRefreshRecyclerView getListView();

    ImageView getBackImgContent();
}
