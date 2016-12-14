package com.wxxiaomi.ming.electricbicycle.dao.db;

import com.wxxiaomi.ming.electricbicycle.dao.bean.User;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo;

/**
 * Created by Administrator on 2016/12/12.
 */

public interface AppDao {
    String TABLE_NAME = "app_record";
    String COLUMN_NAME_ID = "userid";
    String COLUMN_NAME_USERNAME = "username";
    String COLUMN_NAME_PASSWORD = "password";
    String COLUMN_NAME_INFO_NAME = "name";
    String COLUMN_NAME_INFO_HEAD = "head";
    String COLUMN_NAME_INFO_EMNAME = "emname";
    String COLUMN_NAME_INFO_ID = "info_id";

    User getLocalUser(int userid);

    boolean savaUser(User user);

    Integer updateUserInfo(String name,String head,String emname);
}
