package com.wxxiaomi.ming.electricbicycle.db.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.db.bean.InviteMessage;
import com.wxxiaomi.ming.electricbicycle.db.InviteMessgeDao;
import com.wxxiaomi.ming.electricbicycle.db.util.DbOpenHelper;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/12/14.
 */

public class InviteMessgeDaoImpl2 implements InviteMessgeDao {
    DbOpenHelper helper;
    public InviteMessgeDaoImpl2(Context context){
        helper = new DbOpenHelper(context);
    }
    @Override
    public int getUnreadNotifyCount() {
        int count = 0;
        SQLiteDatabase db = helper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select " + InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT + " from " + InviteMessgeDao.TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            cursor.close();
        }
        return count;
    }

    @Override
    public Integer saveMessage(InviteMessage message) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int id = -1;
        if(db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(InviteMessgeDao.COLUMN_NAME_FROM, message.getFrom());
            values.put(InviteMessgeDao.COLUMN_NAME_REASON, message.getReason());
            values.put(InviteMessgeDao.COLUMN_NAME_TIME, message.getTime());
            values.put(InviteMessgeDao.COLUMN_NAME_AVATAR, message.getAvatar());
            values.put(InviteMessgeDao.COLUMN_NAME_NICK, message.getNickname());
            db.replace(InviteMessgeDao.TABLE_NAME, null, values);
            Cursor cursor = db.rawQuery("select last_insert_rowid() from " + InviteMessgeDao.TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                id = cursor.getInt(0);
            }
            cursor.close();
        }
            return id;
    }

    @Override
    public List<InviteMessage> getMessagesList() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<InviteMessage> msgs = new ArrayList<InviteMessage>();
        if(db.isOpen()){
            Cursor cursor = db.rawQuery("select * from " + InviteMessgeDao.TABLE_NAME + " desc",null);
            while(cursor.moveToNext()){
                InviteMessage msg = new InviteMessage();
                int id = cursor.getInt(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_ID));
                String from = cursor.getString(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_FROM));
                String reason = cursor.getString(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_REASON));
                long time = cursor.getLong(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_TIME));
                String avatar = cursor.getString(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_AVATAR));
                String nickname = cursor.getString(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_NICK));

                msg.setId(id);
                msg.setFrom(from);
                msg.setReason(reason);
                msg.setTime(time);
                msg.setAvatar(avatar);
                msg.setNickname(nickname);

                msgs.add(msg);
            }
            cursor.close();
        }
        return msgs;
    }

    @Override
    public Observable<List<InviteMessage>> getMessagesListRx() {
        return Observable.create(new Observable.OnSubscribe<List<InviteMessage>>() {
            @Override
            public void call(Subscriber<? super List<InviteMessage>> subscriber) {
                try {
                    SQLiteDatabase db = helper.getReadableDatabase();
                    List<InviteMessage> msgs = new ArrayList<InviteMessage>();
                    if (db.isOpen()) {
                        Cursor cursor = db.rawQuery("select * from " + InviteMessgeDao.TABLE_NAME + " desc", null);
                        while (cursor.moveToNext()) {
                            InviteMessage msg = new InviteMessage();
                            int id = cursor.getInt(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_ID));
                            String from = cursor.getString(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_FROM));
                            String reason = cursor.getString(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_REASON));
                            long time = cursor.getLong(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_TIME));
                            String avatar = cursor.getString(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_AVATAR));
                            String nickname = cursor.getString(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_NICK));
                            msg.setAvatar(avatar);
                            msg.setNickname(nickname);

                            msg.setId(id);
                            msg.setFrom(from);
                            msg.setReason(reason);
                            msg.setTime(time);

                            msgs.add(msg);
                        }
                        cursor.close();
                        subscriber.onNext(msgs);

                    }
                }catch (Exception e){
                    Log.i("wang","在数据取邀请信息的时候出错了");
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        });
    }
}
