package com.wxxiaomi.ming.electricbicycle.im.notice;

import android.content.Context;

import com.wxxiaomi.ming.common.util.SharedPreferencesHelper;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/31.
 * 全局消息实体
 */

public class NoticeBean implements Serializable{
    private int invite;
    private int message;
    //回复
    private int review;
    private int like;

    public NoticeBean add(NoticeBean bean) {
        this.invite += bean.invite;
        this.message += bean.message;
        this.review += bean.review;
        this.like += bean.like;
        // 暂不累加点赞数据
        //this.like += bean.like;
        return this;
    }

    public NoticeBean save(Context context) {
        SharedPreferencesHelper.save(context, this);
        return this;
    }

    public static NoticeBean getInstance(Context context) {
        NoticeBean bean = SharedPreferencesHelper.load(context, NoticeBean.class);
        if (bean == null)
            bean = new NoticeBean();
        return bean;
    }

    void clear() {
        this.invite = 0;
        this.message = 0;
        this.review = 0;
        this.like = 0;
    }

    public int getInvite() {
        return invite;
    }

    public void setInvite(int invite) {
        this.invite = invite;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    @Override
    public String toString() {
        return "NoticeBean{" +
                "invite=" + invite +
                ", message=" + message +
                ", review=" + review +
                ", like=" + like +
                '}';
    }
}
