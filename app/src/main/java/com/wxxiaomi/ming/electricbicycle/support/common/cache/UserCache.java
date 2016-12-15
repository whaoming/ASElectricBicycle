package com.wxxiaomi.ming.electricbicycle.support.common.cache;

import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/12/15.
 */

public class UserCache {
    private UserCache(){};
    private static UserCache INSTANCE;
    public static UserCache getInstance(){
        if(INSTANCE==null){
            synchronized (CacheEngine.class){
                INSTANCE = new UserCache();
            }
        }
        return INSTANCE;
    }

    public void init(List<UserCommonInfo> infos){

    }
}
