package com.wxxiaomi.ming.electricbicycle.core.ui.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.wxxiaomi.ming.electricbicycle.GlobalParams;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.core.ui.base.BaseActivity;
import com.wxxiaomi.ming.electricbicycle.core.ui.presenter.HomePresenter;
import com.wxxiaomi.ming.electricbicycle.core.ui.presenter.impl.HomePresenterImpl;
import com.wxxiaomi.ming.electricbicycle.core.ui.view.HomeView;
import com.wxxiaomi.ming.electricbicycle.core.weight.custom.CircularImageView;
import com.wxxiaomi.ming.electricbicycle.common.GlobalManager;
import com.wxxiaomi.ming.electricbicycle.support.baidumap.LocationUtil;

/**
 * Created by 12262 on 2016/6/6.
 * 主页面
 */
public class HomeActivity extends BaseActivity<HomeView,HomePresenter> implements HomeView<HomePresenter> {

    private CoordinatorLayout sn_layout;

    /**
     * 百度view
     */
    private TextureMapView mMapView;
    /**
     * 百度view对应的map空间
     */
    private BaiduMap mBaiduMap;

    boolean isFirstLoc = true; // 是否首次定位

    /**
     * 导航按钮
     */
    private FloatingActionButton btn_go;
    private FloatingActionButton btn_locat;


    private LinearLayout ll_nearby_view;
    private ImageButton iv_contact;
    private TextView tv_name;

    /**
     * 附近的人view显示位移动画
     */
    private TranslateAnimation mShowAction;
    /**
     * 附近的人view隐藏位移动画
     */
    private TranslateAnimation mHiddenAction;
    /**
     * 当前附近的人infoView是否可见
     */
    private boolean isNearViewVis = false;
    private CircularImageView iv_my_head;
    private CircularImageView near_iv_head;
    private TextView tv_near_name;
    private TextView tv_near_description;
    private TextView unread_msg_number;
    private ImageButton ib_topic;
    /**
     * 更新未读信息ui
     */
    private final int UPDATEUNREAD = 158;

    private final int SHOWREMOTEDIALOG = 2121;


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        unread_msg_number = (TextView) findViewById(R.id.unread_msg);
        unread_msg_number.setVisibility(View.GONE);
        sn_layout = (CoordinatorLayout) findViewById(R.id.sn_layout);
        ll_nearby_view = (LinearLayout) findViewById(R.id.ll_nearby_view);
        near_iv_head = (CircularImageView) ll_nearby_view
                .findViewById(R.id.near_iv_head);
        near_iv_head.setOnClickListener(this);
        tv_near_name = (TextView) ll_nearby_view
                .findViewById(R.id.tv_near_name);
        tv_near_description = (TextView) ll_nearby_view
                .findViewById(R.id.tv_near_description);
        mMapView = (TextureMapView) findViewById(R.id.mpaview);
        iv_contact = (ImageButton) findViewById(R.id.iv_contact);
        tv_name = (TextView) findViewById(R.id.tv_name);
        btn_go = (FloatingActionButton) findViewById(R.id.btn_go);
        btn_locat = (FloatingActionButton) findViewById(R.id.btn_locat);
        btn_locat.setOnClickListener(this);
        iv_my_head = (CircularImageView) findViewById(R.id.iv_my_head);
        iv_my_head.setOnClickListener(this);
        ib_topic = (ImageButton) findViewById(R.id.ib_topic);
        ib_topic.setOnClickListener(this);
        mBaiduMap = mMapView.getMap();
        btn_go.setOnClickListener(this);
        iv_contact.setOnClickListener(this);
        initAnimation();
//        setZoomInVis();
        initMapMarkerClickListener();
        tv_name.setText(GlobalManager.getInstance().getUser().userCommonInfo.name);
    }

    @Override
    public HomePresenter getPresenter() {
        return new HomePresenterImpl();
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_go:
                presenter.goBtnOnClick();

                break;
            case R.id.iv_contact:
                presenter.contactBtnOnClick();
                break;
            case R.id.iv_my_head:
                presenter.headBtnOnClick();
                break;
            case R.id.near_iv_head:
                presenter.nearHeadBtnOnClick();
                break;
            case R.id.btn_locat:
                presenter.locatBtnOnClick();
                break;
            case R.id.ib_topic:
                presenter.topicBtnOnClick();
            default:
                break;
        }
    }

    @Override
    public void showSnackBar(String content) {
        Snackbar.make(sn_layout, content, Snackbar.LENGTH_LONG).show();
    }

