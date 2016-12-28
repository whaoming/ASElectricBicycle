package com.wxxiaomi.ming.electricbicycle.ui.presenter.impl;

import android.os.Bundle;
import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserLocatInfo;

import com.wxxiaomi.ming.electricbicycle.service.FunctionProvider;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.base.BasePreImpl;

import com.wxxiaomi.ming.electricbicycle.ui.presenter.FriendAddPresenter;
import com.wxxiaomi.ming.electricbicycle.service.GlobalManager;
import com.wxxiaomi.ming.electricbicycle.support.rx.SampleProgressObserver;
import com.wxxiaomi.ming.electricbicycle.ui.activity.UserInfoAct;
import com.wxxiaomi.ming.electricbicycle.ui.activity.view.FriendAddView;
import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter.NearFriendRecommendAdapter1;
import com.wxxiaomi.ming.electricbicycle.service.LocatProvider;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pull2refreshreview.PullToRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;


/**
 * Created by 12262 on 2016/6/15.
 */
public class FriendAddPresenterImpl extends BasePreImpl<FriendAddView> implements FriendAddPresenter<FriendAddView> {

    private NearFriendRecommendAdapter1 adapter ;
    private List<UserLocatInfo> tempNearUserList;
//    private PullToRefreshRecyclerView listView;

    @Override
    public void init() {
        tempNearUserList = new ArrayList<UserLocatInfo>();
        initListView();
        getNearFriend();

//        adapter = new NearFriendRecommendAdapter1(mView.getContext(),tempNearUserList);
//        mView.setListAdaper(adapter);
//        getNearFriend();
    }

    private void initListView() {
//        mView.getListView().setRefreshing(true);
    }


    private void getNearFriend() {

        FunctionProvider.getInstance().getNearPeople(GlobalManager.getInstance().getUser().id
                , LocatProvider.getInstance().getLatitude()
                , LocatProvider.getInstance().getLongitude())
                .subscribe(new Action1<List<UserLocatInfo>>() {
                    @Override
                    public void call(List<UserLocatInfo> nearByPerson) {
                        if(nearByPerson!=null){
                            tempNearUserList.addAll(nearByPerson);
                            adapter = new NearFriendRecommendAdapter1(mView.getContext(),tempNearUserList);
                            mView.getListView().setAdapter(adapter);
                            mView.getListView().setRefreshing(false);
//                            adapter.setLoadingComplete();
                        }

                    }
                });
    }

    @Override
    public void onFindClick(String name) {
        Log.i("wang","name:"+name);
        FunctionProvider.getInstance().getUserByNameFWeb(name)
                .subscribe(new SampleProgressObserver<List<UserCommonInfo>>(mView.getContext()) {
                    @Override
                    public void onNext(List<UserCommonInfo> initUserInfo) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userInfo", initUserInfo.get(0));
                        mView.runActivity(UserInfoAct.class,bundle,false);
                    }
                });
    }
}
