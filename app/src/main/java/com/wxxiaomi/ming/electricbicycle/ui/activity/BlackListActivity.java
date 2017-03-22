package com.wxxiaomi.ming.electricbicycle.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.db.FriendDao;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.db.impl.FriendDaoImpl2;
import com.wxxiaomi.ming.electricbicycle.ui.activity.base.NormalActivity;
import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter2.BlackListAdapter;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pulltorefresh.recycleview.PullToRefreshRecyclerView;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
* @author whaoming
* github：https://github.com/whaoming
* created at 2017/3/2 21:14
* Description: 黑名单页面
*/
public class BlackListActivity extends NormalActivity {
    private Toolbar toolbar;
    private PullToRefreshRecyclerView mRecyclerView;
    private BlackListAdapter mAdapter;
    private  Observable<List<UserCommonInfo>> listObservable;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_black_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("邀请信息");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView = (PullToRefreshRecyclerView) findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setRefreshing(true);
        mAdapter = new BlackListAdapter(this,null,false,mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        init();
        loadData();
    }

    private void init() {
        listObservable = Observable.create(new Observable.OnSubscribe<List<UserCommonInfo>>() {
            @Override
            public void call(Subscriber<? super List<UserCommonInfo>> subscriber) {
                FriendDao mDao = new FriendDaoImpl2(BlackListActivity.this);
                subscriber.onNext(mDao.getBlackList());
            }
        });
    }

    private void loadData() {
        listObservable.subscribe(new Observer<List<UserCommonInfo>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(List<UserCommonInfo> userCommonInfos) {
                mAdapter.setNewData(userCommonInfos);
            }
        });
    }

}
