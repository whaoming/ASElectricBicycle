package com.wxxiaomi.ming.electricbicycle.service;


import android.util.Log;

import com.hyphenate.easeui.domain.EaseUser;
import com.wxxiaomi.ming.electricbicycle.EBApplication;
import com.wxxiaomi.ming.electricbicycle.GlobalParams;
import com.wxxiaomi.ming.electricbicycle.api.HttpMethods;
import com.wxxiaomi.ming.electricbicycle.common.util.TimeUtil;
import com.wxxiaomi.ming.electricbicycle.db.bean.InviteMessage;
import com.wxxiaomi.ming.electricbicycle.db.bean.Option;
import com.wxxiaomi.ming.electricbicycle.db.bean.User;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserLocatInfo;

import com.wxxiaomi.ming.electricbicycle.db.bean.format.FootPrintGet;
import com.wxxiaomi.ming.electricbicycle.db.bean.format.UserInfo;
import com.wxxiaomi.ming.electricbicycle.db.AppDao;
import com.wxxiaomi.ming.electricbicycle.db.FriendDao2;
import com.wxxiaomi.ming.electricbicycle.db.UserDao;
import com.wxxiaomi.ming.electricbicycle.db.impl.*;
import com.wxxiaomi.ming.electricbicycle.db.impl.FriendDaoImpl2;
import com.wxxiaomi.ming.electricbicycle.bridge.aliyun.OssEngine;
import com.wxxiaomi.ming.electricbicycle.bridge.easemob.EmHelper;
import com.wxxiaomi.ming.electricbicycle.support.cache.LRUCache;


import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 12262 on 2016/11/16.
 * 用户相关的业务逻辑
 */
public class FunctionProvider {
    private UserDao userDao;
    private static FunctionProvider INSTANCE;
//    static LRUCache<UserCommonInfo> userCache = new LRUCache<>(8);
    private FriendDao2 friendDao2;
    private AppDao appDao;

    private FunctionProvider() {
        userDao = new UserDaoImpl(EBApplication.applicationContext);
        friendDao2 = new FriendDaoImpl2(EBApplication.applicationContext);
        appDao = new AppDaoImpl(EBApplication.applicationContext);
    }

    //获取单例
    public static FunctionProvider getInstance() {
        if (INSTANCE == null) {
            synchronized (FunctionProvider.class) {
                INSTANCE = new FunctionProvider();
            }
        }
        return INSTANCE;
    }

//    public Observable<Integer> UpdateFriendList2(List<String> usernames) {
//        /**
//         * 1.usernames为传入的em服务器上面的联系人
//         * 2.对比本地数据库的好友，删除usernames里没有的
//         * 3.取出数据库里usernames的所有用户的updatetime字段
//         * 4.返回的数据格式为<emname,update_time>
//         * 5.发送给服务器这个信息，服务器返回一些好友信息(更新的，或者本地缺少的)
//         * 6.更新到数据库中,返回更新的总数
//         */
//        return friendDao.CheckFriendList(usernames)
//                //传入的参数为缺少的好友信息，需要从服务器获取获取
//                .flatMap(new Func1<List<String>, Observable<List<UserCommonInfo>>>() {
//                    @Override
//                    public Observable<List<UserCommonInfo>> call(List<String> missingUsernames) {
//                        if(missingUsernames.size()==0){
//                            return Observable.just(null);
//                        }
//                        return userDao.getUserListFromWeb(missingUsernames);
//                    }
//                })
//                //从服务器更新完好友之后，更新到本地数据库
//                .flatMap(new Func1<List<UserCommonInfo>, Observable<Integer>>() {
//                    @Override
//                    public Observable<Integer> call(List<UserCommonInfo> initUserInfo) {
//                        if(initUserInfo==null){
//                            return Observable.just(0);
//                        }
//                        return friendDao.InsertFriendList(initUserInfo);
//                    }
//                });
//    }
    /**
     * 传入em上面的好友列表
     * 然后与服务器对接来更新本地的好友数据
     *
     * @param usernames
     * @return
     */
//    public Observable<Integer> UpdateFriendList(List<String> usernames) {
//        return friendDao.CheckFriendList(usernames)
//                //传入的参数为缺少的好友信息，需要从服务器获取获取
//                .flatMap(new Func1<List<String>, Observable<List<UserCommonInfo>>>() {
//                    @Override
//                    public Observable<List<UserCommonInfo>> call(List<String> missingUsernames) {
//                        if(missingUsernames.size()==0){
//                            return Observable.just(null);
//                        }
//                        return userDao.getUserListFromWeb(missingUsernames);
//                    }
//                })
//                //从服务器更新完好友之后，更新到本地数据库
//                .flatMap(new Func1<List<UserCommonInfo>, Observable<Integer>>() {
//                    @Override
//                    public Observable<Integer> call(List<UserCommonInfo> initUserInfo) {
//                        if(initUserInfo==null){
//                            return Observable.just(0);
//                        }
//                        return friendDao.InsertFriendList(initUserInfo);
//                    }
//                });
//    }

