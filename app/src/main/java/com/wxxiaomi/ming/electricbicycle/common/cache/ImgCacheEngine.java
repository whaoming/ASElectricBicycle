package com.wxxiaomi.ming.electricbicycle.common.cache;



import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.FuncN;
import rx.schedulers.Schedulers;

/**
 * Created by 12262 on 2016/11/25.
 * 图片缓存的处理服务
 */
public class ImgCacheEngine {
    private ImgCacheEngine(){}
    private static ImgCacheEngine INSTANCE;
    public static ImgCacheEngine getInstance(){
        if(INSTANCE==null){
            synchronized (ImgCacheEngine.class){
                INSTANCE = new ImgCacheEngine();
            }
        }
        return INSTANCE;
    }

    /**
     * 多线程下根据path获取本地的一个缓存对象，返回byte数组：
     * 先从硬盘获取，如果硬盘获取不到
     * 再拿图片进行压缩，再存到硬盘缓存
     * 然后返回
     * @param path 对象的本地路径
     * @return 缓存对象的byte数组
     */
    public Observable<byte[]> getLocalObj_Multi(final String path){
        Observable<byte[]> diskCache = DiskCache.getInstance().getDiskCache_Multi(path);
        Observable<byte[]> doCache = ImageCompressUtil.compressImg(path, 100, 100)
                .flatMap(new Func1<byte[], Observable<byte[]>>() {
                    @Override
                    public Observable<byte[]> call(final byte[] bytes) {
                        return DiskCache.getInstance().WriteToDisk_Multi(path, bytes);
                    }
                });
        return Observable.concat(diskCache,doCache)
                .first(new Func1<byte[], Boolean>() {
                    @Override
                    public Boolean call(byte[] bytes) {
                        return bytes != null;
                    }
                });
    }

    /**
     * 根据一张图片的本地地址，当前线程环境下获取此地址一张图片的缓存
     * 先从硬盘获取，如果硬盘获取不到
     * 再拿图片进行压缩，再存到硬盘缓存
     * 然后返回byte数组
     * @param path 本地地址
     * @return 返回结果
     */
    public Observable<byte[]> getImage(final String path){
        Observable<byte[]> diskCache = DiskCache.getInstance().getDiskCache(path);
        return Observable.concat(diskCache,getDoImageCache(path))
                .first(new Func1<byte[], Boolean>() {
                    @Override
                    public Boolean call(byte[] bytes) {
                        return bytes != null;
                    }
                });
    }

    /**
     * 对图片做压缩并缓存的一个操作
     * @param path 图片的本地地址
     * @return 压缩后的byte
     */
    public Observable<byte[]> getDoImageCache(final String path){
        return   ImageCompressUtil.compressImgInMain(path, 100, 100)
                .flatMap(new Func1<byte[], Observable<byte[]>>() {
                    @Override
                    public Observable<byte[]> call(final byte[] bytes) {
                        return DiskCache.getInstance().WriteToDisk(path, bytes);
                    }
                });
    }

    /**
     * 根据本地图片地址的一个集合，来获取缓存集合(如果缓存不存在则压缩存入再返回)
     * 这多张图片取得的缓存结果合并在一个源里面一起发送
     * @param path
     * @return
     */
    public Observable<List<byte[]>> getImages_Zip(final List<String> path){
        List<Observable<byte[]>> list = new ArrayList<>();
        final List<byte[]> result = new ArrayList<>();
        for(String item : path){
            Observable<byte[]> localImg = getImage(item).subscribeOn(Schedulers.io());
            list.add(localImg);
        }
        return Observable.zip(list, new FuncN<List<byte[]>>() {
            @Override
            public List<byte[]> call(Object... args) {
                for(Object item : args){
                    result.add((byte[])item);
                }
                return result;
            }
        });
    }

    /**
     * 根据本地图片地址的一个集合，来获取缓存集合(如果缓存不存在则压缩存入再返回)
     * 这多张图片取得的缓存结果分为多个源发送
     * @param path
     * @return
     */
    public Observable<byte[]> getImages_Merge(final List<String> path){
        List<Observable<byte[]>> list = new ArrayList<>();
        for(String item : path){
            Observable<byte[]> localImg = getImage(item);
            list.add(localImg);
        }
       return Observable.merge(list);

    }

    /**
     * 从本地数据取一些图片，分发多个源发送
     * @param path
     * @return
     */
    public Observable<byte[]> getImages_Form(final List<String> path){
        return Observable.from(path)
                .flatMap(new Func1<String, Observable<byte[]>>() {
                    @Override
                    public Observable<byte[]> call(String s) {
                        return getImage(s);
                    }
                });

    }

}
