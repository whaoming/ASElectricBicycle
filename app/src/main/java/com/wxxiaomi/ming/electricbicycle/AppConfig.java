package com.wxxiaomi.ming.electricbicycle;

import android.content.Context;

import com.wxxiaomi.ming.common.base.AppContext;

/**
 * 应用程序配置类
 * 用于保存用户相关信息及设置
 * config
 */
public class AppConfig {
    public static final String SETTING_OPEN_USER_NEAR = AppContext.context().getString(R.string.setting_open_near);
    public static final  String SETTING_NOTIFY_SOUND = AppContext.context().getString(R.string.setting_notify_sound);;
    public static final  String SETTING_NOTIFY_VIBRATE = AppContext.context().getString(R.string.setting_notify_vibrate);;

    public static final String SPLASH_IMG_URL = "splash_img_url";
    public static final String SPLASH_IMG_ACTION = "splash_img_action";
    private Context mContext;
    private static AppConfig appConfig;

    public static AppConfig getAppConfig(Context context) {
        if (appConfig == null) {
            appConfig = new AppConfig();
            appConfig.mContext = context;
        }
        return appConfig;
    }
}
