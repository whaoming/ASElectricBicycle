//package com.wxxiaomi.ming.electricbicycle.improve.update;
//
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.Message;
//import android.support.annotation.Nullable;
//import android.widget.RemoteViews;
//
//import net.oschina.app.AppConfig;
//import net.oschina.app.R;
//import net.oschina.app.improve.main.MainActivity;
//import net.oschina.common.utils.StreamUtil;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
///**
// * 下载服务
// * Created by haibin
// * on 2016/10/19.
// */
//@SuppressWarnings("all")
//public class DownloadService extends Service {
//    public static boolean isDownload = false;
//
//    private String mUrl;
//    private String mTitle = "正在下载";
////    private String saveFileName = AppConfig.DEFAULT_SAVE_FILE_PATH;
//
//    private NotificationManager mNotificationManager;
//    private Notification mNotification;
//
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 0:
//                    mNotificationManager.cancel(0);
//                    installApk();
//                    break;
//                case 1:
//                    int rate = msg.arg1;
//                    if (rate < 100) {
//                        RemoteViews views = mNotification.contentView;
//                        views.setTextViewText(R.id.tv_download_progress, mTitle + "(" + rate
//                                + "%" + ")");
//                        views.setProgressBar(R.id.pb_progress, 100, rate,
//                                false);
//                    } else {
//                        // 下载完毕后变换通知形式
//                        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
//                        mNotification.contentView = null;
//                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//
//                        intent.putExtra("completed", "yes");
//
//                        PendingIntent contentIntent = PendingIntent.getActivity(
//                                getApplicationContext(), 0, intent,
//                                PendingIntent.FLAG_UPDATE_CURRENT);
//                    }
//                    mNotificationManager.notify(0, mNotification);
//                    break;
//            }
//
//        }
//    };
//
//    public static void startService(Context context, String downloadUrl) {
//        Intent intent = new Intent(context, DownloadService.class);
//        intent.putExtra("url", downloadUrl);
//        context.startService(intent);
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        isDownload = true;
//        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        mUrl = intent.getStringExtra("url");
//        File file = new File(saveFileName);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        final File apkFile = new File(saveFileName + "osc.apk");
//        setUpNotification();
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    downloadUpdateFile(mUrl, apkFile);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    private long downloadUpdateFile(String downloadUrl, File saveFile) throws Exception {
//        int downloadCount = 0;
//        int currentSize = 0;
//        long totalSize = 0;
//        int updateTotalSize = 0;
//
//        HttpURLConnection httpConnection = null;
//        InputStream is = null;
//        FileOutputStream fos = null;
//
//        try {
//            URL url = new URL(downloadUrl);
//            httpConnection = (HttpURLConnection) url.openConnection();
//            httpConnection.setConnectTimeout(10000);
//            httpConnection.setReadTimeout(20000);
//            updateTotalSize = httpConnection.getContentLength();
//            if (httpConnection.getResponseCode() == 404) {
//                throw new Exception("fail!");
//            }
//            is = httpConnection.getInputStream();
//            fos = new FileOutputStream(saveFile, false);
//            byte buffer[] = new byte[2048];
//            int readSize = 0;
//            while ((readSize = is.read(buffer)) > 0) {
//
//                fos.write(buffer, 0, readSize);
//                totalSize += readSize;
//                if ((downloadCount == 0)
//                        || (int) (totalSize * 100 / updateTotalSize) - 4 > downloadCount) {
//                    downloadCount += 4;
//                    Message msg = mHandler.obtainMessage();
//                    msg.what = 1;
//                    msg.arg1 = downloadCount;
//                    mHandler.sendMessage(msg);
//                }
//            }
//
//            mHandler.sendEmptyMessage(0);
//            isDownload = false;
//
//        } finally {
//            if (httpConnection != null) {
//                httpConnection.disconnect();
//            }
//            StreamUtil.close(is, fos);
//            stopSelf();
//        }
//        return totalSize;
//    }
//
//    private void installApk() {
//        File file = new File(saveFileName + "osc.apk");
//        if (!file.exists())
//            return;
//        Intent intent = new Intent();
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//        startActivity(intent);
//    }
//
//    private void setUpNotification() {
//        int icon = R.mipmap.ic_launcher;
//        CharSequence tickerText = "开始下载";
//        long when = System.currentTimeMillis();
//        mNotification = new Notification(icon, tickerText, when);
//
//        mNotification.flags = Notification.FLAG_ONGOING_EVENT;
//
//        RemoteViews contentView = new RemoteViews(getPackageName(),
//                R.layout.layout_notification_view);
//        contentView.setTextViewText(R.id.tv_download_progress, mTitle);
//        mNotification.contentView = contentView;
//
//        Intent intent = new Intent(this, MainActivity.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//                intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        mNotification.contentIntent = contentIntent;
//        mNotificationManager.notify(0, mNotification);
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onDestroy() {
//        isDownload = false;
//        super.onDestroy();
//    }
//}
