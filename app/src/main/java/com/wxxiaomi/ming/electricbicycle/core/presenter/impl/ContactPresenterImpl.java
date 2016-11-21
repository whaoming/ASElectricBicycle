package com.wxxiaomi.ming.electricbicycle.core.presenter.impl;

import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.bean.InviteMessage;
import com.wxxiaomi.ming.electricbicycle.bean.User;
import com.wxxiaomi.ming.electricbicycle.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.core.base.BasePreImpl;
import com.wxxiaomi.ming.electricbicycle.model.impl.EmEngine;

import com.wxxiaomi.ming.electricbicycle.core.presenter.ContactPresenter;
import com.wxxiaomi.ming.electricbicycle.core.ui.activity.FriendAddActivity;
import com.wxxiaomi.ming.electricbicycle.core.ui.ContactView;
import com.wxxiaomi.ming.electricbicycle.core.weight.adapter.NewFriendAddItemAdapter;
import com.wxxiaomi.ming.electricbicycle.core.weight.em.EmManager;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by 12262 on 2016/6/9.
 */
public class ContactPresenterImpl extends BasePreImpl<ContactView> implements ContactPresenter<ContactView> {

    private NewFriendAddItemAdapter adapter;

    @Override
    public void init() {

    }

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
            public void onClick(UserCommonInfo userInfo) {
                // 添加某个好友
                addFriend(userInfo);
            }


        });
        mView.setInviteListAdapter(adapter);
    }

    private void addFriend(UserCommonInfo userInfo) {
    }

    @Override
    public void onViewResume() {
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
        mView.runActivity(FriendAddActivity.class,null);
    }


}
