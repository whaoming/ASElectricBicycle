package com.wxxiaomi.ming.electricbicycle.support.util;

import android.content.Context;

/**
 * Created by 12262 on 2016/11/22.
 */

public class ToolUtils {
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public int Px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int  getResource1(Context context,String imageName){
        int resId = context.getResources().getIdentifier(imageName.split("\\.")[2], "mipmap", context.getPackageName());
        return resId;
    }
}
