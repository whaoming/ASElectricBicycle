package com.wxxiaomi.ming.electricbicycle.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.ui.activity.base.BaseActivity;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.FriendAddPresenter;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.impl.FriendAddPresenterImpl;
import com.wxxiaomi.ming.electricbicycle.ui.activity.view.FriendAddView;
import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter.NearFriendRecommendAdapter1;

/**
 * Created by 12262 on 2016/6/15.
 */
public class FriendAddActivity extends BaseActivity<FriendAddView,FriendAddPresenter> implements FriendAddView<FriendAddPresenter> {

    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private EditText et_name;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_friend_add);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView)findViewById(R.id.list);
        et_name= (EditText) findViewById(R.id.et_name);
        // 标题的文字需在setSupportActionBar之前，不然会无效
        toolbar.setTitle("好友添加");
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); // 设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        et_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                presenter.onFindClick(et_name.getText().toString().trim());
                return false;
            }
        });
    }

    @Override
    public FriendAddPresenter getPresenter() {
        return new FriendAddPresenterImpl();
    }


    @Override
    public void setListAdaper(NearFriendRecommendAdapter1 adapter) {
        mRecyclerView.setAdapter(adapter);
    }

}
