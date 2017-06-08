package com.wxxiaomi.ming.electricbicycle.db.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.db.FriendDao;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.db.util.DbOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;


/**
 * @author whaoming
 *         github：https://github.com/whaoming
 *         created at 2016/12/19
 *         Description: 本地好友列表的维护dao
 */
public class FriendDaoImpl2 implements FriendDao {

    private DbOpenHelper helper;

    public FriendDaoImpl2(Context context) {
        helper = DbOpenHelper.getInstance(context);
    }


    @Override
    public int updateFriendsList(List<UserCommonInfo> userList) {
        if (userList.size() > 0) {
            //1.删除多余的item
            //2.更新现有的数据
            int flag = 0;
            SQLiteDatabase db = helper.getWritableDatabase();
            if (db.isOpen()) {
                String whereClause = FriendDao.COLUMN_NAME_BLACK + "=?";
//            //删除条件参数
                String[] whereArgs = new String[]{"0"};
//            uids.toArray(whereArgs);
                //执行删除，删除原数据库存在的所有好友
                db.delete(FriendDao.TABLE_NAME, whereClause, whereArgs);
                for (UserCommonInfo user : userList) {
                    ContentValues values = new ContentValues();
                    values.put(FriendDao.COLUMN_NAME_ID, user.id);
                    values.put(FriendDao.COLUMN_NAME_EMNAME, user.emname);
                    values.put(FriendDao.COLUMN_NAME_HEAD, user.avatar);
                    values.put(FriendDao.COLUMN_NAME_NAME, user.nickname);
                    values.put(FriendDao.COLUMN_NAME_UPDATETIME, user.update_time);
                    values.put(FriendDao.COLUMN_NAME_BLACK, 0);
                    db.replace(FriendDao.TABLE_NAME, null, values);
                    flag++;
                }
            }
            return flag;
        }
        return 0;

    }

    @Override
    public int updateBlackList(List<UserCommonInfo> userList) {
        if (userList.size() > 0) {
            //1.删除多余的item
            //2.更新现有的数据
            int flag = 0;
            SQLiteDatabase db = helper.getWritableDatabase();
            if (db.isOpen()) {
                String whereClause = FriendDao.COLUMN_NAME_BLACK + "=?";
//            //删除条件参数
                String[] whereArgs = new String[]{"1"};
//            uids.toArray(whereArgs);
                //执行删除，删除原数据库存在的所有好友
                db.delete(FriendDao.TABLE_NAME, whereClause, whereArgs);
                for (UserCommonInfo user : userList) {
                    ContentValues values = new ContentValues();
                    values.put(FriendDao.COLUMN_NAME_ID, user.id);
                    values.put(FriendDao.COLUMN_NAME_EMNAME, user.emname);
                    values.put(FriendDao.COLUMN_NAME_HEAD, user.avatar);
                    values.put(FriendDao.COLUMN_NAME_NAME, user.nickname);
                    values.put(FriendDao.COLUMN_NAME_UPDATETIME, user.update_time);
                    values.put(FriendDao.COLUMN_NAME_BLACK, 1);
                    db.replace(FriendDao.TABLE_NAME, null, values);
                    flag++;
                }
            }
            return flag;
        }
        return 0;
    }

