package com.wxxiaomi.ming.electricbicycle.dao.db.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.EBApplication;
import com.wxxiaomi.ming.electricbicycle.dao.bean.InviteMessage;
import com.wxxiaomi.ming.electricbicycle.dao.db.InviteMessgeDao;
import com.wxxiaomi.ming.electricbicycle.dao.db.util.DbOpenHelper;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;


public class InviteMessgeDaoImpl {

	DbOpenHelper helper;
	private InviteMessgeDaoImpl() {
		helper = DbOpenHelper.getInstance(EBApplication.getInstance().getApplicationContext());
	}
    private static class SingletonHolder {
        private static final InviteMessgeDaoImpl INSTANCE = new InviteMessgeDaoImpl();
    }
    //获取单例
    public static InviteMessgeDaoImpl getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取未读的邀请消息
     * @return
     */
	public Observable<Integer> getUnreadNotifyCount() {
       return  Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                try {
                    int count = 0;
                    SQLiteDatabase db = helper.getReadableDatabase();
                    if (db.isOpen()) {
                        Cursor cursor = db.rawQuery("select " + InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT + " from " + InviteMessgeDao.TABLE_NAME, null);
                        if (cursor.moveToFirst()) {
                            count = cursor.getInt(0);
                        }
                        cursor.close();
                        Log.i("wang","获取未读的消息总数结果："+count);
                        subscriber.onNext(count);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    subscriber.onError(new Exception("我只知道数据库报错了"));
                }
            }
        });
	}

    /**
     * 保存一条邀请消息
     * @param message
     * @return
     */
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

    /**
     * 获取邀请消息列表
     * @return
     */
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

    /**
     * 保存未读的邀请消息总数
     * @param count
     * @return
     */
	public Observable<Integer> saveUnreadMessageCount(final int count) {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                SQLiteDatabase db = helper.getWritableDatabase();
                if(db.isOpen()){
                    Log.i("wang","设置数据库未读的消息总数："+count);
                    String sql = "update "+InviteMessgeDao.TABLE_NAME+" set "+InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT+"="+count;
                    db.execSQL(sql);

//                    ContentValues values = new ContentValues();
//                    values.put(InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT, count);
//                    db.update(InviteMessgeDao.TABLE_NAME, values, null,null);
                    subscriber.onNext(count);
                }
            }
        });
	}

}
