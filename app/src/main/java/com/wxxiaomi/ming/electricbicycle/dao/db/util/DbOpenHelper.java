/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wxxiaomi.ming.electricbicycle.dao.db.util;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wxxiaomi.ming.electricbicycle.GlobalParams;
import com.wxxiaomi.ming.electricbicycle.dao.db.AppDao;
import com.wxxiaomi.ming.electricbicycle.dao.db.FriendDao;
import com.wxxiaomi.ming.electricbicycle.dao.db.FriendDao2;
import com.wxxiaomi.ming.electricbicycle.dao.db.InviteMessgeDao;
import com.wxxiaomi.ming.electricbicycle.dao.db.UserDao;


/**
 * 数据库的初始化帮助类
 * @author Mr.W
 *
 */
public class DbOpenHelper extends SQLiteOpenHelper{

	private static final int DATABASE_VERSION = 6;
	private static DbOpenHelper instance;

	private static final String USERNAME_TABLE_CREATE = "CREATE TABLE "
			+ UserDao.TABLE_NAME + " ("
			+ UserDao.COLUMN_NAME_NAME + " TEXT, "
			+ UserDao.COLUMN_NAME_HEAD + " TEXT, "
			+ UserDao.COLUMN_NAME_EMNAME + " TEXT, "
			+ UserDao.COLUMN_NAME_ID + " TEXT PRIMARY KEY);";
	
	private static final String INIVTE_MESSAGE_TABLE_CREATE = "CREATE TABLE "
			+ InviteMessgeDao.TABLE_NAME + " ("
			+ InviteMessgeDao.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ InviteMessgeDao.COLUMN_NAME_FROM + " TEXT, "
			+ InviteMessgeDao.COLUMN_NAME_REASON + " TEXT, "
			+ InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT + " INTEGER, "
			+ InviteMessgeDao.COLUMN_NAME_TIME + " TEXT); ";
	
	private static final String TEMP_USERNAME_TABLE_CREATE = "CREATE TABLE "
			+ FriendDao.TABLE_NAME + " ("
			+ FriendDao.COLUMN_NAME_NAME + " TEXT, "
			+ FriendDao.COLUMN_NAME_HEAD + " TEXT, "
			+ FriendDao.COLUMN_NAME_EMNAME + " TEXT, "
			+ FriendDao.COLUMN_NAME_ID + " TEXT PRIMARY KEY);";

	private static final String USER_FRIEND_TABLE_CREATE = "CREATE TABLE "
			+ FriendDao2.TABLE_NAME + " ("
			+ FriendDao2.COLUMN_NAME_HEAD + " TEXT, "
			+ FriendDao2.COLUMN_NAME_ALBUMID + " INTEGER, "
			+ FriendDao2.COLUMN_NAME_CITY + " TEXT, "
			+ FriendDao2.COLUMN_NAME_COVER + " TEXT, "
			+ FriendDao2.COLUMN_NAME_CREATETIME + " TEXT, "
			+ FriendDao2.COLUMN_NAME_DESCRIPTION + " TEXT, "
			+ FriendDao2.COLUMN_NAME_EMNAME + " TEXT, "
			+ FriendDao2.COLUMN_NAME_NAME + " TEXT, "
			+ FriendDao2.COLUMN_NAME_SEX + " INTEGER, "
			+ FriendDao2.COLUMN_NAME_UPDATETIME + " TEXT, "
			+ FriendDao2.COLUMN_NAME_ID + " INTEGER PRIMARY KEY);";


