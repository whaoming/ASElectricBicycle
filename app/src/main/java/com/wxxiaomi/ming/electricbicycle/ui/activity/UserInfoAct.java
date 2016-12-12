package com.wxxiaomi.ming.electricbicycle.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.ui.weight.custom.FullyLinearLayoutManager;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.ui.activity.base.BaseActivity;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.UserInfoPresenter;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.impl.UserInfoPresenterImpl;
import com.wxxiaomi.ming.electricbicycle.ui.activity.view.UserInfoView;

/**
 * Created by whaoming on 2016/7/3.
 * 用户信息页面
 */
public class UserInfoAct extends BaseActivity<UserInfoView,UserInfoPresenter> implements UserInfoView<UserInfoPresenter>{

    private Toolbar toolbar1;
    private FloatingActionButton btn_add;
    private RecyclerView mRecyclerView;
    private CollapsingToolbarLayout collapsing_toolbar;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_userinfo2);

        // 标题的文字需在setSupportActionBar之前，不然会无效

        btn_add = (FloatingActionButton) findViewById(R.id.btn_add);

        btn_add.setOnClickListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new FullyLinearLayoutManager(this));
        mRecyclerView.setNestedScrollingEnabled(false);
        toolbar1 = (Toolbar) findViewById(R.id.toolbar1);
        collapsing_toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar1.setTitle("");
        collapsing_toolbar.setTitle("我是标题");

        setSupportActionBar(toolbar1);

//        getSupportActionBar().setHomeButtonEnabled(true); // 设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public UserInfoPresenter getPresenter() {
        return new UserInfoPresenterImpl();
    }
    @Override
    public void setAdapter(RecyclerView.Adapter adapter){
        mRecyclerView.setAdapter(adapter);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_add:
                ///添加好友按钮
                presenter.onAddBtnClick();
                break;
        }
    }

    @Override
    public void setViewData(UserCommonInfo info) {
        //toolbar.setTitle(info.name);
    }

    @Override
    public void setBtnView(Drawable drawable) {
        btn_add.setImageDrawable(drawable);
    }
}
