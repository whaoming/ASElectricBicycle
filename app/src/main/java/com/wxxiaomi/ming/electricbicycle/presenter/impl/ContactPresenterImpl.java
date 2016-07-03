package com.wxxiaomi.ming.electricbicycle.presenter.impl;

import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.bean.InviteMessage;
import com.wxxiaomi.ming.electricbicycle.bean.User;
import com.wxxiaomi.ming.electricbicycle.model.impl.EmEngine;
import com.wxxiaomi.ming.electricbicycle.presenter.base.BasePresenterImpl;
import com.wxxiaomi.ming.electricbicycle.presenter.callback.ContactPresenter;
import com.wxxiaomi.ming.electricbicycle.presenter.base.BasePresenter;
import com.wxxiaomi.ming.electricbicycle.ui.activity.FriendAddActivity1;
import com.wxxiaomi.ming.electricbicycle.ui.view.ContactView;
import com.wxxiaomi.ming.electricbicycle.view.activity.FriendAddActivity;
import com.wxxiaomi.ming.electricbicycle.view.adapter.NewFriendAddItemAdapter;
import com.wxxiaomi.ming.electricbicycle.view.em.EmManager;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by 12262 on 2016/6/9.
 */
public class ContactPresenterImpl extends BasePresenterImpl<ContactView> implements ContactPresenter<ContactView> {

    private NewFriendAddItemAdapter adapter;

    @Override
    public void attach(ContactView mView) {
        super.attach(mView);
        initDrawerData();
    }

    @Override
    public void initDrawerData() {
        EmEngine.getInstance().getUnreadInviteCountTotal()
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer count) {
                        mView.updateUnReadMsg(count);
                    }
                });
        List<InviteMessage> inviteMsgList = EmManager.getInstance().getInviteMsgList();
        Log.i("wang","inviteMsgList.size()="+inviteMsgList.size());
        adapter = new NewFriendAddItemAdapter(mView.getContext(), inviteMsgList
                , new NewFriendAddItemAdapter.ItemAddOnClick() {
            @Override
            public void onClick(User.UserCommonInfo userInfo) {
                // 添加某个好友
                addFriend(userInfo);
            }


        });
        mView.setInviteListAdapter(adapter);
    }

    private void addFriend(User.UserCommonInfo userInfo) {
    }

    @Override
    public void onResume() {
        EmEngine.getInstance().setFriendMsgLis(new EmEngine.FriendMessageListener() {
            @Override
            public void FriendMsgReceive() {
                mView.refershChildUI();
            }
        });
        EmEngine.getInstance().setInviteMsgLis(new EmEngine.InviteMessageListener() {
            @Override
            public void InviteMsgReceive() {
                EmEngine.getInstance().getUnreadInviteCountTotal()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                Log.i("wang","contactPresenter4645464");
                                mView.updateUnReadMsg(integer);
                            }
                        });
            }
        });
    }


    @Override
    public void onAddFriendBtnClick() {
        mView.runActivity(FriendAddActivity1.class,null);
    }


}
