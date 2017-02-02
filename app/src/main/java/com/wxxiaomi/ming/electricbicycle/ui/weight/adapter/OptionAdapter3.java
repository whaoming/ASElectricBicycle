package com.wxxiaomi.ming.electricbicycle.ui.weight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.wxxiaomi.ming.electricbicycle.R;

import com.wxxiaomi.ming.electricbicycle.common.util.TimeUtil;
import com.wxxiaomi.ming.electricbicycle.db.bean.Option;
import com.wxxiaomi.ming.electricbicycle.api.constant.OptionType;
import com.wxxiaomi.ming.electricbicycle.service.AccountHelper;
import com.wxxiaomi.ming.electricbicycle.service.ShowerProvider;

import java.util.List;


/**
 * Created by Administrator on 2016/12/6.
 */

public class OptionAdapter3 extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Option> list;
    private Context mContext;

    public OptionAdapter3(List<Option> feedList, Context context) {
        list = feedList;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_option2, null);
        return new LatestViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof LatestViewHolder2) {
            LatestViewHolder2 holder = (LatestViewHolder2) viewHolder;
            Option option = list.get(position);
            if(option!=null){
                int flag = option.type;
                String avatar = AccountHelper.getAccountInfo().avatar;
                ShowerProvider.showHead(mContext,holder.iv_avatar,avatar);
                switch (flag){
                    case OptionType.TOPIC_PUBLISH:
                        if(option.picture!=null){
                            if("".equals(option.picture)){
                                holder.iv_picture.setVisibility(View.GONE);
                            }else {
                                holder.iv_picture.setVisibility(View.VISIBLE);
                                ShowerProvider.showNormalImage(mContext, holder.iv_picture, option.picture);
                            }
                        }else{
                            holder.iv_picture.setVisibility(View.GONE);
                        }
                        holder.tv_reply.setVisibility(View.GONE);
                        holder.tv_content.setText(option.content);
                        holder.tv_time.setText(TimeUtil.StrToSimpleDate(option.create_time));
                        holder.tv_option.setText(option.nickname+" 发表了主题");
                        break;
                    case OptionType.TOPIC_COMMENT:
                        if(option.picture!=null){
                            if("".equals(option.picture)){
                                holder.iv_picture.setVisibility(View.GONE);
                            }else {
                                holder.iv_picture.setVisibility(View.VISIBLE);
                                ShowerProvider.showNormalImage(mContext, holder.iv_picture, option.picture);
                            }
                        }else{
                            holder.iv_picture.setVisibility(View.GONE);
                        }
                        holder.tv_reply.setVisibility(View.VISIBLE);
                        holder.tv_reply.setText(option.reply);
                        holder.tv_content.setText(option.content);
                        holder.tv_time.setText(TimeUtil.StrToSimpleDate(option.create_time));
                        holder.tv_option.setText(option.nickname+" 评论了主题");
                        break;
                }
            }

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
