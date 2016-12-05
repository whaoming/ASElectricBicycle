package com.wxxiaomi.ming.electricbicycle.support.baidumap;

import com.baidu.location.Address;

/**
 * Created by whaoming on 2016/12/5.
 * 所有需要位置信息的都经由这个类管理
 */
public class LocationUtil {
    private double latitude;
    private double longitude;
    private Address address;
    private String locat_tag;
    public static LocationUtil INSTANCE;
    private LocationUtil(){}

    public static LocationUtil getInstance(){
        if(INSTANCE==null){
            synchronized (LocationUtil.class){
                INSTANCE = new LocationUtil();
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
