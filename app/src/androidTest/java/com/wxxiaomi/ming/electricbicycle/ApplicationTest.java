package com.wxxiaomi.ming.electricbicycle;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {


    public ApplicationTest() {
        super(Application.class);


    }

    public void testInitMapSdk(){
        Log.i("wang","testInitMapSdk()");
//        SDKInitializer.initialize(getApplication().getApplicationContext());
    }
}