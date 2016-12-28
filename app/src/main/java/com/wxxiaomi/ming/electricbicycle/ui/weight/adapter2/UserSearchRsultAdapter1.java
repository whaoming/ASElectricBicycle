package com.wxxiaomi.ming.electricbicycle.ui.weight.adapter2;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.service.ShowerProvider;
import com.wxxiaomi.ming.electricbicycle.ui.weight.baseadapter.ViewHolder;
import com.wxxiaomi.ming.electricbicycle.ui.weight.baseadapter.base.CommonBaseAdapter;
import com.wxxiaomi.ming.electricbicycle.ui.weight.myrecycle.PullToRefreshRecyclerView;

import java.util.List;

/**
 * Created by Administrator on 2016/12/28.
 */

public class UserSearchRsultAdapter1 extends CommonBaseAdapter<UserCommonInfo> {

    PullToRefreshRecyclerView listview;
    public UserSearchRsultAdapter1(Context context, List<UserCommonInfo> datas, boolean isOpenLoadMore,PullToRefreshRecyclerView listview) {
        super(context, datas, isOpenLoadMore);
        this.listview = listview;
    }

    @Override
    protected void convert(ViewHolder holder, UserCommonInfo data, int position) {
//        if(position==0){
//            holder.getView(R.id.content_tip).setVisibility(View.VISIBLE);
//        }else{
//            holder.getView(R.id.content_tip).setVisibility(View.GONE);
//        }
        holder.setText(R.id.tv_name,data.nickname);
        holder.setText(R.id.tv_description,"用户描述");

        ShowerProvider.showHead(mContext, (ImageView) holder.getView(R.id.iv_head),data.avatar);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_near_friend_recommend;
    }
}
