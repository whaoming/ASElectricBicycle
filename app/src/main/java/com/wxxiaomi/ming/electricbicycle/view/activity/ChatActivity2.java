package com.wxxiaomi.ming.electricbicycle.view.activity;

import android.content.Intent;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.wxxiaomi.ming.electricbicycle.R;

import com.wxxiaomi.ming.electricbicycle.bean.User;
import com.wxxiaomi.ming.electricbicycle.dao.impl.UserDaoImpl;
import com.wxxiaomi.ming.electricbicycle.view.activity.base.BaseActivity;
import com.wxxiaomi.ming.electricbicycle.view.em.EmInterface.MsgGetListener;
import com.wxxiaomi.ming.electricbicycle.view.em.EmManager;
import com.wxxiaomi.ming.electricbicycle.view.em.adapter.ChatRowItemAdapter;


/**
 * 聊天页面
 * 
 * @author Mr.W
 * 
 */
public class ChatActivity2 extends BaseActivity {

	private Toolbar toolbar;
	public static ChatActivity2 activityInstance;
	private String toChatUsername;

	private EditText et_msg;
	private Button btn_send;

	protected EMConversation conversation;
	private RecyclerView listView;
	private LinearLayoutManager mLayoutManager;
	private ChatRowItemAdapter messageAdapter;
	private User.UserCommonInfo friendInfoByEmname;
	/**
	 * 是否为添加好友之后跳转的
	 */
	private boolean isAdd;

	@Override
	protected void initView() {
		setContentView(R.layout.activity_chat2);
		activityInstance = this;
		// 聊天人或群id
		toChatUsername = getIntent().getExtras().getString("userId");
		isAdd = getIntent().getExtras().getBoolean("isAdd",false);
		et_msg = (EditText) findViewById(R.id.et_msg);
		btn_send = (Button) findViewById(R.id.btn_send);
		btn_send.setOnClickListener(this);
		listView = (RecyclerView) findViewById(R.id.list);
		mLayoutManager = new LinearLayoutManager(ct);
		listView.setLayoutManager(mLayoutManager);
		toolbar = (Toolbar) this.findViewById(R.id.toolbar1);
		UserDaoImpl impl = new UserDaoImpl(ct);
		friendInfoByEmname = impl.getFriendInfoByEmname(toChatUsername);
		toolbar.setTitle(friendInfoByEmname.name);
		setSupportActionBar(toolbar);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	protected void initData() {
		
		messageAdapter = new ChatRowItemAdapter(ct, toChatUsername, listView,friendInfoByEmname);
		listView.setAdapter(messageAdapter);
		handler.sendEmptyMessage(456);
		if(isAdd){
			sendTextMessage("我已经成为你的好友啦");
		}
	}

	@Override
	protected void handler(Message msg) {
		super.handler(msg);
		switch (msg.what) {
		case 987:
			messageAdapter.refresh();
			break;
		case 456:
			messageAdapter.refreshSelectLast();

		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		activityInstance = null;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// 点击notification bar进入聊天页面，保证只有一个聊天页面
		String username = intent.getStringExtra("userId");
		if (toChatUsername.equals(username))
			super.onNewIntent(intent);
		else {
			finish();
			startActivity(intent);
		}

	}

	// 发送消息方法
	// ==========================================================================
	protected void sendTextMessage(String content) {
		EMMessage message = EMMessage.createTxtSendMessage(content,
				toChatUsername);
		sendMessage(message);
	}

	protected void sendMessage(EMMessage message) {
		// 发送消息
		EMClient.getInstance().chatManager().sendMessage(message);
		handler.sendEmptyMessage(456);
	}

	protected void onResume() {
		super.onResume();
		EmManager.getInstance().registerMsgReceiveListener(new MsgGetListener() {

			@Override
			public void OnMsgReceive(EMMessage msg) {
				handler.sendEmptyMessage(456);
			}
		});

	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void processClick(View v) {
		switch (v.getId()) {
		case R.id.btn_send:
			sendTextMessage(et_msg.getText().toString().trim());
			break;

		default:
			break;
		}

	};
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		} 
		return super.onOptionsItemSelected(item);
	}
}
