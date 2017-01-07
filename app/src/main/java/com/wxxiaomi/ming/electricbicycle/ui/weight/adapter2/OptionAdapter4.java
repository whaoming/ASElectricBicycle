package com.wxxiaomi.ming.electricbicycle.ui.weight.adapter2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.api.constant.OptionType;
import com.wxxiaomi.ming.electricbicycle.common.util.TimeUtil;
import com.wxxiaomi.ming.electricbicycle.db.bean.Option;
import com.wxxiaomi.ming.electricbicycle.service.AccountHelper;
import com.wxxiaomi.ming.electricbicycle.service.ShowerProvider;
import com.wxxiaomi.ming.electricbicycle.ui.weight.baseadapter.ViewHolder;
import com.wxxiaomi.ming.electricbicycle.ui.weight.baseadapter.base.BaseAdapter;
import com.wxxiaomi.ming.electricbicycle.ui.weight.baseadapter.base.MultiBaseAdapter;
import com.wxxiaomi.ming.electricbicycle.ui.weight.myrecycle.PullToRefreshRecyclerView;

import java.util.List;

/**
 * Created by Administrator on 2016/12/30.
 */

public class OptionAdapter4  extends MultiBaseAdapter<Option> {
    private final int TYPE_NORMAL = 1;
    private final int TYPE_MAP = 2;
    PullToRefreshRecyclerView listview;
    private boolean isLoading = true;
    public OptionAdapter4(Context context, List<Option> datas, boolean isOpenLoadMore,PullToRefreshRecyclerView listview) {
        super(context, datas, isOpenLoadMore);
        this.listview = listview;
    }
    public void setIsLoading(boolean isLoading){
        this.isLoading = isLoading;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int viewtype) {
        int viewType = holder.getItemViewType();
        if(viewType== BaseAdapter.TYPE_LOADING_VIEW){
            listview.setRefreshing(true);
        }else{
            if(isLoading){
                listview.setRefreshing(true);
            }else {
                listview.setRefreshing(false);
            }
            super.onBindViewHolder(holder, viewtype);
        }

    }

    @Override
    protected void convert(ViewHolder holder, Option option, int viewType) {
        switch (viewType){
            case TYPE_NORMAL:
                fillNormalData(holder,option);
                break;
            case TYPE_MAP:
                fillMapData(holder,option);
        }

    }

    private void fillMapData(ViewHolder holder, Option option) {
//        if(holder.getView(R.id.mpaview)==null){
            TextureMapView mMapView = (TextureMapView)holder.getView(R.id.mpaview);
        BaiduMap map = mMapView.getMap();
//        map.cen
//        mMapView.cent
        LatLng cenpt = new LatLng(29.806651,121.606983);
//        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(18)
                .build();
//        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
//
//
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//        //改变地图状态
//        mMapView.setma
        map.setMapStatus(mMapStatusUpdate);

//        mMapView.setL;
//        mMapView.s
//            BaiduMap mBaiduMap = mMapView.getMap();
//        }

    }


    @Override
    protected int getItemLayoutId(int viewType) {
        switch (viewType){
            case TYPE_NORMAL:
                return R.layout.item_option2;
            case TYPE_MAP:
                return R.layout.item_option_locat;
        }
        return 0;
    }


    @Override
    protected int getViewType(int position, Option data) {
        int flag = TYPE_NORMAL;
        switch (data.type){
            case OptionType.TOPIC_PUBLISH:
            case OptionType.TOPIC_COMMENT:
                flag= TYPE_NORMAL;
                break;
            case OptionType.FOOT_PRINT:
                flag = TYPE_MAP;
                break;
        }
        return flag;
    }

    private void fillNormalData(ViewHolder holder,Option option) {
        int flag = option.type;
        String avatar = AccountHelper.getAccountInfo().avatar;
        ShowerProvider.showHead(mContext,(ImageView) holder.getView(R.id.iv_avatar),avatar);
        switch (flag){
            case OptionType.TOPIC_PUBLISH:
                if(option.picture!=null){
                    if("".equals(option.picture)){
                        holder.getView(R.id.iv_picture).setVisibility(View.GONE);
                    }else {
                        holder.getView(R.id.iv_picture).setVisibility(View.VISIBLE);
                        ShowerProvider.showNormalImage(mContext, (ImageView)holder.getView(R.id.iv_picture), option.picture);
                    }
                }else{
                    holder.getView(R.id.iv_picture).setVisibility(View.GONE);
                }
                holder.getView(R.id.tv_reply).setVisibility(View.GONE);
                holder.setText(R.id.tv_content,option.content);
                holder.setText(R.id.tv_time,TimeUtil.StrToSimpleDate(option.create_time));
                holder.setText(R.id.tv_option,option.nickname+" 发表了主题");
                break;
            case OptionType.TOPIC_COMMENT:
                if(option.picture!=null){
                    if("".equals(option.picture)){
                        holder.getView(R.id.iv_picture).setVisibility(View.GONE);
                    }else {
                        holder.getView(R.id.iv_picture).setVisibility(View.VISIBLE);
                        ShowerProvider.showNormalImage(mContext, (ImageView)holder.getView(R.id.iv_picture), option.picture);
                    }
                }else{
                    holder.getView(R.id.iv_picture).setVisibility(View.GONE);
                }
                holder.getView(R.id.tv_reply).setVisibility(View.VISIBLE);
                holder.setText(R.id.tv_reply,option.reply);
                holder.setText(R.id.tv_content,option.content);
                holder.setText(R.id.tv_time,TimeUtil.StrToSimpleDate(option.create_time));
                holder.setText(R.id.tv_option,option.nickname+" 评论了主题");
                break;
        }
    }
}
