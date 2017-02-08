package com.wxxiaomi.ming.electricbicycle.ui.weight.adapter2;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserLocatInfo;
import com.wxxiaomi.ming.electricbicycle.manager.ShowerProvider;
import com.wxxiaomi.ming.electricbicycle.ui.activity.UserInfoActivity;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pulltorefresh.baseadapter.ViewHolder;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pulltorefresh.baseadapter.base.CommonBaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/12/28.
 */

public class NearUserAdapter extends CommonBaseAdapter<UserLocatInfo> {
    public NearUserAdapter(Context context, List<UserLocatInfo> datas, boolean isOpenLoadMore,SwipeRefreshLayout listview) {
        super(context, datas, isOpenLoadMore,listview);
    }


    @Override
    protected void convert(ViewHolder holder, final UserLocatInfo data, int position) {
        if(position==0){
            holder.getView(R.id.content_tip).setVisibility(View.VISIBLE);
        }else{
            holder.getView(R.id.content_tip).setVisibility(View.GONE);
        }
        holder.setText(R.id.tv_name,data.userCommonInfo.nickname);
        holder.setText(R.id.tv_description,"用户描述");
        ShowerProvider.showHead(mContext, (ImageView) holder.getView(R.id.iv_head),data.userCommonInfo.avatar);
        holder.setOnClickListener(R.id.content,new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(mContext, UserInfoActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable(ConstantValue.INTENT_USERINFO, data.userCommonInfo);
//                bundle.putBoolean(ConstantValue.INTENT_ISMINE,false);
//                intent.putExtra("value",bundle);
//                mContext.startActivity(intent);
                UserInfoActivity.show(mContext,data.userCommonInfo);
            }
        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_near_friend_recommend;
    }
}
