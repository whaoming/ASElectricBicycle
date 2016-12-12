package com.wxxiaomi.ming.electricbicycle.ui.presenter;

import com.wxxiaomi.ming.electricbicycle.ui.presenter.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.ui.activity.base.BaseView;

/**
 * Created by 12262 on 2016/6/14.
 */
public interface ChatPresenter<V extends BaseView> extends BasePre<V> {
//    void initIntentData(Intent intent);
    void sendTextMessage(String content);
//    void initChatInputMenu(ChatInputMenu inputMenu);
}
