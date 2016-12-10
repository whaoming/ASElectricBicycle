package com.wxxiaomi.ming.electricbicycle.dao.db.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.api.HttpMethods;
import com.wxxiaomi.ming.electricbicycle.dao.bean.User;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserLocatInfo;

import com.wxxiaomi.ming.electricbicycle.dao.db.UserDao;
import com.wxxiaomi.ming.electricbicycle.dao.db.util.DbOpenHelper;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by 12262 on 2016/11/16.
 */

public class UserDaoImpl implements UserDao {

    Context context;
    DbOpenHelper helper;

    public UserDaoImpl(Context context) {
        this.context = context;
        helper = DbOpenHelper.getInstance(context);
    }

    @Override
    public Observable<UserCommonInfo> getUserByEnameFWeb(String emname) {
        Log.i("wang", "UserDaoImpl->getUserByEnameFWeb(emname),emname:" + emname);
        return HttpMethods.getInstance().getUserCommonInfoByEmname(emname)
                .flatMap(new Func1<List<UserCommonInfo>, Observable<UserCommonInfo>>() {
                    @Override
                    public Observable<UserCommonInfo> call(List<UserCommonInfo> initUserInfo) {
                        Log.i("wang","getUserByEnameFWeb:"+initUserInfo.get(0).toString());
                        return Observable.just(initUserInfo.get(0));
                    }
                });
    }

    @Override
    public Observable<List<UserCommonInfo>> getUserListFromWeb(List<String> usernames) {
        return HttpMethods.getInstance().getUserListByEmList(usernames);
    }

    @Override
    public Observable<List<UserLocatInfo>> getNearPeople(int userid, double latitude, double longitude) {
        return HttpMethods.getInstance().getNearByFromServer(userid, latitude, longitude);
    }

    @Override
    public Observable<User> Login(String username, String password) {
        return HttpMethods.getInstance().login(username, password);
    }

    @Override
    public Observable<Boolean> isTempUserExist(final String emname) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                SQLiteDatabase db = helper.getWritableDatabase();
                if (db.isOpen()) {
                    Cursor query = db.query(UserDao.TABLE_NAME, new String[]{"emname"}, "emname=?", new String[]{emname}, null, null, null);
                    if (query.moveToNext()) {
                        subscriber.onNext(true);
                    } else {
                        subscriber.onNext(false);
                    }
                }
            }
        });
    }

    @Override
    public Observable<UserCommonInfo> getUserLocal(final String emname) {
        Log.i("wang", "getUserLocal->info:" + emname);
        return Observable.create(new Observable.OnSubscribe<UserCommonInfo>() {
            @Override
            public void call(Subscriber<? super UserCommonInfo> subscriber) {
                SQLiteDatabase db = helper.getReadableDatabase();
                if (db.isOpen()) {
                    Cursor cursor = db.query(UserDao.TABLE_NAME
                            , new String[]{UserDao.COLUMN_NAME_ID, UserDao.COLUMN_NAME_NAME
                                    , UserDao.COLUMN_NAME_EMNAME, UserDao.COLUMN_NAME_HEAD},
                            UserDao.COLUMN_NAME_EMNAME + "=?", new String[]{emname}, null, null, null);
                    if (cursor.moveToNext()) {
                        int id = cursor.getInt(cursor.getColumnIndex(UserDao.COLUMN_NAME_ID));
                        String name = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_NAME));
                        String emname1 = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_EMNAME));
                        String head = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_HEAD));
                        UserCommonInfo info = new UserCommonInfo();
                        info.id = id;
                        info.emname = emname1;
                        info.head = head;
                        info.name = name;
                        Log.i("wang", "getUserLocal->info:" + info.toString());
                        subscriber.onNext(info);
                    }else{
                        Log.i("wang", "getUserLocal->本地查询不到这个用户emname:" + emname);
                        subscriber.onCompleted();
                    }
                }else {
                    Log.i("wang", "getUserLocal->本地查询不到这个用户emname:" + emname);
                    subscriber.onCompleted();
                }

            }
        });
    }

    @Override
    public Observable<List<UserCommonInfo>> getUserCommonInfoByName(String name) {
        return HttpMethods.getInstance().getUserCommonInfoByName(name);
    }

//    @Override
//    public Observable<Register> registerUser(String username, String password) {
//        return HttpMethods.getInstance().registerUser(username, password);
//    }

    @Override
    public Observable<String> upLoadHead(String fileName, String filePath) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), new File(filePath));
        return HttpMethods.getInstance().upLoadHead(fileName, requestBody);
    }

    @Override
    public Observable<UserCommonInfo> InsertUser(final UserCommonInfo info) {
        return Observable.create(new Observable.OnSubscribe<UserCommonInfo>() {
            @Override
            public void call(Subscriber<? super UserCommonInfo> subscriber) {
                SQLiteDatabase db = helper.getWritableDatabase();
                Log.i("wang", "InsertUser->新插入的临时用户:" + info.toString());
                ContentValues values = new ContentValues();
                values.put(UserDao.COLUMN_NAME_ID, info.id);
                values.put(UserDao.COLUMN_NAME_NAME, info.name);
                values.put(UserDao.COLUMN_NAME_EMNAME, info.emname);
                values.put(UserDao.COLUMN_NAME_HEAD, info.head);
                db.insert(UserDao.TABLE_NAME, null, values);
                subscriber.onNext(info);
            }

        });
    }

//    @Override
//    public UserCommonInfo getUserLocalNoRx(String emname) {
//        SQLiteDatabase db = helper.getReadableDatabase();
//        if (db.isOpen()) {
//            Cursor cursor = db.query(UserDao.TABLE_NAME
//                    , new String[]{UserDao.COLUMN_NAME_ID, UserDao.COLUMN_NAME_NAME
//                            , UserDao.COLUMN_NAME_EMNAME, UserDao.COLUMN_NAME_HEAD},
//                    UserDao.COLUMN_NAME_EMNAME + "=?", new String[]{emname}, null, null, null);
//            if (cursor.moveToNext()) {
//                int id = cursor.getInt(cursor.getColumnIndex(UserDao.COLUMN_NAME_ID));
//                String name = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_NAME));
//                String emname1 = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_EMNAME));
//                String head = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_HEAD));
//                UserCommonInfo info = new UserCommonInfo();
//                info.id = id;
//                info.emname = emname1;
//                info.head = head;
//                info.name = name;
//                return info;
//            }
//        }
//        return null;
//    }
}
