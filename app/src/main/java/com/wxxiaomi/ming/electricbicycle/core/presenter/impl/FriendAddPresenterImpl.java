package com.wxxiaomi.ming.electricbicycle.core.presenter.impl;

import android.os.Bundle;

import com.wxxiaomi.ming.electricbicycle.GlobalParams;
import com.wxxiaomi.ming.electricbicycle.bean.format.InitUserInfo;
import com.wxxiaomi.ming.electricbicycle.bean.format.NearByPerson;
import com.wxxiaomi.ming.electricbicycle.core.base.BasePreImpl;

import com.wxxiaomi.ming.electricbicycle.core.presenter.FriendAddPresenter;
import com.wxxiaomi.ming.electricbicycle.dao.UserService;
import com.wxxiaomi.ming.electricbicycle.support.GlobalManager;
import com.wxxiaomi.ming.electricbicycle.support.rx.SampleProgressObserver;
import com.wxxiaomi.ming.electricbicycle.core.ui.activity.UserInfoAct;
import com.wxxiaomi.ming.electricbicycle.core.ui.FriendAddView;
import com.wxxiaomi.ming.electricbicycle.core.weight.adapter.NearFriendRecommendAdapter1;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;


/**
 * Created by 12262 on 2016/6/15.
 */
public class FriendAddPresenterImpl extends BasePreImpl<FriendAddView> implements FriendAddPresenter<FriendAddView> {

    private NearFriendRecommendAdapter1 adapter ;
    private List<NearByPerson.UserLocatInfo> tempNearUserList;

    @Override
    public void init() {
        tempNearUserList = new ArrayList<NearByPerson.UserLocatInfo>();
        adapter = new NearFriendRecommendAdapter1(mView.getContext(),tempNearUserList);
        mView.setListAdaper(adapter);
        getNearFriend();
    }


    private void getNearFriend() {

        UserService.getInstance().getNearPeople(GlobalManager.getInstance().getUser().id,GlobalParams.latitude, GlobalParams.longitude)
                .subscribe(new Action1<NearByPerson>() {
                    @Override
                    public void call(NearByPerson nearByPerson) {
                        tempNearUserList.addAll(nearByPerson.userLocatList);
                        adapter.setLoadingComplete();
                    }
                });
    }

    @Override
    public void onFindClick(String name) {
        UserService.getInstance().getUserByNameFWeb(name)
                .subscribe(new SampleProgressObserver<InitUserInfo>(mView.getContext()) {
                    @Override
                    public void onNext(InitUserInfo initUserInfo) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userInfo", initUserInfo.friendList.get(0));
                        mView.runActivity(UserInfoAct.class,bundle,false);
                    }
                });
    }
}
