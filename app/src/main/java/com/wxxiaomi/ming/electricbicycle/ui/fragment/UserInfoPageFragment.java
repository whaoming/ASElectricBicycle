package com.wxxiaomi.ming.electricbicycle.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.ui.fragment.base.BaseFragment;


public class UserInfoPageFragment extends BaseFragment {

	@SuppressLint("InflateParams") @Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.fragment_page_userinfo, null);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	}
}
