package com.wxxiaomi.ming.electricbicycle.view.em;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.DateUtils;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.bean.User;
import com.wxxiaomi.ming.electricbicycle.dao.impl.UserDaoImpl;


public class MyEaseConversationAdapter2 extends ArrayAdapter<EMConversation>{

	public MyEaseConversationAdapter2(Context context, int resource,
			List<EMConversation> objects) {
		super(context, resource, objects);
		this.context = context;
		this.conversationList = objects;
	}

	private List<EMConversation> conversationList;
	private Context context;
	

	@Override
	public int getCount() {
		  return conversationList.size();
	}
	 @Override
	    public EMConversation getItem(int arg0) {
	        if (arg0 < conversationList.size()) {
	            return conversationList.get(arg0);
	        }
	        return null;
	    }

	    @Override
	    public long getItemId(int position) {
	        return position;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        if (convertView == null) {
	            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ease_row_chat_history, parent, false);
	        }
	        ViewHolder holder = (ViewHolder) convertView.getTag();
	        if (holder == null) {
	            holder = new ViewHolder();
	            holder.name = (TextView) convertView.findViewById(R.id.name);
	            holder.unreadLabel = (TextView) convertView.findViewById(R.id.unread_msg_number);
	            holder.message = (TextView) convertView.findViewById(R.id.message);
	            holder.time = (TextView) convertView.findViewById(R.id.time);
	            holder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
	            holder.msgState = convertView.findViewById(R.id.msg_state);
	            holder.list_itease_layout = (RelativeLayout) convertView.findViewById(R.id.list_itease_layout);
	            convertView.setTag(holder);
	        }
	        holder.list_itease_layout.setBackgroundResource(R.drawable.ease_mm_listitem);

	        // 获取与此用户/群组的会话
	        EMConversation conversation = getItem(position);
	        // 获取用户username或者群组groupid
	        String username = conversation.getUserName();
	        UserDaoImpl impl = new UserDaoImpl(context);
	        Log.i("wang", "username="+username);
	        User.UserCommonInfo userCommonInfo = impl.getFriendInfoByEmname(username);
//	        Log.i("wang", "username="+username);
	        if(userCommonInfo!=null){
	        	 holder.name.setText(userCommonInfo.name);
	        	 Glide.with(context).load(userCommonInfo.head).into(holder.avatar);
	        }else{
	        	 holder.name.setText(username);
	        }
	       
	       
	        if (conversation.getUnreadMsgCount() > 0) {
	            // 显示与此用户的消息未读数
	            holder.unreadLabel.setText(String.valueOf(conversation.getUnreadMsgCount()));
	            holder.unreadLabel.setVisibility(View.VISIBLE);
	        } else {
	            holder.unreadLabel.setVisibility(View.INVISIBLE);
	        }

	        if (conversation.getAllMsgCount() != 0) {
	            // 把最后一条消息的内容作为item的message内容
	            EMMessage lastMessage = conversation.getLastMessage();
	            EMTextMessageBody txtBody = (EMTextMessageBody) lastMessage.getBody();
	            holder.message.setText(txtBody.getMessage());
	            holder.time.setText(DateUtils.getTimestampString(new Date(lastMessage.getMsgTime())));
	        }

	        return convertView;
	    }
	    
	    @Override
	    public void notifyDataSetChanged() {
	        super.notifyDataSetChanged();
//	        if(!notiyfyByFilter){
//	            copyConversationList.clear();
//	            copyConversationList.addAll(conversationList);
//	            notiyfyByFilter = false;
//	        }
	    }
	    
	    private static class ViewHolder {
	        /** 和谁的聊天记录 */
	        TextView name;
	        /** 消息未读数 */
	        TextView unreadLabel;
	        /** 最后一条消息的内容 */
	        TextView message;
	        /** 最后一条消息的时间 */
	        TextView time;
	        /** 用户头像 */
			ImageView avatar;
	        /** 最后一条消息的发送状态 */
	        @SuppressWarnings("unused")
			View msgState;
	        /** 整个list中每一行总布局 */
	        RelativeLayout list_itease_layout;

	    }

//	}


}
