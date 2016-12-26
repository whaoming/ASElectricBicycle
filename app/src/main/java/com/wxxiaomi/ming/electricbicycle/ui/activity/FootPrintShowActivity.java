package com.wxxiaomi.ming.electricbicycle.ui.activity;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.common.rx.SampleProgressObserver;
import com.wxxiaomi.ming.electricbicycle.common.util.OverlayManager;
import com.wxxiaomi.ming.electricbicycle.dao.bean.format.FootPrintDetail;
import com.wxxiaomi.ming.electricbicycle.dao.bean.format.FootPrintGet;
import com.wxxiaomi.ming.electricbicycle.dao.db.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FootPrintShowActivity extends AppCompatActivity {
    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;
    private Button btn;
    private LinearLayout demo1;
    final CountDownLatch order = new CountDownLatch(2);
    private OverlayManager overlayManager;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    overlayManager.zoomToSpan();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foot_print_show);
        mMapView = (TextureMapView) findViewById(R.id.mpaview);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                order.countDown();
            }
        });
        initData();
    }

    private void initData() {
        new Thread(){
            @Override
            public void run() {
                try {
                    order.await();
                    handler.sendEmptyMessage(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        final List<OverlayOptions> overlayOptions = new ArrayList<>();

        final List<LatLng> polylines = new ArrayList<>();
        UserService.getInstance().getUserFootPrint(25)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SampleProgressObserver<FootPrintGet>(this) {
                    @Override
                    public void onNext(FootPrintGet footPrintGet) {
//                        Log.i("wang","footPrintGet.footPrints.size():"+footPrintGet.footPrints.size());
                        for(int i=0;i<footPrintGet.footPrints.size();i++){
                            FootPrintDetail item = footPrintGet.footPrints.get(i);
                            LatLng point  = new LatLng(item.points[0],item.points[1]);
                            BitmapDescriptor bdA = BitmapDescriptorFactory.fromResource(R.mipmap.ic_about_black);
                            OverlayOptions option = new MarkerOptions()
                                    .position(point)//设置marker的位置
                                    .icon(bdA)//设置marker图标
                                    .draggable(false);
                            overlayOptions.add(option);
                            polylines.add(point);
                        }
//                        Polyline polyline = new Polyline();

                        PolylineOptions polylineOptions = new PolylineOptions()
                                .points(polylines)
                                .width(5)
                                .color(Color.BLUE);
//                        polylineOptions.getZIndex().
//                        polylineOptions.
                         mBaiduMap.addOverlay(polylineOptions);
                        overlayManager = new OverlayManager(mBaiduMap) {
                            @Override
                            public boolean onPolylineClick(Polyline polyline) {
                                return false;
                            }

                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                return false;
                            }

                            @Override
                            public List<OverlayOptions> getOverlayOptions() {
                                return overlayOptions;
                            }
                        };;
                        overlayManager.addToMap();
                        order.countDown();
                        Log.i("wang","userInfo:"+footPrintGet.userInfo.toString());
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        mMapView = null;
    }
}
