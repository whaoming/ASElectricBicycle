package com.wxxiaomi.ming.electricbicycle.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo2;
import com.wxxiaomi.ming.electricbicycle.ui.activity.UserInfoActivity;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pull2refreshreview.PullToRefreshRecyclerView;


/**
 * Created by Administrator on 2016/12/22.
 */

public class InfoDetailFragment extends Fragment implements UserInfoActivity.OnUserLoadCompleteListner {
    private View view;
//    private PullToRefreshRecyclerView mRecyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_info_detail,container,false);
//        mRecyclerView = (PullToRefreshRecyclerView) view.findViewById(R.id.mRecyclerView);
////        initRefreshView();
//        mRecyclerView.setRefreshing(false);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mRecyclerView.setNestedScrollingEnabled(true);
        return view;
    }

    @Override
    public void onUserInfoComplete(UserCommonInfo2 userinfo) {

    }
}
