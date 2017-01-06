package com.wxxiaomi.ming.electricbicycle.ui.presenter.impl;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserLocatInfo;

import com.wxxiaomi.ming.electricbicycle.service.AccountHelper;
import com.wxxiaomi.ming.electricbicycle.service.UserFunctionProvider;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.base.BasePreImpl;

import com.wxxiaomi.ming.electricbicycle.ui.presenter.FriendAddPresenter;

import com.wxxiaomi.ming.electricbicycle.support.rx.ProgressObserver;
import com.wxxiaomi.ming.electricbicycle.ui.activity.view.FriendAddView;
import com.wxxiaomi.ming.electricbicycle.service.LocatProvider;
import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter2.NearUserAdapter;
import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter2.UserSearchRsultAdapter1;
import com.wxxiaomi.ming.electricbicycle.ui.weight.myrecycle.PullToRefreshRecyclerView;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


/**
 * Created by 12262 on 2016/6/15.
 */
public class FriendAddPresenterImpl extends BasePreImpl<FriendAddView> implements FriendAddPresenter<FriendAddView> {

    private PullToRefreshRecyclerView listView;
    private NearUserAdapter mAdapter;
    private View emptyView;
    private boolean isAddHeader = false;
    private boolean isNear = true;

    @Override
    public void init() {
        initListView();
        getNearFriend();
    }

    private void initListView() {
       listView = mView.getListView();
        mAdapter = new NearUserAdapter(mView.getContext(), null, true,listView);
        emptyView = LayoutInflater.from(mView.getContext()).inflate(R.layout.view_list_empty, (ViewGroup) listView.getParent(), false);
        mAdapter.setEmptyView(emptyView);
        listView.setAdapter(mAdapter);
    }


    private void getNearFriend() {
        UserFunctionProvider.getInstance().getNearPeople(AccountHelper.getAccountInfo().id
                , LocatProvider.getInstance().getLatitude()
                , LocatProvider.getInstance().getLongitude())
                .subscribe(new Action1<List<UserLocatInfo>>() {
                    @Override
                    public void call(List<UserLocatInfo> nearByPerson) {
                            mAdapter.setNewData(nearByPerson);
                    }
                });
    }

    @Override
    public void onFindClick(String name) {
        final UserSearchRsultAdapter1 adapter1 = new UserSearchRsultAdapter1(mView.getContext(),null, true,listView);
        adapter1.setEmptyView(emptyView);
        listView.removeHeader();
        listView.getRecyclerView().removeAllViews();
        mView.getListView().setAdapter(adapter1);
        UserFunctionProvider.getInstance().getUserByNameFWeb(name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressObserver<List<UserCommonInfo>>(mView.getContext()) {
                    @Override
                    public void onNext(List<UserCommonInfo> initUserInfo) {
                            isNear = false;
                             adapter1.setNewData(initUserInfo);
                    }
                });
    }

    @Override
    public void onTextChange(String newText) {
        if(!isNear){
            isNear = !isNear;
            if(!"".equals(newText)){
                isAddHeader = !isAddHeader;
            }
            listView.getRecyclerView().swapAdapter(mAdapter,true);
        }
        if(!isAddHeader){
            isAddHeader=!isAddHeader;
            listView.removeHeader();
            listView.addHeaderView(mView.getHeader());
        }
        mView.setHeaderText(newText);
        if("".equals(newText)){
            isAddHeader=!isAddHeader;
            listView.removeHeader();
        }
    }
}
