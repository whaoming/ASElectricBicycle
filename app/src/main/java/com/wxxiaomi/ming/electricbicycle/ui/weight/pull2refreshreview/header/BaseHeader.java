package com.wxxiaomi.ming.electricbicycle.ui.weight.pull2refreshreview.header;

import android.support.v7.widget.RecyclerView;

/**
 * Created by linhonghong on 2015/11/17.
 */
public class BaseHeader extends RecyclerView.ItemDecoration {

    protected int mHeaderHeight;

    public void setHeight(int height){
        mHeaderHeight = height;
    }

    public int getHeight(){
        return mHeaderHeight;
    }
}
