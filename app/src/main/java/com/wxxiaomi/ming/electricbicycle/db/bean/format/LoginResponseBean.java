package com.wxxiaomi.ming.electricbicycle.db.bean.format;

import com.wxxiaomi.ming.electricbicycle.db.bean.User;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;

import java.io.Serializable;
import java.util.List;


public class LoginResponseBean implements Serializable{

	public User user;
	public List<UserCommonInfo> friendList;
	public List<UserCommonInfo> blackList;

	@Override
	public String toString() {
		return user.toString()+",friendList.size:"+friendList.size()+",blackList.size:"+blackList.size();
	}
}
