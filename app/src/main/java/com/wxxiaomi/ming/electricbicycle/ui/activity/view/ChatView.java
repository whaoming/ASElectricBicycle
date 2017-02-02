package com.wxxiaomi.ming.electricbicycle.ui.activity.view;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.wxxiaomi.ming.electricbicycle.ui.presenter.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.ui.activity.base.BaseView;

/**
 * Created by 12262 on 2016/6/14.
 */
public interface ChatView extends BaseView {
    RecyclerView getListView();
//    ChatInputMenu getInputMenu();
    Intent getIntentData();
}
