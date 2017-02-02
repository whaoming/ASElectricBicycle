package com.wxxiaomi.ming.electricbicycle.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.ui.activity.base.BaseActivity;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.FriendAddPresenter;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.impl.FriendAddPresenterImpl;
import com.wxxiaomi.ming.electricbicycle.ui.activity.view.FriendAddView;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pulltorefresh.recycleview.PullToRefreshRecyclerView;

import java.lang.reflect.Field;

/**
 * Created by 12262 on 2016/6/15.
 */
public class FriendAddActivity extends BaseActivity<FriendAddView, FriendAddPresenter> implements FriendAddView<FriendAddPresenter> {

    private Toolbar toolbar;
    private SearchView searchView;
    private PullToRefreshRecyclerView mRecyclerView;
    private View header;
    private TextView tv_search;


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
        searchView.setIconified(false);//输入框内icon不显示
//        searchView.requestFocusFromTouch();//模拟焦点点击事件
        header = View.inflate(this, R.layout.view_header_friend_serach, null);
        tv_search = (TextView) header.findViewById(R.id.tv_search);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onFindClick(tv_search.getText().toString().trim());
            }
        });
        mRecyclerView = (PullToRefreshRecyclerView) findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setRefreshing(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.onFindClick(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                presenter.onTextChange(newText);
                return false;
            }
        });
    }

    @Override
    public FriendAddPresenter getPresenter() {
        return new FriendAddPresenterImpl();
    }

//    @Override
//    public void setListAdaper(NearFriendRecommendAdapter1 adapter) {
//        mRecyclerView.setAdapter(adapter);
//    }

    @Override
    public PullToRefreshRecyclerView getListView() {
        return mRecyclerView;
    }

    @Override
    public View getHeader() {
        return header;
    }

    @Override
    public void setHeaderText(String text) {
        tv_search.setText(text);
    }

//
}
