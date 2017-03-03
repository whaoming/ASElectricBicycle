package com.wxxiaomi.ming.electricbicycle.ui.activity.base;

import android.os.Bundle;
import android.os.PersistableBundle;

/**
 * Created by Mr.W on 2017/2/23.
 * E-mail：122627018@qq.com
 * Github：https://github.com/whaoming
 * TODO: 作为所有普通actiity的基类
 */
public abstract class NormalActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        initView(savedInstanceState);
    }

}
