package com.wxxiaomi.ming.electricbicycle.ui.view;

import android.os.Bundle;

import com.wxxiaomi.ming.electricbicycle.ui.view.base.BaseView;
import com.wxxiaomi.ming.electricbicycle.view.adapter.NewFriendAddItemAdapter;

/**
 * Created by 12262 on 2016/6/9.
 */
public interface ContactView extends BaseView {
    void updateUnReadMsg(int count);
    void setInviteListAdapter(NewFriendAddItemAdapter adapter);
    void runActivity(Class clazz,Bundle bundle);
    void refershChildUI();
}
