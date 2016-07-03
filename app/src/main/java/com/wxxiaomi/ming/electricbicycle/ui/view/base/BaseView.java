package com.wxxiaomi.ming.electricbicycle.ui.view.base;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by 12262 on 2016/5/29.
 */
public interface BaseView {
    Context getContext();
    void runActivity(Class clazz, Bundle bundle,boolean isFinish);
}
