package com.wxxiaomi.ming.electricbicycle.dao.db;

import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by 12262 on 2016/11/16.
 * 好友接口
 */

public interface FriendDao {

    public static final String TABLE_NAME = "users_friend";
    public static final String COLUMN_NAME_ID = "userid";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_HEAD = "haead";
    public static final String COLUMN_NAME_EMNAME = "emname";


    /**
     * 校对数据库的好友列表和em服务器的好友列表
     * 多出的删除
     * 少的就添加到result并返回
     * @param emnamelist  从em获取的emnames
     * @return
     */
    Observable<List<String>> CheckFriendList(final List<String> emnamelist);

    /**
     * 保存好友列表到数据库
     */
    int saveFriendList(List<UserCommonInfo> userList);

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
    String getErrorFriend(List<String> emnames);

}
