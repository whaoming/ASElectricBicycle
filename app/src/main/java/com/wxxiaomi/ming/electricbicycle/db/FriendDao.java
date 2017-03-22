package com.wxxiaomi.ming.electricbicycle.db;

import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;

import java.util.List;

import rx.Observable;

/**
 * Created by 12262 on 2016/11/16.
 * 好友接口
 */

public interface FriendDao {

    String TABLE_NAME = "user_friends";
    String COLUMN_NAME_ID = "userid";
    String COLUMN_NAME_EMNAME = "emname";
    String COLUMN_NAME_NAME = "nickname";
    String COLUMN_NAME_HEAD = "avatar";
    String COLUMN_NAME_UPDATETIME = "update_time";
    //是否拉黑 1-是  0-不是
    String COLUMN_NAME_BLACK = "black";


    /**
     * 更新好友列表
     * 多的删除，少的添加
     * 已经存在的进行更新
     * @param userList
     * @return
     */
    int updateFriendsList(List<UserCommonInfo> userList);

    /**
     * 更新黑名单列表
     * 多的删除，少的添加
     * 已经存在的进行更新
     * @param userList
     * @return 修改的条目数
     */
    int updateBlackList(List<UserCommonInfo> userList);

    /**
     * 获取黑名单列表
     * @return
     */
    List<UserCommonInfo> getBlackList();


    /**
     * 从数据库获取好友列表
     */
    List<UserCommonInfo> getFriendList();

    /**
     * 更新数据库的好友列表
     */
    Observable<Integer> InsertFriendList(List<UserCommonInfo> userList);


    /**
     * 根据emname从数据库取得某一位好友
     */
    Observable<UserCommonInfo> getFriendInfoByEmname(String emname);

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
    @Deprecated
    String getErrorFriend(List<String> emnames);

    List<UserCommonInfo> getEFriends();
}
