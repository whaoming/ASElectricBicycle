package com.wxxiaomi.ming.electricbicycle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wxxiaomi.ming.chatwidget.weidgt.ChatInputMenu;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.presenter.callback.ChatPresenter;
import com.wxxiaomi.ming.electricbicycle.presenter.impl.ChatPresenterImpl;
import com.wxxiaomi.ming.electricbicycle.ui.base.BaseMvpActivity;
import com.wxxiaomi.ming.electricbicycle.ui.view.ChatView;
import com.wxxiaomi.ming.electricbicycle.view.em.adapter.ChatRowItemAdapter;

/**
 * Created by 12262 on 2016/6/14.
 */
public class ChatActivity1 extends BaseMvpActivity<ChatView,ChatPresenter<ChatView>> implements ChatView {

    private Toolbar toolbar;
    private RecyclerView listView;
    private LinearLayoutManager mLayoutManager;
    private ChatInputMenu inputMenu;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_chat2);
        listView = (RecyclerView) findViewById(R.id.list);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setStackFromEnd(true);
        listView.setLayoutManager(mLayoutManager);
        listView.setItemAnimator(new DefaultItemAnimator());
        toolbar = (Toolbar) this.findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inputMenu = (ChatInputMenu) findViewById(R.id.input_menu);
    }

    @Override
    public ChatInputMenu getInputMenu(){
        return inputMenu;
    }

    public Intent getIntentData(){
        return getIntent();
    }

    @Override
    protected ChatPresenter initPre() {
        return new ChatPresenterImpl();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void setChatRowAdapter(ChatRowItemAdapter adapter) {
        listView.setAdapter(adapter);
    }

    @Override
    public RecyclerView getListView() {
        return listView;
    }
}
