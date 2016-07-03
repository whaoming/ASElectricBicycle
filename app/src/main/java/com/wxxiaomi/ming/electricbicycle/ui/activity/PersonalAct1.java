package com.wxxiaomi.ming.electricbicycle.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wxxiaomi.ming.electricbicycle.GlobalParams;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.presenter.base.BaseTabPresenter;
import com.wxxiaomi.ming.electricbicycle.presenter.impl.BaseTabPresenterImpl;
import com.wxxiaomi.ming.electricbicycle.ui.base.BaseMvpActivity;
import com.wxxiaomi.ming.electricbicycle.ui.view.base.BaseView;
import com.wxxiaomi.ming.electricbicycle.view.adapter.IndexFragmentTabAdapter;
import com.wxxiaomi.ming.electricbicycle.view.fragment.MyCarPageFragment;
import com.wxxiaomi.ming.electricbicycle.view.fragment.SettingPageFragment;
import com.wxxiaomi.ming.electricbicycle.view.fragment.UserInfoPageFragment;
import com.wxxiaomi.ming.electricbicycle.view.fragment.base.BaseFragment;
import com.wxxiaomi.ming.electricbicycle.view.fragment.base.FragmentCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 12262 on 2016/6/21.
 */
public class PersonalAct1 extends BaseMvpActivity<BaseView,BaseTabPresenter<BaseView>> implements FragmentCallback {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentStatePagerAdapter fAdapter;
    private List<Fragment> list_fragment;
    private List<String> list_title;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_personal6);
        tabLayout = (TabLayout) findViewById(R.id.tab_FindFragment_title);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager = (ViewPager) findViewById(R.id.vp_FindFragment_pager);
        toolbar = (Toolbar) this.findViewById(R.id.toolbar1);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setTitle(GlobalParams.user.userCommonInfo.name);
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
//		tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
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
    }

    @Override
    protected BaseTabPresenter<BaseView> initPre() {
        return new BaseTabPresenterImpl();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onFragmentCallback(BaseFragment fragment, int id, Bundle args) {

    }
}
