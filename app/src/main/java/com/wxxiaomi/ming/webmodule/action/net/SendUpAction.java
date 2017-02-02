package com.wxxiaomi.ming.webmodule.action.net;

import java.util.Arrays;

/**
 * Created by 12262 on 2016/11/24.
 */

public class SendUpAction {
    public String url;
    public String pars;
    public String pics[];
    public String picsname;


    @Override
    public String toString() {
        return "SendUpAction{" +
                "url='" + url + '\'' +
                ", pars='" + pars + '\'' +
                ", pics=" + Arrays.toString(pics) +
                ", picsname='" + picsname + '\'' +
                '}';
    }
}
