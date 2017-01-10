package com.wxxiaomi.ming.electricbicycle.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.ui.activity.base.BaseActivity;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.HomePresenter;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.impl.HomePresenterImpl;
import com.wxxiaomi.ming.electricbicycle.ui.activity.view.HomeView;
import com.wxxiaomi.ming.electricbicycle.ui.weight.custom.CircularImageView;
import com.wxxiaomi.ming.electricbicycle.service.LocatProvider;
import com.wxxiaomi.ming.electricbicycle.ui.weight.custom.MsgActionProvider;

/**
 * Created by 12262 on 2016/6/6.
 * 主页面
 */
public class HomeActivity extends BaseActivity<HomeView,HomePresenter> implements HomeView<HomePresenter> {

    private CoordinatorLayout sn_layout;
    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;
    boolean isFirstLoc = true; // 是否首次定位
    private FloatingActionButton btn_go;
    private FloatingActionButton btn_locat;
    private LinearLayout ll_nearby_view;
    private TranslateAnimation mShowAction;
    private TranslateAnimation mHiddenAction;
    private boolean isNearViewVis = false;
    private CircularImageView near_iv_head;
    private TextView tv_near_name;
    private TextView tv_near_description;
    private DrawerLayout mDrawerLayout;
    private MsgActionProvider mActionProvider2;
    private MsgActionProvider mActionProvider;
    private Toolbar toolbar;
    private ImageView iv_avatar;
    private ImageView drawer_iv_avatar;

    private RelativeLayout drawer_setting;
    private RelativeLayout rl_collect;
    private RelativeLayout rl_album;
    private RelativeLayout rl_myfriend;
    private RelativeLayout rl_foot_print;


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        iv_avatar = (ImageView) findViewById(R.id.iv_avatar);
        iv_avatar.setOnClickListener(this);
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
        btn_go = (FloatingActionButton) findViewById(R.id.btn_go);
        btn_locat = (FloatingActionButton) findViewById(R.id.btn_locat);
        btn_locat.setOnClickListener(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        drawer_iv_avatar = (ImageView) findViewById(R.id.drawer_iv_avatar);
        drawer_iv_avatar.setOnClickListener(this);
        drawer_setting = (RelativeLayout) findViewById(R.id.drawer_setting);
        drawer_setting.setOnClickListener(this);
        mBaiduMap = mMapView.getMap();
        btn_go.setOnClickListener(this);
        rl_collect = (RelativeLayout) findViewById(R.id.rl_collect);
        rl_collect.setOnClickListener(this);
        rl_album = (RelativeLayout) findViewById(R.id.rl_album);
        rl_album.setOnClickListener(this);
        rl_myfriend = (RelativeLayout) findViewById(R.id.rl_myfriend);
        rl_myfriend.setOnClickListener(this);
        rl_foot_print = (RelativeLayout) findViewById(R.id.rl_foot_print);
        rl_foot_print.setOnClickListener(this);
        initAnimation();
        initMapMarkerClickListener();
    }

    @Override
    public HomePresenter getPresenter() {
        return new HomePresenterImpl();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_go:
                presenter.onFootPrintActionClick();
                break;
            case R.id.near_iv_head:
                presenter.nearHeadBtnOnClick();
                break;
            case R.id.btn_locat:
                presenter.locatBtnOnClick();
                break;
            case R.id.iv_avatar:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.drawer_setting:
                presenter.onSettingClick();
                break;
            case R.id.drawer_iv_avatar:
                presenter.headBtnOnClick();
                break;
            case R.id.rl_collect:
                presenter.onCollectClick();
                break;
            case R.id.rl_album:
                presenter.onAlbumClick();
                break;
            case R.id.rl_myfriend:
                presenter.contactBtnOnClick();
                break;
            case R.id.rl_foot_print:
                presenter.onFootPrintClick();
                break;
            default:
                break;
        }
    }

    @Override
    public void showSnackBar(String content) {
        Snackbar.make(mDrawerLayout, content, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public View getSnackContent() {
        return sn_layout;
    }


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
                try {
                    View view = getLayoutInflater().inflate(R.layout.view_near_img, null);
                    CircularImageView imgView = (CircularImageView) view.findViewById(R.id.iv_head);
                    imgView.setImageBitmap(resource);
                    if (view != null) {
                        BitmapDescriptor bdA = BitmapDescriptorFactory.fromView(view);
                        MarkerOptions ooA = new MarkerOptions().position(point).icon(bdA)
                                .zIndex(9).draggable(true);
                        ooA.animateType(MarkerOptions.MarkerAnimateType.drop);
                        Marker mMarker = (Marker) (mBaiduMap.addOverlay(ooA));
                        mMarker.setZIndex(posttion);
                    }
                }catch (Exception e){
                    e.printStackTrace();;
                }
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
        LatLng ll = new LatLng(LocatProvider.getInstance().getLatitude(),
                LocatProvider.getInstance().getLongitude());
        MapStatus.Builder builder = new MapStatus.Builder();
//        Poi
        builder.target(ll).zoom(18.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory
                .newMapStatus(builder.build()));
    }

    @Override
    public void updateUnreadLabel(final int count) {
//        Log.i("wang","view-updateUnreadLabel-count="+count);
        if (count > 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(  mActionProvider2!=null){
                        mActionProvider2.setBadge(count);
                    }

//                    unread_msg_number.setVisibility(View.VISIBLE);
//                    unread_msg_number.setText(count+"s");mActionProvider2
                }
            });

//            Log.i("wang","view-updateUnreadLabel-countdvdfdfdfdfdf="+count);
        } else {
//            unread_msg_number.setVisibility(View.GONE);
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
        return iv_avatar;
    }
    @Override
    public ImageView getDrawerAvatar(){
        return drawer_iv_avatar;
    }

    @Override
    public TextView getTvNameView() {
//        return tv_name;
        return null;
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

    @Override
    protected void onNewIntent(Intent intent) {
        presenter.onNewIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_pic);
        mActionProvider = (MsgActionProvider) MenuItemCompat.getActionProvider(menuItem);

        mActionProvider.setOnClickListener(0, new MsgActionProvider.OnClickListener() {
            @Override
            public void onClick(int what) {
                presenter.topicBtnOnClick();
            }
        });// 设置点击监听。

        MenuItem menuItem2 = menu.findItem(R.id.menu_pic2);
        mActionProvider2 = (MsgActionProvider) MenuItemCompat.getActionProvider(menuItem2);
        mActionProvider2.setOnClickListener(0, new MsgActionProvider.OnClickListener() {
            @Override
            public void onClick(int what) {
                presenter.contactBtnOnClick();
            }
        });

        return true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mActionProvider.setIcon(R.mipmap.ic_location_searching_white_36dp);
        mActionProvider.setBadge(0);
        mActionProvider2.setIcon(R.mipmap.ic_notify_none);
        mActionProvider2.setBadge(0);
        presenter.updateUnreadLabel();
    }
}
