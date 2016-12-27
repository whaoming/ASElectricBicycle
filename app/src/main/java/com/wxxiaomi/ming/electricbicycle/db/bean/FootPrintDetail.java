package com.wxxiaomi.ming.electricbicycle.db.bean;

import java.util.Arrays;

/**
 * 足迹item的数据格式
 */
public class FootPrintDetail {

	public int userid;
	public int foot_id;
	public int locat_id;
	public double[] points;
	public String content;
	public String picture;
	public String create_time;
	public String geo;
	

	@Override
	public String toString() {
		return "FootPrintDetail [userid=" + userid + ", foot_id=" + foot_id
				+ ", locat_id=" + locat_id + ", points="
				+ Arrays.toString(points) + ", content=" + content
				+ ", picture=" + picture + ", create_time=" + create_time
				+ ", geo=" + geo + "]";
	}

	
	
}
