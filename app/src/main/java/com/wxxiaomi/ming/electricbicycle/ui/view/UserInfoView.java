package com.wxxiaomi.ming.electricbicycle.ui.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.wxxiaomi.ming.electricbicycle.bean.User;
import com.wxxiaomi.ming.electricbicycle.ui.view.base.BaseView;

/**
 * Created by 12262 on 2016/7/2.
 */
public interface UserInfoView extends BaseView {
    Intent getIntent();
    void setViewData(User.UserCommonInfo info);
    void setBtnView(Drawable drawable);
}
