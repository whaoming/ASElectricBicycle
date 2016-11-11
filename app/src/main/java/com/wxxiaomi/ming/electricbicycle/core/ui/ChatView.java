package com.wxxiaomi.ming.electricbicycle.core.ui;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.wxxiaomi.ming.chatwidget.weidgt.ChatInputMenu;
import com.wxxiaomi.ming.electricbicycle.core.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.core.base.BaseView;
import com.wxxiaomi.ming.electricbicycle.core.weight.em.adapter.ChatRowItemAdapter;

/**
 * Created by 12262 on 2016/6/14.
 */
public interface ChatView<T extends BasePre> extends BaseView<T> {
    void setChatRowAdapter(ChatRowItemAdapter adapter);
    RecyclerView getListView();
    ChatInputMenu getInputMenu();
    Intent getIntentData();
}
