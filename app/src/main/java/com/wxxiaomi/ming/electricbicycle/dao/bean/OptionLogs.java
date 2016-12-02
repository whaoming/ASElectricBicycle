package com.wxxiaomi.ming.electricbicycle.dao.bean;

import java.util.Date;

public class OptionLogs {

	public int id;
	public int obj_type;
	public int obj_id;
	public String create_time;
	public int userid;
	public String title;
	public String content;
	public String pictures;
	public String foor_note;
	public String locat;
	public String locat_tag;
	public int start;
	public String[] picss;
//	public String[] getPicss() {
//		return picss;
//	}
//	public void setPicss(String[] picss) {
//		this.picss = picss;
//	}
//	public int getId() {
//		return id;
//	}
//	public void setId(int id) {
//		this.id = id;
//	}
//	public int getObj_type() {
//		return obj_type;
//	}
//	public void setObj_type(int obj_type) {
//		this.obj_type = obj_type;
//	}
//	public int getObj_id() {
//		return obj_id;
//	}
//	public void setObj_id(int obj_id) {
//		this.obj_id = obj_id;
//	}
//	public Date getCreate_time() {
//		return create_time;
//	}
//	public void setCreate_time(Date create_time) {
//		this.create_time = create_time;
//	}
//	public int getUserid() {
//		return userid;
//	}
//	public void setUserid(int userid) {
//		this.userid = userid;
//	}
//	public String getTitle() {
//		return title;
//	}
//	public void setTitle(String title) {
//		this.title = title;
//	}
//	public String getContent() {
//		return content;
//	}
//	public void setContent(String content) {
//		this.content = content;
//	}
//	public String getPictures() {
//		return pictures;
//	}
//	public void setPictures(String pictures) {
//		picss = pictures.split("#");
//		this.pictures = pictures;
//	}
//	public String getFoor_note() {
//		return foor_note;
//	}
//	public void setFoor_note(String foor_note) {
//		this.foor_note = foor_note;
//	}
//	public String getLocat() {
//		return locat;
//	}
//	public void setLocat(String locat) {
//		this.locat = locat;
//	}
//	public String getLocat_tag() {
//		return locat_tag;
//	}
//	public void setLocat_tag(String locat_tag) {
//		this.locat_tag = locat_tag;
//	}
//	public int getStart() {
//		return start;
//	}
//	public void setStart(int start) {
//		this.start = start;
//	}
	@Override
	public String toString() {
		return "Opt_Logs [id=" + id + ", obj_type=" + obj_type + ", obj_id="
				+ obj_id + ", create_time=" + create_time + ", userid="
				+ userid + ", title=" + title + ", content=" + content
				+ ", pictures=" + pictures + ", foor_note=" + foor_note
				+ ", locat=" + locat + ", locat_tag=" + locat_tag + ", start="
				+ start + "]";
	}
	
	
}
