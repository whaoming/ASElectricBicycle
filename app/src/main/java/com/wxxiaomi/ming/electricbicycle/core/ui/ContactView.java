package com.wxxiaomi.ming.electricbicycle.core.ui;

import android.os.Bundle;

import com.wxxiaomi.ming.electricbicycle.core.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.core.base.BaseView;

import com.wxxiaomi.ming.electricbicycle.core.weight.adapter.NewFriendAddItemAdapter;

/**
 * Created by 12262 on 2016/6/9.
 */
public interface ContactView<T extends BasePre> extends BaseView<T> {
    void updateUnReadMsg(int count);
    void setInviteListAdapter(NewFriendAddItemAdapter adapter);
    void runActivity(Class clazz,Bundle bundle);
    void refershChildUI();
}