    /**
     * 向本地数据库插入一下好友数据
     *
     * @return
     */
//    public Observable<Integer> InsertFriendList(List<UserCommonInfo> list) {
//        return friendDao.InsertFriendList(list);
//    }

    public Observable<List<InviteMessage>> getInviteMsgs(){
        return EmHelper.getInstance().getInviteMsgListRx();
    }

    /**
     * 根据emname获取一条用户信息
     * 先判断本地用户表中是否存在，
     * 如果存在则返回
     * 不存在则向服务器获取
     * @param emname
     * @return
     */
    public Observable<UserCommonInfo> getUserInfoByEname(final String emname) {
//        Observable<UserCommonInfo> userMemoryCache = getUserMemoryCache(emname);
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
//        return Observable.concat(userMemoryCache, userLocal, userNet)
        return Observable.concat(userLocal, userNet)
                .first(new Func1<UserCommonInfo, Boolean>() {
                    @Override
                    public Boolean call(UserCommonInfo userCommonInfo) {
                        return userCommonInfo != null;
                    }
                })
                .flatMap(new Func1<UserCommonInfo, Observable<UserCommonInfo>>() {
                    @Override
                    public Observable<UserCommonInfo> call(UserCommonInfo userCommonInfo) {
                        GlobalManager.getInstance().putEasyUser(userCommonInfo.emname, userCommonInfo);
//                        userCache.put(userCommonInfo.emname, userCommonInfo);
                        return Observable.just(userCommonInfo);
                    }
                })
                ;
    }

    public Observable<EaseUser> getEaseUserByEmname(final String emname){
        Log.i("wang","getEaseUserByEmname");
        Observable<EaseUser> userMemoryCache = Observable.create(new Observable.OnSubscribe<EaseUser>() {

            @Override
            public void call(Subscriber<? super EaseUser> subscriber) {
                EaseUser easyUser = GlobalManager.getInstance().getEasyUser(emname);
                Log.i("wang","从内存中获得easyUser=="+easyUser);
                subscriber.onNext(easyUser);
                subscriber.onCompleted();
            }
        });
        Observable<EaseUser> userLocal = userDao.getUserLocal(emname)
                .map(new Func1<UserCommonInfo, EaseUser>() {
                    @Override
                    public EaseUser call(UserCommonInfo userCommonInfo) {
                        Log.i("wang","从数据库中获得");
                        EaseUser user1 = new EaseUser(userCommonInfo.emname);
                        user1.setNick(userCommonInfo.nickname);
                        user1.setAvatar(userCommonInfo.avatar);
                        return user1;
                    }
                });
        Observable<EaseUser> userNet = userDao.getUserByEnameFWeb(emname)
                .flatMap(new Func1<UserCommonInfo, Observable<UserCommonInfo>>() {
                    @Override
                    public Observable<UserCommonInfo> call(UserCommonInfo userCommonInfo) {
                        Log.i("wang","从网络中获得");
                        return userDao.InsertUser(userCommonInfo);
                    }
                })
                .map(new Func1<UserCommonInfo, EaseUser>() {
                    @Override
                    public EaseUser call(UserCommonInfo userCommonInfo) {
                        EaseUser user1 = new EaseUser(userCommonInfo.emname);
                        user1.setNick(userCommonInfo.nickname);
                        user1.setAvatar(userCommonInfo.avatar);
                        return user1;
                    }
                });
        return Observable.concat(userMemoryCache, userLocal, userNet)
                .first(new Func1<EaseUser, Boolean>() {
                    @Override
                    public Boolean call(EaseUser userCommonInfo) {
                        return userCommonInfo != null;
                    }
                })
                .map(new Func1<EaseUser, EaseUser>() {
                    @Override
                    public EaseUser call(EaseUser easeUser) {

                        GlobalManager.getInstance().putEasyUser(easeUser.getUsername(),easeUser);
                        return easeUser;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                ;

    }

    /**
     * 从数据库获取好友列表
     *
     * @return
     */
//    public List<UserCommonInfo> getFriendList() {
//        return friendDao.getFriendList();
//    }

    /**
     * 从数据库获取好友列表
     *
     * @return
     */
    public List<UserCommonInfo> getEFriends() {
        return friendDao2.getEFriends();
    }

    public Observable<String> upLoadUserCover(String path) {
        return HttpMethods.getInstance().upLoadUserCover(path)
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        GlobalManager.getInstance().getUser().userCommonInfo.cover = s;
                        appDao.updateUserInfo(GlobalManager.getInstance().getUser().userCommonInfo);
                        return s;
                    }
                });
    }

