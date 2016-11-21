package com.wxxiaomi.ming.electricbicycle.core.weight.em.EmInterface;

/**
 * Created by 12262 on 2016/5/6.
 * 异地登录监听
 */
public interface RemoteLoginListener {
    public static int USER_REMOVED = 1;
    public static int USER_LOGIN_ANOTHER_DEVICE = 2;
    public static int ELSE = 3;
    void remoteLogin(int error);
}
