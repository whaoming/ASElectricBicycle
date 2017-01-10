package com.wxxiaomi.ming.electricbicycle.improve.update;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/10.
 */

public class Version implements Serializable {
    private String code;
    private String name;
    private String release;
    private String message;
    private String downloadUrl;
    private String publishDate;
    private String splash_img_url;
    private String splash_img_action;

    public String getSplash_img_url() {
        return splash_img_url;
    }

    public void setSplash_img_url(String splash_img_url) {
        this.splash_img_url = splash_img_url;
    }

    public String getSplash_img_action() {
        return splash_img_action;
    }

    public void setSplash_img_action(String splash_img_action) {
        this.splash_img_action = splash_img_action;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {


        this.publishDate = publishDate;
    }


    @Override
    public String toString() {
        return "Version{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", release='" + release + '\'' +
                ", message='" + message + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", splash_img_url='" + splash_img_url + '\'' +
                ", splash_img_action='" + splash_img_action + '\'' +
                '}';
    }
}
