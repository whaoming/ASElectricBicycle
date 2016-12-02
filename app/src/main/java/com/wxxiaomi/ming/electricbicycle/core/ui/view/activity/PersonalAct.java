package com.wxxiaomi.ming.electricbicycle.core.ui.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

//import com.jph.takephoto.permission.PermissionManager;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.core.ui.base.BaseActivity;

import com.wxxiaomi.ming.electricbicycle.core.ui.presenter.PersonalPresenter;
//import com.wxxiaomi.ming.electricbicycle.core.ui.presenter.impl.PersonalPreImpl;
import com.wxxiaomi.ming.electricbicycle.core.ui.presenter.impl.PersonalPreImpl;
import com.wxxiaomi.ming.electricbicycle.core.ui.view.PersonaView;
import com.wxxiaomi.ming.electricbicycle.core.weight.adapter.IndexFragmentTabAdapter;
import com.wxxiaomi.ming.electricbicycle.core.weight.custom.CircularImageView;
import com.wxxiaomi.ming.electricbicycle.core.ui.fragment.MyCarPageFragment;
import com.wxxiaomi.ming.electricbicycle.core.ui.fragment.SettingPageFragment;
import com.wxxiaomi.ming.electricbicycle.core.ui.fragment.UserInfoPageFragment;
import com.wxxiaomi.ming.electricbicycle.core.ui.fragment.base.BaseFragment;
import com.wxxiaomi.ming.electricbicycle.core.ui.fragment.base.FragmentCallback;
import com.wxxiaomi.ming.electricbicycle.common.GlobalManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 12262 on 2016/6/21.
 * 个人页面
 */
public class PersonalAct extends BaseActivity<PersonaView, PersonalPresenter> implements FragmentCallback, PersonaView<PersonalPresenter> {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentStatePagerAdapter fAdapter;
    private List<Fragment> list_fragment;
    private List<String> list_title;
    private CircularImageView iv_my_head;

    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter.onCreate(savedInstanceState,this);
        setContentView(R.layout.activity_personal6);
        tabLayout = (TabLayout) findViewById(R.id.tab_FindFragment_title);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager = (ViewPager) findViewById(R.id.vp_FindFragment_pager);
        toolbar = (Toolbar) this.findViewById(R.id.toolbar1);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setTitle(GlobalManager.getInstance().getUser().userCommonInfo.name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_fragment = new ArrayList<Fragment>();
        list_fragment.add(new UserInfoPageFragment());
        list_fragment.add(new MyCarPageFragment());
        list_fragment.add(new SettingPageFragment());
        list_title = new ArrayList<String>();
        list_title.add("个人主页");
        list_title.add("我的车子");
        list_title.add("设置");
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(2)));
        fAdapter = new IndexFragmentTabAdapter(getSupportFragmentManager(),
                list_fragment, list_title);
        viewPager.setAdapter(fAdapter);
        viewPager.requestDisallowInterceptTouchEvent(true);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        iv_my_head = (CircularImageView) findViewById(R.id.iv_my_head);
        iv_my_head.setOnClickListener(this);
    }


    @Override
    public PersonalPresenter getPresenter() {
        return new PersonalPreImpl();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_my_head:
                presenter.onHeadBrnClick();
                break;
        }
    }

    @Override
    public void onFragmentCallback(BaseFragment fragment, int id, Bundle args) {

    }

    @Override
    public void updateHeadView() {

    }

    @Override
    public ImageView getHeadView() {
        return iv_my_head;
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        presenter.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        presenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
