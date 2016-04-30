package com.wxxiaomi.ming.electricbicycle.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.view.fragment.base.BaseFragment;


public class MyCarPageFragment extends BaseFragment {

	@SuppressLint("InflateParams") @Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.fragment_page_mycar, null);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

	}

}
