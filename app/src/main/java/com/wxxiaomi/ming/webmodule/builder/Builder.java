package com.wxxiaomi.ming.webmodule.builder;

import android.content.Intent;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/12/1.
 */

public interface Builder {
    /**
     * 建造布局
     * @return
     */
    ViewGroup buildView();

    /**
     * 初始化布局数据
     */
    void initPageData();

    /**
     * 注册各种方法
     */
    void registerMethod();

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
