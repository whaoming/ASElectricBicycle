package com.wxxiaomi.ming.electricbicycle.car;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.car.adapter.BluetoothDeviceAdapter;
import com.wxxiaomi.ming.common.weight.DialogHelper;
import com.wxxiaomi.ming.touch.BluetoothHelper;

import java.util.Collections;
import java.util.Comparator;

public class TouchBoundActivity extends AppCompatActivity {
    private ListView lv_bluetooth_devices;
    private BluetoothDeviceAdapter deviceAdapter;
    private BroadcastReceiver receiver= new BroadcastReceiver() {

        /**
         * 搜索设备的进度条对话框
         */
        private ProgressDialog dialog;

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // 搜索到设备
                Log.i("wang","搜索到设备");
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                BluetoothHelper.addBluetoothDevices(device);
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                Log.i("wang","开始搜索");
                // 开始搜索
//                    dialog = new ProgressDialog(context);
//                    dialog.setMessage("正在搜索设备，请稍候...");
//                    dialog.setCancelable(false);
//                    dialog.show();
//
//                    isSearching = true;
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Log.i("wang","搜索结束");
                // 搜索结束
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

//                    if (isSearching) {
//                        Toast.makeText(BoundActivity.this.getApplicationContext(), "搜索结束，共搜索到" + BluetoothHelper.getBluetoothDevices().size() + "台设备", Toast.LENGTH_SHORT).show();
//                    }
//                    isSearching = false;
                // 根据配对情况进行排序
                Collections.sort(BluetoothHelper.getBluetoothDevices(), new Comparator<BluetoothDevice>() {
                    @Override
                    public int compare(BluetoothDevice device, BluetoothDevice t1) {
                        return device.getBondState() - t1.getBondState();
                    }
                });

                deviceAdapter.notifyDataSetChanged();
            }else if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)){

            }
            else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                Log.i("wang","状态改变");
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                switch (device.getBondState()) {
                    case BluetoothDevice.BOND_BONDING://正在配对
                        Log.i("wang", "正在配对......");
//                        onRegisterBltReceiver.onBltIng(device);
                        break;
                    case BluetoothDevice.BOND_BONDED://配对结束
                        Log.i("wang", "完成配对");
//                        onRegisterBltReceiver.onBltEnd(device);
                        Intent i = new Intent(TouchBoundActivity.this,DriveActivity.class);
                        startActivity(i);
                        finish();
                        break;
                    case BluetoothDevice.BOND_NONE://取消配对/未配对
                        Log.i("wang", "取消配对");
                    default:
                        break;
                }
            }
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                Log.i("wang", "ACTION_STATE_CHANGED");
//                    changeBluetoothStatus();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_bound);
        lv_bluetooth_devices = (ListView) findViewById(R.id.lv_bluetooth_devices);
        deviceAdapter = new BluetoothDeviceAdapter(TouchBoundActivity.this, BluetoothHelper.getBluetoothDevices());
        lv_bluetooth_devices.setAdapter(deviceAdapter);
        initListener();
    }
    @Override
    protected void onStart() {
        super.onStart();

        initIntentFilter();
    }

    private void initIntentFilter() {
        // 设置广播信息过滤
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(receiver, intentFilter);
    }


    private void initListener() {
        lv_bluetooth_devices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
//                Log.i("wang","i:"+i);
                final BluetoothDevice device = BluetoothHelper.getBluetoothDevices().get(i);
                if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                    AlertDialog.Builder confirmDialog = DialogHelper.getConfirmDialog(TouchBoundActivity.this,
                            "提示",
                            "确定使用" + device.getName() + "进行打印吗？",
                            "确定",
                            "取消",
                            false,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            },
                            null);
                    confirmDialog.show();
//                    new AlertDialog.Builder(BoundActivity.this).setTitle("标题")
//                            .setMessage("确定使用" + device.getName() + "进行打印吗？")
//                            .setPositiveButton("取消", null)
//                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    String text = et.getText().toString().trim();
//                                    BluetoothHelper.printDocument(device,text,null);
//
//                                }
//                            }).create().show();
                } else if (device.getBondState() == BluetoothDevice.BOND_BONDING) {
                    // 正在绑定
                    Toast.makeText(getApplicationContext(), "正在配对该设备，暂时无法使用", Toast.LENGTH_SHORT).show();
                } else {
                    // 没有绑定
                    AlertDialog.Builder confirmDialog = DialogHelper.getConfirmDialog(TouchBoundActivity.this,
                            "提示",
                            "您确定要配对该设备吗",
                            "确定",
                            "取消",
                            false,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    boolean b = BluetoothHelper.bondDevice(BluetoothHelper.getBluetoothDevices().get(i));
                                    Log.i("wang","绑定的结果："+b);
                                }
                            },
                            null);
                    confirmDialog.show();

//                    new AlertDialog.Builder(BoundActivity.this).setTitle("标题")
//                            .setMessage("您确定要配对该设备吗？")
//                            .setPositiveButton("取消", null)
//                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int a) {
////                                    BluetoothHelper.getBluetoothDevices().get(i).
//
//                                    boolean b = BluetoothHelper.bondDevice(BluetoothHelper.getBluetoothDevices().get(i));
//                                    Log.i("wang","绑定的结果："+b);
//                                }
//                            }).create().show();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }
}