    public Observable<List<UserLocatInfo>> getNearPeople(int userid, double latitude, double longitude) {
        return userDao.getNearPeople(userid, latitude, longitude);
    }

    public Observable<List<UserCommonInfo>> getUserByNameFWeb(String name) {
//        return userDao.getUserCommonInfo2ByName(name);
        return HttpMethods.getInstance().getUserCommonInfo2ByName(name);
    }

//    public Observable<UserCommonInfo> getUserMemoryCache(final String emname) {
//        return Observable.create(new Observable.OnSubscribe<UserCommonInfo>() {
//            @Override
//            public void call(Subscriber<? super UserCommonInfo> subscriber) {
//                Log.i("wang", "从LruCache中取对象");
////                UserCommonInfo userCommonInfo = GlobalManager.getInstance().getEasyUser(emname);
//                if (userCommonInfo != null) {
//                    Log.i("wang", "从lrucache中去到对象：" + userCommonInfo);
//                }
//                subscriber.onNext(userCommonInfo);
//                subscriber.onCompleted();
//            }
//        });
//    }

    public Observable<List<Option>> getUserOptions(final int userid) {
        return HttpMethods.getInstance().getOption(userid)
                .map(new Func1<List<Option>, List<Option>>() {
                    @Override
                    public List<Option> call(List<Option> options) {
                        Collections.sort(options, new Comparator<Option>() {
                            @Override
                            public int compare(Option option, Option t1) {
                                boolean after = TimeUtil.StrToDate(option.create_time).after(TimeUtil.StrToDate(t1.create_time));
                                if (after) {
                                    return -1;
                                }
                                return 0;
                            }
                        });
                        return options;
                    }
                });
    }

    public Observable<String> upLoadImgToOss(String imgPath) {
        return OssEngine.getInstance().uploadImage(UUID.randomUUID().toString() + ".jpg", imgPath);
    }

