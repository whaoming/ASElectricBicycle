package com.wxxiaomi.ming.electricbicycle.bridge.aliyun;

import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.wxxiaomi.ming.electricbicycle.ConstantValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import rx.Observable;
import rx.Subscriber;
import rx.functions.FuncN;
import rx.schedulers.Schedulers;

/**
 * Created by 12262 on 2016/11/23.
 */

public class OssEngine {
    private OssService ossService;
    private boolean isInit = false;
    private OssEngine(){}
    private static OssEngine INSTANCE;
    public static OssEngine getInstance(){
        if(INSTANCE==null){
            synchronized (OssEngine.class){
                INSTANCE = new OssEngine();
            }
        }

        return INSTANCE;
    }

    public void initOssEngine(Context ct){
        if(!isInit){
            String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
            String stsServer = ConstantValue.OSS_MY_URL;
            OSSCredentialProvider credentialProvider  = new STSGetter(stsServer);;
            String bucket = "mttext";
            ClientConfiguration conf = new ClientConfiguration();
            conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
            conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
            conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
            conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
            OSS oss = new OSSClient(ct, endpoint, credentialProvider, conf);
            ossService = new OssService(oss, bucket);
            String callbackAddress = "http://xiejinhao.me/OssDemo2/ossServlet";
            ossService.setCallbackAddress(callbackAddress);
            isInit= true;
        }

    }

    public Observable<String> uploadImage(final String fileName,final String imgPath){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                ossService.asyncPutImage(fileName, imgPath, new UpLoadListener() {
                    @Override
                    public void onFail() {
                        subscriber.onError(null);
                    }
                    @Override
                    public void onSuccess() {
                        subscriber.onNext("http://mttext.oss-cn-shanghai.aliyuncs.com/"+fileName);
                    }
                });
            }
        });
    }

    public Observable<String> uploadImageWithoutName(final String imgPath){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                UUID uuid = UUID.randomUUID();
                final String fileName =uuid+".jpg";
                ossService.asyncPutImage(fileName, imgPath, new UpLoadListener() {
                    @Override
                    public void onFail() {
                        subscriber.onError(null);
                    }
                    @Override
                    public void onSuccess() {
                        subscriber.onNext("http://mttext.oss-cn-shanghai.aliyuncs.com/"+fileName);
                    }
                });
            }
        });
    }

    public Observable<String> upLoadObj(final String fileName,final byte[] imgPath){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                ossService.asyncPutByte(fileName, imgPath, new UpLoadListener() {
                    @Override
                    public void onFail() {
                        subscriber.onError(null);
                    }
                    @Override
                    public void onSuccess() {
                        subscriber.onNext("http://mttext.oss-cn-shanghai.aliyuncs.com/"+fileName);
                    }
                })

                ;
            }
        })
                .subscribeOn(Schedulers.io())
                ;
    }

    public Observable<List<String>> upLoadObjs(Map<String,byte[]> objs){
        final List<String> result = new ArrayList<>();
        List<Observable<String>> list = new ArrayList<>();
        for(Map.Entry<String,byte[]> item : objs.entrySet()){
            Observable<String> booleanObservable = upLoadObj(item.getKey(), item.getValue());
            list.add(booleanObservable);
        }
        return Observable.zip(list, new FuncN<List<String>>() {
            @Override
            public List<String> call(Object... args) {
                for(Object item : args){
                    result.add((String)item);
                }
                return result;
            }
        });
    }

    public Observable<List<String>> upLoadObjsWithoutName(List<byte[]> objs){
        final List<String> result = new ArrayList<>();
        List<Observable<String>> list = new ArrayList<>();

        for(byte[] item : objs){
            UUID uuid = UUID.randomUUID();
            String fileName =uuid+".jpg";
            Observable<String> booleanObservable = upLoadObj(fileName, item);
            list.add(booleanObservable);
        }
        return Observable.zip(list, new FuncN<List<String>>() {
            @Override
            public List<String> call(Object... args) {
                for(Object item : args){
                    result.add((String)item);
                }
                return result;
            }
        });
    }
    
    public Observable<List<String>> upLoadImages(Map<String,String> images){
        final List<String> result = new ArrayList<>();
        List<Observable<String>> list = new ArrayList<>();
        for(Map.Entry<String,String> item : images.entrySet()){
            Observable<String> booleanObservable = uploadImage(item.getKey(), item.getValue());
            list.add(booleanObservable);
        }
        return Observable.zip(list, new FuncN<List<String>>() {
            @Override
            public List<String> call(Object... args) {
                for(Object item : args){
                    result.add((String)item);
                }
                return result;
            }
        });

    }

    public Observable<List<String>> upLoadImagesWithoutName(String[] images) {
        final List<String> result = new ArrayList<>();
        List<Observable<String>> list = new ArrayList<>();
        for(String item : images){
            item = item.replace("file://","");
            UUID uuid = UUID.randomUUID();
            String fileName =uuid+".jpg";
            Observable<String> booleanObservable = uploadImage(fileName, item);
            list.add(booleanObservable);
        }
        return Observable.zip(list, new FuncN<List<String>>() {
            @Override
            public List<String> call(Object... args) {
                for(Object item : args){
                    result.add((String)item);
                }
                return result;
            }
        });
    }

    
}
