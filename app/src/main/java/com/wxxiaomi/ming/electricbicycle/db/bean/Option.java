package com.wxxiaomi.ming.electricbicycle.db.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Option implements Parcelable {

	public int id;
	public int userid;
	public String nickname;
	public String avatar;
	public String create_time;
	//xxx 评论了/发表了/
	public String option_tag;
	public String picture;
	public String content;
	//脚view
//	private String foot;
	public int reply_count;
	public int like_count;
	public String locat_tag;
	//如果是回复的操作，那么这里显示回复的内存
	public String reply;
	//类型
	public int type;
	//目标id
	public int obj_id;
	//附加数据，存放地址信息或者图片地址
	public String attch;
	public int pnt_id;

	public Option() {

	}
	@Override
	public String toString() {
		return "Option [id=" + id + ", userid=" + userid + ", nickname="
				+ nickname + ", avatar=" + avatar + ", create_time="
				+ create_time + ", option_tag="  + ", picture="
				+ picture + ", content=" + content + ", reply_count="
				+ reply_count + ", like_count=" + like_count + ", locat_tag="
				+ locat_tag + ", reply=" + reply + ", type=" + type
				+ ", obj_id=" + obj_id + ", attch=" + attch + "]";
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeInt(id);
		parcel.writeInt(userid);
		parcel.writeInt(reply_count);
		parcel.writeInt(like_count);
		parcel.writeInt(type);
		parcel.writeInt(obj_id);
		parcel.writeInt(pnt_id);
		parcel.writeString(nickname);
		parcel.writeString(avatar);
		parcel.writeString(create_time);
		parcel.writeString(option_tag);
		parcel.writeString(picture);
		parcel.writeString(content);
		parcel.writeString(locat_tag);
		parcel.writeString(reply);
		parcel.writeString(attch);

	}
}