    public Observable<Integer> updateFriendList(final List<UserCommonInfo> userCommonInfo2s) {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                int i = friendDao2.updateFriendsList(userCommonInfo2s);
                subscriber.onNext(i);
            }
        });
    }

    public Observable<Integer> HandLogin(String username, String password,
                                         boolean isEmOpen, String uniqueNum) {

        final FriendDao2 dao2 = new FriendDaoImpl2(EBApplication.applicationContext);
        //登陆服务器
        return userDao.Login(username, password, uniqueNum)
                .flatMap(new Func1<User, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(User user) {
                        GlobalManager.getInstance().savaUser(user);
                        //存到数据库
//                        AppDao dao = new AppDaoImpl(EBApplication.applicationContext);
                        appDao.savaUser(user);
                        PreferenceManager.getInstance().savaUserID(user.userCommonInfo.id);
                        return EmHelper.getInstance().LoginFromEm(user.username, user.password);
                    }
                })
                //从服务器获取好友列表
                .flatMap(new Func1<Boolean, Observable<List<String>>>() {
                    @Override
                    public Observable<List<String>> call(Boolean aBoolean) {
                        return EmHelper.getInstance().getContactFromEm();
                    }
                })
                //对比本地数据库，得出键值对
                .map(new Func1<List<String>, String>() {
                    @Override
                    public String call(List<String> strings) {
                        if (strings.size() == 0) {
                            return "";
                        }
                        return dao2.getErrorFriend(strings);
                    }
                })
                //链接服务器对比
                .flatMap(new Func1<String, Observable<List<UserCommonInfo>>>() {
                    @Override
                    public Observable<List<UserCommonInfo>> call(String s) {
                        return HttpMethods.getInstance().updateuserFriend2(s);
                    }
                })
                //得到最新的好友列表
                .map(new Func1<List<UserCommonInfo>, Integer>() {
                    @Override
                    public Integer call(List<UserCommonInfo> userCommonInfo2s) {
                        dao2.updateFriendsList(userCommonInfo2s);
                        return userCommonInfo2s.size();
                    }
                })
                //将好友列表加载到内存中
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        EmHelper.getInstance().openUserCache(getEFriends());
                        return integer;
                    }
                });
    }

    public Observable<Integer> AutoLogin() {
        //自动登陆：先从本地取出之前存的user,判断user是否为空,空就要求重新登陆
        GlobalManager.getInstance().setStoken(PreferenceManager.getInstance().getShortToken());
        User localUser = appDao.getLocalUser(PreferenceManager.getInstance().getUserId());
        if (localUser == null) {
            return Observable.error(new Exception());
        } else {
            GlobalManager.getInstance().savaUser(localUser);
            return EmHelper.getInstance().getContactFromEm()
                    //对比本地数据库，得出键值对
                    .map(new Func1<List<String>, String>() {
                        @Override
                        public String call(List<String> strings) {
                            Log.i("wang","从em服务器获取的联系人的数量:"+strings.size());
                            if (strings.size() == 0) {
                                return "";
                            }
                            return friendDao2.getErrorFriend(strings);
                        }
                    })
                    //链接服务器对比
                    .flatMap(new Func1<String, Observable<List<UserCommonInfo>>>() {
                        @Override
                        public Observable<List<UserCommonInfo>> call(String s) {
                            return HttpMethods.getInstance().updateuserFriend2(s);
                        }
                    })
                    //得到最新的好友列表
                    .map(new Func1<List<UserCommonInfo>, Integer>() {
                        @Override
                        public Integer call(List<UserCommonInfo> userCommonInfo2s) {
                            friendDao2.updateFriendsList(userCommonInfo2s);
                            return userCommonInfo2s.size();
                        }
                    }).map(new Func1<Integer, Integer>() {
                        @Override
                        public Integer call(Integer integer) {
                            EmHelper.getInstance().openUserCache(getEFriends());
                            return integer;
                        }
                    });
        }

    }

    public Observable<Integer> updateUserInfo(final String name, final String head, final String description, final String city) {
        Map<String, String> pars = new HashMap<>();
        if (name != null) {
            pars.put("nickname", name);
        }
        if (head != null) {
            pars.put("avatar", head);
        }

        if (description != null) {
            pars.put("description", description);
        }
        if (city != null) {
            pars.put("city", city);
        }
        return HttpMethods.getInstance().updateUserInfo(pars)
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        if (name != null) {
//                            pars.put("nickname",name);
                            GlobalManager.getInstance().getUser().userCommonInfo.nickname = name;
                        }
                        if (head != null) {
//                            pars.put("avatar",head);
                            Log.i("wang", "head:" + head);
                            GlobalManager.getInstance().getUser().userCommonInfo.avatar = head;
                        }

                        if (description != null) {
//                            pars.put("description",description);
                            GlobalManager.getInstance().getUser().userCommonInfo.description = description;
                        }
                        if (city != null) {
                            GlobalManager.getInstance().getUser().userCommonInfo.city = city;
                        }
//                        GlobalManager.getInstance().getUser().userCommonInfo.nickname = name;
//                        GlobalManager.getInstance().getUser().userCommonInfo.avatar = head;
                        return 1;
                    }
                }).map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        AppDao dao = new AppDaoImpl(EBApplication.applicationContext);
                        UserCommonInfo userCommonInfo = GlobalManager.getInstance().getUser().userCommonInfo;
                        return dao.updateUserInfo(userCommonInfo);
                    }
                });
    }

    public List<UserCommonInfo> getFriendList() {
        return friendDao2.getFriendList();
    }

    public Observable<UserCommonInfo> getUserInfoById(int userid) {
        return HttpMethods.getInstance().getUserInfoById(userid);
    }

    public Observable<UserInfo> getUserInfoAndOption(int userid) {
        return HttpMethods.getInstance().getUserInfoAndOption(userid)
                .map(new Func1<UserInfo, UserInfo>() {
                    @Override
                    public UserInfo call(UserInfo userInfo) {
                        Collections.sort(userInfo.options, new Comparator<Option>() {
                            @Override
                            public int compare(Option option, Option t1) {
                                Log.i("wang", "option.create_time:" + option.create_time);
                                boolean after = TimeUtil.StrToDate(option.create_time).after(TimeUtil.StrToDate(t1.create_time));
                                if (after) {
                                    return 1;
                                }
                                return 0;
                            }
                        });
                        return userInfo;
                    }
                });
    }

    public Observable<FootPrintGet> getUserFootPrint(int userid) {
        return HttpMethods.getInstance().getUserFootPrint(userid);
    }
}
