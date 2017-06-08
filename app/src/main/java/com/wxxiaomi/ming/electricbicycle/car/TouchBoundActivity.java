package com.wxxiaomi.ming.electricbicycle.car;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.car.adapter.BluetoothDeviceAdapter;
import com.wxxiaomi.ming.common.widget.DialogHelper;
import com.wxxiaomi.ming.electricbicycle.car.bluetooth.BluetoothState;
import com.wxxiaomi.ming.electricbicycle.car.bluetooth.improve.DeviceBoundAdapter;
import com.wxxiaomi.ming.electricbicycle.car.bluetooth.improve.DeviceBoundBean;
import com.wxxiaomi.ming.electricbicycle.ui.activity.base.NormalActivity;
import com.wxxiaomi.ming.electricbicycle.ui.weight.custom.SimplexToast;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pulltorefresh.recycleview.PullToRefreshRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;


/**
* @author whaoming
* github：https://github.com/whaoming
* created at 2017/4/16 21:50
* Description:蓝牙绑定界面
*/
public class TouchBoundActivity extends NormalActivity {
    private Toolbar toolbar;
    private PullToRefreshRecyclerView mRecyclerView;
    DeviceBoundAdapter mAdapter;
    private List<DeviceBoundBean> mDatas;

    private BluetoothAdapter mBtAdapter;
    private Set<BluetoothDevice> pairedDevices;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_touch_bound);
        Log.i("wang","TouchBoundActivity");
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView = (PullToRefreshRecyclerView) findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        registerListener();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle("蓝牙绑定");
    }

    private void registerListener() {
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);
        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);
    }

    /**
     * 蓝牙搜索设备
     */
    private void loadData() {
        mDatas = new ArrayList<>();
        mAdapter = new DeviceBoundAdapter(this,mDatas,false,mRecyclerView);
        mAdapter.onAttachedToRecyclerView(mRecyclerView.getRecyclerView());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnMyItemClickListener(new DeviceBoundAdapter.onMyItemClick() {
            @Override
            public void onClick(int position, DeviceBoundBean data) {
                if(mBtAdapter.isDiscovering())
                    mBtAdapter.cancelDiscovery();
                DriveActivity.runDriveActivity(TouchBoundActivity.this,data.device.getAddress());
                finish();
            }
        });
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }
        mBtAdapter.startDiscovery();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device(发现设备的时候)
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent(从intent里面获取设备实体)
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
                //(如果已经有了，跳过，因为已经在列表了)
                DeviceBoundBean bean;
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    bean = new DeviceBoundBean();
                    bean.isHistory = false;
                    bean.macDevice = device.getAddress();
                    bean.name = device.getName();
                    bean.device = device;
                    mDatas.add(bean);
                }else{
                    //已经存在的
                    bean = new DeviceBoundBean();
                    bean.isHistory = true;
                    bean.macDevice = device.getAddress();
                    bean.name = device.getName();
                    bean.device = device;
//                    mAdapter.
                    mDatas.add(bean);
//                    mAdapter.notifyItemChanged(mDatas.size());
                }
                handler.sendEmptyMessage(1);
                // When discovery is finished, change the Activity title
                // 当搜索完成，修改标题
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                setProgressBarIndeterminateVisibility(false);
                SimplexToast.show(TouchBoundActivity.this,"搜索完成");
//                String strSelectDevice = getIntent().getStringExtra("select_device");
//                if(strSelectDevice == null)
//                    strSelectDevice = "Select a device to connect";
//                setTitle(strSelectDevice);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }

        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
        this.finish();
    }

    //    private ListView lv_bluetooth_devices;
