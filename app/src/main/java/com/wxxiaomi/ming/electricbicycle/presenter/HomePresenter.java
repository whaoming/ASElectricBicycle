package com.wxxiaomi.ming.electricbicycle.presenter;

import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.Marker;
import com.wxxiaomi.ming.electricbicycle.presenter.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.view.custom.CircularImageView;

/**
 * Created by 12262 on 2016/6/6.
 */
public interface HomePresenter extends BasePre {
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
    void onResume();

    void goBtnOnClick();
    void contactBtnOnClick();
    void headBtnOnClick();
    void locatBtnOnClick();
    void nearHeadBtnOnClick();
}
