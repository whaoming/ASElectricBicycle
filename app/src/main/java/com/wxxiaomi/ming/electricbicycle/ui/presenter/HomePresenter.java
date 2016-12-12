package com.wxxiaomi.ming.electricbicycle.ui.presenter;

import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.Marker;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.ui.activity.base.BaseView;

import com.wxxiaomi.ming.electricbicycle.ui.weight.custom.CircularImageView;

/**
 * Created by 12262 on 2016/6/6.
 */
public interface HomePresenter<V extends BaseView> extends BasePre<V> {
    /**
     * 初始化百度地图
     * @param mBaiduMap
     */
    void initMap(BaiduMap mBaiduMap);

    /**
     * 初始化地图数据
     */
    void initViewData();

    void onMakerClick(Marker marker);

    void adapterNerarView(CircularImageView imageView,TextView tv_name,TextView tv_description);

    void goBtnOnClick();
    void contactBtnOnClick();
    void headBtnOnClick();
    void locatBtnOnClick();
    void nearHeadBtnOnClick();

    void topicBtnOnClick();
}
