package com.wxxiaomi.ming.webmodule.action.forward;

import java.util.List;

/**
 * Created by 12262 on 2016/11/14.
 */

public class ManyH5Action extends ForwardAction {
    public List<String> pages;
    public List<String> datas;
    public int pageCount;
    public String title;
    public List<String> tabs;

    public ManyH5Action(List<String> pages, List<String> datas, int pageCount, String title, List<String> tabs) {
        this.pages = pages;
        this.datas = datas;
        this.pageCount = pageCount;
        this.title = title;
        this.tabs = tabs;
    }

    @Override
    public String toString() {
        return "ManyH5Action{" +
                "pages=" + pages +
                ", datas=" + datas +
                ", pageCount=" + pageCount +
                ", title='" + title + '\'' +
                ", tabs=" + tabs +
                '}';
    }
}
