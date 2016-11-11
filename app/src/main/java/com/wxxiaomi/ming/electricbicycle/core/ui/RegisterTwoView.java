package com.wxxiaomi.ming.electricbicycle.core.ui;

import android.content.Intent;

import com.wxxiaomi.ming.electricbicycle.core.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.core.base.BaseView;

/**
 * Created by 12262 on 2016/6/26.
 */
public interface RegisterTwoView<T extends BasePre> extends BaseView<T> {
    Intent getIntentData();
}
