package com.wxxiaomi.ming.electricbicycle.ui.activity.base;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by 12262 on 2016/10/18.
 *
 * 初级视图接口
 */
public interface BaseView<T> {
    void runActivity(Class clazz, Bundle bundle, boolean isFinish);
    T getPresenter();
    Context getContext();

}
