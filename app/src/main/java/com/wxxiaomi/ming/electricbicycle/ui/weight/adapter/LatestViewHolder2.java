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

public class LatestViewHolder2 extends RecyclerView.ViewHolder {
    public ImageView iv_avatar;
    public TextView tv_option;
    public TextView tv_time;
    public ImageView iv_picture;
    public TextView tv_content;
    public TextView tv_reply;
    public TextView tv_like;
    public TextView tv_reply_count;

    public LatestViewHolder2(View itemView) {
        super(itemView);
        iv_avatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
        iv_picture = (ImageView) itemView.findViewById(R.id.iv_picture);
        tv_option = (TextView) itemView.findViewById(R.id.tv_option);
        tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        tv_content = (TextView) itemView.findViewById(R.id.tv_content);
        tv_like = (TextView) itemView.findViewById(R.id.tv_like);
        tv_reply = (TextView) itemView.findViewById(R.id.tv_reply);
        tv_reply_count = (TextView) itemView.findViewById(R.id.tv_reply_count);
    }
}
