package com.wxxiaomi.ming.electricbicycle.core.ui;

import android.os.Bundle;
import android.widget.ImageView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.model.LatLng;
import com.wxxiaomi.ming.electricbicycle.core.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.core.base.BaseView;


/**
 * Created by 12262 on 2016/6/6.
 */
public interface HomeView<T extends BasePre> extends BaseView<T> {
    void showSnackBar(String content);

    /**
     * 设置缩放控件不可见
     */
    void addMaker(LatLng point,int position);
    void scrollToMyLocat();
    void updateUnreadLabel(int count);
    void showRemoteLoginDialog();
    boolean isNearViewVis();
    void editNearViewState(boolean hide,boolean repeatShow);
    void runActivity(Class clazz,Bundle bundle);

    BaiduMap getMap();

    ImageView getHeadView();
}
