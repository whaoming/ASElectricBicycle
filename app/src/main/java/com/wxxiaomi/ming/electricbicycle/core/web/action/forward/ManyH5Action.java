package com.wxxiaomi.ming.electricbicycle.core.web.action.forward;

import java.io.Serializable;
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
    public ManyH5Action(){

    }

}
