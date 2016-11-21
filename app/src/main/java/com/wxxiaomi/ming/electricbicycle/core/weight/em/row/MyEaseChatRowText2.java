package com.wxxiaomi.ming.electricbicycle.core.weight.em.row;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.core.weight.em.adapter.ChatRowItemAdapter;


import android.text.Spannable;
import android.view.View;
import android.widget.TextView;

public class MyEaseChatRowText2 extends MyEaseChatRow2 {

	private TextView contentView;
	private View view;
	EMMessage emMessage;
	ChatRowItemAdapter adapter;

	@Override
	public void onDataInit(EMMessage emMessage1, ChatRowItemAdapter adapter) {
		this.emMessage = emMessage1;
		EMTextMessageBody txtBody = (EMTextMessageBody) emMessage.getBody();
		Spannable span = EaseSmileUtils.getSmiledText(context, txtBody.getMessage());
		contentView.setText(span, TextView.BufferType.SPANNABLE);
		this.adapter = adapter;
		if (emMessage.direct() == EMMessage.Direct.SEND) {
			setMessageSendCallback();
			switch (emMessage.status()) {
			case CREATE:
				progressBar.setVisibility(View.GONE);
				statusView.setVisibility(View.VISIBLE);
				// 发送消息
				// sendMsgInBackground(message);
				break;
			case SUCCESS: // 发送成功
				// Log.i("wang", "发送成功");
				progressBar.setVisibility(View.GONE);
				statusView.setVisibility(View.GONE);
				break;
			case FAIL: // 发送失败
				progressBar.setVisibility(View.GONE);
				statusView.setVisibility(View.VISIBLE);
				break;
			case INPROGRESS: // 发送中
				progressBar.setVisibility(View.VISIBLE);
				statusView.setVisibility(View.GONE);
				break;
			default:
				break;
			}
		}
	}

	public MyEaseChatRowText2(View view1, boolean isSend) {
		super(view1);
		this.view = view1;
		contentView = (TextView) view.findViewById(R.id.tv_chatcontent);
	}

	@Override
	public void updateView() {
		adapter.refresh();
	}

}
