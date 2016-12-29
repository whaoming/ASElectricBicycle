package com.wxxiaomi.ming.electricbicycle.ui.weight.adapter2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.hyphenate.easeui.domain.EaseUser;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.db.bean.InviteMessage;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserLocatInfo;
import com.wxxiaomi.ming.electricbicycle.service.FunctionProvider;
import com.wxxiaomi.ming.electricbicycle.service.GlobalManager;
import com.wxxiaomi.ming.electricbicycle.service.ShowerProvider;
import com.wxxiaomi.ming.electricbicycle.ui.weight.baseadapter.ViewHolder;
import com.wxxiaomi.ming.electricbicycle.ui.weight.baseadapter.base.BaseAdapter;
import com.wxxiaomi.ming.electricbicycle.ui.weight.baseadapter.base.CommonBaseAdapter;
import com.wxxiaomi.ming.electricbicycle.ui.weight.myrecycle.PullToRefreshRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Action1;

/**
 * Created by Administrator on 2016/12/29.
 */

public class InviteAdapter extends CommonBaseAdapter<InviteMessage> {
    PullToRefreshRecyclerView listview;
    Map<Integer,EaseUser> users ;
    public InviteAdapter(Context context, List<InviteMessage> datas, boolean isOpenLoadMore,PullToRefreshRecyclerView listview) {
        super(context, datas, isOpenLoadMore);
        this.listview = listview;
        users = new HashMap<>();
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
        EaseUser user = users.get(data.getId());
        ShowerProvider.showHead(mContext,(ImageView)holder.getView(R.id.iv_head),user.getAvatar());
        holder.setText(R.id.tv_reason,data.getReason());
        holder.setText(R.id.tv_name,user.getNickname());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_invite;
    }

    public void initData(List<InviteMessage> msgs,Map<Integer,EaseUser> users){
        this.users = users;
        setNewData(msgs);
    }
}
