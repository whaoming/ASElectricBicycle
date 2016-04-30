package com.wxxiaomi.ming.electricbicycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.chat.EMClient;

public class MainActivity extends AppCompatActivity {
    private BaiduMap mBaiduMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EMClient.getInstance().addConnectionListener(new EMConnectionListener() {
            @Override
            public void onConnected() {

            }

            @Override
            public void onDisconnected(int i) {

            }
        });
    }
}
