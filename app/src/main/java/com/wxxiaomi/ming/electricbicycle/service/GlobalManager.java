package com.wxxiaomi.ming.electricbicycle.service;

import com.hyphenate.easeui.domain.EaseUser;
import com.wxxiaomi.ming.electricbicycle.ConstantValue;
import com.wxxiaomi.ming.electricbicycle.db.bean.User;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.support.cache.LRUCache;

import java.util.List;

/**
 * Created by Mr.W on 2016/12/19.
 * E-maiil：122627018@qq.com
 * github：https://github.com/whaoming
 * 管理存在于内存中的东西
 */
public class GlobalManager {

    private User user;

    private String stoken;
    private LRUCache<EaseUser> userCache = new LRUCache<>(20);


    private static GlobalManager INSTANCE;
    private GlobalManager(){};
    public static GlobalManager getInstance(){
        if(INSTANCE == null){
            synchronized (GlobalManager.class){
                INSTANCE = new GlobalManager();
            }
        }
        return INSTANCE;
    }


    public void initFriendList(List<UserCommonInfo> friendList){
        for (UserCommonInfo info : friendList) {
            EaseUser user = new EaseUser(info.emname);
            user.setNick(info.nickname);
            user.setAvatar(info.avatar);
            userCache.put(info.emname, user);
        }
        EaseUser user1 = new EaseUser(user.userCommonInfo.emname);
        user1.setNick(user.userCommonInfo.nickname);
        user1.setAvatar(user.userCommonInfo.avatar);
        userCache.put(user.userCommonInfo.emname, user1);
    }

    /**
     * 这里不仅要从内存中拿，还必须从数据库中拿，拿不到再从服务器拿
     * @param emname
     * @return
     */
    public EaseUser getEasyUser(String emname){
        return userCache.get(emname);
    }

    public void putEasyUser(String emname,UserCommonInfo info){
        if(userCache.get(emname)==null){
            EaseUser user1 = new EaseUser(user.userCommonInfo.emname);
            user1.setNick(user.userCommonInfo.nickname);
            user1.setAvatar(user.userCommonInfo.avatar);
            userCache.put(emname, user1);
        }

    }

    public void putEasyUser(String emname,EaseUser info){
        if(userCache.get(emname)==null){
            userCache.put(emname, info);
        }

    }

    public void savaUserInfo(UserCommonInfo info){
        this.user.userCommonInfo = info;
    }

    public void updateUserHead(String path){
        if(user!=null){
            user.userCommonInfo.avatar = ConstantValue.SERVER_URL + path;
        }
    }

    public void savaUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    public boolean isUserNull(){
        return user==null;
    }

    public String getUserCurrentInfo(){
        String result = "{\"userid\":\""+getUser().userCommonInfo.id+"\"" +
                        ",\"name\":\""+getUser().userCommonInfo.nickname+"\"" +
                        ",\"locat\":\""+ LocatProvider.getInstance().getLocat()+"\"" +
                        ",\"locat_tag\":\""+ LocatProvider.getInstance().getLocatTag()+"\"}";
        return result;
    }

    public String getStoken(){
        return stoken;
    }

    public void setStoken(String token){
        this.stoken = token;
    }

}
