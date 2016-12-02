package com.wxxiaomi.ming.electricbicycle.support.easemob.ui;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.dao.UserService;
import com.wxxiaomi.ming.electricbicycle.common.GlobalManager;

import rx.functions.Action1;

/**
 * Created by 12262 on 2016/11/16.
 */

public class MyUserProvider implements EaseUI.EaseUserProfileProvider {
//    static LRUCache<UserCommonInfo> userCache = new LRUCache<>(5);
//    static Map<String,EaseUser> userCache = new HashMap<>();

//    synchronized public static void  putUser(UserCommonInfo info){
//        Log.i("wang","缓存中现在存如emname:"+info.emname);
//        EaseUser user = new EaseUser(info.emname);
//        user.setNick(info.name);
//        user.setAvatar(info.head);
//        userCache.put(info.emname,user);
//    }

    @Override
    synchronized public EaseUser getUser(String username) {
        Log.i("wang","进入MyUserProvider->getUser,username:"+username);
        final EaseUser info = new EaseUser(username);
        if(!username.equals(GlobalManager.getInstance().getUser().userCommonInfo.emname)) {
            Log.i("wang","我在MyUserProvider的if里面");
//            if(userCache.containsKey(username)){
//                Log.i("wang","从缓存中取到emname:"+username);
//                return userCache.get(username);
//            }else {
                //这里不能是getFriend,而是应该从临时用户里面取，然后再又好友里面取

//                UserCommonInfo item = UserService.getInstance().getFriendLocal(username);
                info.setNick("asd");
                info.setAvatar("http://appdpwallpaper.u.qiniudn.com/wp-content/uploads/sites/48/2015/04/38.jpg");
//            }
        }else{
            info.setAvatar("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3972100125,3086204015&fm=116&gp=0.jpg");
            info.setNick(GlobalManager.getInstance().getUser().userCommonInfo.name);
        }

        Log.i("wang","MyUserProvider->getUser的结果info:"+info.toString());
        return info;
    }

    @Override
    public void showImg(final Context ct, String username, final ImageView imageView) {
            UserService.getInstance().getUserInfoByEname(username)
                    .subscribe(new Action1<UserCommonInfo>() {
                        @Override
                        public void call(UserCommonInfo userCommonInfo) {
                            Glide.with(ct).load(userCommonInfo.head).into(imageView);
                        }
                    });
        }

    @Override
    public void setUserNick(String username, final TextView textView) {
        UserService.getInstance().getUserInfoByEname(username)
                .subscribe(new Action1<UserCommonInfo>() {
                    @Override
                    public void call(UserCommonInfo userCommonInfo) {
                        textView.setText(userCommonInfo.name);
                    }
                });
    }
}
