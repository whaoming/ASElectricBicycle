package com.wxxiaomi.ming.electricbicycle.dao.bean;

import java.util.Arrays;
import java.util.Date;


public class Topic {
	public int id;
	public UserCommonInfo userCommonInfo;
	public String content;
	public String time;
	public String pics;
	public String title;
	public int hot;
	public int ccount;
	public String locat;
	public String locat_tag;
	public String picss[];
	
	@Override
	public String toString() {
		return "Topic [id=" + id + ", userCommonInfo=" + userCommonInfo
				+ ", content=" + content + ", time=" + time + ", pics=" + pics
				+ ", title=" + title + ", hot=" + hot + ", ccount=" + ccount
				+ ", locat=" + locat + ", locat_tag=" + locat_tag + ", picss="
				+ Arrays.toString(picss) + "]";
	}
	
	
	
	

}
