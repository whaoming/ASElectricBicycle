package com.wxxiaomi.ming.electricbicycle.api.exception;

/**
 * Created by 12262 on 2016/5/31.
 * 与服务器约定好的异常
 */
public class ERROR {
    /**
     * 未知错误
     */
    public static final int UNKNOWN = 1000;
    /**
     * 解析错误
     */
    public static final int PARSE_ERROR = 1001;
    /**
     * 网络错误
     */
    public static final int NETWORD_ERROR = 1002;
    /**
     * 协议出错
     */
    public static final int HTTP_ERROR = 1003;

    /**
     * 从em服务器拉取好友失败
     */
    public static final int EM_GETCONTACT_ERROR = 1004;

    public static final int EM_AGREEINVITE_ERROR = 1005;
    //添加了联系人出错
    public static final int EM_ADDCONNTACT_ERROR = 1006;
}
