package com.wxxiaomi.ming.electricbicycle.ui.activity.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;

import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.ui.activity.base.BaseView;


/**
 * Created by 12262 on 2016/7/2.
 */
public interface UserInfoView extends BaseView {
    Intent getIntent();
    void setViewData(UserCommonInfo info);
    void setBtnView(Drawable drawable);
    void setAdapter(RecyclerView.Adapter adapter);
}
