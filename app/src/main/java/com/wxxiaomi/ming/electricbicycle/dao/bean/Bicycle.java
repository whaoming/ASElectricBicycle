package com.wxxiaomi.ming.electricbicycle.dao.bean;

import java.io.Serializable;


public class Bicycle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int id;
	public int typeid;
	public int isbund;
	public int userid;
	public int version;
	public String name;
	public String size;
	public String batterysize;
	public Bicycle(int typeid, int isbund, int userid, int version,
			String name, String size, String batterysize) {
		super();
		this.typeid = typeid;
		this.isbund = isbund;
		this.userid = userid;
		this.version = version;
		this.name = name;
		this.size = size;
		this.batterysize = batterysize;
	}
	
	
}
