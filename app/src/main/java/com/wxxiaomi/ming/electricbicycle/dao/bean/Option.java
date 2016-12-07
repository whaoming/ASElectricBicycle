package com.wxxiaomi.ming.electricbicycle.dao.bean;

import java.util.Date;

public class Option {

	public int id;
	public int user_id;
	public int obj_type;
	public int obj_id;
	public int parent_type;
	public int parent_id;
	public String create_time;

	public Object dobj;
	public Object dparent;



	public String json_obj;
	public String json_parent;

	@Override
	public String toString() {
		return "Option{" +
				"id=" + id +
				", user_id=" + user_id +
				", obj_type=" + obj_type +
				", obj_id=" + obj_id +
				", parent_type=" + parent_type +
				", parent_id=" + parent_id +
				", create_time=" + create_time +
				", json_obj='" + json_obj + '\'' +
				", json_parent='" + json_parent + '\'' +
				'}';
	}
}
