package com.wxxiaomi.ming.electricbicycle.ui.presenter.impl;


import com.wxxiaomi.ming.electricbicycle.manager.AccountHelper;
import com.wxxiaomi.ming.electricbicycle.manager.UserFunctionProvider;
import com.wxxiaomi.ming.electricbicycle.common.rx.ProgressObserver;
import com.wxxiaomi.ming.electricbicycle.db.bean.Option;
import com.wxxiaomi.ming.electricbicycle.manager.ShowerProvider;
import com.wxxiaomi.ming.electricbicycle.common.photoget.PhotoTakeUtil;
import com.wxxiaomi.ming.electricbicycle.ui.activity.SettingActivity;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.base.BasePreImpl;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.PersonalPresenter;
import com.wxxiaomi.ming.electricbicycle.ui.activity.view.PersonaView;
import com.wxxiaomi.ming.electricbicycle.ui.activity.MyInfoEditActivity;
import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter.OptionAdapter2;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pulltorefresh.recycleview.PullToRefreshRecyclerView;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by 12262 on 2016/11/1.
 */

public class PersonalPreImpl extends BasePreImpl<PersonaView> implements PersonalPresenter<PersonaView>{
    private PullToRefreshRecyclerView listView;
    private PhotoTakeUtil util;

    @Override
    public void init() {
        listView = mView.getListView();
        listView.setRefreshing(true);
        String cover = AccountHelper.getAccountInfo().cover;
        if(cover!=null){
            ShowerProvider.showNormalImage(mView.getContext(), mView.getBackImgContent(),cover);
        }
//        mView.getBackImgContent().setBackground();
        requestOptionData();
    }

    @Override
    public void onViewResume() {
        super.onViewResume();
        ShowerProvider.showHead(mView.getContext(), mView.getHeadView(), AccountHelper.getAccountInfo().avatar);
        mView.setViewData(AccountHelper.getAccountInfo());
    }

    private void requestOptionData() {
        UserFunctionProvider.getInstance().getUserOptions(AccountHelper.getAccountInfo().id)
                .subscribe(new Action1<List<Option>>() {
                    @Override
                    public void call(List<Option> options) {
                        OptionAdapter2 adapter = new OptionAdapter2(options,mView.getContext());
                        listView.setAdapter(adapter);
                        listView.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onHeadBrnClick() {
    }

    @Override
    public void onBackImgClick() {
        util = new PhotoTakeUtil(mView.getContext());
        util.takePhotoCut()
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> strings) {
                        UserFunctionProvider.getInstance().upLoadImgToOss(strings.get(0))
                                .flatMap(new Func1<String, Observable<String>>() {
                                    @Override
                                    public Observable<String> call(String s) {
                                        return UserFunctionProvider.getInstance().upLoadUserCover(s);
                                    }
                                })
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new ProgressObserver<String>(mView.getContext()) {
                                    @Override
                                    public void onNext(String s) {
                                        closeDialog();
                                        ShowerProvider.showNormalImage(mView.getContext(),mView.getBackImgContent(),s);
                                    }
                                });
                    }
                });


        /**
         * new Action1<String>() {
        @Override
        public void call(String s) {
        ShowerProvider.showNormalImage(mView.getContext(),mView.getBackImgContent(),s);
        }
        }
         */
    }

    @Override
    public void onSettingClick() {
        mView.runActivity(SettingActivity.class,null,false);
    }

    @Override
    public void onEditClick() {
        mView.runActivity(MyInfoEditActivity.class,null,false);
    }


}
