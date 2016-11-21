package com.wxxiaomi.ming.electricbicycle.dao;


import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.EBApplication;
import com.wxxiaomi.ming.electricbicycle.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.bean.format.InitUserInfo;
import com.wxxiaomi.ming.electricbicycle.bean.format.Login;
import com.wxxiaomi.ming.electricbicycle.bean.format.NearByPerson;
import com.wxxiaomi.ming.electricbicycle.bean.format.Register;
import com.wxxiaomi.ming.electricbicycle.core.em.LRUCache;
import com.wxxiaomi.ming.electricbicycle.dao.impl.FriendDaoImpl;
import com.wxxiaomi.ming.electricbicycle.dao.impl.UserDaoImpl;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 12262 on 2016/11/16.
 * 提供用户相关的服务
 */

public class UserService {
    private UserDao userDao;
    private FriendDao friendDao;
    private static UserService INSTANCE;
    static LRUCache<UserCommonInfo> userCache = new LRUCache<>(8);
    private UserService(){
        userDao = new UserDaoImpl(EBApplication.applicationContext);
        friendDao = new FriendDaoImpl(EBApplication.applicationContext);
    };
    //获取单例
    public static UserService getInstance() {
        if(INSTANCE==null){
            synchronized (UserService.class) {
                INSTANCE = new UserService();
            }
        }
        return INSTANCE;
    }

    /**
     * 传入em上面的好友列表
     * 然后与服务器对接来更新本地的好友数据
     * @param usernames
     * @return
     */
    public Observable<Integer> UpdateFriendList(List<String> usernames){
        return friendDao.CheckFriendList(usernames)
                //传入的参数为缺少的好友信息，需要从服务器获取获取
                .flatMap(new Func1<List<String>, Observable<InitUserInfo>>() {
                    @Override
                    public Observable<InitUserInfo> call(List<String> missingUsernames) {
                        return userDao.getUserListFromWeb(missingUsernames);
                    }
                })
                .flatMap(new Func1<InitUserInfo, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(InitUserInfo initUserInfo) {
                        return friendDao.InsertFriendList(initUserInfo.friendList);
                    }
                });
    }

    /**
     * 向本地数据库插入一下好友数据
     * @param list
     * @return
     */
    public Observable<Integer> InsertFriendList(List<UserCommonInfo> list){
        return friendDao.InsertFriendList(list);
    }

    /**
     * 根据emname获取一条用户信息
     * 先判断本地用户表中是否存在，
     * 如果存在则返回
     * 不存在则向服务器获取
     * @param emname
     * @return
     */
    public Observable<UserCommonInfo> getUserInfoByEname(final String emname){
        Observable<UserCommonInfo> userMemoryCache = getUserMemoryCache(emname);
        Observable<UserCommonInfo> userLocal = userDao.getUserLocal(emname);
        Observable<UserCommonInfo> userNet = userDao.getUserByEnameFWeb(emname)
                .flatMap(new Func1<UserCommonInfo, Observable<UserCommonInfo>>() {
                    @Override
                    public Observable<UserCommonInfo> call(UserCommonInfo userCommonInfo) {
                        return userDao.InsertUser(userCommonInfo);
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                ;
        return Observable.concat(userMemoryCache,userLocal,userNet)
                .first(new Func1<UserCommonInfo, Boolean>() {
                    @Override
                    public Boolean call(UserCommonInfo userCommonInfo) {
                        return userCommonInfo!=null;
                    }
                })
                .flatMap(new Func1<UserCommonInfo, Observable<UserCommonInfo>>() {
                    @Override
                    public Observable<UserCommonInfo> call(UserCommonInfo userCommonInfo) {
//                        MyUserProvider.putUser(userCommonInfo);
                        userCache.put(userCommonInfo.emname,userCommonInfo);
                        return Observable.just(userCommonInfo);
                    }
                })

                ;
    }

    /**
     * 从数据库获取好友列表
     * @return
     */
    public List<UserCommonInfo> getFriendList(){
        return friendDao.getFriendList();
    }

    public Observable<UserCommonInfo> getFriendInfoByEmname(final String emname){
        return friendDao.getFriendInfoByEmname(emname);
    }

    public Observable<NearByPerson> getNearPeople(int userid, double latitude, double longitude){
        return userDao.getNearPeople(userid, latitude, longitude);
    }

    public Observable<InitUserInfo> getUserByNameFWeb(String name){
        return userDao.getUserCommonInfoByName(name);
    }

    public Observable<Login> Login(String username, String password){
        return  userDao.Login(username, password);
    }

    public Observable<String> upLoadHead(String fileName,String filePath){
        return userDao.upLoadHead(fileName, filePath);
    }

    public Observable<Register> registerUser(String username, String password){
        return userDao.registerUser(username, password);
    }

    public boolean isMyFriend(String emname){
        return friendDao.isMyFriend(emname);
    }

//    public UserCommonInfo getFriendLocal(String emname){
//        UserCommonInfo info = friendDao.getFriendLocal(emname);
//        if(info==null){
//            return userDao.getUserLocalNoRx(emname);
//        }
//        return info;
//    }

    public Observable<UserCommonInfo> getUserMemoryCache(final String emname){
        return Observable.create(new Observable.OnSubscribe<UserCommonInfo>() {
            @Override
            public void call(Subscriber<? super UserCommonInfo> subscriber) {
                Log.i("wang", "从LruCache中取对象");
                UserCommonInfo userCommonInfo = userCache.get(emname);
                if(userCommonInfo!=null) {
                    Log.i("wang", "从lrucache中去到对象："+userCommonInfo);
                }
                subscriber.onNext(userCommonInfo);
                subscriber.onCompleted();
            }
        });
    }



}
