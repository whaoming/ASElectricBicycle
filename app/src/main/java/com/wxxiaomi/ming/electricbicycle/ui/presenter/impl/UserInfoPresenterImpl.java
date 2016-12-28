package com.wxxiaomi.ming.electricbicycle.ui.presenter.impl;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.db.bean.Option;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.service.FunctionProvider;
import com.wxxiaomi.ming.electricbicycle.bridge.easemob.EmHelper;
import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter.OptionAdapter2;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.base.BasePreImpl;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.UserInfoPresenter;
import com.wxxiaomi.ming.electricbicycle.support.rx.SampleProgressObserver;
import com.wxxiaomi.ming.electricbicycle.ui.activity.view.UserInfoView;

import com.wxxiaomi.ming.electricbicycle.ui.weight.custom.EditableDialog;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by 12262 on 2016/7/2.
 */
public class UserInfoPresenterImpl extends BasePreImpl<UserInfoView> implements UserInfoPresenter<UserInfoView> {
    private UserCommonInfo userInfo;
    private boolean isMyFriendFlag = false;
    private EditableDialog dialog;

    @Override
    public void onAddBtnClick() {
        Log.i("wang","添加好友按钮");
        if(isMyFriendFlag){
            Bundle bundle = new Bundle();
            bundle.putString("userId",userInfo.emname);
//            mView.runActivity(ChatActivity.class,bundle,false);
        }else{

            /**
             * 添加好友的逻辑：
             * 点击添加好友，先从服务器
             */
          
            dialog.show();

        }
    }

    @Override
    public void init() {
        Intent intent = mView.getIntent();
        Bundle bundle = intent.getBundleExtra("value");
        if(bundle==null){
            String value = intent.getStringExtra("value");
            userInfo = new Gson().fromJson(value,UserCommonInfo.class);
        } else {
            userInfo = (UserCommonInfo) bundle.get("userInfo");
        }
//        isMyFriendFlag = FunctionProvider.getInstance().isMyFriend(userInfo.emname);
        if (isMyFriendFlag)
            mView.setBtnView(mView.getContext().getResources().getDrawable(R.mipmap.ic_mode_edit_black_18dp));
        else
            mView.setBtnView(mView.getContext().getResources().getDrawable(R.mipmap.ic_common_add_press));
        mView.setViewData(userInfo);
//        HttpMethods.getInstance().optionLogs(25)
//                .subscribe(new Action1<List<OptionLogs>>() {
//                    @Override
//                    public void call(List<OptionLogs> optionLogses) {
//                        OptionAdapter adapter = new OptionAdapter(optionLogses,mView.getContext());
//                        mView.setAdapter(adapter);
//                    }
//                });
        FunctionProvider.getInstance().getUserOptions(25)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SampleProgressObserver<List<Option>>(mView.getContext()) {
                    @Override
                    public void onNext(List<Option> options) {
                        OptionAdapter2 adapter = new OptionAdapter2(options,mView.getContext());
                        mView.setAdapter(adapter);
                    }
                });
//                .subscribe(new Action1<List<Option>>() {
//                    @Override
//                    public void call(List<Option> options) {
//                        OptionAdapter2 adapter = new OptionAdapter2(options,mView.getContext());
//                        mView.setAdapter(adapter);
//                    }
//                });

       dialog = new EditableDialog(mView.getContext()).builder()
                .setHint("理由")
                .setTitle("添加好友")
                .setOnPositiveButtonClick(new EditableDialog.PositiveButtonOnClick() {
                    @Override
                    public void onClick(DialogInterface dialog, String content) {
                        EmHelper.getInstance().addContact(userInfo.emname,content)
                                .subscribe(new SampleProgressObserver<Boolean>(mView.getContext()) {
                                    @Override
                                    public void onNext(Boolean aBoolean) {
                                        showMsg("成功添加好友");
                                    }
                                });
                    }
                })
                ;
    }


}
