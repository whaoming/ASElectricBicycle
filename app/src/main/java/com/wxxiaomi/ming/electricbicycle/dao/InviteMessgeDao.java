package com.wxxiaomi.ming.electricbicycle.dao;

import com.wxxiaomi.ming.electricbicycle.bean.InviteMessage;

import java.util.List;



public interface InviteMessgeDao {
	public static final String TABLE_NAME = "new_friends_msgs";
	public static final String COLUMN_NAME_FROM = "username";
	public static final String COLUMN_NAME_TIME = "time";
	public static final String COLUMN_NAME_REASON = "reason";
	public static final String COLUMN_NAME_UNREAD_MSG_COUNT = "unreadMsgCount";
	public static final String COLUMN_NAME_ID = "id";

	/**
	 * 获取邀请消息数量
	 * @return
	 */
	int getUnreadNotifyCount();

	/**
	 * 存储一条消息
	 * @param message
	 * @return
	 */
	Integer saveMessage(InviteMessage message);


	/**
	 * 获取未读消息列表
	 * @return
	 */
	List<InviteMessage> getMessagesList();

	/**
	 * 设置未读的邀请消息的数目
	 * @param count
	 */
//	void saveUnreadMessageCount(int count);
}
