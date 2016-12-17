package com.wxxiaomi.ming.electricbicycle.support.easemob.provider;

import android.util.Log;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.controller.EaseUI;
import com.wxxiaomi.ming.electricbicycle.common.PreferenceManager;

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
        boolean flag = PreferenceManager.getInstance().getSoundOpen();

        return flag;
    }

    @Override
    public boolean isMsgVibrateAllowed(EMMessage message) {
        return PreferenceManager.getInstance().getVibrate();
    }

    @Override
    public boolean isSpeakerOpened() {
        return true;
    }
}
