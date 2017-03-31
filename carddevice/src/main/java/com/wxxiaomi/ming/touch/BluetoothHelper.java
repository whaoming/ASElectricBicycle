package com.wxxiaomi.ming.touch;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * Author 林乔
 * Package cc.huaxun.agbao.manage
 * Description TODO 蓝牙功能工具类
 * Date 16/8/15 上午10:30
 */
public class BluetoothHelper {

    private Context context;
    private BluetoothDevice currentDevice;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();  ;
    BluetoothConnector connector;
    BluetoothConnector.BluetoothSocketWrapper connect;
    TouchMsgThread thread;
    /**
     * 已配对的蓝牙设备
     */
    private List<BluetoothDevice> bluetoothDevices = new ArrayList<>();

    private BluetoothHelper() {}
    public static BluetoothHelper INSTANCE;
    public static BluetoothHelper getInstance(){
        if(INSTANCE==null){
            INSTANCE = new BluetoothHelper();
        }
        return INSTANCE;
    }
    public static void init(Context ct){
        INSTANCE.context = ct;
    }

    /**
     * 打开蓝牙
     */
    public static void openBluetooth(@NonNull Activity activity) {
        if (INSTANCE.bluetoothAdapter == null) {
            // 设备不支持蓝牙
            Toast.makeText(INSTANCE.context.getApplicationContext(), "您的设备似乎不支持蓝牙", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(enableBtIntent, 6);
    }

    /**
     * 关闭蓝牙
     */
    public static void closeBluetooth() {
        if (INSTANCE.bluetoothAdapter != null) {
            INSTANCE.bluetoothAdapter.disable();
        }
    }

    /**
     * 判断蓝牙是否打开
     *
     * @return boolean 蓝牙是否打开
     */
    public static boolean isBluetoothOpen() {
        return INSTANCE.bluetoothAdapter != null && INSTANCE.bluetoothAdapter.isEnabled();
    }

    /**
     * 搜索蓝牙设备
     */
    public static void searchDevices() {
        INSTANCE.bluetoothDevices.clear();
        if (INSTANCE.bluetoothAdapter != null) {
            // 寻找蓝牙设备，android会将查找到的设备以广播形式发出去
            INSTANCE.bluetoothAdapter.startDiscovery();
        }
    }

    /**
     * 添加蓝牙设备到list集合
     *
     * @param device 蓝牙设备
     */
    public static void addBluetoothDevices(BluetoothDevice device) {
        if (!INSTANCE.bluetoothDevices.contains(device)) {
            INSTANCE.bluetoothDevices.add(device);
        }
    }

    /**
     * 获取搜索到的蓝牙设备列表
     *
     * @return 蓝牙设备列表
     */
    public static List<BluetoothDevice> getBluetoothDevices() {
        return INSTANCE.bluetoothDevices;
    }

    /**
     * 绑定设备
     * @param device BluetoothDevice对象
     * @return 是否绑定成功
     */
    public static boolean bondDevice(BluetoothDevice device) {
        if (INSTANCE.bluetoothAdapter == null) {
            return false;
        }
        // 取消蓝牙设备搜索
        INSTANCE.bluetoothAdapter.cancelDiscovery();
        try {
            if (device.getBondState() == BluetoothDevice.BOND_NONE) {
                // 设备未配对，进行配对操作
                Method method = BluetoothDevice.class.getMethod("createBond");
                method.invoke(device);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 打印文档
     *
     * @param device BluetoothDevice对象
     * @return 是否打印成功
     */
    public static boolean printDocument(@NonNull final BluetoothDevice device, String msg, Handler handler) {
        try {
            if (!isBluetoothOpen()) {
                return false;
            }
            // 连接蓝牙设备
            if (INSTANCE.connector == null) {
                INSTANCE.initConnector(device);
            }
            if (INSTANCE.connect == null) {
                INSTANCE.connect = INSTANCE.connector.connect();
                if(INSTANCE.thread!=null){
                    INSTANCE.thread.cancel();
                    INSTANCE.thread.stop();
                    INSTANCE.thread = null;
                }
                INSTANCE.thread = new TouchMsgThread(INSTANCE.connect.getUnderlyingSocket(), handler);
                INSTANCE.thread.start();
            }
            INSTANCE.thread.write(msg.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void initConnector(BluetoothDevice device) {
        connector = new BluetoothConnector(device, true, BluetoothHelper.this.bluetoothAdapter, null);
    }

    /**
     * 判断是否已经有绑定的设备了
     *
     * @return
     */
    public static boolean isEverDevice(){
        //应该取出已绑定的设备里面，已绑定的设备里面是否有这个东西
        Set<BluetoothDevice> bondedDevices = INSTANCE.bluetoothAdapter.getBondedDevices();
        Iterator<BluetoothDevice> iterator = bondedDevices.iterator();
        while(iterator.hasNext()){
            BluetoothDevice device = iterator.next();
            Log.i("wang","device:"+device.toString());
            if(device.getAddress().equals("98:D3:31:FC:3A:3C")){
                INSTANCE.currentDevice = device;
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    public static BluetoothDevice getCurrentDevice(){
        return INSTANCE.currentDevice;
    }

    public static void Destory(){
        try {

            INSTANCE.connect.close();
            INSTANCE.connect=null;
            INSTANCE.thread.cancel();
            INSTANCE.thread=null;
            INSTANCE.connector=null;
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
