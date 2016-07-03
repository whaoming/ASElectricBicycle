package com.wxxiaomi.ming.electricbicycle.presenter.callback;

import android.content.Intent;

import com.wxxiaomi.ming.chatwidget.weidgt.ChatInputMenu;
import com.wxxiaomi.ming.electricbicycle.presenter.base.BasePresenter;

/**
 * Created by 12262 on 2016/6/14.
 */
public interface ChatPresenter<T> extends BasePresenter<T> {
//    void initIntentData(Intent intent);
    void sendTextMessage(String content);
//    void initChatInputMenu(ChatInputMenu inputMenu);
}
