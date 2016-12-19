package com.wxxiaomi.ming.electricbicycle.support.common.cache;

import com.hyphenate.easeui.domain.EaseUser;
import com.wxxiaomi.ming.electricbicycle.common.GlobalManager;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo2;
import com.wxxiaomi.ming.electricbicycle.support.common.cache.base.LRUCache;

import java.util.List;

/**
 * Created by Mr.W on 2016/12/19.
 * E-maiil：122627018@qq.com
 * github：https://github.com/122627018
 */

public class UserCacheProvider {
    private UserCacheProvider(){}
    private static UserCacheProvider INSTANCE;
    public static UserCacheProvider getInstance(){
        if(INSTANCE==null){
            synchronized (ImgCacheProvider.class){
                INSTANCE = new UserCacheProvider();
            }
        }
        return INSTANCE;
    }

    LRUCache<EaseUser> userCache = new LRUCache<>(20);

    public void initCache(List<UserCommonInfo2> infos) {
        for (UserCommonInfo2 info : infos) {
            EaseUser user = new EaseUser(info.emname);
            user.setNick(info.nickname);
            user.setAvatar(info.avatar);
            userCache.put(info.emname, user);
        }
        EaseUser user = new EaseUser(GlobalManager.getInstance().getUser().userCommonInfo.emname);
        user.setNick(GlobalManager.getInstance().getUser().userCommonInfo.nickname);
        user.setAvatar(GlobalManager.getInstance().getUser().userCommonInfo.avatar);
        userCache.put(user.getUsername(), user);
    }

    public void updateUserInfo(UserCommonInfo2 info){
        userCache.remove(info.emname);
        EaseUser user = new EaseUser(info.emname);
        user.setNick(info.nickname);
        user.setAvatar(info.avatar);
        userCache.put(info.emname, user);
    }

    public EaseUser get(String emname){
        return userCache.get(emname);
    }
}
