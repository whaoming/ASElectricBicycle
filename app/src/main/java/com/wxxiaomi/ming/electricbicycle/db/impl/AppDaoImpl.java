//package com.wxxiaomi.ming.electricbicycle.db.impl;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.wxxiaomi.ming.electricbicycle.db.bean.User;
//import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;
//import com.wxxiaomi.ming.electricbicycle.db.AppDao;
//import com.wxxiaomi.ming.electricbicycle.db.util.DbOpenHelper;
//
///**
// * Created by Administrator on 2016/12/12.
// * appdao
// */
//public class AppDaoImpl implements AppDao {
//
//
//    private DbOpenHelper helper;
//
//    public AppDaoImpl(Context context) {
//        helper = DbOpenHelper.getInstance(context);
//    }
//
//    @Override
//    public User getLocalUser(int userid) {
//        SQLiteDatabase db = helper.getReadableDatabase();
//        if (db.isOpen()) {
//            Cursor cursor = db.query(AppDao.TABLE_NAME
//                    , new String[]{
//                            AppDao.COLUMN_NAME_USERNAME
//                            ,AppDao.COLUMN_NAME_PASSWORD
//                            ,AppDao.COLUMN_NAME_INFO_ID
//                            ,AppDao.COLUMN_NAME_ALBUMID
//                            ,AppDao.COLUMN_NAME_CITY
//                            ,AppDao.COLUMN_NAME_COVER
//                            ,AppDao.COLUMN_NAME_CREATETIME
//                            ,AppDao.COLUMN_NAME_DESCRIPTION
//                            ,AppDao.COLUMN_NAME_EMNAME
//                            ,AppDao.COLUMN_NAME_HEAD
//                            ,AppDao.COLUMN_NAME_ID
//                            ,AppDao.COLUMN_NAME_NAME
//                            ,AppDao.COLUMN_NAME_SEX
//                            ,AppDao.COLUMN_NAME_UPDATETIME
//                           },
//                    AppDao.COLUMN_NAME_INFO_ID + "=?", new String[]{userid + ""}, null, null, null);
//            if (cursor.moveToNext()) {
//                String username = cursor.getString(cursor.getColumnIndex(AppDao.COLUMN_NAME_USERNAME));
//                String password = cursor.getString(cursor.getColumnIndex(AppDao.COLUMN_NAME_PASSWORD));
//                int id = cursor.getInt(cursor.getColumnIndex(AppDao.COLUMN_NAME_INFO_ID));
//                int album_id = cursor.getInt(cursor.getColumnIndex(AppDao.COLUMN_NAME_ALBUMID));
//                String city = cursor.getString(cursor.getColumnIndex(AppDao.COLUMN_NAME_CITY));
//                String cover = cursor.getString(cursor.getColumnIndex(AppDao.COLUMN_NAME_COVER));
//                String create_time = cursor.getString(cursor.getColumnIndex(AppDao.COLUMN_NAME_CREATETIME));
//                String description = cursor.getString(cursor.getColumnIndex(AppDao.COLUMN_NAME_DESCRIPTION));
//                String emname = cursor.getString(cursor.getColumnIndex(AppDao.COLUMN_NAME_EMNAME));
//                String avatar = cursor.getString(cursor.getColumnIndex(AppDao.COLUMN_NAME_HEAD));
//                String nickname = cursor.getString(cursor.getColumnIndex(AppDao.COLUMN_NAME_NAME));
//                int sex = cursor.getInt(cursor.getColumnIndex(AppDao.COLUMN_NAME_SEX));
//                String update_time = cursor.getString(cursor.getColumnIndex(AppDao.COLUMN_NAME_UPDATETIME));
//                UserCommonInfo info = new UserCommonInfo();
//                info.city = city;
//                info.id = id;
//                info.emname = emname;
//                info.cover = cover;
//                info.create_time = create_time;
//                info.description = description;
//                info.avatar = avatar;
//                info.nickname = nickname;
//                info.sex = sex;
//                info.update_time = update_time;
//                info.album_id = album_id;
//                User user = new User();
//                user.userCommonInfo = info;
//                user.username = username;
//                user.password = password;
//                return user;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public boolean savaUser(User usr) {
//        SQLiteDatabase db = helper.getWritableDatabase();
//        if (db.isOpen()) {
//            UserCommonInfo user = usr.userCommonInfo;
//            ContentValues values = new ContentValues();
//            values.put(AppDao.COLUMN_NAME_INFO_ID, user.id);
//            values.put(AppDao.COLUMN_NAME_ALBUMID, user.album_id);
//            values.put(AppDao.COLUMN_NAME_CITY, user.city);
//            values.put(AppDao.COLUMN_NAME_COVER, user.cover);
//            values.put(AppDao.COLUMN_NAME_CREATETIME, user.create_time);
//            values.put(AppDao.COLUMN_NAME_DESCRIPTION, user.description);
//            values.put(AppDao.COLUMN_NAME_EMNAME, user.emname);
//            values.put(AppDao.COLUMN_NAME_HEAD, user.avatar);
//            values.put(AppDao.COLUMN_NAME_NAME, user.nickname);
//            values.put(AppDao.COLUMN_NAME_SEX, user.sex);
//            values.put(AppDao.COLUMN_NAME_UPDATETIME, user.update_time);
//
//            values.put(AppDao.COLUMN_NAME_USERNAME, usr.username);
//            values.put(AppDao.COLUMN_NAME_PASSWORD, usr.password);
//            values.put(AppDao.COLUMN_NAME_ID, usr.id);
//            db.replace(AppDao.TABLE_NAME, null, values);
//        }
//        return true;
//    }
//
//    @Override
//    public Integer updateUserInfo(UserCommonInfo user) {
//        SQLiteDatabase db = helper.getWritableDatabase();
//        if (db.isOpen()) {
//            ContentValues values = new ContentValues();
//            values.put(AppDao.COLUMN_NAME_ALBUMID, user.album_id);
//            values.put(AppDao.COLUMN_NAME_CITY, user.city);
//            values.put(AppDao.COLUMN_NAME_COVER, user.cover);
//            values.put(AppDao.COLUMN_NAME_CREATETIME, user.create_time);
//            values.put(AppDao.COLUMN_NAME_DESCRIPTION, user.description);
//            values.put(AppDao.COLUMN_NAME_EMNAME, user.emname);
//            values.put(AppDao.COLUMN_NAME_HEAD, user.avatar);
//            values.put(AppDao.COLUMN_NAME_NAME, user.nickname);
//            values.put(AppDao.COLUMN_NAME_SEX, user.sex);
//            values.put(AppDao.COLUMN_NAME_UPDATETIME, user.update_time);
//            String where = AppDao.COLUMN_NAME_INFO_ID + "=" + user.id;
//            db.update(AppDao.TABLE_NAME, values, where, null);
//        }
//        return 1;
//    }
//}
