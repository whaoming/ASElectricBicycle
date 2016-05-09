package com.wxxiaomi.ming.electricbicycle.view.em.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMConversation.EMConversationType;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessage.Direct;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.bean.User;
import com.wxxiaomi.ming.electricbicycle.view.activity.UserInfoActivity;
import com.wxxiaomi.ming.electricbicycle.view.em.row.MyEaseChatRow2;
import com.wxxiaomi.ming.electricbicycle.view.em.row.MyEaseChatRowText2;

import java.util.List;


public class ChatRowItemAdapter extends RecyclerView.Adapter<ViewHolder> {

	private Context context;
	private EMConversation conversation;
	private String toChatUsername;
	private final static int TXT = 11;
	private final static int LOCATION = 22;
	private final static int FILE = 33;
	private final static int IMAGE = 44;
	private final static int VOICE = 55;
	private final static int VIDEO = 66;
	private RecyclerView listView;
	protected final int HANDLER_MESSAGE_REFRESH_LIST = 123;
	protected final int HANDLER_MESSAGE_SELECT_LAST = 456;
	protected final int HANDLER_MESSAGE_SEEK_TO = 789;
	private ChatRowItemAdapter instance;
	private User.UserCommonInfo toChatUserInfo;

	public ChatRowItemAdapter(Context context, String username,
			RecyclerView listView,User.UserCommonInfo toChatUserInfo) {
		super();
		this.context = context;
		toChatUsername = username;
		this.conversation = EMClient.getInstance().chatManager()
				.getConversation(toChatUsername,EMConversationType.Chat,true);
		this.listView = listView;
		conversation.markAllMessagesAsRead();
		instance = this;
		this.toChatUserInfo = toChatUserInfo;
//		if(conversation.getLastMessage()!=null){
//			conversation.loadMoreMsgFromDB(conversation.getLastMessage().getMsgId(), 10);
//		}
//		messages = conversation.getAllMessages().toArray(
//				new EMMessage[0]);
//		conversation.markAllMessagesAsRead();
	}

	protected final int DEMO = 65465;
	protected EMMessage[] messages;


	Handler handler = new Handler() {
		private void refreshList() {
			if(conversation.getLastMessage()!=null){
				conversation.loadMoreMsgFromDB(conversation.getLastMessage().getMsgId(), 10);
			}
			messages = conversation.getAllMessages().toArray(
					new EMMessage[0]);
			conversation.markAllMessagesAsRead();
			notifyDataSetChanged();
		}

		@Override
		public void handleMessage(android.os.Message message) {
			switch (message.what) {
			case HANDLER_MESSAGE_REFRESH_LIST:
				// 刷新列表
				refreshList();
				break;
			case HANDLER_MESSAGE_SELECT_LAST:
				// 选择最后一个
				if (messages.length > 0) {
					listView.scrollToPosition(messages.length - 1);
				}
				break;
			case HANDLER_MESSAGE_SEEK_TO:
				// 选择某条消息
//				int position = message.arg1;
				// listView.setSelection(position);
				break;
			// notifyDataSetChanged();
			case DEMO:
				//notifyDataSetChanged();
				if(conversation.getLastMessage()!=null){
					conversation.loadMoreMsgFromDB(conversation.getLastMessage().getMsgId(), 10);
				}
				messages = conversation.getAllMessages().toArray(
						new EMMessage[0]);
				conversation.markAllMessagesAsRead();
				notifyItemInserted(messages.length-1);
//				notifyItemRangeInserted(getItemCount()+1);
				listView.scrollToPosition(messages.length - 1);
				break;
			default:
				break;
			}
		}

	};

	public void refresh() {
		if (handler.hasMessages(HANDLER_MESSAGE_REFRESH_LIST)) {
			return;
		}
		android.os.Message msg = handler
				.obtainMessage(HANDLER_MESSAGE_REFRESH_LIST);
		handler.sendMessage(msg);
	}

	/**
	 * 刷新页面, 选择最后一个
	 */
	public void refreshSelectLast() {
		final int TIME_DELAY_REFRESH_SELECT_LAST = 100;
		handler.removeMessages(HANDLER_MESSAGE_REFRESH_LIST);
		handler.removeMessages(HANDLER_MESSAGE_SELECT_LAST);
		handler.sendEmptyMessageDelayed(HANDLER_MESSAGE_REFRESH_LIST,
				TIME_DELAY_REFRESH_SELECT_LAST);
		handler.sendEmptyMessageDelayed(HANDLER_MESSAGE_SELECT_LAST,
				TIME_DELAY_REFRESH_SELECT_LAST);
	}

	public void scrollToBottom(){
		handler.sendEmptyMessage(DEMO);
	}

	/**
	 * 获取消息的类型
	 */
	@Override
	public int getItemViewType(int position) {
		EMMessage emMessage = messages[position];
		int type = 0;
		switch (emMessage.getType()) {
		case TXT:
			type = TXT;
			break;
		case LOCATION:
			type = LOCATION;
			break;
		case FILE:
			type = FILE;
			break;
		case IMAGE:
			type = IMAGE;
			break;
		case VOICE:
			type = VOICE;
			break;
		case VIDEO:
			type = VIDEO;
			break;
		default:
			break;
		}
		Direct direct = emMessage.direct();
		if (direct == Direct.RECEIVE) {
			type += 100;
		}
		return type;
	}

	@Override
	public int getItemCount() {
		return messages == null ? 0 : messages.length;
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int position) {
		EMMessage emMessage = messages[position];
		switch (emMessage.getType()) {
		case TXT:
			SendTxtViewHolder holder = (SendTxtViewHolder) viewHolder;
			holder.demo.init(emMessage,position,instance,toChatUserInfo,context);
			holder.demo.setOnHeadClickListener(new MyEaseChatRow2.RowClickListener() {
				@Override
				public void onHeadClick() {
					Bundle bundle = new Bundle();
					bundle.putSerializable("userInfo", toChatUserInfo);
					Intent intent4 = new Intent(context, UserInfoActivity.class);
					intent4.putExtra("value", bundle);
					context.startActivity(intent4);
				}
			});
			break;
		default:
			break;
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		View view = null;
		boolean isReceive = false;
		if (viewType > 100) {
			isReceive = true;
			viewType = viewType - 100;
		} else {
			isReceive = false;
		}
		switch (viewType) {
		case TXT:
			if (isReceive) {
				view = LayoutInflater.from(context).inflate(
						(R.layout.ease_row_received_message), viewGroup, false);
				view.setTag(false);
			} else {
				view = LayoutInflater.from(context).inflate(
						(R.layout.ease_row_sent_message), viewGroup, false);
				view.setTag(true);
			}
			break;
		case LOCATION:
			break;
		case FILE:
			break;
		case IMAGE:
			break;
		case VOICE:
			break;
		case VIDEO:
			break;
		default:
			break;
		}
		return new SendTxtViewHolder(view);
	}

	public class SendTxtViewHolder extends ViewHolder {
		MyEaseChatRowText2 demo;

		public SendTxtViewHolder(View view) {
			super(view);
			boolean isSend = (Boolean) view.getTag();
			demo = new MyEaseChatRowText2(view, isSend);
		}
	}

	public EMMessage getItem(int i) {
		return messages[i];
	}

}
