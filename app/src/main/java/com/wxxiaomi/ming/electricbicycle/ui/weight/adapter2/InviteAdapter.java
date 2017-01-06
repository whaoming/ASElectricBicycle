package com.wxxiaomi.ming.electricbicycle.ui.weight.adapter2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.db.bean.InviteMessage;
import com.wxxiaomi.ming.electricbicycle.service.ShowerProvider;
import com.wxxiaomi.ming.electricbicycle.ui.weight.baseadapter.ViewHolder;
import com.wxxiaomi.ming.electricbicycle.ui.weight.baseadapter.base.BaseAdapter;
import com.wxxiaomi.ming.electricbicycle.ui.weight.baseadapter.base.CommonBaseAdapter;
import com.wxxiaomi.ming.electricbicycle.ui.weight.myrecycle.PullToRefreshRecyclerView;

import java.util.List;

/**
 * Created by Administrator on 2016/12/29.
 */

public class InviteAdapter extends CommonBaseAdapter<InviteMessage> {
    PullToRefreshRecyclerView listview;
    public InviteAdapter(Context context, List<InviteMessage> datas, boolean isOpenLoadMore,PullToRefreshRecyclerView listview) {
        super(context, datas, isOpenLoadMore);
        this.listview = listview;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        if(viewType== BaseAdapter.TYPE_LOADING_VIEW){
            listview.setRefreshing(true);
        }else{
            listview.setRefreshing(false);
            super.onBindViewHolder(holder, position);
        }

    }

    @Override
    protected void convert(final ViewHolder holder, final InviteMessage data, int position) {
    //ib_contact,tv_reason,tv_name,iv_head
        ShowerProvider.showHead(mContext,(ImageView)holder.getView(R.id.iv_head),data.getAvatar());
        holder.setText(R.id.tv_reason,data.getReason());
        holder.setText(R.id.tv_name,data.getNickname());
        holder.setOnClickListener(R.id.ib_contact, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ImHelper1.getInstance().agreeInvite(data.getFrom());
            }
        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_invite;
    }

}
