package com.wxxiaomi.ming.electricbicycle.api;

import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.support.util.RxUnitTestTools;

import junit.framework.TestCase;

import rx.Observer;

/**
 * Created by 12262 on 2016/11/8.
 */
public class WebMethodsTest extends TestCase {
    public void setUp() throws Exception {
        //super.setUp();
        RxUnitTestTools.openRxTools();
        // EBApplication.getInstance().onCreate();;
    }
    public void testListTopic() throws Exception {
//        WebMethods.getInstance().listTopic()
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                        Log.i("wang","出错拉");
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        Log.i("wang","result="+s);
//                    }
//                });
    }

}