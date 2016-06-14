package com.wxxiaomi.ming.electricbicycle.presenter.impl;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.wxxiaomi.ming.electricbicycle.GlobalParams;
import com.wxxiaomi.ming.electricbicycle.api.exception.ApiException;
import com.wxxiaomi.ming.electricbicycle.bean.User;
import com.wxxiaomi.ming.electricbicycle.bean.format.NearByPerson;
import com.wxxiaomi.ming.electricbicycle.model.impl.RxEmModelImpl2;
import com.wxxiaomi.ming.electricbicycle.model.impl.UserModelImpl;
import com.wxxiaomi.ming.electricbicycle.presenter.HomePresenter;
import com.wxxiaomi.ming.electricbicycle.presenter.base.BasePresenter;
import com.wxxiaomi.ming.electricbicycle.support.rx.MyObserver;
import com.wxxiaomi.ming.electricbicycle.ui.activity.ContactActivity1;
import com.wxxiaomi.ming.electricbicycle.ui.view.HomeView;
import com.wxxiaomi.ming.electricbicycle.view.activity.PersonalActivity;
import com.wxxiaomi.ming.electricbicycle.view.activity.SearchActivity;
import com.wxxiaomi.ming.electricbicycle.view.activity.UserInfoActivity;
import com.wxxiaomi.ming.electricbicycle.view.custom.CircularImageView;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 12262 on 2016/6/6.
 */
public class HomePresenterImpl extends BasePresenter<HomeView, UserModelImpl> implements HomePresenter {

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
    private User.UserCommonInfo currentNearPerson;
    private List<NearByPerson.UserLocatInfo> userLocatList;

    public HomePresenterImpl(HomeView homeView) {
        super(homeView);
    }

    @Override
    public void initMap(BaiduMap mBaiduMap) {
        this.mBaiduMap = mBaiduMap;
        myListener = new MyLocationListenner();
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap.setMyLocationEnabled(true);
        /**
         * mode - 定位图层显示方式, 默认为 LocationMode.NORMAL 普通态 enableDirection -
         * 是否允许显示方向信息 customMarker - 设置用户自定义定位图标，可以为 null
         */
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                mCurrentMode, true, null));
        // 定位初始化
        mLocClient = new LocationClient(view.getContext());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(2000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    @Override
    public void initViewData() {
        RxEmModelImpl2.getInstance().init();
    }

    @Override
    public void onMakerClick(Marker marker) {
        int zIndex = marker.getZIndex();
        User.UserCommonInfo tempUser = userLocatList.get(zIndex).userCommonInfo;
        Log.i("wang",tempUser.toString());
        boolean isSame = (currentNearPerson == tempUser);
        currentNearPerson = tempUser;
        view.editNearViewState(view.isNearViewVis(),isSame);
    }

    @Override
    public void adapterNerarView(CircularImageView imageView, TextView tv_name, TextView tv_description) {
        tv_name.setText(currentNearPerson.name);
        tv_description.setText("生活就像海洋,只有意志坚定的人才能到彼岸");
    }

    @Override
    public void onResume() {
        updateUnreadLabel();
//        RxEmModelImpl.getInstance().handleEMListener()
//                .subscribe(new MyObserver<Integer>() {
//                    @Override
//                    protected void onError(ApiException ex) {
//
//                    }
//
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        Log.i("wang","在HomePresenter中收到消息数：="+integer);
//                        updateUnreadLabel();
//                    }
//                });
        RxEmModelImpl2.getInstance().setAllMsgLis(new RxEmModelImpl2.AllMsgListener() {
            @Override
            public void AllMsgReceive() {
                updateUnreadLabel();
            }
        });
    }

    @Override
    public void goBtnOnClick() {
        view.runActivity(SearchActivity.class,null);
    }

    @Override
    public void contactBtnOnClick() {
        view.runActivity(ContactActivity1.class,null);
    }

    @Override
    public void headBtnOnClick() {
        view.runActivity(PersonalActivity.class,null);
    }

    @Override
    public void locatBtnOnClick() {
        view.scrollToMyLocat();
    }

    @Override
    public void nearHeadBtnOnClick() {
        Bundle bundle = new Bundle();
                bundle.putSerializable("userInfo", currentNearPerson);
        view.runActivity(UserInfoActivity.class,bundle);
    }

    public void updateUnreadLabel(){
        RxEmModelImpl2.getInstance().getUnreadMsgCount()
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

                        view.updateUnreadLabel(integer);
                    }
                });
    }

    @Override
    public void onViewCreate() {

    }

    @Override
    public void onViewDestory() {
        RxEmModelImpl2.getInstance().logout();
        mLocClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
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
            // 测试用
            GlobalParams.latitude = latitude;
            GlobalParams.longitude = longitude;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(0)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                Log.i("wang", "获取自己的位置");
                isFirstLoc = false;
                view.scrollToMyLocat();
                getNearByFromServer(latitude, longitude);
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    public void getNearByFromServer(final double latitude,
                                    final double longitude){
        model.getNearPeople(GlobalParams.user.id,latitude,longitude)
                .flatMap(new Func1<NearByPerson, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(NearByPerson nearByPerson) {
                        userLocatList = nearByPerson.userLocatList;
                       for(int i=0;i<nearByPerson.userLocatList.size();i++){
                           NearByPerson.UserLocatInfo user = nearByPerson.userLocatList.get(i);
                           LatLng point = new LatLng(user.locat[0], user.locat[1]);
                           view.addMaker(point,i);
                       }
                        return Observable.just(true);
                    }
                })
                .subscribe(new MyObserver<Boolean>() {
                    @Override
                    protected void onError(ApiException ex) {

                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {

                    }
                });
    }
}
