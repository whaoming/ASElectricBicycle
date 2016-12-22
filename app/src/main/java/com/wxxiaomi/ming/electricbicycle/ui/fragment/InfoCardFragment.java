package com.wxxiaomi.ming.electricbicycle.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo2;
import com.wxxiaomi.ming.electricbicycle.ui.activity.UserInfoActivity;
import com.wxxiaomi.ming.electricbicycle.ui.fragment.base.BaseFragment;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pull2refreshreview.PullToRefreshRecyclerView;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pull2refreshreview.footer.DefaultLoadMoreView;


/**
 * Created by Administrator on 2016/12/22.
 */

public class InfoCardFragment extends BaseFragment implements UserInfoActivity.OnUserLoadCompleteListner {
    private View view;
    private PullToRefreshRecyclerView mRecyclerView;


    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_info_card,null);
        mRecyclerView = (PullToRefreshRecyclerView) view.findViewById(R.id.mRecyclerView);
        initRefreshView();
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

//    @Override
//    public void onRefresh(boolean isRefresh) {
//        mRecyclerView.setRefreshing(isRefresh);
//    }

    private void initRefreshView() {
        mRecyclerView.setSwipeEnable(false);

        DefaultLoadMoreView defaultLoadMoreView = new DefaultLoadMoreView(getActivity(), mRecyclerView.getRecyclerView());
        defaultLoadMoreView.setLoadmoreString("加载更多");
        defaultLoadMoreView.setLoadMorePadding(100);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setPagingableListener(new PullToRefreshRecyclerView.PagingableListener() {
            @Override
            public void onLoadMoreItems() {
                Log.i("wang", "onLoadMoreItems");
            }
        });
        mRecyclerView.setLoadMoreFooter(defaultLoadMoreView);
//
    }


    @Override
    public void onUserInfoComplete(UserCommonInfo2 userinfo) {
        mRecyclerView.setRefreshing(false);
    }
}
