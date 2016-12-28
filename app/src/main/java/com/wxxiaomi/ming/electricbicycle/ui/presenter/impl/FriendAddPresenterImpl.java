package com.wxxiaomi.ming.electricbicycle.ui.presenter.impl;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserLocatInfo;

import com.wxxiaomi.ming.electricbicycle.service.FunctionProvider;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.base.BasePreImpl;

import com.wxxiaomi.ming.electricbicycle.ui.presenter.FriendAddPresenter;
import com.wxxiaomi.ming.electricbicycle.service.GlobalManager;
import com.wxxiaomi.ming.electricbicycle.support.rx.SampleProgressObserver;
import com.wxxiaomi.ming.electricbicycle.ui.activity.view.FriendAddView;
import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter.NearFriendRecommendAdapter1;
import com.wxxiaomi.ming.electricbicycle.service.LocatProvider;
import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter.UserSearchResultAdapter;
import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter2.NearUserAdapter;
import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter2.UserSearchRsultAdapter1;
import com.wxxiaomi.ming.electricbicycle.ui.weight.myrecycle.PullToRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


/**
 * Created by 12262 on 2016/6/15.
 */
public class FriendAddPresenterImpl extends BasePreImpl<FriendAddView> implements FriendAddPresenter<FriendAddView> {

//    private NearFriendRecommendAdapter1 adapter ;
//    private List<UserLocatInfo> tempNearUserList;
    private PullToRefreshRecyclerView listView;
    private NearUserAdapter mAdapter;
    private View emptyView;
//    private PullToRefreshRecyclerView listView;
    private boolean isAddHeader = false;
    private boolean isNear = true;

    @Override
    public void init() {
//        tempNearUserList = new ArrayList<UserLocatInfo>();
        initListView();
        getNearFriend();

//        adapter = new NearFriendRecommendAdapter1(mView.getContext(),tempNearUserList);
//        mView.setListAdaper(adapter);
//        getNearFriend();
    }

    private void initListView() {
       listView = mView.getListView();
        mAdapter = new NearUserAdapter(mView.getContext(), null, true,listView);
        emptyView = LayoutInflater.from(mView.getContext()).inflate(R.layout.view_list_empty, (ViewGroup) listView.getParent(), false);
        mAdapter.setEmptyView(emptyView);
        listView.setAdapter(mAdapter);
    }


    private void getNearFriend() {
        FunctionProvider.getInstance().getNearPeople(GlobalManager.getInstance().getUser().id
                , LocatProvider.getInstance().getLatitude()
                , LocatProvider.getInstance().getLongitude())
                .subscribe(new Action1<List<UserLocatInfo>>() {
                    @Override
                    public void call(List<UserLocatInfo> nearByPerson) {
                        if(nearByPerson!=null){
//                            tempNearUserList.addAll(nearByPerson);
//                            adapter = new NearFriendRecommendAdapter1(mView.getContext(),tempNearUserList);
                            mAdapter.setNewData(nearByPerson);

                        }

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
//        Log.i("wang","name:"+name);
        FunctionProvider.getInstance().getUserByNameFWeb(name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SampleProgressObserver<List<UserCommonInfo>>(mView.getContext()) {
                    @Override
                    public void onNext(List<UserCommonInfo> initUserInfo) {
//                        if(initUserInfo.size()!=0){
//                            Bundle bundle = new Bundle();
//                            bundle.putSerializable("userInfo", initUserInfo.get(0));
//                            mView.runActivity(UserInfoAct.class|,bundle,false);
//                        Log.i("wang","initUserInfo.size:"+initUserInfo.size());
                        isNear = false;
                             adapter1.setNewData(initUserInfo);
//                            adapter = new NearFriendRecommendAdapter1(mView.getContext(),tempNearUserList);

//                            adapter1.set

//                            mView.getListView().setRefreshing(false);
//                            mView.getListView().setAdapter();
//                        }else{
//                            Log.i("wang","找不到这个人");
//                        }

                    }
                });
    }

    @Override
    public void onTextChange(String newText) {
        if(!isNear){
            isNear = !isNear;
            isAddHeader = !isAddHeader;
//            mAdapter.setNewData(nearByPerson);
            listView.removeAllViews();
            listView.removeHeader();
            listView.getRecyclerView().removeAllViews();
            listView.setAdapter(mAdapter);
        }

        if(!isAddHeader){
            isAddHeader=!isAddHeader;
            listView.addHeaderView(mView.getHeader());
        }
//        tv_search.setText(newText);
        mView.setHeaderText(newText);
        if("".equals(newText)){
            isAddHeader=!isAddHeader;
            listView.removeHeader();
        }
    }
}
