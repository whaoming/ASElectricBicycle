package com.wxxiaomi.ming.electricbicycle.core.weight.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.dao.bean.Comment;
import com.wxxiaomi.ming.electricbicycle.dao.bean.Option;
import com.wxxiaomi.ming.electricbicycle.dao.bean.Topic;
import com.wxxiaomi.ming.electricbicycle.dao.constant.OptionType;

import java.util.List;


/**
 * Created by Administrator on 2016/12/6.
 */

public class OptionAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Option> list;
    private Context mContext;
    private int linePading;


    public OptionAdapter2(List<Option> feedList, Context context) {
        list = feedList;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_option, null);
        return new LatestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof LatestViewHolder) {
            LatestViewHolder holder = (LatestViewHolder) viewHolder;
            Option option = list.get(position);

            int flag = option.obj_type;
            switch (flag){
                case OptionType.TOPIC_PUBLISH:
                    Topic topic = (Topic)option.dobj;
                    if("".equals(topic.pics)){
                        holder.iv_img.setVisibility(View.GONE);
                    }else{
                        holder.iv_img.setVisibility(View.VISIBLE);
                        Glide.with(mContext).load(topic.picss[0]).into( holder.iv_img);
                    }
                    holder.tv_content.setText(topic.content);
                    holder.rl_comment_content.setVisibility(View.GONE);
                    holder.line.setVisibility(View.GONE);
                    holder.tv_locat_tag.setText(topic.locat_tag);
                    break;
                case OptionType.TOPIC_COMMENT:
                    Topic t = (Topic)option.dparent;
                    Comment c = (Comment)option.dobj;
                    if("".equals(t.pics)){
                        holder.iv_img.setVisibility(View.GONE);
                    }else{
                        holder.iv_img.setVisibility(View.VISIBLE);
                        Glide.with(mContext).load(t.picss[0]).into( holder.iv_img);
                    }
                    holder.rl_comment_content.setVisibility(View.VISIBLE);
                    holder.line.setVisibility(View.VISIBLE);
                    holder.tv_user_comment.setText("wang:"+c.content);
                    Log.i("wang","c.from_head:"+c.from_head);
                    Glide.with(mContext).load(c.from_head).into(holder.iv_user_head);
                    holder.tv_locat_tag.setText(t.locat_tag);
                    break;
            }
//            Log.i("wang","flag:"+flag);
//            switch (flag) {
//                case OptionType.FOOT_PRINT:
////                    holder.time_marker.setMarker(mContext.getResources().getDrawable(R.mipmap.d1));
//                    holder.foot_content.setVisibility(View.GONE);
//                    holder.tv_option.setText("122627018来到了这里");
//                    setOption(holder.tv_option);
//                    holder.no_img_content.setVisibility(View.GONE);
//                    holder.pic_content.setVisibility(View.VISIBLE);
//                    holder.tv_pic_title.setVisibility(View.GONE);
//                    break;
//                case OptionType.TOPIC_PUBLISH:
////                    holder.time_marker.setMarker(mContext.getResources().getDrawable(R.mipmap.d2));
//                    holder.tv_option.setText("122627018发表了话题");
//                    holder.foot_content.setVisibility(View.VISIBLE);
//                    setOption(holder.tv_option);
//                    if(myModel.pics==null){
//                        holder.no_img_content.setVisibility(View.VISIBLE);
//                        holder.pic_content.setVisibility(View.GONE);
//                        holder.tv_no_pic_title.setText("我是没有图片的");
//                    }else{
//                        holder.no_img_content.setVisibility(View.GONE);
//                        holder.pic_content.setVisibility(View.VISIBLE);
//                        holder.tv_pic_title.setVisibility(View.VISIBLE);
//                        holder.tv_pic_title.setText("我是有图片的");
//                    }
//                    break;
//                case OptionType.TOPIC_COMMENT:
////                    holder.time_marker.setMarker(mContext.getResources().getDrawable(R.mipmap.d3));
//                    holder.tv_option.setText("122627018发表了评论");
//                    holder.foot_content.setVisibility(View.VISIBLE);
//                    setOption(holder.tv_option);
//                    if(myModel.pics==null){
//                        holder.no_img_content.setVisibility(View.VISIBLE);
//                        holder.pic_content.setVisibility(View.GONE);
//                        holder.tv_no_pic_title.setText("我是没有图片的");
//                    }else{
//                        holder.no_img_content.setVisibility(View.GONE);
//                        holder.pic_content.setVisibility(View.VISIBLE);
//                        holder.tv_pic_title.setVisibility(View.VISIBLE);
//                        holder.tv_pic_title.setText("我是有图片的");
//                    }
//                    break;
//                case OptionType.PHOTO_PUBLISH:
////                    holder.time_marker.setMarker(mContext.getResources().getDrawable(R.mipmap.d4));
//                    holder.no_img_content.setVisibility(View.GONE);
//                    holder.foot_content.setVisibility(View.GONE);
//                    holder.tv_option.setText("122627018更新了相册");
//                    setOption(holder.tv_option);
//                    holder.tv_pic_title.setVisibility(View.GONE);
//                    holder.pic_content.setVisibility(View.VISIBLE);
//                    break;
//
//
//            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

//    public void setOption(TextView tv){
//        SpannableStringBuilder builder = new SpannableStringBuilder(tv.getText().toString());
//        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.BLUE);
//        builder.setSpan(redSpan, 0, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        tv.setText(builder);
//    }

}
