package com.wxxiaomi.ming.electricbicycle.improve.im.provider;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.controller.EaseUI;
import com.wxxiaomi.ming.electricbicycle.improve.common.AppConfig;
import com.wxxiaomi.ming.electricbicycle.improve.common.AppContext;

/**
 * Created by Mr.W on 2016/12/16.
 * E-maiil：122627018@qq.com
 * github：https://github.com/122627018
 */

public class MySettingProvider implements EaseUI.EaseSettingsProvider {
    @Override
    public boolean isMsgNotifyAllowed(EMMessage message) {
        return true;
    }

    @Override
    public boolean isMsgSoundAllowed(EMMessage message) {
        boolean flag = AppContext.get(AppConfig.SETTING_NOTIFY_SOUND,false);

        return flag;
    }

    @Override
    public boolean isMsgVibrateAllowed(EMMessage message) {
        return AppContext.get(AppConfig.SETTING_NOTIFY_VIBRATE,false);
    }

    @Override
    public boolean isSpeakerOpened() {
        return true;
    }
}
