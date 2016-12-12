package com.wxxiaomi.ming.electricbicycle.api;

import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.common.util.RxUnitTestTools;
import com.wxxiaomi.ming.electricbicycle.dao.db.UserService;
import com.wxxiaomi.ming.electricbicycle.dao.bean.Option;

import java.util.List;

import rx.Observer;


/**
 * Created by Administrator on 2016/12/7.
 */
public class HttpMethodsTest {

    public void setUp() throws Exception {
        RxUnitTestTools.openRxTools();
    }
    @org.junit.Test
    public void getOption() throws Exception {
//       HttpMethods.getInstance().getOption(25)
//               .subscribe(new Observer<List<Option>>() {
//                   @Override
//                   public void onCompleted() {
//
//                   }
//
//                   @Override
//                   public void onError(Throwable e) {
//                        e.printStackTrace();;
//                   }
//
//                   @Override
//                   public void onNext(List<Option> options) {
//                       for(Option o : options){
//                           Log.i("wang",o.toString());
//                       }
//                   }
//               });
        UserService.getInstance().getUserOptions(25)
                .subscribe(new Observer<List<Option>>() {
                   @Override
                   public void onCompleted() {

                   }

                   @Override
                   public void onError(Throwable e) {
                        e.printStackTrace();;
                   }

                   @Override
                   public void onNext(List<Option> options) {
                       for(Option o : options){
                           Log.i("wang",o.toString());
                       }
                   }
               });
    }

}