package com.wxxiaomi.ming.electricbicycle.bridge.easemob.provider;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.wxxiaomi.ming.electricbicycle.service.GlobalManager;
import com.wxxiaomi.ming.electricbicycle.service.ShowerProvider;

/**
 * Created by 12262 on 2016/11/16.
 */
public class MyUserProvider implements EaseUI.EaseUserProfileProvider {

    @Override
    synchronized public EaseUser getUser(String username) {
            return GlobalManager.getInstance().getEasyUser(username);
    }

    @Override
    public void showImg(final Context ct, String username, final ImageView imageView) {
        if(GlobalManager.getInstance().getEasyUser(username)!=null){
            ShowerProvider.showHead(ct,imageView,GlobalManager.getInstance().getEasyUser(username).getAvatar());
        }

    }

    @Override
    public void setUserNick(String username, final TextView textView) {
        EaseUser user = GlobalManager.getInstance().getEasyUser(username);
        if(user==null){
            textView.setText("未知用户");
        }else {
            textView.setText(user.getNick());
        }
    }
}
