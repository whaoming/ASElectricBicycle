package com.wxxiaomi.ming.electricbicycle.dao;

import com.wxxiaomi.ming.electricbicycle.bean.User;

import java.util.List;

import rx.Observable;


public interface UserDao {
	public static final String TABLE_NAME = "users_friend";
	public static final String COLUMN_NAME_ID = "userid";
	public static final String COLUMN_NAME_NAME = "name";
	public static final String COLUMN_NAME_HEAD = "haead";
	public static final String COLUMN_NAME_EMNAME = "emname";

	/**
	 * 保存好友列表
	 * @param userList
	 * @return 修改的行数
	 */
	int saveFriendList(List<User.UserCommonInfo> userList);
	
	/**
	 * 取得好友列表
	 * @return
	 */
	List<User.UserCommonInfo> getFriendList();
	
	/**
	 * 更新好友列表
	 * @param userList
	 * @return
	 */
	boolean updateFriendList(List<User.UserCommonInfo> userList);
	
	/**
	 * 根据emname取出一个好友
	 * @param emname
	 * @return
	 */
	User.UserCommonInfo getFriendInfoByEmname(String emname);
	
	/**
	 * 根据emname删除对应好友
	 * @param emname
	 * @return
	 */
	boolean deleteFriend(String emname);
}
