package com.wxxiaomi.ming.electricbicycle.core.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.wxxiaomi.ming.electricbicycle.core.base.BaseActivity;
import com.wxxiaomi.ming.electricbicycle.core.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.core.base.BaseView;

/**
 * Created by 12262 on 2016/11/1.
 */

public interface PersonalPresenter<V extends BaseView> extends BasePre<V> {
    /**
     * 头像点击事件
     */
    void onHeadBrnClick();

    /**
     * 更新头像控件
     * @param iv
     */
    void updateHeadView(ImageView iv);

    void onCreate(Bundle savedInstanceState, BaseActivity act);

    void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onSaveInstanceState(Bundle outState);
}
