package com.wxxiaomi.ming.electricbicycle.core.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.bean.User;
import com.wxxiaomi.ming.electricbicycle.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.core.base.BaseActivity;
import com.wxxiaomi.ming.electricbicycle.core.presenter.UserInfoPresenter;
import com.wxxiaomi.ming.electricbicycle.core.presenter.impl.UserInfoPresenterImpl;
import com.wxxiaomi.ming.electricbicycle.core.ui.UserInfoView;

/**
 * Created by 12262 on 2016/7/3.
 */
public class UserInfoAct extends BaseActivity<UserInfoView,UserInfoPresenter> implements UserInfoView<UserInfoPresenter>{

    private Toolbar toolbar;
    private FloatingActionButton btn_add;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_userinfo);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // 标题的文字需在setSupportActionBar之前，不然会无效
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); // 设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_add = (FloatingActionButton) findViewById(R.id.btn_add);

        btn_add.setOnClickListener(this);
    }

    @Override
    public UserInfoPresenter getPresenter() {
        return new UserInfoPresenterImpl();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_add:
                ///添加好友按钮
                presenter.onAddBtnClick();
                break;
        }
    }

    @Override
    public void setViewData(UserCommonInfo info) {
        toolbar.setTitle(info.name);
    }

    @Override
    public void setBtnView(Drawable drawable) {
        btn_add.setImageDrawable(drawable);
    }
}
