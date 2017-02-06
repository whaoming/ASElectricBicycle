//package com.wxxiaomi.ming.electricbicycle.improve.common;
//
//import android.annotation.SuppressLint;
//import android.app.Application;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;
//import android.support.v4.content.SharedPreferencesCompat;
//import android.view.Gravity;
//import android.widget.Toast;
//
//import com.wxxiaomi.ming.electricbicycle.ui.weight.custom.SimplexToast;
//
//
//@SuppressLint("InflateParams")
//public class BaseApplication extends Application {
////    private static final String PREF_NAME = "creativelocker.pref";
//    static Context _context;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        _context = getApplicationContext();
//        //LeakCanary.install(this);
//    }
//
//    public static synchronized BaseApplication context() {
//        return (BaseApplication) _context;
//    }
//
//
//    public static void set(String key, int value) {
//        Editor editor = getPreferences().edit();
//        editor.putInt(key, value);
//        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
//    }
//
//    public static void set(String key, boolean value) {
//        Editor editor = getPreferences().edit();
//        editor.putBoolean(key, value);
//        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
//    }
//
//    public static void set(String key, String value) {
//        Editor editor = getPreferences().edit();
//        editor.putString(key, value);
//        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
//    }
//
//    public static boolean get(String key, boolean defValue) {
//        return getPreferences().getBoolean(key, defValue);
//    }
//
//    public static String get(String key, String defValue) {
//        return getPreferences().getString(key, defValue);
//    }
//
//    public static int get(String key, int defValue) {
//        return getPreferences().getInt(key, defValue);
//    }
//
//    public static long get(String key, long defValue) {
//        return getPreferences().getLong(key, defValue);
//    }
//
//    public static float get(String key, float defValue) {
//        return getPreferences().getFloat(key, defValue);
//    }
//
//    public static SharedPreferences getPreferences() {
//        return android.preference.PreferenceManager.getDefaultSharedPreferences(context());
//    }
//
//    public static void showToast(int message) {
//        showToast(message, Toast.LENGTH_LONG, 0);
//    }
//
//    public static void showToast(String message) {
//        showToast(message, Toast.LENGTH_LONG, 0, Gravity.BOTTOM);
//    }
//
//    public static void showToast(int message, int icon) {
//        showToast(message, Toast.LENGTH_LONG, icon);
//    }
//
//    public static void showToast(String message, int icon) {
//        showToast(message, Toast.LENGTH_LONG, icon, Gravity.BOTTOM);
//    }
//
//    public static void showToastShort(int message) {
//        showToast(message, Toast.LENGTH_SHORT, 0);
//    }
//
//    public static void showToastShort(String message) {
//        showToast(message, Toast.LENGTH_SHORT, 0, Gravity.BOTTOM);
//    }
//
//    public static void showToastShort(int message, Object... args) {
//        showToast(message, Toast.LENGTH_SHORT, 0, Gravity.BOTTOM, args);
//    }
//
//    public static void showToast(int message, int duration, int icon) {
//        showToast(message, duration, icon, Gravity.BOTTOM);
//    }
//
//    public static void showToast(int message, int duration, int icon,
//                                 int gravity) {
//        showToast(context().getString(message), duration, icon, gravity);
//    }
//
//    public static void showToast(int message, int duration, int icon,
//                                 int gravity, Object... args) {
//        showToast(context().getString(message, args), duration, icon, gravity);
//    }
//
//    public static void showToast(String message, int duration, int icon, int gravity) {
//        SimplexToast.show(_context, message, gravity, duration);
//    }
//}
