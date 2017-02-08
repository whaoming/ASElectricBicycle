package com.wxxiaomi.ming.electricbicycle.common.cache;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * 1、避免内存过多的压缩方法：
 * 归根结底，图片是要显示在界面组件上的，所以还是要用到bitmap，
 * 从上面可得出Bitmap的在内存中的大小只和图片尺寸和色彩模式有关，
 * 那么要想改变Bitmap在内存中的大小，要么改变尺寸，要么改变色彩模式。
 * 2、避免上传浪费流量的压缩方法：
 * 改变图片尺寸，改变色彩模式，改变图片质量都行。正常情况下，先改变图片尺寸和色彩模式，再改变图片质量
 */
public class ImageCompressUtil {

    /**
     * 传入指定的图片地址，然后进行压缩，并返回byte
     * 需要注意的是：最好的新的线程做压缩工作，因为这是耗时操作
     *
     * @param srcPath
     * @param rqsW
     * @param rqsH
     * @return
     */
    public final static Observable<byte[]> compressImg(final String srcPath, final int rqsW, final int rqsH) {
        return
                Observable.create(new Observable.OnSubscribe<byte[]>() {
                    @Override
                    public void call(Subscriber<? super byte[]> subscriber) {
                        Bitmap bitmap = compressBitmap(srcPath, rqsW, rqsH);
                        int degree = getDegress(srcPath);
                        try {
                            if (degree != 0) bitmap = rotateBitmap(bitmap, degree);
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 70, bos);
                            byte[] bytes = bos.toByteArray();
                            subscriber.onNext(bytes);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .subscribeOn(Schedulers.newThread())
                ;
    }


    /**
     * 将img的byte数据通过base64编码转化为字符串
     * @param par
     * @return
     */
    public static Observable<String> byte2Base64(final byte[] par){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Log.i("cache","处理数据的线程currentThread:"+Thread.currentThread().getName());
                String temp = Base64.encodeToString(par, Base64.DEFAULT);
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(temp);
                temp = m.replaceAll("");
                String img = "<img src=\"data:image/png;base64,"+temp+"\" height=\"70\" width=\"70\"/>";
                subscriber.onNext(img);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread());
    }

    /**
     * rotate the bitmap
     *
     * @param bitmap
     * @param degress
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }

    /**
     * 压缩指定路径的图片，并得到图片对象
     *
     * @param path bitmap source path
     * @return Bitmap {@link Bitmap}
     */
    public final static Bitmap compressBitmap(String path, int rqsW, int rqsH) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = caculateInSampleSize(options, rqsW, rqsH);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }


    /**
     * caculate the bitmap sampleSize
     * 计算bitmap的尺寸
     *
     * @return
     */
    public final static int caculateInSampleSize(BitmapFactory.Options options, int rqsW, int rqsH) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (rqsW == 0 || rqsH == 0) return 1;
        if (height > rqsH || width > rqsW) {
            final int heightRatio = Math.round((float) height / (float) rqsH);
            final int widthRatio = Math.round((float) width / (float) rqsW);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 得到指定路径图片的options
     *
     * @param srcPath
     * @return Options {@link BitmapFactory.Options}
     */
    public final static BitmapFactory.Options getBitmapOptions(String srcPath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath, options);
        return options;
    }


    /**
     * get the orientation of the bitmap {@link ExifInterface}
     * 获取bitmap的方向信息
     *
     * @param path
     * @return
     */
    public final static int getDegress(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    public static Observable<byte[]> compressImgInMain(final String srcPath, final int rqsW, final int rqsH) {
        return
                Observable.create(new Observable.OnSubscribe<byte[]>() {
                    @Override
                    public void call(Subscriber<? super byte[]> subscriber) {
                        Bitmap bitmap = compressBitmap(srcPath, rqsW, rqsH);
                        int degree = getDegress(srcPath);
                        try {
                            if (degree != 0) bitmap = rotateBitmap(bitmap, degree);
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 50, bos);
                            byte[] bytes = bos.toByteArray();
                            subscriber.onNext(bytes);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    /**
     * 压缩指定路径图片，并将其保存在缓存目录中，通过isDelSrc判定是否删除源文件，并获取到缓存后的图片路径
     *
     * @return
     */
//    public final static String compressBitmap(String srcPath, int rqsW, int rqsH, boolean isDelSrc) {
//        Bitmap bitmap = compressBitmap(srcPath, rqsW, rqsH);
//        File srcFile = new File(srcPath);
//        String desPath = "storage/sdcard0/Gallery/Pictures/" + srcFile.getName();
//        int degree = getDegress(srcPath);
//        try {
//            if (degree != 0) bitmap = rotateBitmap(bitmap, degree);
//            File file = new File(desPath);
//            FileOutputStream fos = new FileOutputStream(file);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 70, fos);
//            fos.close();
//            if (isDelSrc) srcFile.deleteOnExit();
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//        return desPath;
//    }

    /**
     * 将本地图片转化为byte数组
     * @param imgPath
     * @return
     */
    public static Observable<byte[]> File2Byte(final String imgPath){
        return Observable.create(new Observable.OnSubscribe<byte[]>() {
            @Override
            public void call(Subscriber<? super byte[]> subscriber) {
                Bitmap bmp = BitmapFactory.decodeFile(imgPath);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                subscriber.onNext(byteArray);
                subscriber.onCompleted();
            }
        });
    }
}
