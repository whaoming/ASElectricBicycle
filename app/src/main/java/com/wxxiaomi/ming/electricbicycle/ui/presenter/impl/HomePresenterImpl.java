package com.wxxiaomi.ming.electricbicycle.ui.presenter.impl;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.baidu.location.Address;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.bumptech.glide.Glide;
import com.wxxiaomi.ming.electricbicycle.ConstantValue;
import com.wxxiaomi.ming.electricbicycle.api.exception.ApiException;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserLocatInfo;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.base.BasePreImpl;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.HomePresenter;
import com.wxxiaomi.ming.electricbicycle.support.baidumap.LocationUtil;
import com.wxxiaomi.ming.electricbicycle.support.web.TestWebActivity;
import com.wxxiaomi.ming.electricbicycle.dao.db.UserService;
import com.wxxiaomi.ming.electricbicycle.support.easemob.EmEngine;
import com.wxxiaomi.ming.electricbicycle.support.easemob.listener.AllMsgListener;
import com.wxxiaomi.ming.electricbicycle.common.GlobalManager;
import com.wxxiaomi.ming.electricbicycle.common.rx.MyObserver;
import com.wxxiaomi.ming.electricbicycle.ui.activity.ContactActivity;
import com.wxxiaomi.ming.electricbicycle.ui.activity.PersonalAct;
import com.wxxiaomi.ming.electricbicycle.ui.activity.UserInfoAct;
import com.wxxiaomi.ming.electricbicycle.ui.activity.view.HomeView;

import com.wxxiaomi.ming.electricbicycle.ui.weight.custom.CircularImageView;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by 12262 on 2016/6/6.
 * 主界面
 */
public class HomePresenterImpl extends BasePreImpl<HomeView> implements HomePresenter<HomeView> {

    private LocationClient mLocClient;
    /**
     * 定位的模式
     */
    private MyLocationConfiguration.LocationMode mCurrentMode;
    /**
     * 定位完成后的监听
     */
    public MyLocationListenner myListener;
    private BaiduMap mBaiduMap;
    boolean isFirstLoc = true; // 是否首次定位
    /**
     * 当前所点击的附近的人的信息
     */
    private UserCommonInfo currentNearPerson;
    private List<UserLocatInfo> userLocatList;

    @Override
    public void init() {
        initMap(mView.getMap());
        initViewData();
    }



    @Override
    public void initMap(BaiduMap mBaiduMap) {
        this.mBaiduMap = mBaiduMap;
        myListener = new MyLocationListenner();
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(mView.getContext());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    @Override
    public void initViewData() {

    }

    @Override
    public void onMakerClick(Marker marker) {
        int zIndex = marker.getZIndex();
        UserCommonInfo tempUser = userLocatList.get(zIndex).userCommonInfo;
        Log.i("wang",tempUser.toString());
        boolean isSame = (currentNearPerson == tempUser);
        currentNearPerson = tempUser;
        mView.editNearViewState(mView.isNearViewVis(),isSame);
    }

    @Override
    public void adapterNerarView(CircularImageView imageView, TextView tv_name, TextView tv_description) {
        tv_name.setText(currentNearPerson.name);
        tv_description.setText("生活就像海洋,只有意志坚定的人才能到彼岸");
    }


    @Override
    public void goBtnOnClick() {
//        mView.runActivity(SearchActiity.class,null);
    }

    @Override
    public void contactBtnOnClick() {
        mView.runActivity(ContactActivity.class,null);
    }

    @Override
    public void headBtnOnClick() {
        mView.runActivity(PersonalAct.class,null);
    }

    @Override
    public void locatBtnOnClick() {
        mView.scrollToMyLocat();
    }

    @Override
    public void nearHeadBtnOnClick() {
        Bundle bundle = new Bundle();
                bundle.putSerializable("userInfo", currentNearPerson);
        mView.runActivity(UserInfoAct.class,bundle);
    }

    @Override
    public void topicBtnOnClick() {
        Log.i("wang","话题按钮被点击了");
        Intent intent = new Intent(mView.getContext(), TestWebActivity.class);
        intent.putExtra("url", ConstantValue.SERVER_URL+"/app/topicList_1.html");
        mView.getContext().startActivity(intent);
    }

    public void updateUnreadLabel(){
        EmEngine.getInstance().getUnreadMsgCount()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new MyObserver<Integer>() {
                    @Override
                    protected void onError(ApiException ex) {

                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(Integer integer) {

                        mView.updateUnreadLabel(integer);
                    }
                });
    }


    @Override
    public void onViewDestory() {
        EmEngine.getInstance().logout();
        mLocClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
    }



    @Override
    public void onViewResume() {
        Glide.with(mView.getContext()).load(GlobalManager.getInstance().getUser().userCommonInfo.head)
                .into(mView.getHeadView());
        updateUnreadLabel();
        EmEngine.getInstance().setAllMsgLis(new AllMsgListener() {
            @Override
            public void AllMsgReceive() {
                updateUnreadLabel();
            }
        });
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mBaiduMap == null) {
                return;
            }
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            Address address = location.getAddress();
            String locat_tag = location.getLocationDescribe();
            LocationUtil.getInstance().init(latitude,longitude,address,locat_tag);
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(0)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100)
                    .latitude(latitude)
                    .longitude(longitude)
                    .build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                Log.i("wang", "获取自己的位置");
                isFirstLoc = false;
                mView.scrollToMyLocat();
                getNearByFromServer(latitude, longitude);
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    public void getNearByFromServer(final double latitude,
                                    final double longitude){
        UserService.getInstance().getNearPeople(GlobalManager.getInstance().getUser().id,latitude,longitude)
                .subscribe(new Action1<List<UserLocatInfo>>() {
                    @Override
                    public void call(List<UserLocatInfo> nearByPerson) {
                        userLocatList = nearByPerson;
                       for(int i=0;i<nearByPerson.size();i++){
                           UserLocatInfo user = nearByPerson.get(i);
                           LatLng point = new LatLng(user.point[0], user.point[1]);
                           mView.addMaker(point,i);
                       }
                    }
                });
//
    }

}
