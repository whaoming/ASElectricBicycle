package com.wxxiaomi.ming.electricbicycle.common;

import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.ConstantValue;
import com.wxxiaomi.ming.electricbicycle.dao.bean.User;

/**
 * Created by 12262 on 2016/11/3.
 * 全局化对象管理器
 */

public class GlobalManager {

    private User user;

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

    public void updateUserHead(String path){
        if(user!=null){
            user.userCommonInfo.head = ConstantValue.SERVER_URL + path;
        }
    }

    public void savaUser(User user){
        //user.userCommonInfo.head = ConstantValue.SERVER_URL + user.userCommonInfo.head.replace("\\","");
        //Log.i("wang","修改之后的head="+user.userCommonInfo.head);
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    public boolean isUserNull(){
        return user==null;
    }


}
