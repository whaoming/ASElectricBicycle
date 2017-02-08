package com.wxxiaomi.ming.electricbicycle.ui.weight.adapter2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;

import com.wxxiaomi.ming.electricbicycle.ConstantValue;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.manager.ShowerProvider;
import com.wxxiaomi.ming.electricbicycle.ui.activity.UserInfoActivity;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pulltorefresh.baseadapter.ViewHolder;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pulltorefresh.baseadapter.base.CommonBaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/12/28.
 */

public class UserSearchRsultAdapter1 extends CommonBaseAdapter<UserCommonInfo> {

    public UserSearchRsultAdapter1(Context context, List<UserCommonInfo> datas, boolean isOpenLoadMore,SwipeRefreshLayout listview) {
        super(context, datas, isOpenLoadMore,listview);
    }

    @Override
    protected void convert(ViewHolder holder, final UserCommonInfo data, int position) {
        holder.getView(R.id.content_tip).setVisibility(View.GONE);
        holder.setText(R.id.tv_name,data.nickname);
        holder.setText(R.id.tv_description,"用户描述");
        ShowerProvider.showHead(mContext, (ImageView) holder.getView(R.id.iv_head),data.avatar);
        holder.getView(R.id.content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UserInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ConstantValue.INTENT_USERINFO, data);
                bundle.putBoolean(ConstantValue.INTENT_ISMINE,false);
                intent.putExtra("value",bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_near_friend_recommend;
    }
}
