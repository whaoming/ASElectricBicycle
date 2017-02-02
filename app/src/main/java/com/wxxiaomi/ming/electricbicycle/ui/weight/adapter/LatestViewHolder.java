package com.wxxiaomi.ming.electricbicycle.ui.weight.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.ui.weight.custom.CircularImageView;


/**
 * Created by Administrator on 2016/12/3.
 */

public class LatestViewHolder extends RecyclerView.ViewHolder {

    public ImageView iv_img;
    public TextView tv_content;
    //图标那一行的容器
    public RelativeLayout rl_option_content;
    public ImageView iv_zan;
    public TextView tv_locat_tag;
    public CircularImageView iv_user_head;
    public TextView tv_user_comment;

    public RelativeLayout rl_comment_content;
    public View line;
    public LatestViewHolder(View itemView) {
        super(itemView);
        iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
        tv_content = (TextView) itemView.findViewById(R.id.tv_content);
        rl_option_content = (RelativeLayout) itemView.findViewById(R.id.rl_option_content);
        tv_locat_tag = (TextView) itemView.findViewById(R.id.tv_locat_tag);
        iv_user_head = (CircularImageView) itemView.findViewById(R.id.iv_user_head);
        tv_user_comment = (TextView) itemView.findViewById(R.id.tv_user_comment);
        tv_user_comment = (TextView) itemView.findViewById(R.id.tv_user_comment);
        rl_comment_content = (RelativeLayout)itemView.findViewById(R.id.rl_comment_content);
        line = itemView.findViewById(R.id.line);

//        foot_content = (RelativeLayout) itemView.findViewById(R.id.rl_foot);
//        tv_option = (TextView) itemView.findViewById(R.id.tv_option);
//        tv_pic_title = (TextView) itemView.findViewById(R.id.tv_pic_title);
//        pic_content = (RelativeLayout) itemView.findViewById(R.id.rl_pic_content);
//        no_img_content = (CardView) itemView.findViewById(R.id.cv_no_img_content);
//        tv_no_pic_title = (TextView) itemView.findViewById(R.id.tv_no_pic_title);
//        time_marker = (LatestTimeLine) itemView.findViewById(R.id.time_marker);
    }
}
