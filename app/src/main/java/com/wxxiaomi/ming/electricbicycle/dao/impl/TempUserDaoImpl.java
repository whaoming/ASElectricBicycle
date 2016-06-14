package com.wxxiaomi.ming.electricbicycle.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.bean.User;
import com.wxxiaomi.ming.electricbicycle.dao.TempUserDao;
import com.wxxiaomi.ming.electricbicycle.dao.util.DbOpenHelper;


public class TempUserDaoImpl implements TempUserDao {
	
	Context context;
	DbOpenHelper helper ;
	
	public TempUserDaoImpl(Context context) {
		super();
		this.context = context;
		helper = DbOpenHelper.getInstance(context);
	}

	@Override
	public void savaPerson(User.UserCommonInfo user) {
//		Log.i("wang", "TempDeoLmpl插入临时用户:"+user.toString());
		int id;
		SQLiteDatabase db = helper.getWritableDatabase();
		if(db.isOpen()){
//			Log.i("wang", "db.isopen()");
			 ContentValues values = new ContentValues();
             values.put(TempUserDao.COLUMN_NAME_ID, user.userid);
                 values.put(TempUserDao.COLUMN_NAME_NAME, user.name);
                 values.put(TempUserDao.COLUMN_NAME_EMNAME, user.emname);
                 values.put(TempUserDao.COLUMN_NAME_HEAD, user.head);
             db.insert(TempUserDao.TABLE_NAME, null, values);
             Cursor cursor = db.rawQuery("select last_insert_rowid() from " + TempUserDao.TABLE_NAME,null); 
             if(cursor.moveToFirst()){
                 id = cursor.getInt(0);
                 Log.i("wang", "TempDeoLmpl插入临时用户,id:"+id);
             }
             cursor.close();
             
		}
//		db.close();
	}

	@Override
	public User.UserCommonInfo getPersonByEmname(String emname) {
		Log.i("wang","获取一个临时用户，emname："+emname);
		SQLiteDatabase db = helper.getReadableDatabase();
		if(db.isOpen()){
			Cursor cursor = db.query(TempUserDao.TABLE_NAME
					 , new String[]{TempUserDao.COLUMN_NAME_ID,TempUserDao.COLUMN_NAME_NAME
					 ,TempUserDao.COLUMN_NAME_EMNAME,TempUserDao.COLUMN_NAME_HEAD}, 
					 TempUserDao.COLUMN_NAME_EMNAME+"=?", new String[]{emname}, null, null, null);
			 while(cursor.moveToNext()){  
	                int id = cursor.getInt(cursor.getColumnIndex(TempUserDao.COLUMN_NAME_ID));  
	                String name = cursor.getString(cursor.getColumnIndex(TempUserDao.COLUMN_NAME_NAME));  
	                String emname1 = cursor.getString(cursor.getColumnIndex(TempUserDao.COLUMN_NAME_EMNAME));  
	                String head = cursor.getString(cursor.getColumnIndex(TempUserDao.COLUMN_NAME_HEAD));  
//	                System.out.println("query------->" + "姓名："+name+" "+"年龄："+age+" "+"性别："+sex);  
	                User.UserCommonInfo info = new User.UserCommonInfo();
	                info.userid = id;
	                info.emname = emname1;
	                info.head = head;
	                info.name = name;
	                return info;
	            }  
		}
		return null;
	}

}
