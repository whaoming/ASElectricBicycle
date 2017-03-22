package com.wxxiaomi.ming.electricbicycle.ui.weight.adapter2;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.db.bean.InviteMessage;
import com.wxxiaomi.ming.electricbicycle.manager.ShowerProvider;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pulltorefresh.baseadapter.ViewHolder;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pulltorefresh.baseadapter.base.CommonBaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/12/29.
 */

public class InviteAdapter extends CommonBaseAdapter<InviteMessage> {
    public InviteAdapter(Context context, List<InviteMessage> datas, boolean isOpenLoadMore,SwipeRefreshLayout listview) {
        super(context, datas, isOpenLoadMore,listview);
    }


    @Override
    protected void convert(final ViewHolder holder, final InviteMessage data, int position) {
        ShowerProvider.showHead(mContext,(ImageView)holder.getView(R.id.iv_head),data.getAvatar());
        holder.setText(R.id.tv_reason,data.getReason());
        holder.setText(R.id.tv_name,data.getNickname());
        holder.setOnClickListener(R.id.ib_contact, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_invite;
    }

}
