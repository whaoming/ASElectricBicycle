package com.wxxiaomi.ming.electricbicycle.ui.presenter.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.dao.bean.InviteMessage;
import com.wxxiaomi.ming.electricbicycle.support.easemob.EmConstant;
import com.wxxiaomi.ming.electricbicycle.support.easemob.EmHelper2;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.base.BasePreImpl;

import com.wxxiaomi.ming.electricbicycle.support.easemob.ui.ChatActivity;
import com.wxxiaomi.ming.electricbicycle.support.easemob.ui.Constant;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.ContactPresenter;
import com.wxxiaomi.ming.electricbicycle.ui.activity.FriendAddActivity;
import com.wxxiaomi.ming.electricbicycle.ui.activity.view.ContactView;
import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter.NewFriendAddItemAdapter;
import com.wxxiaomi.ming.electricbicycle.dao.db.impl.InviteMessgeDaoImpl;
import com.wxxiaomi.ming.electricbicycle.support.easemob.EmEngine;
import com.wxxiaomi.ming.electricbicycle.support.easemob.listener.FriendMessageListener;
import com.wxxiaomi.ming.electricbicycle.support.easemob.listener.InviteMessageListener;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by 12262 on 2016/6/9.
 * 联系人界面
 */
public class ContactPresenterImpl extends BasePreImpl<ContactView> implements ContactPresenter<ContactView> {

    private NewFriendAddItemAdapter adapter;
    private LocalBroadcastManager broadcastManager;
    private BroadcastReceiver broadcastReceiver;

    @Override
    public void init() {
        initDrawerData();
        registerBroadcastReceiver();
    }

    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(mView.getContext());
        IntentFilter intentFilter = new IntentFilter();
        //联系人事件
        intentFilter.addAction(EmConstant.ACTION_CONTACT_CHANAGED);
        intentFilter.addAction(EmConstant.ACTION_GROUP_CHANAGED);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
//                updateUnreadLabel();
                Log.i("wang"," 通过广播接受者收什么鬼信息了");
            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void initDrawerData() {
        refreshInviteUI();
        List<InviteMessage> inviteMsgList = EmHelper2.getInstance().getInviteMsgList();
        adapter = new NewFriendAddItemAdapter(mView.getContext(), inviteMsgList
                , new NewFriendAddItemAdapter.ItemAddOnClick() {
            @Override
            public void onClick(String userEmName) {
                // 添加某个好友
                addFriend(userEmName);
            }


        });
        mView.setInviteListAdapter(adapter);
    }

    private void addFriend(final String emname) {
        EmHelper2.getInstance().agreeInvite(emname)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        Log.i("wang","添加好友成功");
                        Intent intent = new Intent(mView.getContext(), ChatActivity.class);
                        intent.putExtra(Constant.EXTRA_USER_ID, emname);
                        intent.putExtra("isAdd",true);
                        mView.getContext().startActivity(intent);
                        mView.refershChildUI();
                    }
                });
    }

    @Override
    public void onViewResume() {
        //设置好友发来的消息监听
        EmEngine.getInstance().setFriendMsgLis(new FriendMessageListener() {
            @Override
            public void FriendMsgReceive() {
                mView.refershChildUI();
            }
        });
        //设置邀请消息的监听
//        EmEngine.getInstance().setInviteMsgLis(new InviteMessageListener() {
//            @Override
//            public void InviteMsgReceive() {
//                adapter.refersh();
//                refreshInviteUI();
//            }
//        })

        ;
    }

    public void refreshInviteUI(){
        EmEngine.getInstance().getUnreadInviteCountTotal()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mView.updateUnReadMsg(integer);
                    }
                });
    }


    @Override
    public void onAddFriendBtnClick() {
        mView.runActivity(FriendAddActivity.class,null);
    }

    @Override
    public void onDrawClick() {
        InviteMessgeDaoImpl.getInstance().saveUnreadMessageCount(0)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mView.updateUnReadMsg(0);
                    }
                });
    }


}
