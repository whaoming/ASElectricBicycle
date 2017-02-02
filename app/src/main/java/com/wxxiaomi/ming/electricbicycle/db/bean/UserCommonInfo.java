package com.wxxiaomi.ming.electricbicycle.db.bean;

import java.io.Serializable;

/**
 * 用户公共信息
 */
public class UserCommonInfo implements Serializable {
        /**
         *
         */
        public static final long serialVersionUID = 1L;
        public int id;
        public String nickname;
        public String avatar;
        public String emname;
        public int album_id;
        public String update_time;
        public String create_time;
        public String description;
        public String city;
        public int sex;
        public String cover;

        @Override
        public String toString() {
                return "UserCommonInfo{" +
                        "id=" + id +
                        ", nickname='" + nickname + '\'' +
                        ", avatar='" + avatar + '\'' +
                        ", emname='" + emname + '\'' +
                        ", album_id=" + album_id +
                        ", update_time='" + update_time + '\'' +
                        ", create_time='" + create_time + '\'' +
                        ", description='" + description + '\'' +
                        ", city='" + city + '\'' +
                        ", sex=" + sex +
                        ", cover='" + cover + '\'' +
                        '}';
        }
}
