package com.wxxiaomi.ming.electricbicycle.view.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.hyphenate.chat.EMMessage;
import com.wxxiaomi.ming.electricbicycle.GlobalParams;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.bean.User;
import com.wxxiaomi.ming.electricbicycle.dao.impl.InviteMessgeDaoImpl;
import com.wxxiaomi.ming.electricbicycle.view.activity.base.BaseActivity;
import com.wxxiaomi.ming.electricbicycle.view.adapter.IndexFragmentTabAdapter;
import com.wxxiaomi.ming.electricbicycle.view.adapter.NewFriendAddItemAdapter;
import com.wxxiaomi.ming.electricbicycle.view.em.EmInterface.FriendReqDetailListener;
import com.wxxiaomi.ming.electricbicycle.view.em.EmInterface.MsgGetListener;
import com.wxxiaomi.ming.electricbicycle.view.em.EmManager;
import com.wxxiaomi.ming.electricbicycle.view.fragment.LatelyFriendFragment;
import com.wxxiaomi.ming.electricbicycle.view.fragment.MyFriendFragment;
import com.wxxiaomi.ming.electricbicycle.view.fragment.base.BaseFragment;
import com.wxxiaomi.ming.electricbicycle.view.fragment.base.FragmentCallback;

/**
 * 好友cativity
 * @author Administrator
 * 
 */
public class ContactActivity2 extends BaseActivity implements FragmentCallback {
	/**
	 * 在点击侧边菜单选项的时候我们往往需要隐藏菜单来显示整个菜单对应的内容。 DrawerLayout.closeDrawer方法用于隐藏侧边菜单
	 * ，DrawerLayout.openDrawer方法用于展开侧边菜单（参见第3点中的代码部分）
	 */
	private Toolbar toolbar;
	private TabLayout tabLayout;
	private ViewPager viewPager;
	private FragmentStatePagerAdapter fAdapter;
	private List<Fragment> list_fragment;
	private List<String> list_title;

	private DrawerLayout mDrawerLayout;
	private RecyclerView mRecyclerView;
	private LinearLayout drawer;
	private LinearLayoutManager mLayoutManager;
	private NewFriendAddItemAdapter adapter;
	private LatelyFriendFragment latelyFriendFragment;

//	private EditText et_serach;
	private MyFriendFragment myFriendFragment;
	private RelativeLayout drawer_ll;
	private ImageButton iv_contact;

	private TextView unread_msg;
	private TextView tv_title;
//	private InviteMessgeDaoImpl inviteMessgeDaoImpl;
	@Override
	protected void initView() {
		setContentView(R.layout.activity_contact2);
		tabLayout = (TabLayout) findViewById(R.id.tab_FindFragment_title);
		tabLayout.setTabMode(TabLayout.MODE_FIXED);
		viewPager = (ViewPager) findViewById(R.id.vp_FindFragment_pager);
		iv_contact = (ImageButton) findViewById(R.id.iv_contact);
		iv_contact.setOnClickListener(this);
		unread_msg = (TextView) findViewById(R.id.unread_msg);
		tv_title = (TextView) findViewById(R.id.tv_title);
		toolbar = (Toolbar) this.findViewById(R.id.toolbar1);
//		toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
		//toolbar.setTitle(GlobalParams.user.userCommonInfo.name);
		tv_title.setText(GlobalParams.user.userCommonInfo.name);
		setSupportActionBar(toolbar);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
		drawer = (LinearLayout) findViewById(R.id.drawer);
		drawer_ll = (RelativeLayout)drawer.findViewById(R.id.drawer_ll);
		drawer_ll.setOnClickListener(this);
		mRecyclerView = (RecyclerView) drawer.findViewById(R.id.list);
//		et_serach = (EditText) drawer.findViewById(R.id.et_serach);
		mLayoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(mLayoutManager);
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
	}

