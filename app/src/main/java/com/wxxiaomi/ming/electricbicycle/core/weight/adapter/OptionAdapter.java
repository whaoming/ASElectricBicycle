package com.wxxiaomi.ming.electricbicycle.core.weight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.core.weight.timelineview.TimelineView;
import com.wxxiaomi.ming.electricbicycle.dao.bean.OptionLogs;
import com.wxxiaomi.ming.electricbicycle.dao.constant.OptionType;

import java.util.List;

/**
 * Created by HP-HP on 05-12-2015.
 */
public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {

    private List<OptionLogs> mFeedList;
    private Context mContext;

    public OptionAdapter(List<OptionLogs> feedList) {
        mFeedList = feedList;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    @Override
    public OptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mContext = parent.getContext();

        View view;

//        if(mOrientation == Orientation.horizontal) {
//            view = View.inflate(parent.getContext(), R.layout.item_timeline_horizontal, null);
//        } else {
            view = View.inflate(parent.getContext(), R.layout.ming_item_timeline, null);
//        }

        return new OptionViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(OptionViewHolder holder, int position) {

        OptionLogs timeLineModel = mFeedList.get(position);
        switch (timeLineModel.obj_type){
            case OptionType.PHOTO_PUBLISH:
                holder.user_name.setText("wang更新了相册");
                holder.rl_foot.setVisibility(View.GONE);
                holder.info_content.setVisibility(View.GONE);
                break;
            case OptionType.TOPIC_COMMENT:
                holder.user_name.setText("122627018发表了评论");
                holder.info_pic.setVisibility(View.GONE);
                break;
            case OptionType.TOPIC_PUBLISH:
                holder.user_name.setText("122627018发表了一个话题");
                break;
            default:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return (mFeedList!=null? mFeedList.size():0);
    }


    public class OptionViewHolder extends RecyclerView.ViewHolder{
        public TimelineView mTimelineView;
        public ImageView user_head;
        public ImageView info_pic;
        public TextView user_name;
        public TextView info_content;
        public TextView info_locat_tag;
        public TextView info_star;
        public RelativeLayout rl_foot;


        public OptionViewHolder(View itemView, int viewType) {
            super(itemView);
            user_head = (ImageView) itemView.findViewById(R.id.user_head);
            info_pic = (ImageView) itemView.findViewById(R.id.info_pic);
            user_name = (TextView) itemView.findViewById(R.id.user_name);
            info_content = (TextView) itemView.findViewById(R.id.info_content);
            info_locat_tag = (TextView) itemView.findViewById(R.id.info_locat_tag);
            info_star = (TextView) itemView.findViewById(R.id.info_star);
            mTimelineView = (TimelineView) itemView.findViewById(R.id.time_marker);
            rl_foot = (RelativeLayout) itemView.findViewById(R.id.rl_foot);
            mTimelineView.initLine(viewType);
        }
    }

}
