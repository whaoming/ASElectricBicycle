package com.wxxiaomi.ming.electricbicycle.service.em;

/**
 * Created by 12262 on 2016/6/13.
 */
public class EmEvent<T> {
    //好友请求被同意
    public static final int ONCONNTACTAGREE = 101;
    //好友请求被拒绝
     static final int ONCONTACTREFUSED = 102;
    //收到好友邀请
    public static final int ONCONTACTINVITED = 103;
    //被删除
    public static final int ONCONTACTDELETED = 104;
    //被添加
    public static final int ONCONTACTADDED = 105;
    //收到信息
    public static final int onMessageReceived = 106;
    //收到cmd信息
    public static final int onCmdMessageReceived = 107;
    public static final int onMessageReadAckReceived = 108;
    public static final int onMessageDeliveryAckReceived = 109;
    public static final int onMessageChanged = 110;

    private int Type;
    private String username;
    private T data;

    public EmEvent(int type, String username, T data) {
        Type = type;
        this.username = username;
        this.data = data;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public T getReason() {
        return data;
    }

    public void setReason(T data) {
        this.data = data;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }
}
