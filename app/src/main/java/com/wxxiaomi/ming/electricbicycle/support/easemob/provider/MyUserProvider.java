package com.wxxiaomi.ming.electricbicycle.support.easemob.provider;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo2;
import com.wxxiaomi.ming.electricbicycle.common.GlobalManager;
import com.wxxiaomi.ming.electricbicycle.support.common.cache.UserCacheProvider;
import com.wxxiaomi.ming.electricbicycle.support.common.cache.base.LRUCache;
import com.wxxiaomi.ming.electricbicycle.support.common.myglide.ImgShower;

import java.util.List;

/**
 * Created by 12262 on 2016/11/16.
 */

public class MyUserProvider implements EaseUI.EaseUserProfileProvider {

    @Override
    synchronized public EaseUser getUser(String username) {
            return UserCacheProvider.getInstance().get(username);
    }

    @Override
    public void showImg(final Context ct, String username, final ImageView imageView) {
        ImgShower.showHead(ct,imageView,UserCacheProvider.getInstance().get(username).getAvatar());
    }

    @Override
    public void setUserNick(String username, final TextView textView) {
        EaseUser user = UserCacheProvider.getInstance().get(username);
        if(user==null){
            textView.setText("未知用户");
        }else {
            textView.setText(UserCacheProvider.getInstance().get(username).getNick());
        }
    }
}
