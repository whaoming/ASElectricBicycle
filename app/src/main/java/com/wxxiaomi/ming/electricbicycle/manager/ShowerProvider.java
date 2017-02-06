package com.wxxiaomi.ming.electricbicycle.manager;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wxxiaomi.ming.electricbicycle.R;

/**
 * Created by Administrator on 2016/12/8.
 * 图片显示的提供者
 */

public class ShowerProvider {
    public static void showHead(Context context,ImageView imageView,String path){
        if(!TextUtils.isEmpty(path)){
            Glide.with(context)
                    .load(path)
                    .dontAnimate()
                    .placeholder(R.mipmap.noone)
                    .into(imageView);
        }

    }

    public static void showNormalImage(Context context,ImageView imageView,String path){
        if(!TextUtils.isEmpty(path)) {
            Glide.with(context)
                    .load(path)
                    .crossFade(500)
                    .into(imageView);
        }
    }

    public static void showSplashImage(Context context,ImageView imageView,int defaultID,String path){
        if(!TextUtils.isEmpty(path)) {
            Glide.with(context)
                    .load(path)
                    //设置跳过内存缓存
                    .skipMemoryCache(true)
                    .error(defaultID)
                    .crossFade(500)
                    //设置图片全缓存(而不是适应imageview大小的缓存)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .animate(R.anim.item_alpha_in)
                    .into(imageView);
        }
    }
}
