package com.wxxiaomi.ming.electricbicycle.improve.im.provider;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.wxxiaomi.ming.common.cache.LRUCache;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.manager.AccountHelper;
import com.wxxiaomi.ming.electricbicycle.manager.ShowerProvider;

import java.util.List;

/**
 * Created by 12262 on 2016/11/16.
 */
public class MyUserProvider implements EaseUI.EaseUserProfileProvider {

    private LRUCache<EaseUser> userCache = new LRUCache<>(20);

//    public MyUserProvider(){
//        putUsers(UserFunctionProvider.getInstance().getEFriends());
//    }



    public void putUsers(List<UserCommonInfo> friendList){
        for (UserCommonInfo info : friendList) {
            EaseUser user = new EaseUser(info.emname);
            user.setNick(info.nickname);
            user.setAvatar(info.avatar);

            userCache.put(info.emname, user);
        }
        UserCommonInfo info = AccountHelper.getAccountInfo();
        putUser(info);
    }

    public void putUser(UserCommonInfo usr){
            EaseUser user = new EaseUser(usr.emname);
            user.setNick(usr.nickname);
            user.setAvatar(usr.avatar);
            userCache.put(usr.emname, user);
    }


    @Override
    synchronized public EaseUser getUser(String username) {
            return userCache.get(username);
    }

    @Override
    public void showImg(final Context ct, String username, final ImageView imageView) {
        if(userCache.get(username)!=null){
            String avatar = userCache.get(username).getAvatar();
            ShowerProvider.showHead(ct,imageView,avatar);
        }
    }

    @Override
    public void setUserNick(String username, final TextView textView) {
        EaseUser user = userCache.get(username);
        if(user==null){
            textView.setText("未知用户");
        }else {
            textView.setText(user.getNick());
        }
    }
}
