//package com.wxxiaomi.ming.electricbicycle.ui.activity;
//
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.support.design.widget.CollapsingToolbarLayout;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.wxxiaomi.ming.electricbicycle.R;
//import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;
//import com.wxxiaomi.ming.electricbicycle.ui.activity.base.BaseActivity;
//import com.wxxiaomi.ming.electricbicycle.ui.presenter.UserInfoPresenter;
//import com.wxxiaomi.ming.electricbicycle.ui.presenter.impl.UserInfoPresenterImpl;
//import com.wxxiaomi.ming.electricbicycle.ui.activity.view.UserInfoView;
//import com.wxxiaomi.ming.electricbicycle.ui.weight.pull2refer.DefaultLoadMoreView;
//
///**
// * Created by whaoming on 2016/7/3.
// * 用户信息页面
// */
//public class UserInfoAct extends BaseActivity<UserInfoView, UserInfoPresenter> implements UserInfoView{
//
//    private Toolbar toolbar1;
//    private FloatingActionButton btn_add;
//    private PullToRefreshRecyclerView mRecyclerView;
//    private CollapsingToolbarLayout collapsing_toolbar;
//    private TextView tv_description;
//    private TextView tv_nick;
//    private ImageView iv_avatvr;
//
//    @Override
//    protected void initView(Bundle savedInstanceState) {
//        setContentView(R.layout.activity_userinfo2);
//
//        // 标题的文字需在setSupportActionBar之前，不然会无效
//
//        btn_add = (FloatingActionButton) findViewById(R.id.btn_add);
//
//        btn_add.setOnClickListener(this);
//        mRecyclerView = (PullToRefreshRecyclerView) findViewById(R.id.mRecyclerView);
//        initRefreshView();
//        toolbar1 = (Toolbar) findViewById(R.id.toolbar1);
//        collapsing_toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        toolbar1.setTitle("");
//        collapsing_toolbar.setTitle("我是标题");
//        tv_description = (TextView) findViewById(R.id.tv_description);
//        tv_nick = (TextView) findViewById(R.id.tv_nick);
//        iv_avatvr = (ImageView) findViewById(R.id.iv_avatvr);
//        setSupportActionBar(toolbar1);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//    }
//
//    private void initRefreshView() {
//        DefaultLoadMoreView defaultLoadMoreView = new DefaultLoadMoreView(this, mRecyclerView.getRecyclerView());
//        defaultLoadMoreView.setLoadmoreString("加载更多");
//        defaultLoadMoreView.setLoadMorePadding(100);
//
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setNestedScrollingEnabled(false);
//
//        mRecyclerView.setPagingableListener(new PullToRefreshRecyclerView.PagingableListener() {
//            @Override
//            public void onLoadMoreItems() {
//                Log.i("wang", "onLoadMoreItems");
//            }
//        });
//        mRecyclerView.setLoadMoreFooter(defaultLoadMoreView);
//        mRecyclerView.setRefreshing(true);
//        ;
//
//    }
//
//    @Override
//    public UserInfoPresenter getPresenter() {
//        return new UserInfoPresenterImpl();
//    }
//
//    @Override
//    public void setAdapter(RecyclerView.Adapter adapter) {
//        mRecyclerView.setAdapter(adapter);
////        mRecyclerView.setRefreshing(false);
////        mRecyclerView.onFinishLoading(true, false);
//
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_add:
//                ///添加好友按钮
//                Log.i("wang", "按钮被按下了");
//                presenter.onAddBtnClick();
//                break;
//        }
//        super.onClick(v);
//    }
//
//    @Override
//    public void setViewData(UserCommonInfo info) {
//        //toolbar.setTitle(info.name);
//    }
//
//    @Override
//    public void setBtnView(Drawable drawable) {
//        btn_add.setImageDrawable(drawable);
//    }
//}
