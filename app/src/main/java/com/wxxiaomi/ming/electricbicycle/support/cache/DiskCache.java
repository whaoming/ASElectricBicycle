package com.wxxiaomi.ming.electricbicycle.support.cache;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;

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
public class DiskCache {
    DiskLruCache mDiskLruCache = null;
    public static DiskCache INSTANCE;

    private DiskCache() {
    }

    public static DiskCache getInstance() {
        if (INSTANCE == null) {
            synchronized (DiskCache.class) {
                INSTANCE = new DiskCache();
            }
        }
        return INSTANCE;
    }


    public void open(Context context) {
        try {
            File cacheDir = getDiskCacheDir(context, "bitmap");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            //第一个参数指定的是数据的缓存地址，
            // 第二个参数指定当前应用程序的版本号，
            // 第三个参数指定同一个key可以对应多少个缓存文件，基本都是传1，
            // 第四个参数指定最多可以缓存多少字节的数据。
            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 在当前线程下取得缓存对象，返回byte数组
     * @param path key
     * @return
     */
    public Observable<byte[]> getDiskCache(final String path) {
        return Observable.create(new Observable.OnSubscribe<byte[]>() {
            @Override
            public void call(Subscriber<? super byte[]> subscriber) {
                try {
                    String key = hashKeyForDisk(path);
                    DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
                    if (snapShot != null) {
                        InputStream is = snapShot.getInputStream(0);
//                        ObjectOutputStream oos = new ObjectOutputStream()
//                        BufferedInputStream buf=new BufferedInputStream(is);
//                        ObjectInputStream obj=new ObjectInputStream(buf);
//                        String info=obj.readUTF();
//                        int ok=obj.readInt();
//                        Person tempPerson=(Person)obj.readObject();

                        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
                        byte[] buff = new byte[100];
                        int rc = 0;
                        while ((rc = is.read(buff, 0, 100)) > 0) {
                            swapStream.write(buff, 0, rc);
                        }
                        byte[] in2b = swapStream.toByteArray();
                        subscriber.onNext(in2b);
                    } else {
                        subscriber.onNext(null);
                    }
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(null);
                }
            }
        });
    }

    /**
     * 在当前线程下写入chuanru de 缓存对象,返回写入的对象的byte数组
     * @param path 缓存对象的本地路径，key
     * @param obj 该缓存对象
     * @return 对象
     */
    public Observable<byte[]> WriteToDisk(final String path, final byte[] obj) {
        return Observable.create(new Observable.OnSubscribe<byte[]>() {
            @Override
            public void call(Subscriber<? super byte[]> subscriber) {
                String key = hashKeyForDisk(path);

                BufferedInputStream in = null;
                BufferedOutputStream out = null;
                try {
                    DiskLruCache.Snapshot snapshot1 = mDiskLruCache.get(key);
                    if (snapshot1 == null) {
                        DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                        in = new BufferedInputStream(new ByteArrayInputStream(obj), 8 * 1024);
                        OutputStream outputStream = editor.newOutputStream(0);

                        out = new BufferedOutputStream(outputStream, 8 * 1024);
                        int b;
                        while ((b = in.read()) != -1) {
                            out.write(b);
                        }
                        editor.commit();
                    } else {
                        Log.i("wang", "已经存在，不用存入缓存了");
                    }
                    subscriber.onNext(obj);
                    subscriber.onCompleted();

                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                        if (in != null) {
                            in.close();
                        }
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        })
                ;
    }

    /**
     * 单线程环境下写入一个缓存对象，返回成功与否
     * @param path 缓存对象的本地路径
     * @return
     */
    public Observable<Boolean> WriteToDisk(final String path) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                String key = hashKeyForDisk(path);
                BufferedInputStream in = null;
                BufferedOutputStream out = null;
                try {
                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    File file = new File(path);
                    in = new BufferedInputStream(new FileInputStream(file), 8 * 1024);
                    OutputStream outputStream = editor.newOutputStream(0);
                    out = new BufferedOutputStream(outputStream, 8 * 1024);
                    int b;
                    while ((b = in.read()) != -1) {
                        out.write(b);
                    }
                    subscriber.onNext(true);
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onNext(false);
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                        if (in != null) {
                            in.close();
                        }
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 在多线程环境下写入一个缓存对戏那个
     * @param path
     * @param obj
     * @return
     */
    public Observable<byte[]> WriteToDisk_Multi(final String path, final byte[] obj) {
        return Observable.create(new Observable.OnSubscribe<byte[]>() {
            @Override
            public void call(Subscriber<? super byte[]> subscriber) {
                String key = hashKeyForDisk(path);

                BufferedInputStream in = null;
                BufferedOutputStream out = null;
                try {
                    DiskLruCache.Snapshot snapshot1 = mDiskLruCache.get(key);
                    if (snapshot1 == null) {
                        Log.i("wang", "存入缓存");
                        DiskLruCache.Editor editor = mDiskLruCache.edit(key);

                        in = new BufferedInputStream(new ByteArrayInputStream(obj), 8 * 1024);
                        OutputStream outputStream = editor.newOutputStream(0);
                        out = new BufferedOutputStream(outputStream, 8 * 1024);
                        int b;
                        while ((b = in.read()) != -1) {
                            out.write(b);
                        }
                        editor.commit();
                    } else {
                        Log.i("wang", "已经存在，不用存入缓存了");
                    }
                    subscriber.onNext(obj);
                    subscriber.onCompleted();

                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                        if (in != null) {
                            in.close();
                        }
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        })
                .subscribeOn(Schedulers.newThread())
                ;
    }



    /**
     * 在子线程中取得一个缓存对象，返回这个对象的byte数组
     * @param url key
     * @return byte
     */
    public Observable<byte[]> getDiskCache_Multi(final String url) {
        return Observable.create(new Observable.OnSubscribe<byte[]>() {
            @Override
            public void call(Subscriber<? super byte[]> subscriber) {
                try {
                    String key = hashKeyForDisk(url);
                    DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
                    if (snapShot != null) {
                        InputStream is = snapShot.getInputStream(0);
                        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
                        byte[] buff = new byte[100];
                        int rc = 0;
                        while ((rc = is.read(buff, 0, 100)) > 0) {
                            swapStream.write(buff, 0, rc);
                        }
                        byte[] in2b = swapStream.toByteArray();
                        subscriber.onNext(in2b);
                    } else {
                        subscriber.onNext(null);
                    }
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(null);
                }
            }
        })
                .subscribeOn(Schedulers.newThread())
                ;
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public int getAppVersion(Context context) {
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
    public File getDiskCacheDir(Context context, String uniqueName) {
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
    public String hashKeyForDisk(String key) {
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

    private String bytesToHexString(byte[] bytes) {
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
