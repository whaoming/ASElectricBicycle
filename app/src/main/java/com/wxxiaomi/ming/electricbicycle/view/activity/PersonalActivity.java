package com.wxxiaomi.ming.electricbicycle.view.activity;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.wxxiaomi.ming.electricbicycle.GlobalParams;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.view.activity.base.BaseActivity;
import com.wxxiaomi.ming.electricbicycle.view.adapter.IndexFragmentTabAdapter;
import com.wxxiaomi.ming.electricbicycle.view.fragment.MyCarPageFragment;
import com.wxxiaomi.ming.electricbicycle.view.fragment.SettingPageFragment;
import com.wxxiaomi.ming.electricbicycle.view.fragment.UserInfoPageFragment;
import com.wxxiaomi.ming.electricbicycle.view.fragment.base.BaseFragment;
import com.wxxiaomi.ming.electricbicycle.view.fragment.base.FragmentCallback;


/**
 * 个人页面activity
 * 
 * @author Administrator
 * 
 */
public class PersonalActivity extends BaseActivity implements FragmentCallback {

	private Toolbar toolbar;
	private TabLayout tabLayout;
	private ViewPager viewPager;
	private FragmentStatePagerAdapter fAdapter;
	private List<Fragment> list_fragment;
	private List<String> list_title;

	@Override
	protected void initView() {
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
	}

	@Override
	protected void initData() {
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
		viewPager.addOnPageChangeListener(new OnPageChangeListener() {
			
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
	protected void processClick(View v) {

	}

	@Override
	public void onFragmentCallback(BaseFragment fragment, int id, Bundle args) {
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
