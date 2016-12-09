package com.wxxiaomi.ming.electricbicycle.dao;


import android.util.Log;

import com.google.gson.Gson;
import com.wxxiaomi.ming.electricbicycle.EBApplication;
import com.wxxiaomi.ming.electricbicycle.api.HttpMethods;
import com.wxxiaomi.ming.electricbicycle.common.GlobalManager;
import com.wxxiaomi.ming.electricbicycle.dao.bean.Comment;
import com.wxxiaomi.ming.electricbicycle.dao.bean.Option;
import com.wxxiaomi.ming.electricbicycle.dao.bean.Topic;
import com.wxxiaomi.ming.electricbicycle.dao.bean.User;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.dao.bean.format.InitUserInfo;
import com.wxxiaomi.ming.electricbicycle.dao.bean.format.Login;
import com.wxxiaomi.ming.electricbicycle.dao.bean.format.NearByPerson;
import com.wxxiaomi.ming.electricbicycle.dao.bean.format.Register;
import com.wxxiaomi.ming.electricbicycle.dao.constant.OptionType;
import com.wxxiaomi.ming.electricbicycle.support.aliyun.OssEngine;
import com.wxxiaomi.ming.electricbicycle.support.easemob.EmEngine;
import com.wxxiaomi.ming.electricbicycle.support.easemob.ui.LRUCache;
import com.wxxiaomi.ming.electricbicycle.dao.impl.FriendDaoImpl;
import com.wxxiaomi.ming.electricbicycle.dao.impl.UserDaoImpl;
import com.wxxiaomi.ming.electricbicycle.support.img.ImageUtil;

import java.util.List;
import java.util.UUID;

