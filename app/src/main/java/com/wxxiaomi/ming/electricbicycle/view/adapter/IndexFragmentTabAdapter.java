package com.wxxiaomi.ming.electricbicycle.view.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class IndexFragmentTabAdapter extends FragmentStatePagerAdapter {

	private List<Fragment> list_fragment; // fragment列表
	private List<String> list_Title; // tab名的列表
	
	public IndexFragmentTabAdapter(FragmentManager fm,List<Fragment> list_fragment,
			List<String> list_Title) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.list_fragment = list_fragment;
		this.list_Title = list_Title;
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		return list_fragment.get(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_Title.size();
	}
	
	// 此方法用来显示tab上的名字
		@Override
		public CharSequence getPageTitle(int position) {

			return list_Title.get(position % list_Title.size());
		}

}
