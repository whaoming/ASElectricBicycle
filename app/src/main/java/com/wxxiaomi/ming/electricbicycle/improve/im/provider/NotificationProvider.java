package com.wxxiaomi.ming.electricbicycle.improve.im.provider;

import android.content.Context;
import android.content.Intent;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.wxxiaomi.ming.electricbicycle.improve.im.ImHelper1;
import com.wxxiaomi.ming.electricbicycle.ui.activity.ContactActivity;

/**
 * Created by Administrator on 2017/1/9.
 * 通知栏内容提供者
 */
public class NotificationProvider implements EaseNotifier.EaseNotificationInfoProvider {
    private Context context;
    public NotificationProvider(Context context){
        this.context = context;
    }
    @Override
    public String getDisplayedText(EMMessage message) {
        String ticker = EaseCommonUtils.getMessageDigest(message, context);
        if(message.getType() == EMMessage.Type.TXT){
            ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
        }
        EaseUser user = ImHelper1.getInstance().getEmUserProvider().getUser(message.getFrom());
        if(user != null){
            return user.getNick() + ": " + ticker;
        }else{
            return message.getFrom() + ": " + ticker;
        }
    }

    @Override
    public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
        return null;
    }

    @Override
    public String getTitle(EMMessage message) {
        return null;
    }

    @Override
    public int getSmallIcon(EMMessage message) {
        return 0;
    }

    @Override
    public Intent getLaunchIntent(EMMessage message) {
        Intent intent = new Intent(context, ContactActivity.class);
        return intent;
    }
}
