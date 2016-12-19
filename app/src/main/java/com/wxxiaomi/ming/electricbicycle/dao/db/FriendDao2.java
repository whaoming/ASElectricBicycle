package com.wxxiaomi.ming.electricbicycle.dao.db;

import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo2;

import java.util.List;

import rx.Observable;

/**
 * Created by 12262 on 2016/11/16.
 * 好友接口
 */

public interface FriendDao2 {

    String TABLE_NAME = "user_friends";
    String COLUMN_NAME_ID = "userid";
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


    /**
     * 更新好友列表
     * @param userList
     * @return
     */
    int updateFriendsList(List<UserCommonInfo2> userList);


    /**
     * 从数据库获取好友列表
     */
    List<UserCommonInfo2> getFriendList();

    /**
     * 更新数据库的好友列表
     */
    Observable<Integer> InsertFriendList(List<UserCommonInfo2> userList);


    /**
     * 根据emname从数据库取得某一位好友
     */
    Observable<UserCommonInfo2> getFriendInfoByEmname(String emname);

    /**
     * 从数据库删除好友
     */
    boolean deleteFriend(String emname);

    /**
     * 根据数据库判断当前emname是否为好友
     * @param emname
     * @return
     */
    boolean isMyFriend(String emname);


    /**
     * 获取错开的好友列表
     * 字符串拼装返回
     * @param emnames
     * @return
     */
    String getErrorFriend(List<String> emnames);

    List<UserCommonInfo2> getEFriends();
}
