package com.wxxiaomi.ming.electricbicycle.dao.db;


import android.util.Log;

import com.google.gson.Gson;
import com.wxxiaomi.ming.electricbicycle.EBApplication;
import com.wxxiaomi.ming.electricbicycle.api.HttpMethods;
import com.wxxiaomi.ming.electricbicycle.common.GlobalManager;
import com.wxxiaomi.ming.electricbicycle.common.PreferenceManager;
import com.wxxiaomi.ming.electricbicycle.dao.bean.Comment;
import com.wxxiaomi.ming.electricbicycle.dao.bean.Option;
import com.wxxiaomi.ming.electricbicycle.dao.bean.Topic;
import com.wxxiaomi.ming.electricbicycle.dao.bean.User;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo2;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserLocatInfo;

import com.wxxiaomi.ming.electricbicycle.dao.constant.OptionType;
import com.wxxiaomi.ming.electricbicycle.dao.db.impl.*;
import com.wxxiaomi.ming.electricbicycle.dao.db.impl.FriendDaoImpl2;
import com.wxxiaomi.ming.electricbicycle.support.aliyun.OssEngine;
import com.wxxiaomi.ming.electricbicycle.support.easemob.EmHelper2;
import com.wxxiaomi.ming.electricbicycle.support.common.cache.LRUCache;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 12262 on 2016/11/16.
 * 用户相关的业务逻辑
 */

public class UserService {
    private UserDao userDao;
    private static UserService INSTANCE;
    static LRUCache<UserCommonInfo2> userCache = new LRUCache<>(8);
    private FriendDao2 friendDao2;
    private AppDao appDao;

    private UserService() {
        userDao = new UserDaoImpl(EBApplication.applicationContext);
//        friendDao = new FriendDaoImpl(EBApplication.applicationContext);
        friendDao2 = new FriendDaoImpl2(EBApplication.applicationContext);
        appDao = new AppDaoImpl(EBApplication.applicationContext);
    }

