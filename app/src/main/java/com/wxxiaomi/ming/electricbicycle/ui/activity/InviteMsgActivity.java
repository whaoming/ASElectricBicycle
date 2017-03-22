package com.wxxiaomi.ming.electricbicycle.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.db.bean.InviteMessage;
import com.wxxiaomi.ming.electricbicycle.db.impl.InviteMessgeDaoImpl2;
import com.wxxiaomi.ming.electricbicycle.im.notice.NoticeManager;
import com.wxxiaomi.ming.electricbicycle.ui.activity.base.BaseActivity;
import com.wxxiaomi.ming.electricbicycle.ui.activity.base.NormalActivity;
import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter2.InviteAdapter;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pulltorefresh.recycleview.PullToRefreshRecyclerView;

import java.util.List;

import rx.functions.Action1;

public class InviteMsgActivity extends NormalActivity {
    private Toolbar toolbar;
    private PullToRefreshRecyclerView mRecyclerView;
    private InviteAdapter mAdapter;

    private void initData() {
        //从数据库取出邀请信息
        //根据邀请信息取得用户信息传入
        NoticeManager.clearNotice(this,NoticeManager.FLAG_CLEAR_INVITE);
        InviteMessgeDaoImpl2 dao = new InviteMessgeDaoImpl2(this);
        dao.getMessagesListRx()
                .subscribe(new Action1<List<InviteMessage>>() {
            @Override
            public void call(List<InviteMessage> inviteMessages) {
                mAdapter.setNewData(inviteMessages);
            }
        });
    }

    private void initListview() {
        mAdapter = new InviteAdapter(this, null, true,mRecyclerView);
        View emptyView = LayoutInflater.from(this).inflate(R.layout.view_list_empty, (ViewGroup) mRecyclerView.getParent(), false);
        mAdapter.setEmptyView(emptyView);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_invite_msg);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("邀请信息");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView = (PullToRefreshRecyclerView) findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initListview();
        initData();
    }
}
