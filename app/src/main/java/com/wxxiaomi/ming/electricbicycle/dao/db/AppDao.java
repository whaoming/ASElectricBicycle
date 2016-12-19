package com.wxxiaomi.ming.electricbicycle.dao.db;

import com.wxxiaomi.ming.electricbicycle.dao.bean.User;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo2;

/**
 * Created by Administrator on 2016/12/12.
 */

public interface AppDao {
    String TABLE_NAME = "app_record";
    String COLUMN_NAME_ID = "id";
    String COLUMN_NAME_USERNAME = "username";
    String COLUMN_NAME_PASSWORD = "password";
    String COLUMN_NAME_INFO_ID = "userid";
    String COLUMN_NAME_EMNAME = "emname";
    String COLUMN_NAME_NAME = "nickname";
    String COLUMN_NAME_HEAD = "avatar";
    String COLUMN_NAME_ALBUMID = "album_id";
    String COLUMN_NAME_UPDATETIME = "update_time";
    String COLUMN_NAME_CREATETIME = "create_time";
    String COLUMN_NAME_DESCRIPTION = "description";
    String COLUMN_NAME_CITY = "city";
    String COLUMN_NAME_SEX = "sex";
    String COLUMN_NAME_COVER = "cover";

    User getLocalUser(int userid);

    boolean savaUser(User user);

    Integer updateUserInfo(UserCommonInfo2 info);
}
