package com.wxxiaomi.ming.electricbicycle.common.photoget;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by 12262 on 2016/11/23.
 */

public class PhotoTakeUtil {
    private Context context;
    private GalleryConfig galleryConfig;
    private IHandlerCallBack iHandlerCallBack;
    private String TAG = "wang";
    private List<String> path = new ArrayList<>();
    private final int PERMISSIONS_REQUEST_READ_CONTACTS = 8;
//    private Map<String,String> cache = new HashMap<>();

    public PhotoTakeUtil(Context context){
        this.context = context;

        galleryConfig = new GalleryConfig.Builder()
                .imageLoader(new GlideImageLoader())    // ImageLoader 加载框架（必填）
//                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                .pathList(path)                         // 记录已选的图片
                .multiSelect(false)                      // 是否多选   默认：false
                .multiSelect(false, 9)                   // 配置是否多选的同时 配置多选数量   默认：false ， 9
                .maxSize(9)                             // 配置多选时 的多选数量。    默认：9
                .crop(false)                             // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                .crop(false, 1, 1, 500, 500)             // 配置裁剪功能的参数，   默认裁剪比例 1:1
                .isShowCamera(true)                     // 是否现实相机按钮  默认：false
                .filePath("/Gallery/Pictures")          // 图片存放路径
                .build();
        galleryConfig.getBuilder().isShowCamera(true).build();
    }

    /**
     * 获取一张图片
     * @return
     */
    public Observable<List<String>> takePhoto(){
        galleryConfig.getBuilder().multiSelect(false).build();
        galleryConfig.getBuilder().crop(false).build();
        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                setListener(subscriber);
                openTakeWindow();
            }
        });

    }

    /**
     * 获取多张图片
     * @return
     */
    public Observable<List<String>> takePhotos(){
        Log.i("wang","takePhotos");
        galleryConfig.getBuilder().multiSelect(true).build();
        galleryConfig.getBuilder().crop(false).build();
        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                setListener(subscriber);
                openTakeWindow();
            }
        });
    }

    /**
     * 获取一张裁剪过的图片
     * @return
     */
    public Observable<List<String>> takePhotoCut(){
        galleryConfig.getBuilder().multiSelect(false).build();
        galleryConfig.getBuilder().crop(true).build();
        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                setListener(subscriber);
                openTakeWindow();
            }
        })
               ;
    }

    public void openTakeWindow(){
        galleryConfig.getBuilder().isOpenCamera(false).build();
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "需要授权 ");
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Log.i(TAG, "拒绝过了");
                Toast.makeText(context, "请在 设置-应用管理 中开启此应用的储存授权。", Toast.LENGTH_SHORT).show();
            } else {
                Log.i(TAG, "进行授权");
                ActivityCompat.requestPermissions((Activity)context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        } else {
            Log.i(TAG, "不需要授权 sadsa,context==null?"+context==null?"true":"false"+",galleryConfig==null?"+galleryConfig==null?"true":"false");
            GalleryPick.getInstance().setGalleryConfig(galleryConfig).open((Activity)context);

        }
    }

    public void setListener(final Subscriber<? super List<String>> subscriber){
        iHandlerCallBack = new IHandlerCallBack() {
            @Override
            public void onStart() {
                Log.i(TAG, "onStart: 开启");
            }

            @Override
            public void onSuccess(List<String> photoList) {
                Log.i(TAG, "onSuccess: 返回数据");
//                for (String s : photoList) {
//                    Log.i(TAG, s);
//                }
//                List<String> cache = new ArrayList<>();
//                for(String item : photoList){
//                    Log.i("wang","item:"+item);
//                    cache.add(ImageCompressUtil.compressBitmap(item,200,200,false));
//                }

               subscriber.onNext(photoList);
            }

            @Override
            public void onCancel() {
                Log.i(TAG, "onCancel: 取消");
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "onFinish: 结束");
            }

            @Override
            public void onError() {
                Log.i(TAG, "onError: 出错");
            }
        };

        galleryConfig.getBuilder().iHandlerCallBack(iHandlerCallBack);
    }

    public void cancel(){
        if(context!=null) {
            context = null;
            galleryConfig = null;
        }
    }
}
