//package com.wxxiaomi.ming.electricbicycle.improve.common;
//
//import android.app.Activity;
//import android.content.ActivityNotFoundException;
//import android.content.ClipboardManager;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.content.pm.ResolveInfo;
//import android.content.res.Configuration;
//import android.graphics.Rect;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.net.Uri;
//import android.text.TextUtils;
//import android.util.DisplayMetrics;
//import android.view.View;
//import android.view.Window;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.EditText;
//
//
//import java.io.File;
//import java.util.List;
//
///**
// *
// * @updator thanatosx
// */
//public class TDevice {
//
//    public static float dp2px(float dp) {
//        return dp * getDisplayMetrics().density;
//    }
//
//    public static float px2dp(float px) {
//        return px / getDisplayMetrics().density;
//    }
//
//    public static DisplayMetrics getDisplayMetrics() {
//        return BaseApplication.context().getResources().getDisplayMetrics();
//    }
//
//    public static float getScreenHeight() {
//        return getDisplayMetrics().heightPixels;
//    }
//
//    public static float getScreenWidth() {
//        return getDisplayMetrics().widthPixels;
//    }
//
//    public static int getStatusBarHeight(Activity context) {
//        Rect rectangle = new Rect();
//        Window window = context.getWindow();
//        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
//        return rectangle.top;
//    }
//
//    public static boolean hasInternet() {
//        ConnectivityManager cm = (ConnectivityManager) BaseApplication.context()
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo info = cm.getActiveNetworkInfo();
//        return info != null && info.isAvailable() && info.isConnected();
//    }
//
//    public static boolean isPortrait() {
//        return BaseApplication.context().getResources().getConfiguration()
//                .orientation == Configuration.ORIENTATION_PORTRAIT;
//    }
//
//    /**
//     * 打开或关闭键盘
//     */
//    public static void startOrCloseKeyboard(View view) {
//        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        // 得到InputMethodManager的实例
//        if (imm.isActive()) {
//            // 如果开启
//            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
//                    InputMethodManager.HIDE_NOT_ALWAYS);
//        }
//    }
//
//    public static void closeKeyboard(EditText view) {
//        view.clearFocus();
//        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
//    }
//
//    public static void openKeyboard(View view) {
//        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//    }
//
//    /**
//     * 平板电脑?
//     *
//     * @return ??
//     */
//    public static boolean isTablet() {
//        int s = BaseApplication.context().getResources().getConfiguration().screenLayout;
//        s &= Configuration.SCREENLAYOUT_SIZE_MASK;
//        return s >= Configuration.SCREENLAYOUT_SIZE_LARGE;
//    }
//
//    public static void hideSoftKeyboard(View view) {
//        if (view == null) return;
//        View mFocusView = view;
//
//        Context context = view.getContext();
//        if (context != null && context instanceof Activity) {
//            Activity activity = ((Activity) context);
//            mFocusView = activity.getCurrentFocus();
//        }
//        if (mFocusView == null) return;
//        mFocusView.clearFocus();
//        InputMethodManager manager = (InputMethodManager) mFocusView.getContext()
//                .getSystemService(Context.INPUT_METHOD_SERVICE);
//        manager.hideSoftInputFromWindow(mFocusView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//    }
//
//    public static void showSoftKeyboard(View view) {
//        if (view == null) return;
//        view.setFocusable(true);
//        view.setFocusableInTouchMode(true);
//        if (!view.isFocused()) view.requestFocus();
//
//        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext()
//                .getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputMethodManager.showSoftInput(view, 0);
//    }
//
//    public static void gotoMarket(Context context, String pck) {
//        if (!isHaveMarket(context)) {
//            AppContext.showToast("你手机中没有安装应用市场！");
//            return;
//        }
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("market://details?id=" + pck));
//        if (intent.resolveActivity(context.getPackageManager()) != null) {
//            context.startActivity(intent);
//        }
//    }
//
//    public static boolean isHaveMarket(Context context) {
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_APP_MARKET);
//        PackageManager pm = context.getPackageManager();
//        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
//        return infos.size() > 0;
//    }
//
//    public static void openAppInMarket(Context context) {
//        if (context == null) return;
//        String pckName = context.getPackageName();
//        gotoMarket(context, pckName);
//    }
//
//    public static int getVersionCode() {
//        return getVersionCode(BaseApplication.context().getPackageName());
//    }
//
//    public static int getVersionCode(String packageName) {
//        try {
//            return BaseApplication.context()
//                    .getPackageManager()
//                    .getPackageInfo(packageName, 0)
//                    .versionCode;
//        } catch (PackageManager.NameNotFoundException ex) {
//            return 0;
//        }
//    }
//
//    public static String getVersionName() {
//        try {
//            return BaseApplication
//                    .context()
//                    .getPackageManager()
//                    .getPackageInfo(BaseApplication.context().getPackageName(), 0)
//                    .versionName;
//        } catch (PackageManager.NameNotFoundException ex) {
//            return "undefined version name";
//        }
//    }
//
//    public static void installAPK(Context context, File file) {
//        if (file == null || !file.exists())
//            return;
//        Intent intent = new Intent();
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//        context.startActivity(intent);
//    }
//
//    public static boolean openAppActivity(Context context,
//                                          String packageName,
//                                          String activityName) {
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        ComponentName cn = new ComponentName(packageName, activityName);
//        intent.setComponent(cn);
//        try {
//            context.startActivity(intent);
//            return true;
//        }catch (ActivityNotFoundException e){
//            return false;
//        }
//    }
//
//    public static boolean isWifiOpen() {
//        ConnectivityManager cm = (ConnectivityManager) BaseApplication
//                .context().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo info = cm.getActiveNetworkInfo();
//        if (info == null) return false;
//        if (!info.isAvailable() || !info.isConnected()) return false;
//        if (info.getType() != ConnectivityManager.TYPE_WIFI) return false;
//        return true;
//    }
//
//    @SuppressWarnings("deprecation")
//    public static void copyTextToBoard(String string) {
//        if (TextUtils.isEmpty(string)) return;
//        ClipboardManager clip = (ClipboardManager) BaseApplication.context()
//                .getSystemService(Context.CLIPBOARD_SERVICE);
//        clip.setText(string);
//        AppContext.showToast(R.string.copy_success);
//    }
//
//    /**
//     * 调用系统安装了的应用分享
//     *
//     * @param context
//     * @param title
//     * @param url
//     */
//    public static void showSystemShareOption(Activity context,
//                                             final String title, final String url) {
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setType("text/plain");
//        intent.putExtra(Intent.EXTRA_SUBJECT, "分享：" + title);
//        intent.putExtra(Intent.EXTRA_TEXT, title + " " + url);
//        context.startActivity(Intent.createChooser(intent, "选择分享"));
//    }
//
//}
