package com.wxxiaomi.ming.electricbicycle.dao.bean;

import java.io.Serializable;

/**
 * Created by 12262 on 2016/11/9.
 */
public class UserCommonInfo implements Serializable {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        public int id;
        public String name;
        public String head;
        public String emname;
        @Override
        public String toString() {
            return "UserCommonInfo [id=" + id + ", name=" + name
                    + ", head=" + head + ", emname=" + emname + "]";
        }


}
