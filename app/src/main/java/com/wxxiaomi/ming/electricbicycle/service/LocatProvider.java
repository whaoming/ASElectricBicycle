package com.wxxiaomi.ming.electricbicycle.service;

import com.baidu.location.Address;

/**
 * Created by whaoming on 2016/12/5.
 * 所有需要位置信息的都经由这个类管理
 */
public class LocatProvider {
    private double latitude;
    private double longitude;
    private Address address;
    private String locat_tag;
    public static LocatProvider INSTANCE;
    private LocatProvider(){}

    public static LocatProvider getInstance(){
        if(INSTANCE==null){
            synchronized (LocatProvider.class){
                INSTANCE = new LocatProvider();
            }
        }
        return INSTANCE;
    }

    public void init(double latitude,double longitude ,Address address,String locat_tag){
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.locat_tag = locat_tag;
    }

    public String getAddress(){
        return address.address;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public String getLocatTag(){
        return locat_tag;
    }

    public String getLocat(){
        return latitude+"#"+longitude;
    }

}
