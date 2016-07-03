package com.wxxiaomi.ming.electricbicycle.ui.view;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.wxxiaomi.ming.chatwidget.weidgt.ChatInputMenu;
import com.wxxiaomi.ming.electricbicycle.ui.view.base.BaseView;
import com.wxxiaomi.ming.electricbicycle.view.em.adapter.ChatRowItemAdapter;

/**
 * Created by 12262 on 2016/6/14.
 */
public interface ChatView extends BaseView {
    void setChatRowAdapter(ChatRowItemAdapter adapter);
    RecyclerView getListView();
    ChatInputMenu getInputMenu();
    Intent getIntentData();
}
