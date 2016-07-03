package com.wxxiaomi.ming.electricbicycle.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.bean.User;
import com.wxxiaomi.ming.electricbicycle.presenter.callback.UserInfoPresenter;
import com.wxxiaomi.ming.electricbicycle.presenter.impl.UserInfoPresenterImpl;
import com.wxxiaomi.ming.electricbicycle.ui.base.BaseMvpActivity;
import com.wxxiaomi.ming.electricbicycle.ui.view.UserInfoView;

/**
 * Created by 12262 on 2016/7/3.
 */
public class UserInfoAct extends BaseMvpActivity<UserInfoView,UserInfoPresenter<UserInfoView>> implements UserInfoView {

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
    protected UserInfoPresenter<UserInfoView> initPre() {
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
    public void setViewData(User.UserCommonInfo info) {
        toolbar.setTitle(info.name);
    }

    @Override
    public void setBtnView(Drawable drawable) {
        btn_add.setImageDrawable(drawable);
    }
}
