package com.wxxiaomi.ming.electricbicycle.car.bluetooth.improve;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.car.bluetooth.BluetoothService;
import com.wxxiaomi.ming.electricbicycle.car.bluetooth.BluetoothState;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserLocatInfo;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pulltorefresh.baseadapter.ViewHolder;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pulltorefresh.baseadapter.base.CommonBaseAdapter;

import java.util.List;

/**
 * Created by Mr.W on 2017/4/16.
 * E-mail：122627018@qq.com
 * Github：https://github.com/whaoming
 * TODO:
 */

public class DeviceBoundAdapter extends CommonBaseAdapter<DeviceBoundBean> {

//    private boolean isFirstHistory = true;
    private boolean isNewFind = true;
    private int hisSize = 0;
    private onMyItemClick lis;
    public void setOnMyItemClickListener(onMyItemClick lis){
        this.lis = lis;
    }

    public DeviceBoundAdapter(Context context, List<DeviceBoundBean> datas, boolean isOpenLoadMor,SwipeRefreshLayout listviewe) {
        super(context, datas, isOpenLoadMor,listviewe);
    }

    @Override
    protected void convert(ViewHolder holder, final DeviceBoundBean data, final int position) {
        holder.setText(R.id.tv_name,data.name);
//        if(data.isHistory){
//            if(hisSize==0){
//                holder.getView(R.id.tv_title).setVisibility(View.VISIBLE);
//                holder.setText(R.id.tv_title,"历史记录"+position);
//            }else {
//                mDatas.remove(getItemCount()-1);
//                mDatas.add(hisSize,data);
                holder.getView(R.id.tv_title).setVisibility(View.GONE);
        holder.setText(R.id.tv_name,data.device.getName());
//            }
//            hisSize++;
//        }else{
//            if(isNewFind){
//                holder.getView(R.id.tv_title).setVisibility(View.VISIBLE);
//                holder.setText(R.id.tv_title,"新的设备");
//                isNewFind = !isNewFind;
//            }else{
//                holder.getView(R.id.tv_title).setVisibility(View.GONE);
//            }
//        }
        holder.setOnClickListener(R.id.btn_bound, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lis.onClick(position,data);
            }
        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_device_bound;
    }

    public interface onMyItemClick{

        void onClick(int position,DeviceBoundBean data);
    }

}
