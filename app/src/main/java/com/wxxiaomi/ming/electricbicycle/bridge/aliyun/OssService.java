package com.wxxiaomi.ming.electricbicycle.bridge.aliyun;

import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import java.io.File;
import java.util.HashMap;

/**
 * Created by oss on 2015/12/7 0007.
 * 支持普通上传，普通下载和断点上传
 */
public class OssService {

    private OSS oss;
    private String bucket;
//    private UIDisplayer UIDisplayer;
//    private MultiPartUploadManager multiPartUploadManager;
    private String callbackAddress;
    //根据实际需求改变分片大小
    private final static int partSize = 256 * 1024;


    public OssService(OSS oss, String bucket) {
        this.oss = oss;
        this.bucket = bucket;
    }

    public void SetBucketName(String bucket) {
        this.bucket = bucket;
    }

    public void InitOss(OSS _oss) {
        this.oss = _oss;
    }

    public void setCallbackAddress(String callbackAddress) {
        this.callbackAddress = callbackAddress;
    }

    public void asyncPutImage(String object, String localFile,final UpLoadListener liss) {
        if (object.equals("")) {
            Log.w("AsyncPutImage", "ObjectNull");
            return;
        }

        File file = new File(localFile);
        if (!file.exists()) {
            Log.w("AsyncPutImage", "FileNotExist");
            Log.w("LocalFile", localFile);
            return;
        }


        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(bucket, object, localFile);

        if (callbackAddress != null) {
            // 传入对应的上传回调参数，这里默认使用OSS提供的公共测试回调服务器地址
            put.setCallbackParam(new HashMap<String, String>() {
                {
                    put("callbackUrl", callbackAddress);
                    //callbackBody可以自定义传入的信息
                    put("callbackBody", "{\"state\":\"200\"}");
                }
            });
        }

        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                //Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
                int progress = (int) (100 * currentSize / totalSize);
//                UIDisplayer.updateProgress(progress);
//                UIDisplayer.displayInfo("上传进度: " + String.valueOf(progress) + "%");
            }
        });

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                if(liss!=null) liss.onSuccess();
                Log.d("PutObject", "UploadSuccess");

                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());

            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                if(liss!=null) liss.onFail();
                String info = "";
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    info = clientExcepion.toString();
                }

                Log.e("wang","Bucket: " + bucket
                        + "\nObject: " + request.getObjectKey()
                        );
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                    info = serviceException.toString();
                }
//                UIDisplayer.uploadFail(info);
//                UIDisplayer.displayInfo(info);
            }
        });
        Log.i("wang","task:"+task);
    }

    public void asyncPutByte(String object, byte[] localFile,final UpLoadListener liss) {
        if (object.equals("")) {
            Log.w("AsyncPutImage", "ObjectNull");
            return;
        }




        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(bucket, object, localFile);

        if (callbackAddress != null) {
            // 传入对应的上传回调参数，这里默认使用OSS提供的公共测试回调服务器地址
            put.setCallbackParam(new HashMap<String, String>() {
                {
                    put("callbackUrl", callbackAddress);
                    //callbackBody可以自定义传入的信息
                    put("callbackBody", "{\"state\":\"200\"}");
                }
            });
        }

        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                //Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
                int progress = (int) (100 * currentSize / totalSize);
//                UIDisplayer.updateProgress(progress);
//                UIDisplayer.displayInfo("上传进度: " + String.valueOf(progress) + "%");
            }
        });

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                if(liss!=null) liss.onSuccess();
                Log.d("PutObject", "UploadSuccess");

                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());

            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                if(liss!=null) liss.onFail();
                String info = "";
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    info = clientExcepion.toString();
                }

                Log.e("wang","Bucket: " + bucket
                        + "\nObject: " + request.getObjectKey()
                );
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                    info = serviceException.toString();
                }
//                UIDisplayer.uploadFail(info);
//                UIDisplayer.displayInfo(info);
            }
        });
        Log.i("wang","task:"+task);
    }

}
