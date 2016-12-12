package com.wxxiaomi.ming.electricbicycle.ui.presenter;

import com.wxxiaomi.ming.electricbicycle.ui.presenter.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.ui.activity.base.BaseView;

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
//     * @param iv
     */
//    void updateHeadView(ImageView iv);

//    void onCreate(Bundle savedInstanceState, BaseActivity act);

//    void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);

//    void onActivityResult(int requestCode, int resultCode, Intent data);

//    void onSaveInstanceState(Bundle outState);

    void onSettingClick();

    void onEditClick();
}
