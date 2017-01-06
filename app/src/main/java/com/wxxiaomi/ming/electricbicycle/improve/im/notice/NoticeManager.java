package com.wxxiaomi.ming.electricbicycle.improve.im.notice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;


import com.wxxiaomi.ming.electricbicycle.improve.im.ImHelper1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/31.
 */

public final class NoticeManager {
    public static final int FLAG_CLEAR_INVITE = 0x1;
    public static final int FLAG_CLEAR_MESSAGE = 0x2;
    public static final int FLAG_CLEAR_REVIEW = 0x3;
    public static final int FLAG_CLEAR_LIKE = 0x5;
    private static NoticeManager INSTANCE;
    static {
        INSTANCE = new NoticeManager();
    }
    private NoticeManager() {}
    private final List<NoticeNotify> mNotifies = new ArrayList<>();
    private NoticeBean mNotice;
    public interface NoticeNotify {
        void onNoticeArrived(NoticeBean bean);
    }
    public static void init(Context context) {
//        // 未登陆时不启动服务
//        if (!AccountHelper.isLogin()) {
//            return;
//        }
//        // 启动服务
//        NoticeServer.startAction(context);
//        // 注册广播
        Log.i("wang","NoticeManager初始化了");
        NoticeBean nb = NoticeBean.getInstance(context);
        INSTANCE.onNoticeChanged(nb);

        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(context);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ImHelper1.NOTICE_MESSAGE_RECEIVE);
        broadcastManager.registerReceiver(INSTANCE.mReceiver, filter);
    }

    public static void  clearNotice(Context context,int type) {
        NoticeBean notice = NoticeBean.getInstance(context);
        switch (type) {
            case NoticeManager.FLAG_CLEAR_INVITE:
                notice.setInvite(0);
                break;
            case NoticeManager.FLAG_CLEAR_MESSAGE:
                notice.setMessage(0);
                break;
            case NoticeManager.FLAG_CLEAR_REVIEW:
                notice.setReview(0);
                break;
            case NoticeManager.FLAG_CLEAR_LIKE:
                notice.setLike(0);
                break;

        }
        INSTANCE.mNotice = notice;
        notice.save(context);
    }

    private void onNoticeChanged(NoticeBean bean) {
        mNotice = bean;
        //  Notify all
        for (NoticeNotify notify : mNotifies) {
            notify.onNoticeArrived(mNotice);
        }
    }
    private void check(NoticeNotify noticeNotify) {
        if (mNotice != null)
            noticeNotify.onNoticeArrived(mNotice);
    }
    public static void bindNotify(NoticeNotify noticeNotify) {
        INSTANCE.mNotifies.add(noticeNotify);
//        INSTANCE.check(noticeNotify);
    }

    public static void unBindNotify(NoticeNotify noticeNotify) {
        INSTANCE.mNotifies.remove(noticeNotify);
    }
    public static NoticeBean getNotice() {
        final NoticeBean bean = INSTANCE.mNotice;
        if (bean == null) {
            return new NoticeBean();
        } else {
            return bean;
        }
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null &&
                    ImHelper1.NOTICE_MESSAGE_RECEIVE.equals(intent.getAction())) {
                Serializable serializable = intent.getSerializableExtra(ImHelper1.EXTRA_BEAN);
                if (serializable != null) {
                    try {
                        onNoticeChanged((NoticeBean) serializable);
                    } catch (Exception e) {
                        e.fillInStackTrace();
                    }
                }
            }
        }
    };
}
