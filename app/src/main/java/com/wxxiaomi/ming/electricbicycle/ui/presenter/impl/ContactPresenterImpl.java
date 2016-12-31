package com.wxxiaomi.ming.electricbicycle.ui.presenter.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.bridge.easemob.ImHelper;
import com.wxxiaomi.ming.electricbicycle.bridge.easemob.common.EmConstant;
import com.wxxiaomi.ming.electricbicycle.ui.activity.InviteMsgActivity;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.base.BasePreImpl;

import com.wxxiaomi.ming.electricbicycle.bridge.easemob.ui.ChatActivity;
import com.wxxiaomi.ming.electricbicycle.bridge.easemob.common.Constant;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.ContactPresenter;
import com.wxxiaomi.ming.electricbicycle.ui.activity.FriendAddActivity;
import com.wxxiaomi.ming.electricbicycle.ui.activity.view.ContactView;
import com.wxxiaomi.ming.electricbicycle.db.impl.InviteMessgeDaoImpl;

import rx.Observer;
import rx.functions.Action1;

/**
 * Created by 12262 on 2016/6/9.
 * 联系人界面
 */
public class ContactPresenterImpl extends BasePreImpl<ContactView> implements ContactPresenter<ContactView> {

//    private NewFriendAddItemAdapter adapter;
    private LocalBroadcastManager broadcastManager;
    private BroadcastReceiver broadcastReceiver;

    @Override
    public void init() {
//        initDrawerData();
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
                Log.i("wang", " 通过广播接受者收什么鬼信息了");
//                mView.refershChildUI();
//                adapter.refersh();
                refreshInviteUI();
            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void initDrawerData() {
        //修改成rx模式，切换线程，加快页面进入速度
//        ImHelper.getInstance().getInviteMsgListRx()
//                .subscribe(new Action1<List<InviteMessage>>() {
//                    @Override
//                    public void call(List<InviteMessage> inviteMessages) {
//                        adapter = new NewFriendAddItemAdapter(mView.getContext(), inviteMessages
//                                , new NewFriendAddItemAdapter.ItemAddOnClick() {
//                            @Override
//                            public void onClick(String userEmName) {
//                                // 添加某个好友
//                                addFriend(userEmName);
//                            }
//                        });
//                        mView.setInviteListAdapter(adapter);
//                    }
//                });
    }

    private void addFriend(final String emname) {
        ImHelper.getInstance().agreeInvite(emname)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        Log.i("wang", "添加好友成功");
                        Intent intent = new Intent(mView.getContext(), ChatActivity.class);
                        intent.putExtra(Constant.EXTRA_USER_ID, emname);
                        intent.putExtra("isAdd", true);
                        mView.getContext().startActivity(intent);
                        mView.refershChildUI();
                    }
                });
    }

    @Override
    public void onViewResume() {
        refreshInviteUI();
        mView.refershChildUI();
        //设置好友发来的消息监听
        ImHelper.getInstance().setMessageListener(new ImHelper.MyMessageListener() {
            @Override
            public void onMessageReceive() {
                mView.refershChildUI();
            }
        });

    }

    public void refreshInviteUI() {
        ImHelper.getInstance().getUnreadInviteCountTotal().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                mView.updateUnReadMsg(integer);
            }
        });
    }


    @Override
    public void onAddFriendBtnClick() {
        mView.runActivity(FriendAddActivity.class, null);
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

    @Override
    public void refershInviteUI() {
        refreshInviteUI();
    }

    @Override
    public void onInviteBtnClick() {
        mView.runActivity(InviteMsgActivity.class,null,false);
    }

    @Override
    public void onViewDestory() {
//        EmEngine.getInstance().logout();
        unregisterBroadcastReceiver();
    }

    private void unregisterBroadcastReceiver() {
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }


}
