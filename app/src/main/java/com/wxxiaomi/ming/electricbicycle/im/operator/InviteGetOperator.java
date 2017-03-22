package com.wxxiaomi.ming.electricbicycle.im.operator;

import com.hyphenate.easeui.domain.EaseUser;
import com.wxxiaomi.ming.electricbicycle.EBApplication;
import com.wxxiaomi.ming.electricbicycle.db.InviteMessgeDao;
import com.wxxiaomi.ming.electricbicycle.db.bean.InviteMessage;
import com.wxxiaomi.ming.electricbicycle.db.impl.InviteMessgeDaoImpl2;
import com.wxxiaomi.ming.electricbicycle.im.ImHelper1;
import com.wxxiaomi.ming.electricbicycle.manager.UserFunctionProvider;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/1/3.
 */

public class InviteGetOperator implements Contract.IOperator {
    private String emname;
    private Contract.IService service;
    private int startId;
    private String reason;

    public InviteGetOperator(String emname,String reason, Contract.IService service, int startId){
        this.emname = emname;
        this.service = service;
        this.startId = startId;
        this.reason = reason;
    }

    @Override
    public void stop() {

    }

    @Override
    public void run() {
//        Log.i("wang","wang");
//        final Observable<Integer> objectObservable2 = InviteMessgeDaoImpl.getInstance().getUnreadNotifyCount()
//                .flatMap(new Func1<Integer, Observable<Integer>>() {
//                    @Override
//                    public Observable<Integer> call(Integer integer) {
//                        return InviteMessgeDaoImpl.getInstance().saveUnreadMessageCount(integer + 1);
//                    }
//                });
        //获取此用户的公共信息，封装msg存储，增加未读消息数量
        UserFunctionProvider.getInstance().getEaseUserByEmname(emname)
                .flatMap(new Func1<EaseUser, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(EaseUser easeUser) {
                        InviteMessage msg = new InviteMessage();
                        msg.setFrom(emname);
                        msg.setTime(System.currentTimeMillis());
                        msg.setReason(reason);
                        msg.setNickname(easeUser.getNick());
                        msg.setAvatar(easeUser.getAvatar());
                        InviteMessgeDao dao = new InviteMessgeDaoImpl2(EBApplication.applicationContext);
                        dao.saveMessage(msg);
                        return Observable.just(1);
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        service.notifyMsg(ImHelper1.ACTION_INVITE);
                    }
                });


//        //存储信息
//        Observable<Integer> integerObservable = InviteMessgeDaoImpl.getInstance().saveMessage(msg);
//        //增加未读消息数量
//
//        //从服务器获取用户公共信息并存到本地服务器
//        Observable<EaseUser> userCommonInfoObservable = UserFunctionProvider.getInstance().getEaseUserByEmname(emname
//        );
//
//        Observable.zip(integerObservable, userCommonInfoObservable, objectObservable2, new Func3<Integer, EaseUser, Integer, EaseUser>() {
//
//            @Override
//            public EaseUser call(Integer integer, EaseUser userCommonInfo, Integer integer2) {
//                return userCommonInfo;
//            }
//        })
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<EaseUser>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                        Log.i("wang", "在处理收到好友消息的过程中出错了");
//                    }
//                    @Override
//                    public void onNext(EaseUser userCommonInfo) {
//                        service.notifyMsg(ImHelper1.ACTION_INVITE);
//                    }
//                })
//        ;
    }
}
