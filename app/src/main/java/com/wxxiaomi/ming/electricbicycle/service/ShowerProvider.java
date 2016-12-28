package com.wxxiaomi.ming.electricbicycle.service;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wxxiaomi.ming.electricbicycle.R;

/**
 * Created by Administrator on 2016/12/8.
 * 图片显示的提供者
 */

public class ShowerProvider {
    public static void showHead(Context context,ImageView imageView,String path){
        Glide.with(context)
                .load(path)
                .dontAnimate()
                .placeholder(R.mipmap.noone)
                .into(imageView);
    }

    public static void showNormalImage(Context context,ImageView imageView,String path){
        Glide.with(context)
                .load(path)
                .crossFade(500)
                .into(imageView);
    }
}
