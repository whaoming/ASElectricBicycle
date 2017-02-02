package com.wxxiaomi.ming.electricbicycle.ui.weight.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserLocatInfo;
import com.wxxiaomi.ming.electricbicycle.service.ShowerProvider;
import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter.base.BaseAdapter;
import com.wxxiaomi.ming.electricbicycle.ui.weight.custom.CircularImageView;

import java.util.List;

/**
 * 附近好友推荐的adapter
 * 总共需要三种状态：
 * 1.加载数据ing
 * 2.加载失败
 * 3.无数据
 * 4.有item
 * 5.还没开始加载
 */
public class UserSearchResultAdapter extends BaseAdapter {

    private Context context;
    private List<UserCommonInfo> userInfos;

    public UserSearchResultAdapter(Context context, List<UserCommonInfo> userInfos) {
        super(context);
        this.context = context;
        this.userInfos = userInfos;
    }

    @Override
    public ViewHolder onCreateViewHolderOnChild(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.item_near_friend_recommend, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolderOnChild(ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ItemViewHolder) {

            ItemViewHolder holder = (ItemViewHolder) viewHolder;
            if(position==(getItemCount()-userInfos.size())){
                holder.content_tip.setVisibility(View.VISIBLE);
            }else{
                holder.content_tip.setVisibility(View.GONE);
            }
            final UserCommonInfo userCommonInfo = userInfos.get(position);
            holder.tv_name.setText(userCommonInfo.nickname);
            holder.tv_reason.setText("用户描述");
            holder.rl_item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("userInfo", userCommonInfo);
//                    Intent intent4 = new Intent(context, UserInfoAct.class);
//                    intent4.putExtra("value", bundle);
//                    context.startActivity(intent4);
                }
            });
//            Glide.with(context).load(userCommonInfo.avatar).into(holder.iv_head);
            ShowerProvider.showHead(context,holder.iv_head,userCommonInfo.avatar);
        }
    }

    @Override
    public int getItemCountOnChild() {
        return userInfos.size();
    }


    public class ItemViewHolder extends ViewHolder {
        public TextView tv_name;
        public TextView tv_reason;
        public RelativeLayout rl_item;
        public CircularImageView iv_head;
        public RelativeLayout content_tip;

        public ItemViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_reason = (TextView) view.findViewById(R.id.tv_description);
            rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
            iv_head = (CircularImageView) view.findViewById(R.id.iv_head);
            content_tip = (RelativeLayout) view.findViewById(R.id.content_tip);
        }
    }
}
