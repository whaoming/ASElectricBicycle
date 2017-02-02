package com.wxxiaomi.ming.webmodule.action.forward;

/**
 * Created by 12262 on 2016/11/14.
 */

public class NativeAction extends ForwardAction {
    public String page;
    public String callback;
    public boolean isReturn;
    public String data;

    public NativeAction(String page,  String data, boolean isReturn,String callback) {
        this.page = page;
        this.callback = callback;
        this.isReturn = isReturn;
        this.data = data;
    }
}
