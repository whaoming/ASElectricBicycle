package com.wxxiaomi.ming.electricbicycle.support.cache;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;
import com.wxxiaomi.ming.electricbicycle.db.bean.format.UserInfo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by 12262 on 2016/11/24.
 * 硬盘缓存
 */
public class CacheManager {
    private DiskLruCache mBitmapCache = null;
    private DiskLruCache mUserCache = null;
    public static CacheManager INSTANCE;

    private CacheManager() {
    }

    public static void init(Context context){
        try {
            if (INSTANCE == null) {
                synchronized (CacheManager.class) {
                    INSTANCE = new CacheManager();
                }
            }
            File bitmapCacheDir = getDiskCacheDir(context, "bitmap");
            File userCacheDir = getDiskCacheDir(context, "user");
            if (!bitmapCacheDir.exists()) {
                bitmapCacheDir.mkdirs();
            }
            if (!userCacheDir.exists()) {
                userCacheDir.mkdirs();
            }
            //第一个参数指定的是数据的缓存地址，
            // 第二个参数指定当前应用程序的版本号，
            // 第三个参数指定同一个key可以对应多少个缓存文件，基本都是传1，
            // 第四个参数指定最多可以缓存多少字节的数据。
            INSTANCE.mBitmapCache = DiskLruCache.open(bitmapCacheDir, getAppVersion(context), 1, 10 * 1024 * 1024);
            INSTANCE.mUserCache = DiskLruCache.open(userCacheDir, getAppVersion(context), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> void savaUserInfo(int userid,T t){
        OutputStream outputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            String key = hashKeyForDisk(String.valueOf(userid));
//            DiskLruCache.Snapshot snapShot = INSTANCE.mUserCache.get(key);
                DiskLruCache.Editor editor = INSTANCE.mUserCache.edit(key);
                outputStream = editor.newOutputStream(0);
                objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(t);
                editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static <T> T getUserInfo(int userid){
        InputStream is = null;
        ObjectInputStream objectInputStream = null;
        try{
            String key = hashKeyForDisk(String.valueOf(userid));
            DiskLruCache.Snapshot snapShot = INSTANCE.mUserCache.get(key);
            if (snapShot != null) {
                is = snapShot.getInputStream(0);
                objectInputStream = new ObjectInputStream(is);
                T t = (T)objectInputStream.readObject();
                return t;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }





    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }


    /**
     * 获取缓存位置
     *
     * @param context
     * @param uniqueName
     * @return
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            try {
                cachePath = context.getExternalCacheDir().getPath();
            }catch (Exception e){
                cachePath = context.getCacheDir().getPath();
            }
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 用来将字符串进行MD5编码
     *
     * @param key
     * @return
     */
    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }


}
