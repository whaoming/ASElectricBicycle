package com.wxxiaomi.ming.electricbicycle.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.EBApplication;
import com.wxxiaomi.ming.electricbicycle.api.HttpMethods;
import com.wxxiaomi.ming.electricbicycle.bean.User;
import com.wxxiaomi.ming.electricbicycle.bean.format.InitUserInfo;
import com.wxxiaomi.ming.electricbicycle.bean.format.Login;
import com.wxxiaomi.ming.electricbicycle.bean.format.NearByPerson;
import com.wxxiaomi.ming.electricbicycle.bean.format.Register;
import com.wxxiaomi.ming.electricbicycle.dao.TempUserDao;
import com.wxxiaomi.ming.electricbicycle.dao.UserDao;
import com.wxxiaomi.ming.electricbicycle.dao.util.DbOpenHelper;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;


public class UserDaoImpl2 {

	Context context;
	DbOpenHelper helper;

	private UserDaoImpl2() {
		super();
		this.context = EBApplication.applicationContext;
		helper = DbOpenHelper.getInstance(context);
	}

	//在访问HttpMethods时创建单例
	private static class SingletonHolder {
		private static final UserDaoImpl2 INSTANCE = new UserDaoImpl2();
	}

	//获取单例
	public static UserDaoImpl2 getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/**
	 * 校对数据库的好友列表和em服务器的好友列表
	 * 多出的删除
	 * 少的就添加到result并返回
	 *
	 * @param emnamelist
	 * @return
	 */
	synchronized public Observable<List<String>> checkFriendList(final List<String> emnamelist) {
		return Observable.create(new Observable.OnSubscribe<List<String>>() {

			@Override
			public void call(Subscriber<? super List<String>> subscriber) {
				List<String> result = new ArrayList<String>();
				SQLiteDatabase db = helper.getWritableDatabase();
				List<String> daoEmnameList = new ArrayList<String>();
//		List<String> daoMoreList = new ArrayList<String>();
//		List<String> daMissingList = new ArrayList<String>();
				if (db.isOpen()) {
					Cursor cursor = db.rawQuery("select " + UserDao.COLUMN_NAME_EMNAME + " from " + UserDao.TABLE_NAME + " desc", null);
					while (cursor.moveToNext()) {
//				Log.i("wang", "")
						daoEmnameList.add(cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_EMNAME)));
					}
					//拿出数据库里面有,em服务器没有的
					for (String daoemname : daoEmnameList) {
						if (!emnamelist.contains(daoemname)) {
//					daoMoreList.add(daoemname);
							deleteFriend(daoemname);
						}
					}
					//删除操作

					//拿出em服务器有,数据库里没有的
					for (String emname : emnamelist) {
						if (!daoEmnameList.contains(emname)) {
							result.add(emname);
						}
					}
				}
				subscriber.onNext(result);
			}
		});
	}

	/**
	 * 保存好友列表
	 */
	synchronized public int saveFriendList(List<User.UserCommonInfo> userList) {
		SQLiteDatabase db = helper.getWritableDatabase();
		if (db.isOpen()) {
//			 db.delete(UserDao1.TABLE_NAME, null, null);
			for (User.UserCommonInfo user : userList) {
				ContentValues values = new ContentValues();
				values.put(UserDao.COLUMN_NAME_ID, user.userid);
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
	synchronized public List<User.UserCommonInfo> getFriendList() {
		SQLiteDatabase db = helper.getReadableDatabase();
		List<User.UserCommonInfo> list = new ArrayList<User.UserCommonInfo>();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select * from " + UserDao.TABLE_NAME + " desc", null);
			while (cursor.moveToNext()) {
				User.UserCommonInfo info = new User.UserCommonInfo();
				int id = cursor.getInt(cursor.getColumnIndex(UserDao.COLUMN_NAME_ID));
				String name = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_NAME));
				String emname = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_EMNAME));
				String head = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_HEAD));
