package com.wxxiaomi.ming.electricbicycle.support.easemob.provider;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.dao.db.UserService;
import com.wxxiaomi.ming.electricbicycle.common.GlobalManager;
import com.wxxiaomi.ming.electricbicycle.support.common.cache.LRUCache;
import com.wxxiaomi.ming.electricbicycle.support.common.myglide.ImgShower;
import com.wxxiaomi.ming.electricbicycle.support.img.ImageUtil;

import java.util.HashMap;
import java.util.List;

import rx.functions.Action1;

/**
 * Created by 12262 on 2016/11/16.
 */

public class MyUserProvider implements EaseUI.EaseUserProfileProvider {
    static LRUCache<EaseUser> userCache = new LRUCache<>(20);

    public void initCache(List<UserCommonInfo> infos) {
        for (UserCommonInfo info : infos) {
            EaseUser user = new EaseUser(info.emname);
            user.setNick(info.name);
            user.setAvatar(info.head);
            userCache.put(info.emname, user);
        }
        EaseUser user = new EaseUser(GlobalManager.getInstance().getUser().userCommonInfo.emname);
        user.setNick(GlobalManager.getInstance().getUser().userCommonInfo.name);
        user.setAvatar(GlobalManager.getInstance().getUser().userCommonInfo.head);
        userCache.put(user.getUsername(), user);
    }

    @Override
    synchronized public EaseUser getUser(String username) {
            return userCache.get(username);
    }

    @Override
    public void showImg(final Context ct, String username, final ImageView imageView) {

        ImgShower.showHead(ct,imageView,userCache.get(username).getAvatar());
    }

    @Override
    public void setUserNick(String username, final TextView textView) {
//        if("admin".equals(username)){
        EaseUser user = userCache.get(username);
        if(user==null){
            textView.setText("未知用户");
        }else {
            textView.setText(userCache.get(username).getNick());
        }
//        }else {
//            UserService.getInstance().getUserInfoByEname(username)
//                    .subscribe(new Action1<UserCommonInfo>() {
//                        @Override
//                        public void call(UserCommonInfo userCommonInfo) {
//                            textView.setText(userCommonInfo.name);
//                        }
//                    });
//        }
    }
}
