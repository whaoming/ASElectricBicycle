package com.wxxiaomi.ming.electricbicycle.db;

import com.wxxiaomi.ming.electricbicycle.db.bean.User;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserLocatInfo;
import com.wxxiaomi.ming.electricbicycle.db.bean.format.LoginResponseBean;

import java.util.List;

import rx.Observable;

/**
 * Created by 12262 on 2016/11/16.
 * 用户接口
 */

public interface UserDao {
    public static final String TABLE_NAME = "users_temp";
    public static final String COLUMN_NAME_ID = "userid";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_HEAD = "haead";
    public static final String COLUMN_NAME_EMNAME = "emname";

    /**
     * 根据emname从服务器获取一个用户公共信息
     * @param emname
     * @return
     */
    Observable<UserCommonInfo> getUserByEnameFWeb(String emname);

    /**
     * 根据emname集合从服务器获取用户公共信息集合
     * @param usernames
     * @return
     */
    Observable<List<UserCommonInfo>> getUserListFromWeb(List<String> usernames);

    /**
     * 从服务器获取附近的人
     * @param userid
     * @param latitude
     * @param longitude
     * @return
     */
    Observable<List<UserLocatInfo>> getNearPeople(int userid, double latitude, double longitude);

    /**
     * 登录到服务器
     * @param username
     * @param password
     * @return
     */
    Observable<LoginResponseBean> Login(String username, String password, String num);

    /**
     * 判断是否存在当前临时用户表中
     * @param emname
     * @return
     */
    Observable<Boolean> isTempUserExist(String emname);

    /**
     * 从临时表中取出一个用户
     * @param emname
     * @return
     */
    Observable<UserCommonInfo> getUserLocal(String emname);

    /**
     * 根据名称从服务器获取用户公共信息
     * @param name
     * @return
     */
    Observable<List<UserCommonInfo>> getUserCommonInfo2ByName(String name);

    /**
     * 注册一个用户
     * @param username
     * @param password
     * @return
     */

    /**
     * 上传用户头像到服务器
     * @param fileName
     * @param filePath
     * @return
     */
    Observable<String> upLoadHead(String fileName,String filePath);

    Observable<UserCommonInfo> InsertUser(UserCommonInfo info);

}
