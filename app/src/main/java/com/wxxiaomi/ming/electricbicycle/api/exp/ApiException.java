package com.wxxiaomi.ming.electricbicycle.api.exp;

/**
 * Created by 12262 on 2016/5/31.
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
