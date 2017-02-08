package com.wxxiaomi.ming.common.net;

/**
 * Created by 12262 on 2016/5/31.
 * 用来匹配服务器的数据规则
 */
public class ServerException extends RuntimeException {


    private int code;
    private String msg;

    public ServerException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
