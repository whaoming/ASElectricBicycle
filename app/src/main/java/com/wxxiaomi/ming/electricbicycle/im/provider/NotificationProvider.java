package com.wxxiaomi.ming.electricbicycle.im.provider;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.wxxiaomi.ming.electricbicycle.web.TestWebActivity;
import com.wxxiaomi.ming.electricbicycle.im.ImHelper1;
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
        if(message.getType() == EMMessage.Type.CMD){
            String title = message.getStringAttribute("title","");

            if(!"".equals(title)){
                Log.i("wang","title:"+title);
                return title;
            }
        }
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
//        EMMessageBody body = message.getBody();
//        body.
        if(message.getType() == EMMessage.Type.CMD){
            String title = message.getStringAttribute("title","");

            if(!"".equals(title)){
                Log.i("wang","title1:"+title);
                return title;
            }
        }
        return null;
    }

    @Override
    public String getTitle(EMMessage message) {
        return "我是尼玛的标题啊";
    }

    @Override
    public int getSmallIcon(EMMessage message) {
        return 0;
    }

    @Override
    public Intent getLaunchIntent(EMMessage message) {
        if(message.getType().equals(EMMessage.Type.CMD)){
            String action = message.getStringAttribute("action","");
            Intent intent = new Intent(context, TestWebActivity.class);
            intent.putExtra("url", action);
            return intent;
        }else {
            Intent intent = new Intent(context, ContactActivity.class);
            return intent;
        }
    }
}
