package com.wxxiaomi.ming.webmodule;

import android.content.Intent;
import android.view.ViewGroup;

import com.wxxiaomi.ming.webmodule.builder.Builder;

/**
 * Created by Administrator on 2016/12/1.
 */

public class Director {
    private Builder builder;
    public Director(Builder builder){
        this.builder = builder;
    }

    public ViewGroup construct(){
        return builder.buildView();
    }

    public void PageInit(){
        builder.initPageData();
        builder.registerMethod();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        builder.onActivityResult(requestCode,resultCode,data);
    }
}
