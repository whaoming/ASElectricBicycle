package com.wxxiaomi.ming.electricbicycle.core.ui.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.wxxiaomi.ming.electricbicycle.core.weight.adapter.OptionAdapter;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.core.ui.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.core.ui.base.BaseView;


/**
 * Created by 12262 on 2016/7/2.
 */
public interface UserInfoView<T extends BasePre> extends BaseView<T> {
    Intent getIntent();
    void setViewData(UserCommonInfo info);
    void setBtnView(Drawable drawable);
    void setAdapter(OptionAdapter adapter);
}
