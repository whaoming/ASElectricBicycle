package com.wxxiaomi.ming.electricbicycle.view.fragment;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.bean.User;
import com.wxxiaomi.ming.electricbicycle.dao.impl.UserDaoImpl;
import com.wxxiaomi.ming.electricbicycle.view.activity.ChatActivity2;
import com.wxxiaomi.ming.electricbicycle.view.adapter.MyFriendItemAdapter;
import com.wxxiaomi.ming.electricbicycle.view.fragment.base.BaseFragment;


public class MyFriendFragment extends BaseFragment {

	private View view;
//	private ListView lv_listview;
//	private DemoListAdapter adapter;
	private RecyclerView mRecyclerView;
	List<User.UserCommonInfo> friendList;
	private LinearLayoutManager mLayoutManager;
	private MyFriendItemAdapter adapter;

	
	@SuppressLint("InflateParams") @Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.fragment_myfriend, null);
		mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
		mLayoutManager = new LinearLayoutManager(ct);
		mRecyclerView.setLayoutManager(mLayoutManager);
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		return view;
	}
	
	@Override
	public void onResume() {
//		UserDaoImpl impl = new UserDaoImpl(ct);
//		friendList = impl.getFriendList();
//		if(adapter == null){
//			adapter = new DemoListAdapter(friendList);
//			lv_listview.setAdapter(adapter);
//		}else{
//			adapter.notifyDataSetChanged();
//		}
		super.onResume();
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		UserDaoImpl impl = new UserDaoImpl(ct);
		friendList = impl.getFriendList();
//		Log.i("wang", "initData()中friendList.size=" + friendList.size());
		adapter = new MyFriendItemAdapter(ct, friendList,new MyFriendItemAdapter.OnItemClick() {
			
			@Override
			public void onClick(User.UserCommonInfo info) {
				Intent intent = new Intent(ct, ChatActivity2.class).putExtra(
						"userId", info.emname);
				startActivity(intent);
				
			}
		});
		mRecyclerView.setAdapter(adapter);


	}
	public void refersh() {
		UserDaoImpl impl = new UserDaoImpl(ct);
		friendList.clear();
		friendList.addAll(impl.getFriendList());
		adapter.notifyDataSetChanged();
		
	}
}
