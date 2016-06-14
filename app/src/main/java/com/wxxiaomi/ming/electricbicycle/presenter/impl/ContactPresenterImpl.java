package com.wxxiaomi.ming.electricbicycle.presenter.impl;

import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.api.exception.ApiException;
import com.wxxiaomi.ming.electricbicycle.bean.InviteMessage;
import com.wxxiaomi.ming.electricbicycle.bean.User;
import com.wxxiaomi.ming.electricbicycle.model.impl.RxEmModelImpl2;
import com.wxxiaomi.ming.electricbicycle.model.impl.UserModelImpl;
import com.wxxiaomi.ming.electricbicycle.presenter.ContactPresenter;
import com.wxxiaomi.ming.electricbicycle.presenter.base.BasePresenter;
import com.wxxiaomi.ming.electricbicycle.support.rx.MyObserver;
import com.wxxiaomi.ming.electricbicycle.ui.view.ContactView;
import com.wxxiaomi.ming.electricbicycle.view.activity.FriendAddActivity;
import com.wxxiaomi.ming.electricbicycle.view.adapter.NewFriendAddItemAdapter;
import com.wxxiaomi.ming.electricbicycle.view.em.EmManager;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by 12262 on 2016/6/9.
 */
public class ContactPresenterImpl extends BasePresenter<ContactView,UserModelImpl> implements ContactPresenter {

    private NewFriendAddItemAdapter adapter;
    public ContactPresenterImpl(ContactView contactView) {
        super(contactView);
    }

    @Override
    public void initDrawerData() {
        RxEmModelImpl2.getInstance().getUnreadInviteCountTotal()
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer count) {
                        view.updateUnReadMsg(count);
                    }
                });
        List<InviteMessage> inviteMsgList = EmManager.getInstance().getInviteMsgList();
        Log.i("wang","inviteMsgList.size()="+inviteMsgList.size());
        adapter = new NewFriendAddItemAdapter(view.getContext(), inviteMsgList
                , new NewFriendAddItemAdapter.ItemAddOnClick() {
            @Override
            public void onClick(User.UserCommonInfo userInfo) {
                // 添加某个好友
                addFriend(userInfo);
            }


        });
        view.setInviteListAdapter(adapter);
    }

    private void addFriend(User.UserCommonInfo userInfo) {
//        RxEmModelImpl2.getInstance().agreeInvite(userInfo)
//                .subscribe(new MyObserver<Integer>() {
//                    @Override
//                    protected void onError(ApiException ex) {
//
//                    }
//
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//
//                    }
//                });
    }

    @Override
    public void onActResume() {
//        RxEmModelImpl.getInstance().handleEMListener()
//                .observeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe(new MyObserver<Integer>() {
//            @Override
//            protected void onError(ApiException ex) {
//
//            }
//
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                Log.i("wang","在contactAct中收到消息，数量："+integer);
//                view.updateUnReadMsg(integer);
//
//            }
//        });
        RxEmModelImpl2.getInstance().setFriendMsgLis(new RxEmModelImpl2.FriendMessageListener() {
            @Override
            public void FriendMsgReceive() {
                view.refershChildUI();
            }
        });
        RxEmModelImpl2.getInstance().setInviteMsgLis(new RxEmModelImpl2.InviteMessageListener() {
            @Override
            public void InviteMsgReceive() {
//                view.updateUnReadMsg(InviteMessgeDaoImpl2.getInstance().getUnreadNotifyCount());
                RxEmModelImpl2.getInstance().getUnreadInviteCountTotal()
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                view.updateUnReadMsg(integer);
                            }
                        });
            }
        });
    }

    @Override
    public void onAddFriendBtnClick() {
        view.runActivity(FriendAddActivity.class,null);
    }

    @Override
    public void onViewCreate() {

    }

    @Override
    public void onViewDestory() {

    }
}
