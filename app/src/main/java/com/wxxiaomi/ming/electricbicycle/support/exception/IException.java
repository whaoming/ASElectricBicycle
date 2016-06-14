package com.wxxiaomi.ming.electricbicycle.support.exception;

/**
 * Created by 12262 on 2016/5/29.
 */
public class IException extends Throwable{

    private int errorCode;
    private String error;
    public IException(int errorCode,String error) {
        this.errorCode = errorCode;
        this.error = error;
    }
    public String getErrorInfo(){
        return error;
    }
}