//    private BluetoothDeviceAdapter deviceAdapter;
//    private BroadcastReceiver receiver= new BroadcastReceiver() {
//
//        /**
//         * 搜索设备的进度条对话框
//         */
//        private ProgressDialog dialog;
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
//                // 搜索到设备
//                Log.i("wang","搜索到设备");
//                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                BluetoothHelper.addBluetoothDevices(device);
//            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
//                Log.i("wang","开始搜索");
//                // 开始搜索
////                    dialog = new ProgressDialog(context);
////                    dialog.setMessage("正在搜索设备，请稍候...");
////                    dialog.setCancelable(false);
////                    dialog.show();
////
////                    isSearching = true;
//            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
//                Log.i("wang","搜索结束");
//                // 搜索结束
//                if (dialog != null && dialog.isShowing()) {
//                    dialog.dismiss();
//                }
//
////                    if (isSearching) {
////                        Toast.makeText(BoundActivity.this.getApplicationContext(), "搜索结束，共搜索到" + BluetoothHelper.getBluetoothDevices().size() + "台设备", Toast.LENGTH_SHORT).show();
////                    }
////                    isSearching = false;
//                // 根据配对情况进行排序
//                Collections.sort(BluetoothHelper.getBluetoothDevices(), new Comparator<BluetoothDevice>() {
//                    @Override
//                    public int compare(BluetoothDevice device, BluetoothDevice t1) {
//                        return device.getBondState() - t1.getBondState();
//                    }
//                });
//
//                deviceAdapter.notifyDataSetChanged();
//            }else if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)){
//
//            }
//            else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
//                Log.i("wang","状态改变");
//                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                switch (device.getBondState()) {
//                    case BluetoothDevice.BOND_BONDING://正在配对
//                        Log.i("wang", "正在配对......");
////                        onRegisterBltReceiver.onBltIng(device);
//                        break;
//                    case BluetoothDevice.BOND_BONDED://配对结束
//                        Log.i("wang", "完成配对");
////                        onRegisterBltReceiver.onBltEnd(device);
//                        Intent i = new Intent(TouchBoundActivity.this,DriveActivity.class);
//                        startActivity(i);
//                        finish();
//                        break;
//                    case BluetoothDevice.BOND_NONE://取消配对/未配对
//                        Log.i("wang", "取消配对");
//                    default:
//                        break;
//                }
//            }
//            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
//                Log.i("wang", "ACTION_STATE_CHANGED");
////                    changeBluetoothStatus();
//            }
//        }
//    };
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_touch_bound);
//        lv_bluetooth_devices = (ListView) findViewById(R.id.lv_bluetooth_devices);
//        deviceAdapter = new BluetoothDeviceAdapter(TouchBoundActivity.this, BluetoothHelper.getBluetoothDevices());
//        lv_bluetooth_devices.setAdapter(deviceAdapter);
//        initListener();
//    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        initIntentFilter();
//    }
//
//    private void initIntentFilter() {
//        // 设置广播信息过滤
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
//        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
//        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
//        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
//        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
//        registerReceiver(receiver, intentFilter);
//    }
//
//
//    private void initListener() {
//        lv_bluetooth_devices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
//                final BluetoothDevice device = BluetoothHelper.getBluetoothDevices().get(i);
//                if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
//                    AlertDialog.Builder confirmDialog = DialogHelper.getConfirmDialog(TouchBoundActivity.this,
//                            "提示",
//                            "确定使用" + device.getName() + "进行打印吗？",
//                            "确定",
//                            "取消",
//                            false,
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//
//                                }
//                            },
//                            null);
//                    confirmDialog.show();
//                } else if (device.getBondState() == BluetoothDevice.BOND_BONDING) {
//                    // 正在绑定
//                    Toast.makeText(getApplicationContext(), "正在配对该设备，暂时无法使用", Toast.LENGTH_SHORT).show();
//                } else {
//                    // 没有绑定
//                    AlertDialog.Builder confirmDialog = DialogHelper.getConfirmDialog(TouchBoundActivity.this,
//                            "提示",
//                            "您确定要配对该设备吗",
//                            "确定",
//                            "取消",
//                            false,
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    boolean b = BluetoothHelper.bondDevice(BluetoothHelper.getBluetoothDevices().get(i));
//                                    Log.i("wang","绑定的结果："+b);
//                                }
//                            },
//                            null);
//                    confirmDialog.show();
//
////                    new AlertDialog.Builder(BoundActivity.this).setTitle("标题")
////                            .setMessage("您确定要配对该设备吗？")
////                            .setPositiveButton("取消", null)
////                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
////                                @Override
////                                public void onClick(DialogInterface dialogInterface, int a) {
//////                                    BluetoothHelper.getBluetoothDevices().get(i).
////
////                                    boolean b = BluetoothHelper.bondDevice(BluetoothHelper.getBluetoothDevices().get(i));
////                                    Log.i("wang","绑定的结果："+b);
////                                }
////                            }).create().show();
//                }
//            }
//        });
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        unregisterReceiver(receiver);
//    }
}
