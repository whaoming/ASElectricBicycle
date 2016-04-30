package com.wxxiaomi.ming.electricbicycle.view.em.row;

import java.util.Date;

import com.bumptech.glide.Glide;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessage.Direct;
import com.hyphenate.easeui.R;
import com.hyphenate.util.DateUtils;
import com.wxxiaomi.ming.electricbicycle.GlobalParams;
import com.wxxiaomi.ming.electricbicycle.bean.User;
import com.wxxiaomi.ming.electricbicycle.view.em.adapter.ChatRowItemAdapter;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public abstract class MyEaseChatRow2 {

	private View view;

	protected ProgressBar progressBar;
	protected ImageView statusView;
	protected ImageView userAvatarView;
	protected TextView timeStampView;
	EMMessage emMessage;
	protected int position;
	ChatRowItemAdapter adapter;
	User.UserCommonInfo userInfo;
	Context context;
	HeadOnClickListener headOnClickListener;

	public MyEaseChatRow2(View view1) {
		this.view = view1;
		progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
		statusView = (ImageView) view.findViewById(R.id.msg_status);
		timeStampView = (TextView) view.findViewById(R.id.timestamp);
		userAvatarView = (ImageView) view.findViewById(R.id.iv_userhead);
	}

	public void init(EMMessage emMessage, int position,
					 ChatRowItemAdapter adapter, User.UserCommonInfo userInfo, Context ct) {
		this.emMessage = emMessage;
		this.position = position;
		this.adapter = adapter;
		this.userInfo = userInfo;
		this.context = ct;
		initBaseView();
		onDataInit(emMessage, adapter);
	}

	private void initBaseView() {
		TextView timestamp = (TextView) view.findViewById(R.id.timestamp);
		if (timestamp != null) {
			if (position == 0) {
				timestamp.setText(DateUtils.getTimestampString(new Date(
						emMessage.getMsgTime())));
				timestamp.setVisibility(View.VISIBLE);
			} else {
				// 两条消息时间离得如果稍长，显示时间
				EMMessage prevMessage = (EMMessage) adapter
						.getItem(position - 1);
				if (prevMessage != null
						&& DateUtils.isCloseEnough(emMessage.getMsgTime(),
								prevMessage.getMsgTime())) {
					timestamp.setVisibility(View.GONE);
				} else {
					timestamp.setText(DateUtils.getTimestampString(new Date(
							emMessage.getMsgTime())));
					timestamp.setVisibility(View.VISIBLE);
				}
			}
		}
		if (emMessage.direct() == Direct.SEND) {
			Glide.with(context).load(GlobalParams.user.userCommonInfo.head)
					.into(userAvatarView);
		} else {
			Glide.with(context).load(userInfo.head).into(userAvatarView);
		}
		userAvatarView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (headOnClickListener != null) {
					headOnClickListener.click();
				}

			}
		});

	}

	protected EMCallBack messageSendCallback;

	protected void setMessageSendCallback() {
		messageSendCallback = new EMCallBack() {

			@Override
			public void onSuccess() {
				Log.i("wang", "onSuccess()");
				updateView();
			}

			@Override
			public void onProgress(final int progress, String status) {
				// activity.runOnUiThread(new Runnable() {
				// @Override
				// public void run() {
				// if(percentageView != null)
				// percentageView.setText(progress + "%");
				//
				// }
				// });
			}

			@Override
			public void onError(int code, String error) {
				updateView();
			}
		};
		emMessage.setMessageStatusCallback(messageSendCallback);

	}

	public abstract void onDataInit(EMMessage emMessage,
			ChatRowItemAdapter adapter);

	public abstract void updateView();

	public void setOnHeadClickListener(HeadOnClickListener headOnClickListener){
		this.headOnClickListener = headOnClickListener;
	}
	
	public interface HeadOnClickListener {
		void click();
	}

}