import rx.Notification;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
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

    private UserService() {
        userDao = new UserDaoImpl(EBApplication.applicationContext);
        friendDao = new FriendDaoImpl(EBApplication.applicationContext);
    }

    ;

    //获取单例
    public static UserService getInstance() {
        if (INSTANCE == null) {
            synchronized (UserService.class) {
                INSTANCE = new UserService();
            }
        }
        return INSTANCE;
    }

    /**
     * 传入em上面的好友列表
     * 然后与服务器对接来更新本地的好友数据
     *
     * @param usernames
     * @return
     */
    public Observable<Integer> UpdateFriendList(List<String> usernames) {
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
     *
     * @param list
     * @return
     */
    public Observable<Integer> InsertFriendList(List<UserCommonInfo> list) {
        return friendDao.InsertFriendList(list);
    }

    /**
     * 根据emname获取一条用户信息
     * 先判断本地用户表中是否存在，
     * 如果存在则返回
     * 不存在则向服务器获取
     *
     * @param emname
     * @return
     */
    public Observable<UserCommonInfo> getUserInfoByEname(final String emname) {
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
                .unsubscribeOn(Schedulers.io());
        return Observable.concat(userMemoryCache, userLocal, userNet)
                .first(new Func1<UserCommonInfo, Boolean>() {
                    @Override
                    public Boolean call(UserCommonInfo userCommonInfo) {
                        return userCommonInfo != null;
                    }
                })
                .flatMap(new Func1<UserCommonInfo, Observable<UserCommonInfo>>() {
                    @Override
                    public Observable<UserCommonInfo> call(UserCommonInfo userCommonInfo) {
//                        MyUserProvider.putUser(userCommonInfo);
                        userCache.put(userCommonInfo.emname, userCommonInfo);
                        return Observable.just(userCommonInfo);
                    }
                })

                ;
    }

    /**
     * 从数据库获取好友列表
     *
     * @return
     */
    public List<UserCommonInfo> getFriendList() {
        return friendDao.getFriendList();
    }

    public Observable<UserCommonInfo> getFriendInfoByEmname(final String emname) {
        return friendDao.getFriendInfoByEmname(emname);
    }

    public Observable<NearByPerson> getNearPeople(int userid, double latitude, double longitude) {
        return userDao.getNearPeople(userid, latitude, longitude);
    }

    public Observable<InitUserInfo> getUserByNameFWeb(String name) {
        return userDao.getUserCommonInfoByName(name);
    }

    public Observable<Login> Login(String username, String password) {
        return userDao.Login(username, password);
    }


    public Observable<Register> registerUser(String username, String password) {
        return userDao.registerUser(username, password);
    }

    public boolean isMyFriend(String emname) {
        return friendDao.isMyFriend(emname);
    }

    public Observable<UserCommonInfo> getUserMemoryCache(final String emname) {
        return Observable.create(new Observable.OnSubscribe<UserCommonInfo>() {
            @Override
            public void call(Subscriber<? super UserCommonInfo> subscriber) {
                Log.i("wang", "从LruCache中取对象");
                UserCommonInfo userCommonInfo = userCache.get(emname);
                if (userCommonInfo != null) {
                    Log.i("wang", "从lrucache中去到对象：" + userCommonInfo);
                }
                subscriber.onNext(userCommonInfo);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<List<Option>> getUserOptions(final int userid) {
        return HttpMethods.getInstance().getOption(userid)
                .flatMap(new Func1<List<Option>, Observable<List<Option>>>() {
                    @Override
                    public Observable<List<Option>> call(List<Option> options) {
                        //对Option解析里面的东西
                        for (Option option : options) {
                            switch (option.obj_type) {
                                case OptionType.FOOT_PRINT:
                                case OptionType.PHOTO_PUBLISH:
                                    break;
                                case OptionType.TOPIC_PUBLISH:
                                    Topic topic = new Gson().fromJson(option.json_obj, Topic.class);
                                    option.dobj = topic;
                                    break;
                                case OptionType.TOPIC_COMMENT:
                                    Topic topic1 = new Gson().fromJson(option.json_parent, Topic.class);
                                    Comment comment = new Gson().fromJson(option.json_obj, Comment.class);
                                    option.dobj = comment;
                                    option.dparent = topic1;
                                    break;
                            }
                        }

                        return Observable.just(options);
                    }
                });
    }

    public Observable<String> upLoadHeadImg(String imgPath) {
        return OssEngine.getInstance().uploadImage(UUID.randomUUID().toString() + ".jpg", imgPath);
    }

    public Observable<Integer> Login(String username, String password, boolean isEmOpen) {
        //登陆服务器
//        final String[] u = {""};
//        final String[] p = {""};
//
        //登陆服务器
        return  UserService.getInstance().Login(username, password)
                .flatMap(new Func1<Login, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(Login login) {
                        GlobalManager.getInstance().savaUser(login.userInfo);
                        return EmEngine.getInstance().LoginFromEm(login.userInfo.username, login.userInfo.password);
                    }
                })
                //从服务器获取好友列表
                .flatMap(new Func1<Boolean, Observable<List<String>>>() {
                    @Override
                    public Observable<List<String>> call(Boolean aBoolean) {
                        return EmEngine.getInstance().getContactFromEm();
                    }
                })
                .flatMap(new Func1<List<String>, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(List<String> strings) {
                        return UpdateFriendList(strings);
                    }
                });
//                .doOnNext(new Action1<Login>() {
//                    @Override
//                    public void call(Login login) {
//                        u[0] = login.userInfo.username;
//                        p[0] = login.userInfo.password;
//                    }
//                });
//        Observable<Boolean> booleanObservable = EmEngine.getInstance().LoginFromEm(u[0], p[0]);
//        return Observable.concat(flag,booleanObservable)
//                .filter(new Func1<Object, Boolean>() {
//                    @Override
//                    public Boolean call(Object o) {
//                        if(o instanceof  Login){
//                            return true;
//                        }else{
//                            return false;
//                        }
//                    }
//                })
//                .map(new Func1<Object, User>() {
//                    @Override
//                    public User call(Object login) {
//                        Login o = (Login)(login);
//                        return o.userInfo;
//                    }
//                });

//        flag.map(new Func1<Login, Login>() {
//            @Override
//            public Login call(Login login) {
//                return login;
//            }
//        });
//        flag.switchMap(new Func1<Login, Observable<?>>() {
//            @Override
//            public Observable<?> call(Login login) {
//                return null;
//            }
//        },ne)

//        EmEngine.getInstance().LoginFromEm(login.userInfo.username, login.userInfo.password);
//        flag.jo
//        flag.doOnNext(new Action1<Login>() {
//            @Override
//            public void call(Login login) {
//
//            }
//        })


//        flag.doOnEach(new Action1<Notification<? super Login>>() {
//            @Override
//            public void call(Notification<? super Login> notification) {
//                notificat
//            }
//        })
//        flag.start
//        if(isEmOpen){
//            flag.doOnNext(new Action1<Login>() {
//                @Override
//                public void call(Login login) {
//
//                }
//            })
//            return  flag
        //登录到em服务器
//                    .flatMap(new Func1<Login, Observable<Boolean>>() {
//                        @Override
//                        public Observable<Boolean> call(Login login) {
//                            info = login.userInfo;
//                            return EmEngine.getInstance().LoginFromEm(login.userInfo.username, login.userInfo.password);
//                        }
//                    })
//                    //更新好友列表
//                    .flatMap(new Func1<Boolean, Observable<Integer>>() {
//                        @Override
//                        public Observable<Integer> call(Boolean aBoolean) {
//                            return EmEngine.getInstance().updateFriend();
//                        }
//                    })
//                    .subscribeOn(Schedulers.io());
//        }else{
//            return flag;
//        }
    }

}
