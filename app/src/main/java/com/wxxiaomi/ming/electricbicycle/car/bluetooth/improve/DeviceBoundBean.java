package com.wxxiaomi.ming.electricbicycle.car.bluetooth.improve;

import android.bluetooth.BluetoothDevice;

import java.io.Serializable;

/**
 * Created by Mr.W on 2017/4/16.
 * E-mail：122627018@qq.com
 * Github：https://github.com/whaoming
 * TODO:
 */

public class DeviceBoundBean implements Serializable{
    public String name;
    public String macDevice;
    public boolean isHistory;
    public BluetoothDevice device;
}
