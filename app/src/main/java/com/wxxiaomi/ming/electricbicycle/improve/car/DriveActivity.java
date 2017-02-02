package com.wxxiaomi.ming.electricbicycle.improve.car;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.speedlib.PointerSpeedometer;
import com.wxxiaomi.ming.touch.BluetoothHelper;

public class DriveActivity extends AppCompatActivity {
    PointerSpeedometer pointerSpeedometer;
    private Handler hanlder = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Log.i("wang","初始化完成，链接成功");
                    break;
                case 333:
                    pointerSpeedometer.speedTo((int)msg.obj);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive);
        pointerSpeedometer = (PointerSpeedometer) findViewById(R.id.pointerSpeedometer);
        init();
    }
    private void init() {
        Log.i("wang","假装这个是progress：正在初始化");
        //此时应该测试链接
        new Thread(){
            @Override
            public void run() {
                boolean b = BluetoothHelper.bondDevice(BluetoothHelper.getCurrentDevice());
                if(b){
                    boolean ona = BluetoothHelper.printDocument(BluetoothHelper.getCurrentDevice(), "ONA",hanlder);
                    if(ona){
                        hanlder.sendEmptyMessage(1);
                    }
                }
            }
        }.start();

    }
    @Override
    protected void onDestroy() {
        BluetoothHelper.Destory();
        super.onDestroy();
    }
}
