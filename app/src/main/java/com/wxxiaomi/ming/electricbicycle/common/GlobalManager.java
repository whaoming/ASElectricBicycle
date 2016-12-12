package com.wxxiaomi.ming.electricbicycle.common;

import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.ConstantValue;
import com.wxxiaomi.ming.electricbicycle.dao.bean.User;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.support.baidumap.LocationUtil;

/**
 * Created by 12262 on 2016/11/3.
 * 全局化对象管理器
 */

public class GlobalManager {

    private User user;
    private UserCommonInfo userInfo;

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

    public void savaUserInfo(UserCommonInfo info){
        this.userInfo = info;
    }

    public void updateUserHead(String path){
        if(user!=null){
            user.userCommonInfo.head = ConstantValue.SERVER_URL + path;
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
                        ",\"name\":\""+getUser().userCommonInfo.name+"\"" +
                        ",\"locat\":\""+ LocationUtil.getInstance().getLocat()+"\"" +
                        ",\"locat_tag\":\""+LocationUtil.getInstance().getLocatTag()+"\"}";
        return result;
    }


}
