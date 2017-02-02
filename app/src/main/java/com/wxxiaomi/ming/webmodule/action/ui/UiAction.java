package com.wxxiaomi.ming.webmodule.action.ui;

/**
 * Created by 12262 on 2016/11/11.
 * 初始化ui
 *
 */

public class UiAction {
    public String title;
    public Right right;
    public static class Right{
        public String icon;
        public String description;
        public String callback;
    }

    @Override
    public String toString() {
        return "UiAction{" +
                "title='" + title + '\'' +
                ", right=" + right +
                '}';
    }
}
