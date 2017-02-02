package com.wxxiaomi.ming.touch;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wxxiaomi.ming.carddevice.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Author 林乔
 * Package cc.huaxun.agbao.adapter
 * Description TODO 蓝牙设备列表的适配器
 * Date 16/8/15 下午3:37
 */
public class BluetoothDeviceAdapter extends BaseAdapter {

    private List<BluetoothDevice> devices = new ArrayList<>();

    private LayoutInflater inflater;

    /**
     * 构造函数
     *
     * @param context Context对象
     * @param devices 蓝牙设备列表
     */
    public BluetoothDeviceAdapter(Context context, List<BluetoothDevice> devices) {
        this.devices = devices;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return devices.size();
    }

    @Override
    public Object getItem(int i) {
        return devices.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_bluetooth_device, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        BluetoothDevice device = devices.get(i);
        holder.tv_device_name.setText(device.getName());
        if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
            holder.tv_device_status.setText("已配对");
        } else if (device.getBondState() == BluetoothDevice.BOND_BONDING) {
            holder.tv_device_status.setText("正在配对");
        } else {
            holder.tv_device_status.setText("未配对");
        }
        return view;
    }

    /**
     * 控件容器
     */
    static class ViewHolder {
        /**
         * 设备名
         */
        TextView tv_device_name;
        /**
         * 设备绑定状态
         */
        TextView tv_device_status;

        public ViewHolder(View view) {
            tv_device_name = (TextView) view.findViewById(R.id.tv_device_name);
            tv_device_status = (TextView) view.findViewById(R.id.tv_device_status);
        }
    }
}
