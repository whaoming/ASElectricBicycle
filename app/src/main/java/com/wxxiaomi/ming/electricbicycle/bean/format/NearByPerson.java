package com.wxxiaomi.ming.electricbicycle.bean.format;

import com.wxxiaomi.ming.electricbicycle.bean.User;

import java.util.List;



public class NearByPerson {
	public List<UserLocatInfo> userLocatList;
	public static class UserLocatInfo{
		public User.UserCommonInfo userCommonInfo;
		public double[] locat;
	}
}
