package com.wxxiaomi.ming.electricbicycle.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.ui.activity.base.BaseActivity;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.FriendAddPresenter;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.impl.FriendAddPresenterImpl;
import com.wxxiaomi.ming.electricbicycle.ui.activity.view.FriendAddView;
import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter.NearFriendRecommendAdapter1;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pull2refreshreview.PullToRefreshRecyclerView;

/**
 * Created by 12262 on 2016/6/15.
 */
public class FriendAddActivity extends BaseActivity<FriendAddView,FriendAddPresenter> implements FriendAddView<FriendAddPresenter> {

    private Toolbar toolbar;
    private SearchView searchView;
//    private RecyclerView mRecyclerView;
//    private LinearLayoutManager mLayoutManager;
//    private EditText et_name;
    private PullToRefreshRecyclerView mRecyclerView;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_friend_add);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        searchView = (SearchView) findViewById(R.id.searchView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchView.setIconifiedByDefault(true);//设置展开后图标的样式,这里只有两种,一种图标在搜索框外,一种在搜索框内
        searchView.onActionViewExpanded();// 写上此句后searchView初始是可以点击输入的状态，如果不写，那么就需要点击下放大镜，才能出现输入框,也就是设置为ToolBar的ActionView，默认展开
        searchView.requestFocus();//输入焦点
        searchView.setSubmitButtonEnabled(true);//添加提交按钮，监听在OnQueryTextListener的onQueryTextSubmit响应
//        searchView.setFocusable(true);//将控件设置成可获取焦点状态,默认是无法获取焦点的,只有设置成true,才能获取控件的点击事件
        searchView.setIconified(false);//输入框内icon不显示
//        searchView.requestFocusFromTouch();//模拟焦点点击事件
        mRecyclerView = (PullToRefreshRecyclerView) findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setRefreshing(true);
//        mRecyclerView.setRefreshing(true);
//        mRecyclerView = (RecyclerView)findViewById(R.id.list);
//        et_name= (EditText) findViewById(R.id.et_name);
        // 标题的文字需在setSupportActionBar之前，不然会无效
//        toolbar.setTitle("好友添加");
//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setHomeButtonEnabled(true); // 设置返回键可用
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        et_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//                presenter.onFindClick(et_name.getText().toString().trim());
//                return false;
//            }
//        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.onFindClick(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
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
//        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public PullToRefreshRecyclerView getListView() {
        return mRecyclerView;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_frined_add_search, menu);
//        return true;
//    }
}
