package com.wxxiaomi.ming.electricbicycle.dao.db.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.api.HttpMethods;
import com.wxxiaomi.ming.electricbicycle.dao.bean.User;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo2;
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
    public Observable<UserCommonInfo2> getUserByEnameFWeb(String emname) {
        return HttpMethods.getInstance().getUserCommonInfo2ByEmname(emname)
                .flatMap(new Func1<List<UserCommonInfo2>, Observable<UserCommonInfo2>>() {
                    @Override
                    public Observable<UserCommonInfo2> call(List<UserCommonInfo2> initUserInfo) {
                        return Observable.just(initUserInfo.get(0));
                    }
                });
    }

    @Override
    public Observable<List<UserCommonInfo2>> getUserListFromWeb(List<String> usernames) {
        return HttpMethods.getInstance().getUserListByEmList(usernames);
    }

    @Override
    public Observable<List<UserLocatInfo>> getNearPeople(int userid, double latitude, double longitude) {
        return HttpMethods.getInstance().getNearByFromServer(userid, latitude, longitude);
    }

    @Override
    public Observable<User> Login(String username, String password,String num) {
        return HttpMethods.getInstance().login(username, password,num);
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
    public Observable<UserCommonInfo2> getUserLocal(final String emname) {
        return Observable.create(new Observable.OnSubscribe<UserCommonInfo2>() {
            @Override
            public void call(Subscriber<? super UserCommonInfo2> subscriber) {
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
                        UserCommonInfo2 info = new UserCommonInfo2();
                        info.id = id;
                        info.emname = emname1;
                        info.avatar = head;
                        info.nickname = name;
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
    public Observable<List<UserCommonInfo2>> getUserCommonInfo2ByName(String name) {
        return HttpMethods.getInstance().getUserCommonInfo2ByName(name);
    }


    @Override
    public Observable<String> upLoadHead(String fileName, String filePath) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), new File(filePath));
        return HttpMethods.getInstance().upLoadHead(fileName, requestBody);
    }

    @Override
    public Observable<UserCommonInfo2> InsertUser(final UserCommonInfo2 info) {
        return Observable.create(new Observable.OnSubscribe<UserCommonInfo2>() {
            @Override
            public void call(Subscriber<? super UserCommonInfo2> subscriber) {
                SQLiteDatabase db = helper.getWritableDatabase();
                Log.i("wang", "InsertUser->新插入的临时用户:" + info.toString());
                ContentValues values = new ContentValues();
                values.put(UserDao.COLUMN_NAME_ID, info.id);
                values.put(UserDao.COLUMN_NAME_NAME, info.nickname);
                values.put(UserDao.COLUMN_NAME_EMNAME, info.emname);
                values.put(UserDao.COLUMN_NAME_HEAD, info.avatar);
                db.insert(UserDao.TABLE_NAME, null, values);
                subscriber.onNext(info);
            }

        });
    }

}
