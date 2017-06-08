package com.wxxiaomi.ming.electricbicycle.car;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.car.bluetooth.BluetoothSPP;
import com.wxxiaomi.ming.electricbicycle.car.bluetooth.BluetoothState;
import com.wxxiaomi.ming.speedlib.PointerSpeedometer;

public class DriveActivity extends AppCompatActivity {

    public static void runDriveActivity(Context context, String address) {
        Intent intent = new Intent(context, DriveActivity.class);
        intent.putExtra(BluetoothState.EXTRA_DEVICE_ADDRESS, address);
        context.startActivity(intent);
    }

    private BluetoothSPP bt;
    //初始化成功的标识
    public final int INIT_SUCCESS = 1;
    //显示速度消息的标识
    public final int SPEED_RECEIVE = 2;


    PointerSpeedometer pointerSpeedometer;
    private Handler hanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.i("wang", "初始化完成，链接成功");
                    break;
                case SPEED_RECEIVE:
                    pointerSpeedometer.speedTo((int) msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive);
        pointerSpeedometer = (PointerSpeedometer) findViewById(R.id.pointerSpeedometer);
        CarManager.getInstance().init();
        CarManager.getInstance().bindSpeedNotify(new CarManager.OnSpeedMessageGet() {
            @Override
            public void onSpeedGet(int speed) {
                Message msg = new Message();
                msg.what = SPEED_RECEIVE;
                msg.obj = Integer.valueOf(speed);
                hanlder.sendMessage(msg);
            }

            @Override
            public void onDeviceDisconnected() {

            }

            @Override
            public void onDeviceConnectionFailed() {

            }
        });
        CarManager.getInstance().start(getIntent());
//        initBlueTooth();

    }


    private void initBlueTooth() {
        bt = new BluetoothSPP(this);
        if (!bt.isBluetoothAvailable()) {
            Toast.makeText(this, "当前蓝牙设备不可用", Toast.LENGTH_SHORT).show();
        }
        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                parseSpeed(data);
//                Toast.makeText(DriveActivity.this, message, Toast.LENGTH_SHORT).show();
//                for(byte b  : data){
//                    Log.i("wang","byte:"+b);
//                }
            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getApplicationContext()
                        , "Connected to " + name + "\n" + address
                        , Toast.LENGTH_SHORT).show();
            }

            public void onDeviceDisconnected() {
                Toast.makeText(getApplicationContext()
                        , "Connection lost", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceConnectionFailed() {
                Toast.makeText(getApplicationContext()
                        , "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        });

        if (!bt.isBluetoothEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
            }
        }
        bt.connect(getIntent());
    }

    public void parseSpeed(byte[] data) {
        Message msg = new Message();
        msg.what = SPEED_RECEIVE;
        msg.obj = Integer.valueOf(data[2]);
        hanlder.sendMessage(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
