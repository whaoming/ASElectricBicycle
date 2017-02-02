package com.wxxiaomi.ming.electricbicycle.db.bean.format;

import com.wxxiaomi.ming.electricbicycle.db.bean.Option;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/23.
 * 在获取某一个用户的动态的数据格式
 */
public class UserInfo implements Serializable{
    public UserCommonInfo userCommonInfo;
    public ArrayList<Option> options;
}
