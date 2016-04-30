package com.wxxiaomi.ming.electricbicycle.dao;


import com.wxxiaomi.ming.electricbicycle.bean.User;

public interface TempUserDao {

	public static final String TABLE_NAME = "users_temp";
	public static final String COLUMN_NAME_ID = "userid";
	public static final String COLUMN_NAME_NAME = "name";
	public static final String COLUMN_NAME_HEAD = "haead";
	public static final String COLUMN_NAME_EMNAME = "emname";
	
	/**
	 * 保存一个用户
	 * @param userInfo
	 */
	void savaPerson(User.UserCommonInfo userInfo);
	
	/**
	 * 根据emname获取一个用户
	 * @param emname
	 */
	User.UserCommonInfo getPersonByEmname(String emname);
}
