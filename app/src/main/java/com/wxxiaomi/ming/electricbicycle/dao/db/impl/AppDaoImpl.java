package com.wxxiaomi.ming.electricbicycle.dao.db.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wxxiaomi.ming.electricbicycle.dao.bean.User;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.dao.db.AppDao;
import com.wxxiaomi.ming.electricbicycle.dao.db.util.DbOpenHelper;

/**
 * Created by Administrator on 2016/12/12.
 */

public class AppDaoImpl implements AppDao {


    private DbOpenHelper helper;

    public AppDaoImpl(Context context) {
        helper = DbOpenHelper.getInstance(context);
    }

    @Override
    public User getLocalUser(int userid) {
        SQLiteDatabase db = helper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.query(AppDao.TABLE_NAME
                    , new String[]{
                            AppDao.COLUMN_NAME_USERNAME
                            ,AppDao.COLUMN_NAME_PASSWORD
                            ,AppDao.COLUMN_NAME_INFO_ID
                            , AppDao.COLUMN_NAME_INFO_EMNAME
                            , AppDao.COLUMN_NAME_INFO_HEAD
                            , AppDao.COLUMN_NAME_INFO_NAME},
                    AppDao.COLUMN_NAME_INFO_ID + "=?", new String[]{userid + ""}, null, null, null);
            if (cursor.moveToNext()) {
//                int uid = cursor.getInt(cursor.getColumnIndex(AppDao.));
                String username = cursor.getString(cursor.getColumnIndex(AppDao.COLUMN_NAME_USERNAME));
                String password = cursor.getString(cursor.getColumnIndex(AppDao.COLUMN_NAME_PASSWORD));
                int id = cursor.getInt(cursor.getColumnIndex(AppDao.COLUMN_NAME_INFO_ID));
                String name = cursor.getString(cursor.getColumnIndex(AppDao.COLUMN_NAME_INFO_NAME));
                String head = cursor.getString(cursor.getColumnIndex(AppDao.COLUMN_NAME_INFO_HEAD));
                String emname = cursor.getString(cursor.getColumnIndex(AppDao.COLUMN_NAME_INFO_EMNAME));
                UserCommonInfo info = new UserCommonInfo();
                info.name = name;
                info.id = id;
                info.emname = emname;
                info.head = head;
                User user = new User();
                user.userCommonInfo = info;
                user.username = username;
                user.password = password;

                return user;
            }
        }
        return null;
    }

    @Override
    public boolean savaUser(User user) {
        SQLiteDatabase db = helper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(AppDao.COLUMN_NAME_INFO_EMNAME, user.userCommonInfo.emname);
            values.put(AppDao.COLUMN_NAME_INFO_HEAD, user.userCommonInfo.head);
            values.put(AppDao.COLUMN_NAME_INFO_ID, user.userCommonInfo.id);
            values.put(AppDao.COLUMN_NAME_INFO_NAME, user.userCommonInfo.name);
            values.put(AppDao.COLUMN_NAME_PASSWORD, user.password);
            values.put(AppDao.COLUMN_NAME_USERNAME, user.username);
            db.replace(AppDao.TABLE_NAME, null, values);
        }
        return true;
    }


}