//	                long time = cursor.getLong(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_TIME));
				info.name = name;
				info.emname = emname;
				info.head = head;
				info.userid = id;

				list.add(info);
			}
		}
		return list;
	}

	/**
	 * 更新好友列表
	 */
	synchronized public Observable<Integer> updateFriendList(final List<User.UserCommonInfo> userList) {
		return Observable.create(new Observable.OnSubscribe<Integer>() {

			@Override
			public void call(Subscriber<? super Integer> subscriber) {
				SQLiteDatabase db = helper.getWritableDatabase();
				if (db.isOpen()) {
//			 db.delete(UserDao1.TABLE_NAME, null, null);
					for (User.UserCommonInfo user : userList) {
						Log.i("wang", "新插入的好友:" + user.toString());
						ContentValues values = new ContentValues();
						values.put(UserDao.COLUMN_NAME_ID, user.userid);
						values.put(UserDao.COLUMN_NAME_NAME, user.name);
						values.put(UserDao.COLUMN_NAME_EMNAME, user.emname);
						values.put(UserDao.COLUMN_NAME_HEAD, user.head);
						db.insert(UserDao.TABLE_NAME, null, values);
					}
				}
				subscriber.onNext(1);
			}
		});

	}

	/**
	 * 根据emname从数据库取得某一位好友
	 */
	synchronized public Observable<User.UserCommonInfo> getFriendInfoByEmname(final String emname) {
		return Observable.create(new Observable.OnSubscribe<User.UserCommonInfo>() {
			@Override
			public void call(Subscriber<? super User.UserCommonInfo> subscriber) {
				SQLiteDatabase db = helper.getReadableDatabase();
				if (db.isOpen()) {
					//参数1：表名
					//参数2：要想显示的列
					//参数3：where子句
					//参数4：where子句对应的条件值
					//参数5：分组方式
					//参数6：having条件
					//参数7：排序方式
					Cursor cursor = db.query(UserDao.TABLE_NAME
							, new String[]{UserDao.COLUMN_NAME_ID, UserDao.COLUMN_NAME_NAME
									, UserDao.COLUMN_NAME_EMNAME, UserDao.COLUMN_NAME_HEAD},
							UserDao.COLUMN_NAME_EMNAME + "=?", new String[]{emname}, null, null, null);

					while (cursor.moveToNext()) {
						int id = cursor.getInt(cursor.getColumnIndex(UserDao.COLUMN_NAME_ID));
						String name = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_NAME));
						String emname1 = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_EMNAME));
						String head = cursor.getString(cursor.getColumnIndex(UserDao.COLUMN_NAME_HEAD));
//	                System.out.println("query------->" + "姓名："+name+" "+"年龄："+age+" "+"性别："+sex);
						User.UserCommonInfo info = new User.UserCommonInfo();
						info.userid = id;
						info.emname = emname1;
						info.head = head;
						info.name = name;
						subscriber.onNext(info);
					}
				}
			}
		});
	}

	/**
	 * 删除好友
	 */
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

	synchronized public Observable<InitUserInfo> getUserCommonInfoByEmname(String emname){
		Log.i("wang","根据emname从服务器获取用户公共信息,emname:"+emname);
		return HttpMethods.getInstance().getUserCommonInfoByEmname(emname);
	}

	synchronized public Observable<InitUserInfo> getUserListFromByEmList(List<String> usernames){
		return HttpMethods.getInstance().getUserListByEmList(usernames);
	}

	synchronized public Observable<NearByPerson> getNearPeople(int userid, double latitude, double longitude){
		return HttpMethods.getInstance().getNearByFromServer(userid, latitude, longitude);
	}

	synchronized public Observable<Login> Login(String username, String password) {
		return HttpMethods.getInstance().login(username, password);
	}


	/**
	 * 判断是否存在当前临时用户表中
	 * @param emname
	 * @return
     */
	public Observable<Boolean> isTempUserExist(final String emname){
		return Observable.create(new Observable.OnSubscribe<Boolean>() {
			@Override
			public void call(Subscriber<? super Boolean> subscriber) {
				SQLiteDatabase db = helper.getWritableDatabase();
				if(db.isOpen()){
					Cursor query = db.query(TempUserDao.TABLE_NAME, new String[]{"emname"}, "emname=?", new String[]{emname}, null, null, null);
					if(query.moveToNext()){
						subscriber.onNext(true);
					}else{
						subscriber.onNext(false);
					}
				}
			}
		});
	}

	/**
	 * 判断是否存在当前临时用户表中
	 * @param emname
	 * @return
	 */
	public Observable<User.UserCommonInfo> getTempuser(final String emname){
		return Observable.create(new Observable.OnSubscribe<User.UserCommonInfo>() {
			@Override
			public void call(Subscriber<? super User.UserCommonInfo> subscriber) {
				//参数1：表名
				//参数2：要想显示的列
				//参数3：where子句
				//参数4：where子句对应的条件值
				//参数5：分组方式
				//参数6：having条件
				//参数7：排序方式
//				Cursor cursor = db.query(UserDao.TABLE_NAME
//						, new String[]{UserDao.COLUMN_NAME_ID, UserDao.COLUMN_NAME_NAME
//								, UserDao.COLUMN_NAME_EMNAME, UserDao.COLUMN_NAME_HEAD},
//						UserDao.COLUMN_NAME_EMNAME + "=?", new String[]{emname}, null, null, null);
				SQLiteDatabase db = helper.getReadableDatabase();
				User.UserCommonInfo info = null;
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
						info = new User.UserCommonInfo();
						info.userid = id;
						info.emname = emname1;
						info.head = head;
						info.name = name;
					}
				}
				subscriber.onNext(info);
			}
		});
	}

	/**
	 * 把某个用户存到临时用户表
	 * @param user
	 * @return
     */
	synchronized public Observable<Integer> savaPerson(final User.UserCommonInfo user){
		if(isMyFriend(user.emname)){
			return Observable.just(0);
		}
		Log.i("wang", "TempDeoLmpl插入临时用户"+user.toString());
		return Observable.create(new Observable.OnSubscribe<Integer>() {
			@Override
			public void call(Subscriber<? super Integer> subscriber) {
				int id;
				SQLiteDatabase db = helper.getWritableDatabase();
				if(db.isOpen()){
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
					subscriber.onNext(0);
				}
			}
		});
	}

	public Observable<InitUserInfo> getUserCommonInfoByName(String name){
		return HttpMethods.getInstance().getUserCommonInfoByName(name);
	}

	public Observable<Register> registerUser(String username, String password){
		return HttpMethods.getInstance().registerUser(username, password);
	}

}
