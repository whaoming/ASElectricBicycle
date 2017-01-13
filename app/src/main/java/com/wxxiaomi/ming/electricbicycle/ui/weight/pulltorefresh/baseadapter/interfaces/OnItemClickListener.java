package com.wxxiaomi.ming.electricbicycle.ui.weight.pulltorefresh.baseadapter.interfaces;


import com.wxxiaomi.ming.electricbicycle.ui.weight.pulltorefresh.baseadapter.ViewHolder;

/**
 * Author: Othershe
 * Time: 2016/8/29 10:48
 */
public interface OnItemClickListener<T> {
    void onItemClick(ViewHolder viewHolder, T data, int position);
}
