package com.wxxiaomi.ming.electricbicycle.api;

import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.EBApplication;
import com.wxxiaomi.ming.electricbicycle.common.util.RxUnitTestTools;
import com.wxxiaomi.ming.electricbicycle.dao.bean.OptionLogs;

import junit.framework.TestCase;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by 12262 on 2016/11/8.
 */
public class HttpMethodsTest extends TestCase {
    public void setUp() throws Exception {
        //super.setUp();
        RxUnitTestTools.openRxTools();
        // EBApplication.getInstance().onCreate();;
    }

    public void testPublishTopic(){

    }
    public void testGetOptionLogs(){
        HttpMethods.getInstance().optionLogs(25)
                .subscribe(new Action1<List<OptionLogs>>() {
                    @Override
                    public void call(List<OptionLogs> optionLogses) {
                        for(OptionLogs item : optionLogses){
                            Log.i("wang",item.toString());
                        }
                    }
                });
    }


}