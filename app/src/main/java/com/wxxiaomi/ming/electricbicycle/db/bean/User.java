package com.wxxiaomi.ming.electricbicycle.db.bean;

import java.io.Serializable;


public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int id;
	public String username;
	public String password;
	public UserCommonInfo userCommonInfo;

	public String shortToken;
	public String longToken;
	


	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password="
				+ password + ", userCommonInfo=" + userCommonInfo.toString() + "]";
	}
	
	
}
