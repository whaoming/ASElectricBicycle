package com.wxxiaomi.ming.electricbicycle.ui.weight.adapter2;

import android.content.Context;
import android.widget.ImageView;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.manager.ShowerProvider;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pulltorefresh.baseadapter.ViewHolder;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pulltorefresh.baseadapter.base.CommonBaseAdapter;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pulltorefresh.recycleview.PullToRefreshRecyclerView;

import java.util.List;

/**
 * Created by Mr.W on 2017/3/5.
 * E-mail：122627018@qq.com
 * Github：https://github.com/whaoming
 * TODO:
 */

public class BlackListAdapter extends CommonBaseAdapter<UserCommonInfo> {


    public BlackListAdapter(Context context, List<UserCommonInfo> datas, boolean isOpenLoadMore,PullToRefreshRecyclerView mRecyclerView) {
        super(context, datas, isOpenLoadMore,mRecyclerView);
    }

    @Override
    protected void convert(ViewHolder holder, UserCommonInfo data, int position) {
        ShowerProvider.showHead(mContext,(ImageView)holder.getView(R.id.head_image),data.avatar);
        holder.setText(R.id.account,data.nickname);
//        holder.setText(R.id.tv_name,data.getNickname());
//        holder.setOnClickListener(R.id.ib_contact, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                ImHelper1.getInstance().agreeInvite(data.getFrom());
//            }
//        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_black_list;
    }
}
