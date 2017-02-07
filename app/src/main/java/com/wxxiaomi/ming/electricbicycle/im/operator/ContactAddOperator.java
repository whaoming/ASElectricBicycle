package com.wxxiaomi.ming.electricbicycle.im.operator;

import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.im.ImHelper1;
import com.wxxiaomi.ming.electricbicycle.manager.UserFunctionProvider;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Mr.W on 2017/1/2.
 * E-maiil：122627018@qq.com
 * github：https://github.com/122627018
 * 发送添加好友的操作
 */

public class ContactAddOperator implements Contract.IOperator {

    private String emname;
    private Contract.IService service;
    private int startId;

    public ContactAddOperator(String emname, Contract.IService service, int startId){
        this.emname = emname;
        this.service = service;
        this.startId = startId;
    }
    @Override
    public void stop() {

    }

    @Override
    public void run() {
        //同意添加好友的操作
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
                        service.notifyMsg(ImHelper1.ACTION_INVITE_AGREE);
                    }
                });
    }
}