//    public void setZoomInVis() {
//        int childCount = mMapView.getChildCount();
//        View zoom = null;
//        for (int i = 0; i < childCount; i++) {
//            View child = mMapView.getChildAt(i);
//            if (child instanceof ZoomControls) {
//                zoom = child;
//                break;
//            }
//        }
//        zoom.setVisibility(View.GONE);
//    }

    public void initAnimation() {
        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(500);
        mShowAction.setInterpolator(new OvershootInterpolator());
        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f);
        mHiddenAction.setDuration(500);
        mHiddenAction.setInterpolator(new OvershootInterpolator());
    }

    @Override
    public void addMaker(final LatLng point, final int posttion) {
        Glide.with(this).load("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=870609829,3308433796&fm=116&gp=0.jpg").asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                View view = getLayoutInflater().inflate(R.layout.view_near_img,null);
                CircularImageView imgView = (CircularImageView) view.findViewById(R.id.iv_head);
                imgView.setImageBitmap(resource);
                BitmapDescriptor bdA = BitmapDescriptorFactory.fromView(view);
                MarkerOptions ooA = new MarkerOptions().position(point).icon(bdA)
                        .zIndex(9).draggable(true);
                ooA.animateType(MarkerOptions.MarkerAnimateType.drop);
                Marker mMarker = (Marker) (mBaiduMap.addOverlay(ooA));
                mMarker.setZIndex(posttion);
            }
        });
    }

    public void initMapMarkerClickListener() {
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                presenter.onMakerClick(marker);
                return true;
            }
        });
    }

    public void setNearPersonViewVis() {
        ll_nearby_view.clearAnimation();
        ll_nearby_view.setAnimation(mShowAction);
        ll_nearby_view.startAnimation(mShowAction);
        ll_nearby_view.setVisibility(View.VISIBLE);
        isNearViewVis = !isNearViewVis;
    }

    public void setNearPersonViewHid(boolean needShow) {
        if (isNearViewVis) {
            if (!needShow) {
                mHiddenAction.setAnimationListener(null);
            }
            ll_nearby_view.clearAnimation();
            ll_nearby_view.setAnimation(mHiddenAction);
            ll_nearby_view.startAnimation(mHiddenAction);
            ll_nearby_view.setVisibility(View.GONE);
            isNearViewVis = !isNearViewVis;
        }
    }

    @Override
    public void scrollToMyLocat() {
        LatLng ll = new LatLng(LocationUtil.getInstance().getLatitude(),
                LocationUtil.getInstance().getLongitude());
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(18.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory
                .newMapStatus(builder.build()));
    }

    @Override
    public void updateUnreadLabel(int count) {
//        Log.i("wang","view-updateUnreadLabel-count="+count);
        if (count > 0) {
            unread_msg_number.setVisibility(View.VISIBLE);
            unread_msg_number.setText(count+"s");
//            Log.i("wang","view-updateUnreadLabel-countdvdfdfdfdfdf="+count);
        } else {
            unread_msg_number.setVisibility(View.GONE);
        }
    }

    @Override
    public void showRemoteLoginDialog() {

    }

    @Override
    public boolean isNearViewVis() {
        return isNearViewVis;
    }

    @Override
    public void editNearViewState(boolean currentShow, boolean isSame) {
        if(currentShow){
            // view可见
            if(!isSame){

                mHiddenAction.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        presenter.adapterNerarView(near_iv_head,tv_near_name,tv_near_description);
                        setNearPersonViewVis();
                    }
                });
                setNearPersonViewHid(true);
            }
        }else{
            if(!isSame){
                presenter.adapterNerarView(near_iv_head,tv_near_name,tv_near_description);
            }
            setNearPersonViewVis();
        }
    }

    @Override
    public void runActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(this,clazz);
        if(bundle!=null){
            intent.putExtra("value",bundle);
        }
        startActivity(intent);
    }

    @Override
    public BaiduMap getMap() {
        return mBaiduMap;
    }

    @Override
    public ImageView getHeadView() {
        return iv_my_head;
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        mMapView = null;
    }
}
