package com.wxxiaomi.ming.electricbicycle.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wxxiaomi.ming.electricbicycle.EBApplication;
import com.wxxiaomi.ming.electricbicycle.bean.InviteMessage;
import com.wxxiaomi.ming.electricbicycle.dao.InviteMessgeDao;
import com.wxxiaomi.ming.electricbicycle.dao.util.DbOpenHelper;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;


public class InviteMessgeDaoImpl2{

	DbOpenHelper helper;


	private InviteMessgeDaoImpl2() {
		helper = DbOpenHelper.getInstance(EBApplication.getInstance().getApplicationContext());
	}

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final InviteMessgeDaoImpl2 INSTANCE = new InviteMessgeDaoImpl2();
    }

    //获取单例
    public static InviteMessgeDaoImpl2 getInstance() {
        return SingletonHolder.INSTANCE;
    }

	public Observable<Integer> getUnreadNotifyCount() {
       return  Observable.create(new Observable.OnSubscribe<Integer>() {

            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                int count = 0;
                SQLiteDatabase db = helper.getReadableDatabase();
                if(db.isOpen()){
                    Cursor cursor = db.rawQuery("select " + InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT + " from " + InviteMessgeDao.TABLE_NAME, null);
                    if(cursor.moveToFirst()){
                        count = cursor.getInt(0);
                    }
                    cursor.close();
                    subscriber.onNext(count);
                }
            }
        });
	}

	public Observable<Integer> saveMessage(final InviteMessage message) {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                SQLiteDatabase db = helper.getWritableDatabase();
                int id = -1;
                if(db.isOpen()){
                    ContentValues values = new ContentValues();
                    values.put(InviteMessgeDao.COLUMN_NAME_FROM, message.getFrom());
                    values.put(InviteMessgeDao.COLUMN_NAME_REASON, message.getReason());
                    values.put(InviteMessgeDao.COLUMN_NAME_TIME, message.getTime());
                    db.replace(InviteMessgeDao.TABLE_NAME, null, values);

                    Cursor cursor = db.rawQuery("select last_insert_rowid() from " + InviteMessgeDao.TABLE_NAME,null);
                    if(cursor.moveToFirst()){
                        id = cursor.getInt(0);
                    }
                    cursor.close();
                    subscriber.onNext(id);
                }
            }
        });
	}

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
                
                msg.setId(id);
                msg.setFrom(from);
                msg.setReason(reason);
                msg.setTime(time);
                
                msgs.add(msg);
            }
            cursor.close();
        }
        return msgs;
	}

	public Observable<Integer> saveUnreadMessageCount(final int count) {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                SQLiteDatabase db = helper.getWritableDatabase();
                if(db.isOpen()){
                    ContentValues values = new ContentValues();
                    values.put(InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT, count);
                    db.update(InviteMessgeDao.TABLE_NAME, values, null,null);
                    subscriber.onNext(1);
                }
            }
        });
	}

}
