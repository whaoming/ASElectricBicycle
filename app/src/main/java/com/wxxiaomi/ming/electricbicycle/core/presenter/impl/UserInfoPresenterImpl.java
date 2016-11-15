package com.wxxiaomi.ming.electricbicycle.core.presenter.impl;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.bean.User;
import com.wxxiaomi.ming.electricbicycle.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.core.base.BasePreImpl;
import com.wxxiaomi.ming.electricbicycle.dao.impl.UserDaoImpl2;
import com.wxxiaomi.ming.electricbicycle.model.impl.EmEngine;
import com.wxxiaomi.ming.electricbicycle.core.presenter.UserInfoPresenter;
import com.wxxiaomi.ming.electricbicycle.support.rx.SampleProgressObserver;
import com.wxxiaomi.ming.electricbicycle.core.ui.activity.ChatActivity;
import com.wxxiaomi.ming.electricbicycle.core.ui.UserInfoView;

import com.wxxiaomi.ming.electricbicycle.core.weight.custom.EditableDialog;

/**
 * Created by 12262 on 2016/7/2.
 */
public class UserInfoPresenterImpl extends BasePreImpl<UserInfoView> implements UserInfoPresenter<UserInfoView> {
    private UserCommonInfo userInfo;
    private boolean isMyFriendFlag;

    @Override
    public void onAddBtnClick() {
        if(isMyFriendFlag){
            Bundle bundle = new Bundle();
            bundle.putString("userId",userInfo.emname);
            mView.runActivity(ChatActivity.class,bundle,false);
        }else{
            /**
             * 添加好友的逻辑：
             * 点击添加好友，先从服务器
             */
            EditableDialog dialog = new EditableDialog(mView.getContext()).builder()
                    .setHint("理由")
                    .setTitle("添加好友")
                    .setOnPositiveButtonClick(new EditableDialog.PositiveButtonOnClick() {
                        @Override
                        public void onClick(DialogInterface dialog, String content) {
                            EmEngine.getInstance().addContact(userInfo.emname,content)
                                    .subscribe(new SampleProgressObserver<Boolean>(mView.getContext()) {
                                        @Override
                                        public void onNext(Boolean aBoolean) {
                                            showMsg("成功添加好友");
                                        }
                                    });
                        }
                    })
                    ;
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
        isMyFriendFlag = UserDaoImpl2.getInstance().isMyFriend(userInfo.emname);
        if (isMyFriendFlag)
            mView.setBtnView(mView.getContext().getResources().getDrawable(R.mipmap.ic_mode_edit_black_18dp));
        else
            mView.setBtnView(mView.getContext().getResources().getDrawable(R.mipmap.ic_common_add_press));
        mView.setViewData(userInfo);
    }


}
