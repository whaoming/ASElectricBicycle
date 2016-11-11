package com.wxxiaomi.ming.electricbicycle.core.weight.em;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.util.DateUtils;
import com.wxxiaomi.ming.electricbicycle.R;


public class MyEaseConversationAdapter  extends RecyclerView.Adapter<ViewHolder>{

	private List<EMConversation> conversationList;
	private Context context;
	
	
	public MyEaseConversationAdapter(Context context,List<EMConversation> conversationList) {
		super();
		this.conversationList = conversationList;
		this.context = context;
	}

	@Override
	public int getItemCount() {
		  return conversationList.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int position) {
//		InviteMessage info = infos.get(position);
//		ItemViewHolder holder = (ItemViewHolder) viewHolder;
//		holder.tv_name.setText(info.getFrom());
//		holder.tv_reason.setText(info.getReason());
		ItemViewHolder holder = (ItemViewHolder) viewHolder;
		 // 获取与此用户/群组的会话
        EMConversation conversation = conversationList.get(position);
     // 获取用户username或者群组groupid
        String username = conversation.getUserName();
        holder.name.setText(username);
        if (conversation.getUnreadMsgCount() > 0) {
            // 显示与此用户的消息未读数
            holder.unreadLabel.setText(String.valueOf(conversation.getUnreadMsgCount()));
            holder.unreadLabel.setVisibility(View.VISIBLE);
        } else {
            holder.unreadLabel.setVisibility(View.INVISIBLE);
        }
        EMMessage lastMessage = conversation.getLastMessage();
        holder.message.setText(lastMessage.toString());

        holder.time.setText(DateUtils.getTimestampString(new Date(lastMessage.getMsgTime())));
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
		View view = LayoutInflater.from(context).inflate(R.layout.ease_row_chat_history,
				viewGroup, false);
		return new ItemViewHolder(view);
	}
	
	public class ItemViewHolder extends ViewHolder{
		public TextView name;
		public TextView time;
		public TextView message;
		public TextView unreadLabel;
//		public ImageButton ib_contact;
		
		public ItemViewHolder(View view) {
			super(view);
//			tv_name = (TextView) view.findViewById(R.id.tv_name);
//			tv_info = (TextView) view.findViewById(R.id.tv_info);
//			tv_collectCount = (TextView) view.findViewById(R.id.tv_collectCount);
//			tv_borrow = (TextView) view.findViewById(R.id.tv_borrow);
//			tv_number = (TextView) view.findViewById(R.id.tv_number);
			name = (TextView) view.findViewById(R.id.name);
			message = (TextView) view.findViewById(R.id.message);
			time = (TextView) view.findViewById(R.id.time);
			unreadLabel = (TextView) view.findViewById(R.id.unread_msg_number);
//			view.setOnClickListener(this);
		}
//		@Override
//		public void onClick(View v) {
//			Log.i("wang", "getAdapterPosition()="+getAdapterPosition());
//			Log.i("wang", "getItemId="+getItemId());
//			Log.i("wang", "getLayoutPosition()="+getLayoutPosition());
//			Log.i("wang", "getPosition()="+getPosition());
//			lis.click(getAdapterPosition());
//		}
	}

}