	@Override
	protected void initData() {
		list_fragment = new ArrayList<Fragment>();
		latelyFriendFragment = new LatelyFriendFragment();
		myFriendFragment = new MyFriendFragment();
		list_fragment.add(latelyFriendFragment);
		list_fragment.add(myFriendFragment);
		list_title = new ArrayList<String>();
		list_title.add("最近联系人");
		list_title.add("我的好友");
		tabLayout.addTab(tabLayout.newTab().setText(list_title.get(0)));
		tabLayout.addTab(tabLayout.newTab().setText(list_title.get(1)));
		fAdapter = new IndexFragmentTabAdapter(getSupportFragmentManager(),
				list_fragment, list_title);
		viewPager.setAdapter(fAdapter);
		viewPager.requestDisallowInterceptTouchEvent(true);
		tabLayout.setupWithViewPager(viewPager);
//		inviteMessgeDaoImpl = new InviteMessgeDaoImpl();
		initListener();
		initDrawerData();


	}

	private void updateUnreadMsgUi(){
		int unreadMsg = EmManager.getInstance().getUnreadInviteCountTotal();
		if(unreadMsg>0){
			unread_msg.setVisibility(View.VISIBLE);
			unread_msg.setText(unreadMsg+"");
		}else{
			unread_msg.setVisibility(View.GONE);
		}
	}

	private void initDrawerData() {
		updateUnreadMsgUi();
		adapter = new NewFriendAddItemAdapter(ct, EmManager.getInstance().getInviteMsgList()
				, new NewFriendAddItemAdapter.ItemAddOnClick() {
			@Override
			public void onClick(User.UserCommonInfo userInfo) {
				// 添加某个好友

				addFriend(userInfo);
			}
		});
		mRecyclerView.setAdapter(adapter);
	}
	/**
	 * 同意某个好友的好友申请
	 * @param userInfo
	 */
	protected void addFriend(final User.UserCommonInfo userInfo) {
		/**
		 * 发送添加好友请求 EMClient.getInstance().contactManager().
		 * acceptInvitation(username); 刷新ui,更新数据库 添加好友到数据库 刷新fragment
		 * 跳到chatactivity并发送消息
		 * 
		 */
		showLoading1Dialog("正在添加");
		EmManager.getInstance().agreeInvite(userInfo,
				new EmManager.EmResultCallBack<String>() {
					@Override
					public void success(String t) {
						myFriendFragment.refersh();
						// 跳转到聊天页面
						Intent intent = new Intent(ct, ChatActivity2.class);
						intent.putExtra("userId", userInfo.emname);
						intent.putExtra("isAdd", true);
						closeLoading1Dialog();
						startActivity(intent);
						closeLoading1Dialog();
					}
					@Override
					public void error(String t) {
						closeLoading1Dialog();

					}
				});

	}
	private void initListener() {
//		et_serach.setOnEditorActionListener(new OnEditorActionListener() {
//
//			@Override
//			public boolean onEditorAction(TextView v, int actionId,
//					KeyEvent event) {
//				// TODO Auto-generated method stub
//				return false;
//			}
//		});
	}

	@Override
	protected void processClick(View v) {
		Log.i("wang", "contactActivity发生点击事件");
		switch (v.getId()){
			case R.id.drawer_ll:
				Intent intent = new Intent(this,FriendAddActivity.class);
				startActivity(intent);
				break;
			case R.id.iv_contact:
				mDrawerLayout.openDrawer(Gravity.RIGHT);
				break;

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.menu_demo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		} else {
			mDrawerLayout.openDrawer(Gravity.RIGHT);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onFragmentCallback(BaseFragment fragment, int id, Bundle args) {

	}

	@Override
	protected void onResume() {
		super.onResume();
		EmManager.getInstance().registerMsgReceiveListener(
				new MsgGetListener() {
					@Override
					public void OnMsgReceive(EMMessage msg) {
						latelyFriendFragment.Refresh();
					}
				});

		EmManager.getInstance().registerFriendReqDetailListener(
				new FriendReqDetailListener() {

					@Override
					public void refuse(String username) {

					}

					@Override
					public void getFriend(String username, String reason) {
						Log.i("wang", "联系人页面收到好友请求啦");
						updateUnreadMsgUi();
						adapter.refersh();

					}

					@Override
					public void deleted(String username) {

					}

					@Override
					public void agree(String username) {
						latelyFriendFragment.Refresh();
						myFriendFragment.refersh();

					}

					@Override
					public void addContact(String username) {

					}
				});
	}

}