    //获取单例
    public static UserService getInstance() {
        if (INSTANCE == null) {
            synchronized (UserService.class) {
                INSTANCE = new UserService();
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
     * @param list
     * @return
     */
//    public Observable<Integer> InsertFriendList(List<UserCommonInfo2> list) {
//        return friendDao.InsertFriendList(list);
//    }

    /**
     * 根据emname获取一条用户信息
     * 先判断本地用户表中是否存在，
     * 如果存在则返回
     * 不存在则向服务器获取
     *
     * @param emname
     * @return
     */
    public Observable<UserCommonInfo2> getUserInfoByEname(final String emname) {
        Observable<UserCommonInfo2> userMemoryCache = getUserMemoryCache(emname);
        Observable<UserCommonInfo2> userLocal = userDao.getUserLocal(emname);
        Observable<UserCommonInfo2> userNet = userDao.getUserByEnameFWeb(emname)
                .flatMap(new Func1<UserCommonInfo2, Observable<UserCommonInfo2>>() {
                    @Override
                    public Observable<UserCommonInfo2> call(UserCommonInfo2 userCommonInfo) {
                        return userDao.InsertUser(userCommonInfo);
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
        return Observable.concat(userMemoryCache, userLocal, userNet)
                .first(new Func1<UserCommonInfo2, Boolean>() {
                    @Override
                    public Boolean call(UserCommonInfo2 userCommonInfo) {
                        return userCommonInfo != null;
                    }
                })
                .flatMap(new Func1<UserCommonInfo2, Observable<UserCommonInfo2>>() {
                    @Override
                    public Observable<UserCommonInfo2> call(UserCommonInfo2 userCommonInfo) {
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
//    public List<UserCommonInfo2> getFriendList() {
//        return friendDao.getFriendList();
//    }

    /**
     * 从数据库获取好友列表
     *
     * @return
     */
    public List<UserCommonInfo2> getEFriends() {
        return friendDao2.getEFriends();
    }


//    public Observable<UserCommonInfo2> getFriendInfoByEmname(final String emname) {
//        return friendDao.getFriendInfoByEmname(emname);
//    }

    public Observable<List<UserLocatInfo>> getNearPeople(int userid, double latitude, double longitude) {
        return userDao.getNearPeople(userid, latitude, longitude);
    }

    public Observable<List<UserCommonInfo2>> getUserByNameFWeb(String name) {
        return userDao.getUserCommonInfo2ByName(name);
    }

//    public Observable<User> Login(String username, String password,String num) {
//        return userDao.Login(username, password,num);
//    }


//    public Observable<Register> registerUser(String username, String password) {
//        return userDao.registerUser(username, password);
//    }

//    public boolean isMyFriend(String emname) {
//        return friendDao.isMyFriend(emname);
//    }

    public Observable<UserCommonInfo2> getUserMemoryCache(final String emname) {
        return Observable.create(new Observable.OnSubscribe<UserCommonInfo2>() {
            @Override
            public void call(Subscriber<? super UserCommonInfo2> subscriber) {
                Log.i("wang", "从LruCache中取对象");
                UserCommonInfo2 userCommonInfo = userCache.get(emname);
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

//    public Observable<Integer> Login(String username, String password, boolean isEmOpen,String uniqueNum) {
//        //登陆服务器
//        return  Login(username, password,uniqueNum)
//                .flatMap(new Func1<User, Observable<Boolean>>() {
//                    @Override
//                    public Observable<Boolean> call(User user) {
//                        GlobalManager.getInstance().savaUser(user);
//                        //存到数据库
//                        AppDao dao = new AppDaoImpl(EBApplication.applicationContext);
//                        dao.savaUser(user);
//                        return EmHelper2.getInstance().LoginFromEm(user.username, user.password);
//                    }
//                })
//                //从服务器获取好友列表
//                .flatMap(new Func1<Boolean, Observable<List<String>>>() {
//                    @Override
//                    public Observable<List<String>> call(Boolean aBoolean) {
//                        return EmHelper2.getInstance().getContactFromEm();
//                    }
//                })
//                .flatMap(new Func1<List<String>, Observable<Integer>>() {
//                    @Override
//                    public Observable<Integer> call(List<String> strings) {
//                        if(strings.size()==0){
//                            return Observable.just(0);
//                        }
//                        return UpdateFriendList(strings);
//                    }
//                });
//    }

//    public List<UserCommonInfo2> getUserFriends(){
//        return friendDao2.getFriendList();
//    }


    public Observable<Integer> updateFriendList(final List<UserCommonInfo2> userCommonInfo2s){
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                int i = friendDao2.updateFriendsList(userCommonInfo2s);
                subscriber.onNext(i);
            }
        });
    }
    public Observable<Integer> HandLogin(String username, String password,
                                         boolean isEmOpen,String uniqueNum) {

        final FriendDao2 dao2 = new FriendDaoImpl2(EBApplication.applicationContext);
        //登陆服务器
        return  userDao.Login(username,password,uniqueNum)
                .flatMap(new Func1<User, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(User user) {
                        Log.i("wang","登陆到服务器后返回的User："+user);
                        GlobalManager.getInstance().savaUser(user);
                        //存到数据库
//                        AppDao dao = new AppDaoImpl(EBApplication.applicationContext);
                        appDao.savaUser(user);
                        PreferenceManager.getInstance().savaUserID(user.userCommonInfo.id);
                        return EmHelper2.getInstance().LoginFromEm(user.username, user.password);
                    }
                })
                //从服务器获取好友列表
                .flatMap(new Func1<Boolean, Observable<List<String>>>() {
                    @Override
                    public Observable<List<String>> call(Boolean aBoolean) {
                        return EmHelper2.getInstance().getContactFromEm();
                    }
                })
                //对比本地数据库，得出键值对
                .map(new Func1<List<String>, String>() {
                    @Override
                    public String call(List<String> strings) {
                        if(strings.size()==0){
                            return "";
                        }
                        return dao2.getErrorFriend(strings);
                    }
                })
                //链接服务器对比
                .flatMap(new Func1<String, Observable<List<UserCommonInfo2>>>() {
                    @Override
                    public Observable<List<UserCommonInfo2>> call(String s) {
                        return HttpMethods.getInstance().updateuserFriend2(s);
                    }
                })
                //得到最新的好友列表
                .map(new Func1<List<UserCommonInfo2>, Integer>() {
                    @Override
                    public Integer call(List<UserCommonInfo2> userCommonInfo2s) {
                        dao2.updateFriendsList(userCommonInfo2s);
                        return userCommonInfo2s.size();
                    }
                })
                //将好友列表加载到内存中
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        EmHelper2.getInstance().openUserCache(getEFriends());
                        return integer;
                    }
                });
    }

    public Observable<Integer> AutoLogin(){
        //自动登陆：先从本地取出之前存的user,判断user是否为空,空就要求重新登陆
        Log.i("wang","从数据库里存的userid："+PreferenceManager.getInstance().getUserId());
        User localUser = appDao.getLocalUser(PreferenceManager.getInstance().getUserId());
        if(localUser==null){
            Log.i("wang","从数据库取不到user");
            return Observable.error(new Exception());
        }else{
            GlobalManager.getInstance().savaUser(localUser);
            return EmHelper2.getInstance().getContactFromEm()
                    //对比本地数据库，得出键值对
                    .map(new Func1<List<String>, String>() {
                        @Override
                        public String call(List<String> strings) {
                            if(strings.size()==0){
                                return "";
                            }
                            return friendDao2.getErrorFriend(strings);
                        }
                    })
                    //链接服务器对比
                    .flatMap(new Func1<String, Observable<List<UserCommonInfo2>>>() {
                        @Override
                        public Observable<List<UserCommonInfo2>> call(String s) {
                            return HttpMethods.getInstance().updateuserFriend2(s);
                        }
                    })
                    //得到最新的好友列表
                    .map(new Func1<List<UserCommonInfo2>, Integer>() {
                        @Override
                        public Integer call(List<UserCommonInfo2> userCommonInfo2s) {
                            friendDao2.updateFriendsList(userCommonInfo2s);
                            return userCommonInfo2s.size();
                        }
                    }).map(new Func1<Integer, Integer>() {
                        @Override
                        public Integer call(Integer integer) {
                            EmHelper2.getInstance().openUserCache(getEFriends());
                            return integer;
                        }
                    });
        }

    }

    public Observable<Integer> updateUserInfo(final String name, final String head, final String emname){
        Map<String,String> pars = new HashMap<>();
        pars.put("name",name);
        pars.put("head",head);
        pars.put("emname",emname);
        return HttpMethods.getInstance().updateUserInfo(pars)
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        GlobalManager.getInstance().getUser().userCommonInfo.nickname = name;
                        GlobalManager.getInstance().getUser().userCommonInfo.avatar = head;
                        return 1;
                    }
                }).map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        AppDao dao = new AppDaoImpl(EBApplication.applicationContext);
                        UserCommonInfo2 userCommonInfo = GlobalManager.getInstance().getUser().userCommonInfo;
                        userCommonInfo.avatar = head;
                        userCommonInfo.nickname = name;

                        return dao.updateUserInfo(userCommonInfo);
                    }
                });
    }

    public List<UserCommonInfo2> getFriendList() {
        return friendDao2.getFriendList();
    }
}
