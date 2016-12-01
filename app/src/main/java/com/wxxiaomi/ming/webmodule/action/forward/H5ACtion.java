package com.wxxiaomi.ming.webmodule.action.forward;

import java.io.Serializable;

/**
 * Created by 12262 on 2016/11/14.
 */

public class H5ACtion extends ForwardAction implements Serializable{

    public String page;
    public String data;
    public boolean isReturn;
    public String callBack;

    public H5ACtion() {
    }

    public H5ACtion(String page, String data, boolean isReturn, String callBack) {
        this.page = page;
        this.data = data;
        this.isReturn = isReturn;
        this.callBack = callBack;
    }

    @Override
    public String toString() {
        return "H5ACtion{" +
                "page='" + page + '\'' +
                ", data='" + data + '\'' +
                ", isReturn=" + isReturn +
                ", callBack='" + callBack + '\'' +
                '}';
    }
}
