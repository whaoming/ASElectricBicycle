package com.wxxiaomi.ming.electricbicycle.core.weight.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.common.GlobalManager;
import com.wxxiaomi.ming.electricbicycle.core.weight.timelineview.TimelineView;
import com.wxxiaomi.ming.electricbicycle.dao.bean.OptionLogs;
import com.wxxiaomi.ming.electricbicycle.dao.constant.OptionType;

import java.util.List;

/**
 * Created by HP-HP on 05-12-2015.
 */
public class OptionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String currentHead;
    private List<OptionLogs> mFeedList;
    private Context mContext;
    private String currentName;

    public OptionAdapter(List<OptionLogs> feedList,Context context) {
        mFeedList = feedList;
        this.mContext = context;
        currentName = GlobalManager.getInstance().getUser().userCommonInfo.name;
        currentHead = GlobalManager.getInstance().getUser().userCommonInfo.head;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mContext = parent.getContext();

        View view;

            view = View.inflate(parent.getContext(), R.layout.view_item_timeline, null);

        return new OptionViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof OptionViewHolder){
            OptionViewHolder holder = (OptionViewHolder)viewHolder;
            OptionLogs timeLineModel = mFeedList.get(position);

            switch (timeLineModel.obj_type){
                case OptionType.FOOT_PRINT:
                    holder.mTimelineView.setMarker(mContext.getResources().getDrawable(R.mipmap.d1));
                    holder.foot_content.setVisibility(View.GONE);
                    holder.tv_option.setText("122627018来到了这里");
                    setOption(holder.tv_option,currentName.length());
                    holder.no_img_content.setVisibility(View.GONE);
                    holder.pic_content.setVisibility(View.VISIBLE);
                    holder.tv_pic_title.setVisibility(View.GONE);
                    break;
                case OptionType.TOPIC_PUBLISH:
                    holder.mTimelineView.setMarker(mContext.getResources().getDrawable(R.mipmap.d2));
                    holder.tv_option.setText("122627018发表了话题");
                    holder.foot_content.setVisibility(View.VISIBLE);
                    setOption(holder.tv_option,currentName.length());
                    if("".equals(timeLineModel.pictures)){
                        holder.no_img_content.setVisibility(View.VISIBLE);
                        holder.pic_content.setVisibility(View.GONE);
                        holder.tv_no_pic_title.setText(timeLineModel.content);
                    }else{
                        holder.no_img_content.setVisibility(View.GONE);
                        holder.pic_content.setVisibility(View.VISIBLE);
                        holder.tv_pic_title.setVisibility(View.VISIBLE);
                        holder.tv_pic_title.setText(timeLineModel.content);
                    }
                    break;
                case OptionType.TOPIC_COMMENT:
                    holder.mTimelineView.setMarker(mContext.getResources().getDrawable(R.mipmap.d3));
                    holder.tv_option.setText("122627018发表了评论");
                    holder.foot_content.setVisibility(View.VISIBLE);
                    setOption(holder.tv_option,currentName.length());
                    if("".equals(timeLineModel.pictures)){
                        holder.no_img_content.setVisibility(View.VISIBLE);
                        holder.pic_content.setVisibility(View.GONE);
                        holder.tv_no_pic_title.setText(timeLineModel.content);
                    }else{
                        holder.no_img_content.setVisibility(View.GONE);
                        holder.pic_content.setVisibility(View.VISIBLE);
                        holder.tv_pic_title.setVisibility(View.VISIBLE);
                        holder.tv_pic_title.setText(timeLineModel.content);
                    }
                    break;
                case OptionType.PHOTO_PUBLISH:
                    holder.mTimelineView.setMarker(mContext.getResources().getDrawable(R.mipmap.d4));
                    holder.no_img_content.setVisibility(View.GONE);
                    holder.foot_content.setVisibility(View.GONE);
                    holder.tv_option.setText("122627018更新了相册");
                    setOption(holder.tv_option,currentName.length());
                    holder.tv_pic_title.setVisibility(View.GONE);
                    holder.pic_content.setVisibility(View.VISIBLE);
                    break;
            }
        }



    }

    @Override
    public int getItemCount() {
        return (mFeedList!=null? mFeedList.size():0);
    }

    public void setOption(TextView tv,int size){
        SpannableStringBuilder builder = new SpannableStringBuilder(tv.getText().toString());

        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.BLUE);
        builder.setSpan(redSpan, 0, size, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(builder);
    }


}
