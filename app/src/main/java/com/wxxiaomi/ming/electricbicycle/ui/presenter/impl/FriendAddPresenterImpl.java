package com.wxxiaomi.ming.electricbicycle.ui.presenter.impl;

import android.app.ProgressDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wxxiaomi.ming.common.net.ApiException;
import com.wxxiaomi.ming.electricbicycle.EBApplication;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserLocatInfo;

import com.wxxiaomi.ming.common.weight.DialogHelper;
import com.wxxiaomi.ming.electricbicycle.manager.AccountHelper;
import com.wxxiaomi.ming.electricbicycle.manager.UserFunctionProvider;
import com.wxxiaomi.ming.electricbicycle.support.rx.MyObserver;
import com.wxxiaomi.ming.electricbicycle.ui.activity.HomeActivity;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.base.BasePreImpl;

import com.wxxiaomi.ming.electricbicycle.ui.presenter.FriendAddPresenter;

import com.wxxiaomi.ming.electricbicycle.ui.activity.view.FriendAddView;
import com.wxxiaomi.ming.electricbicycle.manager.LocatProvider;
import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter2.NearUserAdapter;
import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter2.UserSearchRsultAdapter1;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pulltorefresh.ViewProvider;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pulltorefresh.recycleview.PullToRefreshRecyclerView;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;


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
        mAdapter.setEmptyView(ViewProvider.makeNoOneView(mView.getContext()
                , (ViewGroup) listView.getParent()
                , new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mView.runActivity(HomeActivity.class,null,true);
                    }
                }));
        mAdapter.setReloadView(ViewProvider.makeNetWorkErrorView(mView.getContext(), (ViewGroup) listView.getParent(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.setLoading();
                getNearFriend();
            }
        }));
        mAdapter.onAttachedToRecyclerView(listView.getRecyclerView());
        listView.setAdapter(mAdapter);
    }


    private void getNearFriend() {
//        new Action1<List<UserLocatInfo>>() {
//            @Override
//            public void call(List<UserLocatInfo> nearByPerson) {
//
//            }
//        };

        UserFunctionProvider.getInstance().getNearPeople(AccountHelper.getAccountInfo().id
                , LocatProvider.getInstance().getLatitude()
                , LocatProvider.getInstance().getLongitude())
                .subscribe(new MyObserver<List<UserLocatInfo>>() {
                    @Override
                    protected void onError(ApiException ex) {
                        EBApplication.showToast(ex.getDisplayMessage());
                        mAdapter.setLoadFail();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(List<UserLocatInfo> userLocatInfos) {
                        mAdapter.setNewData(userLocatInfos);
                    }
                });
    }

    @Override
    public void onFindClick(String name) {
        final UserSearchRsultAdapter1 adapter1 = new UserSearchRsultAdapter1(mView.getContext(),null, true,listView);
        adapter1.setEmptyView(ViewProvider.makeNetWorkErrorView(mView.getContext()
                , (ViewGroup) listView.getParent()
                ,"没有此用户"
                , new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }));
        adapter1.setReloadView(ViewProvider.makeNetWorkErrorView(mView.getContext()
                , (ViewGroup) listView.getParent()
                ,"查找好友失败"
                , new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }));
        listView.removeHeader();
        listView.getRecyclerView().removeAllViews();
        mView.getListView().setAdapter(adapter1);
        final ProgressDialog progress = DialogHelper.getProgressDialog(mView.getContext(), "正在查找好友", false);
        UserFunctionProvider.getInstance().getUserByNameFWeb(name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<List<UserCommonInfo>>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        progress.show();
                    }

                    @Override
                    protected void onError(ApiException ex) {
                        progress.dismiss();
                        EBApplication.showToast(ex.getDisplayMessage());
                        isNear = false;
                        adapter1.setLoadFail();
                    }

                    @Override
                    public void onCompleted() {
                        progress.dismiss();
                    }

                    @Override
                    public void onNext(List<UserCommonInfo> userCommonInfos) {
                        progress.dismiss();
                        isNear = false;
                        adapter1.setNewData(userCommonInfos);
                    }
                });
//                .subscribe(new ProgressObserver<List<UserCommonInfo>>(mView.getContext()) {
//                    @Override
//                    public void onNext(List<UserCommonInfo> initUserInfo) {
//                            isNear = false;
//                             adapter1.setNewData(initUserInfo);
//                    }
//                });
    }

    @Override
    public void onTextChange(String newText) {
        if(!isNear){
            isNear = !isNear;
            if(!"".equals(newText)){
                isAddHeader = !isAddHeader;
            }
            Log.i("wang","切换adpter");
            listView.getRecyclerView().removeAllViews();
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
