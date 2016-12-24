package com.wxxiaomi.ming.electricbicycle.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.TextureMapView;
import com.wxxiaomi.ming.electricbicycle.R;

public class FootPrintShowActivity extends AppCompatActivity {
    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;
    private Button btn;
    private LinearLayout demo1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foot_print_show);
        mMapView = (TextureMapView) findViewById(R.id.mpaview);
        mBaiduMap = mMapView.getMap();
        demo1 = (LinearLayout) findViewById(R.id.demo1);
        btn = (Button) findViewById(R.id.btn);
        final Animation scaleAnimation= new
                ScaleAnimation(0f,1f,0f,1f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RelativeLayout.LayoutParams Params =  (RelativeLayout.LayoutParams)demo1.getLayoutParams();
                Params.height = 500;
                demo1.setLayoutParams(Params);
            }
        });
    }
}
