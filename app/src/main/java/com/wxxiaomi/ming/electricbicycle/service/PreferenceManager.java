package com.wxxiaomi.ming.electricbicycle.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.R;

/**
 * Created by Mr.W on 2016/12/16.
 * E-maiil：122627018@qq.com
 * github：https://github.com/122627018
 * 负责和sp打交道，并负责key的管理
 * 管理存在于sp中的东西
 */
public class PreferenceManager {
    /**
     * 获取的属性值：
     * 是否开启附近的人
     * 是否开启声音提醒
     * 是否开启震动提醒
     * 长token的保存
     * 短token的保存
     */
    private Context context;
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor editor;
    private static PreferenceManager mPreferencemManager;

    private String SETTING_OPEN_USER_NEAR = "";
    private String SETTING_NOTIFY_SOUND = "";
    private String SETTING_NOTIFY_VIBRATE = "";

    private PreferenceManager(Context context){
        mSharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        editor = mSharedPreferences.edit();
        SETTING_OPEN_USER_NEAR = context.getString(R.string.setting_open_near);
        SETTING_NOTIFY_SOUND = context.getString(R.string.setting_notify_sound);
        SETTING_NOTIFY_VIBRATE = context.getString(R.string.setting_notify_vibrate);
    }

    public static synchronized void init(Context cxt){
        if(mPreferencemManager == null){
            mPreferencemManager = new PreferenceManager(cxt);
        }
    }

    public synchronized static PreferenceManager getInstance() {
        if (mPreferencemManager == null) {
            throw new RuntimeException("please init first!");
        }
        return mPreferencemManager;
    }

    public boolean getNearOpen(){
        return mSharedPreferences.getBoolean(SETTING_OPEN_USER_NEAR,false);
    }

    public boolean getSoundOpen(){
        return mSharedPreferences.getBoolean(SETTING_NOTIFY_SOUND,false);

    }

    public boolean getVibrate(){
        return mSharedPreferences.getBoolean(SETTING_NOTIFY_VIBRATE,false);
    }

}
