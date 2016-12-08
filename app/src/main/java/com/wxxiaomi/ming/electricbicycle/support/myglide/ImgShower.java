package com.wxxiaomi.ming.electricbicycle.support.myglide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wxxiaomi.ming.electricbicycle.R;

/**
 * Created by Administrator on 2016/12/8.
 */

public class ImgShower {
    public static void showHead(Context context,ImageView imageView,String path){
        Glide.with(context)
                .load(path)
                .placeholder(R.mipmap.noone)
                .into(imageView);
    }
}
