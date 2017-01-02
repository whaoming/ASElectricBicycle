package com.wxxiaomi.ming.electricbicycle.improve.im.service;

import android.content.Intent;

import com.wxxiaomi.ming.electricbicycle.bridge.easemob.common.EmConstant;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.improve.im.Contract;
import com.wxxiaomi.ming.electricbicycle.service.UserFunctionProvider;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Mr.W on 2017/1/2.
 * E-maiil：122627018@qq.com
 * github：https://github.com/122627018
 */

public class UserGetOperator implements Contract.IOperator {

    private String emname;
    private Contract.IService service;
    private int startId;

    public UserGetOperator(String emname, Contract.IService service,int startId){
        this.emname = emname;
        this.service = service;
        this.startId = startId;
    }
    @Override
    public void stop() {

    }

    @Override
    public void run() {
        UserFunctionProvider.getInstance().getUserInfoByEname(emname)
                .flatMap(new Func1<UserCommonInfo, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(UserCommonInfo userCommonInfo) {
                        List<UserCommonInfo> list = new ArrayList<>();
                        list.add(userCommonInfo);
                        return UserFunctionProvider.getInstance().updateFriendList(list);
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
//                        broadcastManager.sendBroadcast(new Intent(EmConstant.ACTION_CONTACT_CHANAGED));
                        service.notifyMsg();
                    }
                });
    }
}
