package com.wxxiaomi.ming.electricbicycle.ui.weight.baseadapter.interfaces;


import com.wxxiaomi.ming.electricbicycle.ui.weight.baseadapter.ViewHolder;

/**
 * Author: Othershe
 * Time: 2016/8/29 10:48
 */
public interface OnMultiItemClickListeners<T> {
    void onItemClick(ViewHolder viewHolder, T data, int position, int viewType);
}