	private static final String USER_RECORD_TABLE_CREATE = "CREATE TABLE "
			+ AppDao.TABLE_NAME + " ("
			+ AppDao.COLUMN_NAME_ID + " INTEGER, "
			+ AppDao.COLUMN_NAME_USERNAME + " TEXT, "
			+ AppDao.COLUMN_NAME_PASSWORD + " TEXT, "
			+ AppDao.COLUMN_NAME_UPDATETIME + " TEXT, "
			+ AppDao.COLUMN_NAME_SEX + " INTEGER, "
			+ AppDao.COLUMN_NAME_NAME + " TEXT, "
			+ AppDao.COLUMN_NAME_HEAD + " TEXT, "
			+ AppDao.COLUMN_NAME_ALBUMID + " INTEGER, "
			+ AppDao.COLUMN_NAME_CITY + " TEXT, "
			+ AppDao.COLUMN_NAME_COVER + " TEXT, "
			+ AppDao.COLUMN_NAME_CREATETIME + " TEXT, "
			+ AppDao.COLUMN_NAME_DESCRIPTION + " TEXT, "
			+ AppDao.COLUMN_NAME_EMNAME + " TEXT, "
			+ AppDao.COLUMN_NAME_INFO_ID + " TEXT); ";
	
	
//			
//	private static final String ROBOT_TABLE_CREATE = "CREATE TABLE "
//			+ UserDao.ROBOT_TABLE_NAME + " ("
//			+ UserDao.ROBOT_COLUMN_NAME_ID + " TEXT PRIMARY KEY, "
//			+ UserDao.ROBOT_COLUMN_NAME_NICK + " TEXT, "
//			+ UserDao.ROBOT_COLUMN_NAME_AVATAR + " TEXT);";
//			
//	private static final String CREATE_PREF_TABLE = "CREATE TABLE "
//            + UserDao.PREF_TABLE_NAME + " ("
//            + UserDao.COLUMN_NAME_DISABLED_GROUPS + " TEXT, "
//            + UserDao.COLUMN_NAME_DISABLED_IDS + " TEXT);";
	
	/**
	 * 构造方法
	 * 用来定义数据库的名称,数据库查询的结果集,数据库的版本号
	 * @param context
	 */
	public DbOpenHelper(Context context) {
		super(context, getUserDatabaseName(), null, DATABASE_VERSION);
	}
	
	public static DbOpenHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DbOpenHelper(context.getApplicationContext());
		}
		return instance;
	}
	
	private static String getUserDatabaseName() {
//        return  DemoHelper.getInstance().getCurrentUsernName() + "_demo.db";
		return "demo.db";
    }
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(USERNAME_TABLE_CREATE);
		db.execSQL(INIVTE_MESSAGE_TABLE_CREATE);
		db.execSQL(TEMP_USERNAME_TABLE_CREATE);
		db.execSQL(USER_RECORD_TABLE_CREATE);
		db.execSQL(USER_FRIEND_TABLE_CREATE);
//		db.execSQL(CREATE_PREF_TABLE);
//		db.execSQL(ROBOT_TABLE_CREATE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		if(oldVersion < 2){
//		    db.execSQL("ALTER TABLE "+ UserDao.TABLE_NAME +" ADD COLUMN "+ 
//		            UserDao.COLUMN_NAME_AVATAR + " TEXT ;");
//		}
		
//		if(oldVersion < 3){
//		    db.execSQL(CREATE_PREF_TABLE);
//        }
//		if(oldVersion < 4){
//			db.execSQL(ROBOT_TABLE_CREATE);
//		}
//		if(oldVersion < 5){
//		    db.execSQL("ALTER TABLE " + InviteMessgeDao.TABLE_NAME + " ADD COLUMN " + 
//		            InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT + " INTEGER ;");
//		}
//		if (oldVersion < 6) {
//		    db.execSQL("ALTER TABLE " + InviteMessgeDao.TABLE_NAME + " ADD COLUMN " + 
//		            InviteMessgeDao.COLUMN_NAME_GROUPINVITER + " TEXT;");
//		}
	}
	
	public void closeDB() {
	    if (instance != null) {
	        try {
	            SQLiteDatabase db = instance.getWritableDatabase();
	            db.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        instance = null;
	    }
	}
	
}
