package com.wxxiaomi.ming.electricbicycle.dao.impl;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.bean.User;
import com.wxxiaomi.ming.electricbicycle.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.dao.UserDao;
import com.wxxiaomi.ming.electricbicycle.dao.util.DbOpenHelper;


public class UserDaoImpl implements UserDao {

	Context context;
	DbOpenHelper helper ;
	
	public UserDaoImpl(Context context) {
		super();
		this.context = context;
		helper = DbOpenHelper.getInstance(context);
	}
	
	/**
	 * 校对数据库的好友列表和em服务器的好友列表
	 * 多出的删除
	 * 少的就添加到result并返回
	 * @param emnamelist
	 * @return
	 */
	synchronized public List<String> checkFriendList(List<String> emnamelist){
		List<String> result = new ArrayList<String>();
		SQLiteDatabase db = helper.getWritableDatabase();
		List<String> daoEmnameList = new ArrayList<String>();
//		List<String> daoMoreList = new ArrayList<String>();
//		List<String> daMissingList = new ArrayList<String>();
		if(db.isOpen()){
			Cursor cursor = db.rawQuery("select "+UserDao.COLUMN_NAME_EMNAME+" from " + UserDao.TABLE_NAME + " desc",null);
			while(cursor.moveToNext()){
//				Log.i("wang", "")
				daoEmnameList.add(cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_EMNAME)));
			}
			//拿出数据库里面有,em服务器没有的
			for(String daoemname : daoEmnameList){
				if(!emnamelist.contains(daoemname)){
//					daoMoreList.add(daoemname);
					deleteFriend(daoemname);
				}
			}
			//删除操作
			
			//拿出em服务器有,数据库里没有的
			for(String emname : emnamelist){
				if(!daoEmnameList.contains(emname)){
					result.add(emname);
				}
			}
		}
		return result;
	}

	/**
	 * 保存好友列表
	 */
	@Override
	synchronized public int saveFriendList(List<UserCommonInfo> userList) {
		SQLiteDatabase db = helper.getWritableDatabase();
		if(db.isOpen()){
//			 db.delete(UserDao1.TABLE_NAME, null, null);
			 for (UserCommonInfo user : userList) {
	                ContentValues values = new ContentValues();
	                values.put(UserDao.COLUMN_NAME_ID, user.id);
	                    values.put(UserDao.COLUMN_NAME_NAME, user.name);
	                    values.put(UserDao.COLUMN_NAME_EMNAME, user.emname);
	                    values.put(UserDao.COLUMN_NAME_HEAD, user.head);
	                db.replace(UserDao.TABLE_NAME, null, values);
//	                db.e
	            }
		}
		return 0;
	}

	/**
	 * 获取好友列表
	 */
	@Override
	synchronized public List<UserCommonInfo> getFriendList() {
		SQLiteDatabase db = helper.getReadableDatabase();
		 List<UserCommonInfo> list = new ArrayList<UserCommonInfo>();
		 if(db.isOpen()){
			 Cursor cursor = db.rawQuery("select * from " + UserDao.TABLE_NAME + " desc",null);
			  while(cursor.moveToNext()){
				  UserCommonInfo info = new UserCommonInfo();
				  int id = cursor.getInt(cursor.getColumnIndex(UserDao.COLUMN_NAME_ID));
	                String name = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_NAME));
	                String emname = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_EMNAME));
	                String head = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_HEAD));
//	                long time = cursor.getLong(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_TIME));
	                info.name = name;
	                info.emname = emname;
	                info.head = head;
	                info.id = id;
	                
	                list.add(info);
			  }
		 }
		return list;
	}

	/**
	 * 更新好友列表
	 */
	@Override
	synchronized public boolean updateFriendList(List<UserCommonInfo> userList) {
		SQLiteDatabase db = helper.getWritableDatabase();
		if(db.isOpen()){
//			 db.delete(UserDao1.TABLE_NAME, null, null);
			 for (UserCommonInfo user : userList) {
				 Log.i("wang", "新插入的好友:"+user.toString());
	                ContentValues values = new ContentValues();
	                values.put(UserDao.COLUMN_NAME_ID, user.id);
	                    values.put(UserDao.COLUMN_NAME_NAME, user.name);
	                    values.put(UserDao.COLUMN_NAME_EMNAME, user.emname);
	                    values.put(UserDao.COLUMN_NAME_HEAD, user.head);
	                db.insert(UserDao.TABLE_NAME, null, values);
	            }
		}
		return false;
	}

	/**
	 * 根据emname取得某一位好友
	 */
	@Override
	synchronized public UserCommonInfo getFriendInfoByEmname(String emname) {
		SQLiteDatabase db = helper.getReadableDatabase();
		 if(db.isOpen()){
			 //参数1：表名    
	            //参数2：要想显示的列    
	            //参数3：where子句    
	            //参数4：where子句对应的条件值    
	            //参数5：分组方式    
	            //参数6：having条件    
	            //参数7：排序方式 
			 Cursor cursor = db.query(UserDao.TABLE_NAME
					 , new String[]{UserDao.COLUMN_NAME_ID,UserDao.COLUMN_NAME_NAME
					 ,UserDao.COLUMN_NAME_EMNAME,UserDao.COLUMN_NAME_HEAD}, 
					 UserDao.COLUMN_NAME_EMNAME+"=?", new String[]{emname}, null, null, null);
			 
			 while(cursor.moveToNext()){  
	                int id = cursor.getInt(cursor.getColumnIndex(UserDao.COLUMN_NAME_ID));  
	                String name = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_NAME));  
	                String emname1 = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_EMNAME));  
	                String head = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_HEAD));  
//	                System.out.println("query------->" + "姓名："+name+" "+"年龄："+age+" "+"性别："+sex);  
	                UserCommonInfo info = new UserCommonInfo();
	                info.id = id;
	                info.emname = emname1;
	                info.head = head;
	                info.name = name;
	                return info;
	            }  
		 }
		return null;
	}

	
	/**
	 * 删除好友
	 */
	@Override
	synchronized public boolean deleteFriend(String emname) {
		
		SQLiteDatabase db = helper.getReadableDatabase();
		 if(db.isOpen()){
			//删除条件  
			   String whereClause = "emname=?";  
			   
			   //删除条件参数  
			   String[] whereArgs = {emname};  
			   
			   //执行删除  
			   db.delete(UserDao.TABLE_NAME,whereClause,whereArgs);   
		 }
		
		return false;
	}
	
	/**
	 * 判断当前emname是否为好友
	 * @param emname
	 * @return
	 */
	synchronized public boolean isMyFriend(String emname){
		boolean flag = false;
		SQLiteDatabase db = helper.getWritableDatabase();
		if(db.isOpen()){
			Cursor query = db.query(UserDao.TABLE_NAME, new String[]{"emname"}, "emname=?", new String[]{emname}, null, null, null);
			if(query.moveToNext()){
				flag = true;
			}
		}
		return flag;
		
	}

}
