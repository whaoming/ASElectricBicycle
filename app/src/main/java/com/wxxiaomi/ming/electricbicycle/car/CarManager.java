package com.wxxiaomi.ming.electricbicycle.car;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.wxxiaomi.ming.electricbicycle.EBApplication;
import com.wxxiaomi.ming.electricbicycle.car.bluetooth.BluetoothSPP;
import com.wxxiaomi.ming.electricbicycle.car.bluetooth.BluetoothState;
import com.wxxiaomi.ming.electricbicycle.im.notice.NoticeManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.W on 2017/5/13.
 * E-mail：122627018@qq.com
 * Github：https://github.com/whaoming
 * TODO:
 */
public class CarManager {

    private static CarManager INSTANCE;
    private BluetoothSPP bt;
    private Context context;
    private final List<OnSpeedMessageGet> mNotifies = new ArrayList<>();
//    private boolean isToothEnable = false;
    private boolean isConnected = false;

    public static CarManager getInstance(){
        if(INSTANCE==null){
            INSTANCE =new CarManager();
        }
        return INSTANCE;
    }

    private  CarManager(){
        bt = new BluetoothSPP(context);
    }

//    public boolean isToothEnable(){
//        return isToothEnable;
//    }

    public void init(){
        context = EBApplication.applicationContext;

        Log.i("wang","bt.isAutoConnecting():"+bt.isAutoConnecting()+",bt.isBluetoothAvailable()"+bt.isBluetoothAvailable()
                +",bt.isServiceAvailable():"+bt.isBluetoothEnabled()+",bt.isServiceAvailable():"+bt.isServiceAvailable());
        if (!bt.isBluetoothAvailable()) {
            Toast.makeText(context, "当前蓝牙设备不可用", Toast.LENGTH_SHORT).show();
        }
        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                parseSpeed(data);
            }
        });
        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            public void onDeviceConnected(String name, String address) {
                Log.i("wang","onDeviceConnected");
                isConnected = true;
//                isToothEnable = true;
//                Log.i("wang","bt.isAutoConnecting():"+bt.isAutoConnecting()+",bt.isBluetoothAvailable()"+bt.isBluetoothAvailable()
//                +",bt.isServiceAvailable():"+bt.isBluetoothEnabled()+",bt.isServiceAvailable():"+bt.isServiceAvailable());
//                Toast.makeText(context
//                        , "Connected to " + name + "\n" + address
//                        , Toast.LENGTH_SHORT).show();
            }

            public void onDeviceDisconnected() {
                Log.i("wang","onDeviceDisconnected");
                isConnected = false;
                notifyDeviceDisconnected();
//                isToothEnable = false;
//                Toast.makeText(context
//                        , "Connection lost", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceConnectionFailed() {
//                Toast.makeText(context
//                        , "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        });
//        bt.setBluetoothStateListener(new BluetoothSPP.BluetoothStateListener() {
//            @Override
//            public void onServiceStateChanged(int state) {
//                Log.i("wang","state:"+state);
//            }
//        });
        if (!bt.isBluetoothEnabled()) {
//            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            contextstartActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
            }
        }
    }

    /**
     * 通知所有监听者连接断开了
     */
    private void notifyDeviceDisconnected() {
        for (OnSpeedMessageGet notify : mNotifies) {
            notify.onDeviceDisconnected();
        }
    }

    /**
     * 通知所有监听者速度改变
     * @param data
     */
    private void parseSpeed(byte[] data) {
        for (OnSpeedMessageGet notify : mNotifies) {
            notify.onSpeedGet(Integer.valueOf(data[2]));
        }
    }

    /**
     * 绑定设备
     * @param noticeNotify
     */
    public  void bindSpeedNotify(OnSpeedMessageGet noticeNotify) {
        INSTANCE.mNotifies.add(noticeNotify);
    }

    /**
     * 解绑设备
     * @param noticeNotify
     */
    public  void unBindSpeedNotify(OnSpeedMessageGet noticeNotify) {
        INSTANCE.mNotifies.remove(noticeNotify);
    }

    public void start(Intent intent){
        bt.connect(intent);
//        Log.i("wang","bt.isAutoConnecting():"+bt.isAutoConnecting()+",bt.isBluetoothAvailable()"+bt.isBluetoothAvailable()
//                +",bt.isServiceAvailable():"+bt.isBluetoothEnabled()+",bt.isServiceAvailable():"+bt.isServiceAvailable());
    }

    /**
     * 结束当前设备绑定
     */
    public void destoryDevice(){
        bt.stopService();
//        Log.i("wang","bt.isAutoConnecting():"+bt.isAutoConnecting()+",bt.isBluetoothAvailable()"+bt.isBluetoothAvailable()
//                +",bt.isServiceAvailable():"+bt.isBluetoothEnabled()+",bt.isServiceAvailable():"+bt.isServiceAvailable());
    }

    public interface OnSpeedMessageGet{
        void onSpeedGet(int speed);
        void onDeviceDisconnected();
        void onDeviceConnectionFailed();
    }

    public boolean isConnecting(){
//        bt.isServiceAvailable();
        Log.i("wang","isConnected:"+isConnected);
        return isConnected;
    }

}
