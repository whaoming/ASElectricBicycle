package com.wxxiaomi.ming.electricbicycle.ui.presenter.impl;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.wxxiaomi.ming.electricbicycle.common.GlobalManager;
import com.wxxiaomi.ming.electricbicycle.dao.bean.Option;
import com.wxxiaomi.ming.electricbicycle.dao.db.UserService;
import com.wxxiaomi.ming.electricbicycle.support.myglide.ImgShower;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.base.BasePreImpl;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.PersonalPresenter;
import com.wxxiaomi.ming.electricbicycle.ui.activity.view.PersonaView;
import com.wxxiaomi.ming.electricbicycle.ui.activity.MyInfoEditActivity;
import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter.OptionAdapter2;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by 12262 on 2016/11/1.
 */

public class PersonalPreImpl extends BasePreImpl<PersonaView> implements PersonalPresenter<PersonaView>{
    RecyclerView listView;

    @Override
    public void init() {
//        Log.i("wang","head:"+GlobalManager.getInstance().getUser().userCommonInfo.head);
        ImgShower.showHead(mView.getContext(), mView.getHeadView(), GlobalManager.getInstance().getUser().userCommonInfo.head);
        mView.setViewData(GlobalManager.getInstance().getUser().userCommonInfo);
        listView = mView.getListView();
//        listView.setOnListLoadListener(this);
//        listView.setOnRefreshListener(this);
//        listView.setEmptyText("数据又没有了!");
        requestOptionData();
    }

    private void requestOptionData() {
        UserService.getInstance().getUserOptions(GlobalManager.getInstance().getUser().userCommonInfo.id)
                .subscribe(new Action1<List<Option>>() {
                    @Override
                    public void call(List<Option> options) {
                        OptionAdapter2 adapter = new OptionAdapter2(options,mView.getContext());
                        Log.i("wang","asdasd");
                        listView.setAdapter(adapter);
                    }
                });
    }

    @Override
    public void onHeadBrnClick() {
    }

    @Override
    public void onSettingClick() {

    }

    @Override
    public void onEditClick() {
        mView.runActivity(MyInfoEditActivity.class,null,false);
    }


}
