package com.wxxiaomi.ming.electricbicycle.db.bean;

public class InviteMessage {
	
		private int id;
	
		//时间
		private long time;
		//添加理由
		private String reason;
	private String nickname;
	private String avatar;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	private String from;
		public long getTime() {
			return time;
		}
		public void setTime(long time) {
			this.time = time;
		}
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}
		public String getFrom() {
			return from;
		}
		public void setFrom(String from) {
			this.from = from;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		

}
