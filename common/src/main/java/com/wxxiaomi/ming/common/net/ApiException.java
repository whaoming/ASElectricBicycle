package com.wxxiaomi.ming.common.net;

/**
* @author whaoming
* github：https://github.com/whaoming
* created at 2017/2/6 22:37
* Description:用于展示的异常类
*/
public class ApiException extends Throwable {
    private int code;
    private String displayMessage;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;

    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public int getCode() {
        return code;
    }
}