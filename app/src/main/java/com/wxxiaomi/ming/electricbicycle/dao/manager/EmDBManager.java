package com.wxxiaomi.ming.electricbicycle.dao.manager;//package com.wxxiaomi.electricbicycle.dao.manager;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.content.ContentValues;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.util.Log;
//
//import com.wxxiaomi.electricbicycle.EBApplication;
//import com.wxxiaomi.electricbicycle.bean.User.UserCommonInfo;
//import com.wxxiaomi.electricbicycle.dao.DbOpenHelper;
//import com.wxxiaomi.electricbicycle.dao.InviteMessage;
//import com.wxxiaomi.electricbicycle.dao.InviteMessgeDao;
//import com.wxxiaomi.electricbicycle.dao.UserDao;
//
//
//public class EmDBManager {
//	 static private EmDBManager dbMgr = new EmDBManager();
//	 private DbOpenHelper dbHelper;
//	 
//	  private EmDBManager(){
//	        dbHelper = DbOpenHelper.getInstance(EBApplication.getInstance().getApplicationContext());
//	    }
//	  
//	  public static synchronized EmDBManager getInstance(){
//	        if(dbMgr == null){
//	            dbMgr = new EmDBManager();
//	        }
//	        return dbMgr;
//	    }
//	  
//	 public synchronized void setUnreadNotifyCount(int count){
////		 dbHelper.get
//	        SQLiteDatabase db = dbHelper.getWritableDatabase();
//	        if(db.isOpen()){
//	            ContentValues values = new ContentValues();
//	            values.put(InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT, count);
//
//	            db.update(InviteMessgeDao.TABLE_NAME, values, null,null);
//	        }
//	        
//	    }
//	  
//	  public synchronized int getUnreadNotifyCount(){
//		  Log.i("wang", "EmManager->getUnreadNotifyCount()");
//	        int count = 0;
//	        SQLiteDatabase db = dbHelper.getReadableDatabase();
//	        Log.i("wang", "db.isOpen()="+db.isOpen());
//	        if(db.isOpen()){
//	            Cursor cursor = db.rawQuery("select " + InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT + " from " + InviteMessgeDao.TABLE_NAME, null);
//	            if(cursor.moveToFirst()){
//	                count = cursor.getInt(0);
//	            }
//	            cursor.close();
//	        }
//	         return count;
//	    }
//	  
//	  /**
//	     * 保存message
//	     * @param message
//	     * @return  返回这条messaged在db中的id
//	     */
//	    public synchronized Integer saveMessage(InviteMessage message){
//	        SQLiteDatabase db = dbHelper.getWritableDatabase();
//	        int id = -1;
//	        if(db.isOpen()){
//	            ContentValues values = new ContentValues();
//	            values.put(InviteMessgeDao.COLUMN_NAME_FROM, message.getFrom());
//	            values.put(InviteMessgeDao.COLUMN_NAME_REASON, message.getReason());
//	            values.put(InviteMessgeDao.COLUMN_NAME_TIME, message.getTime());
//	            db.insert(InviteMessgeDao.TABLE_NAME, null, values);
//	            
//	            Cursor cursor = db.rawQuery("select last_insert_rowid() from " + InviteMessgeDao.TABLE_NAME,null); 
//	            if(cursor.moveToFirst()){
//	                id = cursor.getInt(0);
//	            }
//	            
//	            cursor.close();
//	        }
//	        return id;
//	    }
//	  
//	  /**
//	     * 保存好友list
//	     * 
//	     * @param contactList
//	     */
//	    synchronized public void saveContactList(List<UserCommonInfo> contactList) {
//	        SQLiteDatabase db = dbHelper.getWritableDatabase();
//	        if (db.isOpen()) {
//	            db.delete(UserDao.TABLE_NAME, null, null);
//	            for (UserCommonInfo user : contactList) {
//	                ContentValues values = new ContentValues();
//	                values.put(UserDao.COLUMN_NAME_ID, user.userid);
//	                    values.put(UserDao.COLUMN_NAME_NAME, user.name);
//	                    values.put(UserDao.COLUMN_NAME_EMNAME, user.emname);
//	                    values.put(UserDao.COLUMN_NAME_HEAD, user.head);
//	                db.replace(UserDao.TABLE_NAME, null, values);
//	            }
//	        }
//	    }
//	    
//	    /**
//	     * 获取messges
//	     * @return
//	     */
//	    synchronized public List<InviteMessage> getMessagesList(){
//	        SQLiteDatabase db = dbHelper.getReadableDatabase();
//	        List<InviteMessage> msgs = new ArrayList<InviteMessage>();
//	        if(db.isOpen()){
//	            Cursor cursor = db.rawQuery("select * from " + InviteMessgeDao.TABLE_NAME + " desc",null);
//	            while(cursor.moveToNext()){
//	                InviteMessage msg = new InviteMessage();
//	                int id = cursor.getInt(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_ID));
//	                String from = cursor.getString(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_FROM));
//	                String reason = cursor.getString(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_REASON));
//	                long time = cursor.getLong(cursor.getColumnIndex(InviteMessgeDao.COLUMN_NAME_TIME));
//	                
//	                msg.setId(id);
//	                msg.setFrom(from);
//	                msg.setReason(reason);
//	                msg.setTime(time);
//	                
//	                
//	                msgs.add(msg);
//	            }
//	            cursor.close();
//	        }
//	        return msgs;
//	    }
//}
