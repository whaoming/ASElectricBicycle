package com.wxxiaomi.ming.electricbicycle.ui.activity.view;

import android.os.Bundle;

import com.wxxiaomi.ming.electricbicycle.ui.presenter.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.ui.activity.base.BaseView;

import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter.NewFriendAddItemAdapter;

/**
 * Created by 12262 on 2016/6/9.
 */
public interface ContactView<T extends BasePre> extends BaseView {
    void updateUnReadMsg(int count);
    void setInviteListAdapter(NewFriendAddItemAdapter adapter);
    void runActivity(Class clazz,Bundle bundle);
    void refershChildUI();
}