    @Override
    public List<UserCommonInfo> getBlackList() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<UserCommonInfo> list = new ArrayList<>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + FriendDao.TABLE_NAME + " where " + FriendDao.COLUMN_NAME_BLACK + "=1 order " +
                    "by " + FriendDao.COLUMN_NAME_ID + " desc", null);
            while (cursor.moveToNext()) {
                UserCommonInfo info = new UserCommonInfo();
                int id = cursor.getInt(cursor.getColumnIndex(FriendDao.COLUMN_NAME_ID));
                String nickname = cursor.getString(cursor.getColumnIndex(FriendDao.COLUMN_NAME_NAME));
                String emname = cursor.getString(cursor.getColumnIndex(FriendDao.COLUMN_NAME_EMNAME));
                String avater = cursor.getString(cursor.getColumnIndex(FriendDao.COLUMN_NAME_HEAD));
                String update_time = cursor.getString(cursor.getColumnIndex(FriendDao.COLUMN_NAME_UPDATETIME));
                info.nickname = nickname;
                info.emname = emname;
                info.avatar = avater;
                info.update_time = update_time;
                info.id = id;
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<UserCommonInfo> getFriendList() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<UserCommonInfo> list = new ArrayList<>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from " + FriendDao.TABLE_NAME + " where " + FriendDao.COLUMN_NAME_BLACK + "=0 order by " + FriendDao.COLUMN_NAME_ID + " desc", null);
            while (cursor.moveToNext()) {
                UserCommonInfo info = new UserCommonInfo();
                int id = cursor.getInt(cursor.getColumnIndex(FriendDao.COLUMN_NAME_ID));
                String nickname = cursor.getString(cursor.getColumnIndex(FriendDao.COLUMN_NAME_NAME));
                String emname = cursor.getString(cursor.getColumnIndex(FriendDao.COLUMN_NAME_EMNAME));
                String avater = cursor.getString(cursor.getColumnIndex(FriendDao.COLUMN_NAME_HEAD));
                String update_time = cursor.getString(cursor.getColumnIndex(FriendDao.COLUMN_NAME_UPDATETIME));
                info.nickname = nickname;
                info.emname = emname;
                info.avatar = avater;
                info.update_time = update_time;
                info.id = id;
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public Observable<Integer> InsertFriendList(List<UserCommonInfo> userList) {
        return null;
    }

    @Override
    public Observable<UserCommonInfo> getFriendInfoByEmname(String emname) {
        return null;
    }

    @Override
    public boolean deleteFriend(String emname) {
        SQLiteDatabase db = helper.getReadableDatabase();
        if (db.isOpen()) {
            //删除条件
            String whereClause = "emname=?";
            //删除条件参数
            String[] whereArgs = {emname};
            //执行删除
            db.delete(FriendDao.TABLE_NAME, whereClause, whereArgs);
            return true;
        }
        return false;
    }

    @Override
    public boolean isMyFriend(String emname) {
        return false;
    }

    @Override
    public String getErrorFriend(List<String> emnames) {
        /**
         * 最终是要返回xxx=ssss#dddd=66666这样的数据格式
         * 1.
         */
        SQLiteDatabase db = helper.getWritableDatabase();
        Map<String, String> daoEmnameList = new HashMap<>();
        String result = "";
        if (db.isOpen()) {
            //1.取出emname(key),update_time(value)存到map里
            Cursor cursor = db.rawQuery("select " + FriendDao.COLUMN_NAME_EMNAME + "," + FriendDao.COLUMN_NAME_UPDATETIME + " from " + FriendDao.TABLE_NAME + " desc", null);
            while (cursor.moveToNext()) {
                daoEmnameList.put(cursor.getString(cursor.getColumnIndex(FriendDao.COLUMN_NAME_EMNAME)),
                        cursor.getString(cursor.getColumnIndex(FriendDao.COLUMN_NAME_UPDATETIME)));
            }
            //2.拿出现在数据库里的每一个，检查emnames里面有没有，没有就删除
            for (Map.Entry<String, String> item : daoEmnameList.entrySet()) {
                if (!emnames.contains(item.getKey())) {
                    Log.i("wang", "应该呗删除的key：" + item.getKey());
                    deleteFriend(item.getKey());
                }
            }
            for (int i = 0; i < emnames.size(); i++) {
                if (i == 0) {
                    result = emnames.get(i) + "=" + (daoEmnameList.get(emnames.get(i)) == null ? "" : daoEmnameList.get(emnames.get(i)));
                } else {
                    result += "#" + emnames.get(i) + "=" + (daoEmnameList.get(emnames.get(i)) == null ? "" : daoEmnameList.get(emnames.get(i)));
                }
            }
        }
        return result;
    }

    @Override
    public List<UserCommonInfo> getEFriends() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<UserCommonInfo> list = new ArrayList<>();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select " + FriendDao.COLUMN_NAME_ID + "," +
                    FriendDao.COLUMN_NAME_NAME + "," +
                    FriendDao.COLUMN_NAME_EMNAME + "," +
                    FriendDao.COLUMN_NAME_HEAD + " from " + FriendDao.TABLE_NAME + " desc", null);
            while (cursor.moveToNext()) {
                UserCommonInfo info = new UserCommonInfo();
                int id = cursor.getInt(cursor.getColumnIndex(FriendDao.COLUMN_NAME_ID));
                String nickname = cursor.getString(cursor.getColumnIndex(FriendDao.COLUMN_NAME_NAME));
                String emname = cursor.getString(cursor.getColumnIndex(FriendDao.COLUMN_NAME_EMNAME));
                String avater = cursor.getString(cursor.getColumnIndex(FriendDao.COLUMN_NAME_HEAD));
                info.nickname = nickname;
                info.emname = emname;
                info.avatar = avater;
                info.id = id;
                list.add(info);
            }
        }
        return list;
    }
}
