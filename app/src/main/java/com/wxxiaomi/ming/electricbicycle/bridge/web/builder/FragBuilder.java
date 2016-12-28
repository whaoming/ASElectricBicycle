package com.wxxiaomi.ming.electricbicycle.bridge.web.builder;

import android.content.Context;
import android.os.Bundle;

import com.wxxiaomi.ming.electricbicycle.bridge.web.ComBuildImpl;


/**
 * Created by Administrator on 2016/12/1.
 */

public class FragBuilder extends ComBuildImpl {

    String url = "";
    public FragBuilder(Context context, Bundle bundle) {
        super(context);
        url = bundle.getString("url");
    }

    @Override
    public String getTargetUrl() {
        return url;
    }




}
