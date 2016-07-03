package com.wxxiaomi.ming.electricbicycle.presenter.impl;

import android.os.Bundle;

import com.wxxiaomi.ming.electricbicycle.GlobalParams;
import com.wxxiaomi.ming.electricbicycle.api.exception.ApiException;
import com.wxxiaomi.ming.electricbicycle.bean.format.InitUserInfo;
import com.wxxiaomi.ming.electricbicycle.bean.format.NearByPerson;
import com.wxxiaomi.ming.electricbicycle.dao.impl.UserDaoImpl2;
import com.wxxiaomi.ming.electricbicycle.presenter.base.BasePresenterImpl;
import com.wxxiaomi.ming.electricbicycle.presenter.callback.FriendAddPresenter;
import com.wxxiaomi.ming.electricbicycle.support.rx.SampleProgressObserver;
import com.wxxiaomi.ming.electricbicycle.ui.view.FriendAddView;
import com.wxxiaomi.ming.electricbicycle.view.activity.UserInfoActivity;
import com.wxxiaomi.ming.electricbicycle.view.adapter.NearFriendRecommendAdapter1;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 12262 on 2016/6/15.
 */
public class FriendAddPresenterImpl extends BasePresenterImpl<FriendAddView> implements FriendAddPresenter<FriendAddView> {

    private NearFriendRecommendAdapter1 adapter ;
    private List<NearByPerson.UserLocatInfo> tempNearUserList;

    @Override
    public void attach(FriendAddView mView) {
        super.attach(mView);
        tempNearUserList = new ArrayList<NearByPerson.UserLocatInfo>();
        adapter = new NearFriendRecommendAdapter1(mView.getContext(),tempNearUserList);
        mView.setListAdaper(adapter);
        getNearFriend();
    }

    private void getNearFriend() {
        UserDaoImpl2.getInstance().getNearPeople(GlobalParams.user.id,GlobalParams.latitude, GlobalParams.longitude)
                .subscribe(new SampleProgressObserver<NearByPerson>(mView.getContext()) {
                    @Override
                    public void onNext(NearByPerson nearByPerson) {
                        tempNearUserList.addAll(nearByPerson.userLocatList);
                        adapter.setLoadingComplete();
                    }
                });
    }

    @Override
    public void onFindClick(String name) {
        UserDaoImpl2.getInstance().getUserCommonInfoByName(name)
                .subscribe(new SampleProgressObserver<InitUserInfo>(mView.getContext()) {
                    @Override
                    public void onNext(InitUserInfo initUserInfo) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userInfo", initUserInfo.friendList.get(0));
                        mView.runActivity(UserInfoActivity.class,bundle,false);
                    }
                });
    }
}
