package com.wxxiaomi.ming.electricbicycle.dao.db.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wxxiaomi.ming.electricbicycle.dao.bean.InviteMessage;
import com.wxxiaomi.ming.electricbicycle.dao.db.InviteMessgeDao;
import com.wxxiaomi.ming.electricbicycle.dao.db.util.DbOpenHelper;

import java.util.ArrayList;
import java.util.List;

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
}
