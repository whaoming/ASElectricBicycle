//package com.wxxiaomi.ming.electricbicycle.dao.db.impl;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.util.Log;
//
//import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo;
//import com.wxxiaomi.ming.electricbicycle.dao.db.FriendDao;
//import com.wxxiaomi.ming.electricbicycle.dao.db.util.DbOpenHelper;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import rx.Observable;
//import rx.Subscriber;
//
///**
// * Created by 12262 on 2016/11/16.
// * 好友dao实现类
// */
//
//public class FriendDaoImpl implements FriendDao {
//
//    private DbOpenHelper helper;
//
//    public FriendDaoImpl(Context context) {
//        helper = DbOpenHelper.getInstance(context);
//    }
//
//
//    @Override
//    public String getErrorFriend(List<String> emnames) {
//        /**
//         * 最终是要返回xxx=ssss#dddd=66666这样的数据格式
//         * 1.
//         */
//        SQLiteDatabase db = helper.getWritableDatabase();
//        Map<String, String> daoEmnameList = new HashMap<>();
//        String result = "";
//        if (db.isOpen()) {
//            //1.取出emname(key),update_time(value)存到map里
//            Cursor cursor = db.rawQuery("select " + FriendDao.COLUMN_NAME_EMNAME + " from " + FriendDao.TABLE_NAME + " desc", null);
//            while (cursor.moveToNext()) {
//                daoEmnameList.put(cursor.getString(cursor.getColumnIndex(FriendDao.COLUMN_NAME_EMNAME)), "update_time");
//            }
//            //2.拿出现在数据库里的每一个，检查emnames里面有没有，没有就删除
//            for (Map.Entry<String, String> item : daoEmnameList.entrySet()) {
//                if (!emnames.contains(item.getKey())) {
//                    deleteFriend(item.getKey());
//                }
//            }
//            for (int i = 0; i < emnames.size(); i++) {
//                Log.i("wang","emnames.get(i):"+emnames.get(i));
//                if(i==0){
//                    result = emnames.get(i)+"="+(daoEmnameList.get(emnames.get(i))==null?"2016-12-16 15:25:23":daoEmnameList.get(emnames.get(i)));
//                }else{
//                    result += "#"+emnames.get(i)+"="+(daoEmnameList.get(emnames.get(i))==null?"":daoEmnameList.get(emnames.get(i)));
//                }
//            }
//        }
//        return result;
//    }
//
//    @Override
//    public Observable<List<String>> CheckFriendList(final List<String> emnamelist) {
//        return Observable.create(new Observable.OnSubscribe<List<String>>() {
//
//            @Override
//            public void call(Subscriber<? super List<String>> subscriber) {
//                List<String> result = new ArrayList<String>();
//                SQLiteDatabase db = helper.getWritableDatabase();
//                List<String> daoEmnameList = new ArrayList<String>();
//                if (db.isOpen()) {
//                    Cursor cursor = db.rawQuery("select " + FriendDao.COLUMN_NAME_EMNAME + " from " + FriendDao.TABLE_NAME + " desc", null);
//                    while (cursor.moveToNext()) {
//                        daoEmnameList.add(cursor.getString(cursor.getColumnIndex(FriendDao.COLUMN_NAME_EMNAME)));
//                    }
//                    //拿出数据库里面有,em服务器没有的
//                    for (String daoemname : daoEmnameList) {
//                        if (!emnamelist.contains(daoemname)) {
//                            deleteFriend(daoemname);
//                        }
//                    }
//                    //删除操作
//                    //拿出em服务器有,数据库里没有的
//                    for (String emname : emnamelist) {
//                        if (!daoEmnameList.contains(emname)) {
//                            result.add(emname);
//                        }
//                    }
//                }
//                subscriber.onNext(result);
//            }
//        });
//    }
//
//    @Override
//    public int saveFriendList(List<UserCommonInfo> userList) {
//        SQLiteDatabase db = helper.getWritableDatabase();
//        if (db.isOpen()) {
//            for (UserCommonInfo user : userList) {
//                ContentValues values = new ContentValues();
//                values.put(FriendDao.COLUMN_NAME_ID, user.id);
//                values.put(FriendDao.COLUMN_NAME_NAME, user.name);
//                values.put(FriendDao.COLUMN_NAME_EMNAME, user.emname);
//                values.put(FriendDao.COLUMN_NAME_HEAD, user.head);
//                db.replace(FriendDao.TABLE_NAME, null, values);
//            }
//        }
//        return 1;
//    }
//
//    @Override
//    public List<UserCommonInfo> getFriendList() {
//        SQLiteDatabase db = helper.getReadableDatabase();
//        List<UserCommonInfo> list = new ArrayList<>();
//        if (db.isOpen()) {
//            Cursor cursor = db.rawQuery("select * from " + FriendDao.TABLE_NAME + " desc", null);
//            while (cursor.moveToNext()) {
//                UserCommonInfo info = new UserCommonInfo();
//                int id = cursor.getInt(cursor.getColumnIndex(FriendDao.COLUMN_NAME_ID));
//                String name = cursor.getString(cursor.getColumnIndex(FriendDao.COLUMN_NAME_NAME));
//                String emname = cursor.getString(cursor.getColumnIndex(FriendDao.COLUMN_NAME_EMNAME));
//                String head = cursor.getString(cursor.getColumnIndex(FriendDao.COLUMN_NAME_HEAD));
////	                long time = cursor.getLong(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_TIME));
//                info.name = name;
//                info.emname = emname;
//                info.head = head;
//                info.id = id;
//                list.add(info);
//            }
//        }
//        return list;
//    }
//
//    @Override
//    public Observable<Integer> InsertFriendList(final List<UserCommonInfo> userList) {
//        return Observable.create(new Observable.OnSubscribe<Integer>() {
//            @Override
//            public void call(Subscriber<? super Integer> subscriber) {
//                Log.i("wang", "InsertFriendList->插入好友:");
//                SQLiteDatabase db = helper.getWritableDatabase();
//                if (db.isOpen()) {
//                    for (UserCommonInfo user : userList) {
//                        Log.i("wang", "InsertFriendList->新插入的好友:" + user.toString());
//                        ContentValues values = new ContentValues();
//                        values.put(FriendDao.COLUMN_NAME_ID, user.id);
//                        values.put(FriendDao.COLUMN_NAME_NAME, user.name);
//                        values.put(FriendDao.COLUMN_NAME_EMNAME, user.emname);
//                        values.put(FriendDao.COLUMN_NAME_HEAD, user.head);
//                        db.insert(FriendDao.TABLE_NAME, null, values);
//                    }
//                }
//                subscriber.onNext(1);
//            }
//        });
//
//    }
//
//    @Override
//    public Observable<UserCommonInfo> getFriendInfoByEmname(final String emname) {
//        Log.i("wang", "getFriendInfoByEmname->:" + emname);
//        return Observable.create(new Observable.OnSubscribe<UserCommonInfo>() {
//            @Override
//            public void call(Subscriber<? super UserCommonInfo> subscriber) {
//                SQLiteDatabase db = helper.getReadableDatabase();
//                if (db.isOpen()) {
//                    //参数1：表名
//                    //参数2：要想显示的列
//                    //参数3：where子句
//                    //参数4：where子句对应的条件值
//                    //参数5：分组方式
//                    //参数6：having条件
//                    //参数7：排序方式
//                    Cursor cursor = db.query(FriendDao.TABLE_NAME
//                            , new String[]{FriendDao.COLUMN_NAME_ID, FriendDao.COLUMN_NAME_NAME
//                                    , FriendDao.COLUMN_NAME_EMNAME, FriendDao.COLUMN_NAME_HEAD},
//                            FriendDao.COLUMN_NAME_EMNAME + "=?", new String[]{emname}, null, null, null);
//
//                    while (cursor.moveToNext()) {
//                        int id = cursor.getInt(cursor.getColumnIndex(FriendDao.COLUMN_NAME_ID));
//                        String name = cursor.getString(cursor.getColumnIndex(FriendDao.COLUMN_NAME_NAME));
//                        String emname1 = cursor.getString(cursor.getColumnIndex(FriendDao.COLUMN_NAME_EMNAME));
//                        String head = cursor.getString(cursor.getColumnIndex(FriendDao.COLUMN_NAME_HEAD));
////	                System.out.println("query------->" + "姓名："+name+" "+"年龄："+age+" "+"性别："+sex);
//                        UserCommonInfo info = new UserCommonInfo();
//                        info.id = id;
//                        info.emname = emname1;
//                        info.head = head;
//                        info.name = name;
//                        subscriber.onNext(info);
//                    }
//                }
//            }
//        });
//    }
//
//    @Override
//    public boolean deleteFriend(String emname) {
//        SQLiteDatabase db = helper.getReadableDatabase();
//        if (db.isOpen()) {
//            //删除条件
//            String whereClause = "emname=?";
//            //删除条件参数
//            String[] whereArgs = {emname};
//            //执行删除
//            db.delete(FriendDao.TABLE_NAME, whereClause, whereArgs);
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public boolean isMyFriend(String emname) {
//        boolean flag = false;
//        SQLiteDatabase db = helper.getWritableDatabase();
//        if (db.isOpen()) {
//            Cursor query = db.query(FriendDao.TABLE_NAME, new String[]{"emname"}, "emname=?", new String[]{emname}, null, null, null);
//            if (query.moveToNext()) {
//                flag = true;
//            }
//        }
//        return flag;
//    }
//
//
////    @Override
////    public UserCommonInfo getFriendLocal(String emname) {
////        SQLiteDatabase db = helper.getReadableDatabase();
////        if (db.isOpen()) {
////            Cursor cursor = db.query(FriendDao.TABLE_NAME
////                    , new String[]{FriendDao.COLUMN_NAME_ID, FriendDao.COLUMN_NAME_NAME
////                            , FriendDao.COLUMN_NAME_EMNAME, FriendDao.COLUMN_NAME_HEAD},
////                    FriendDao.COLUMN_NAME_EMNAME + "=?", new String[]{emname}, null, null, null);
////
////            if (cursor.moveToNext()) {
////                int id = cursor.getInt(cursor.getColumnIndex(FriendDao.COLUMN_NAME_ID));
////                String name = cursor.getString(cursor.getColumnIndex(FriendDao.COLUMN_NAME_NAME));
////                String emname1 = cursor.getString(cursor.getColumnIndex(FriendDao.COLUMN_NAME_EMNAME));
////                String head = cursor.getString(cursor.getColumnIndex(FriendDao.COLUMN_NAME_HEAD));
//////	                System.out.println("query------->" + "姓名："+name+" "+"年龄："+age+" "+"性别："+sex);
////                UserCommonInfo info = new UserCommonInfo();
////                info.id = id;
////                info.emname = emname1;
////                info.head = head;
////                info.name = name;
////                return info;
////            }
////        }
////        return null;
////    }
//}
