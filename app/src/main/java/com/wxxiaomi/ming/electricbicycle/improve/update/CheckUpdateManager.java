package com.wxxiaomi.ming.electricbicycle.improve.update;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.wxxiaomi.ming.common.base.AppContext;
import com.wxxiaomi.ming.electricbicycle.net.HttpMethods;
import com.wxxiaomi.ming.electricbicycle.AppConfig;
import com.wxxiaomi.ming.common.weight.DialogHelper;
import com.wxxiaomi.ming.electricbicycle.ui.weight.custom.SimplexToast;


import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by haibin
 * on 2016/10/19.
 */

public class CheckUpdateManager {


    private ProgressDialog mWaitDialog;
    private Context mContext;
    private boolean mIsShowDialog;
    private RequestPermissions mCaller;

    public CheckUpdateManager(Context context, boolean showWaitingDialog) {
        this.mContext = context;
        mIsShowDialog = showWaitingDialog;
        if (mIsShowDialog) {
            mWaitDialog = DialogHelper.getProgressDialog(mContext);
            mWaitDialog.setMessage("正在检查中...");
            mWaitDialog.setCancelable(false);
            mWaitDialog.setCanceledOnTouchOutside(false);
        }
    }


    public void checkUpdate() {
        if (mIsShowDialog) {
            mWaitDialog.show();
        }
        HttpMethods.getInstance().checkUpdate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Version>() {
                    @Override
                    public void onCompleted() {
                        if (mIsShowDialog) {
                            mWaitDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (mIsShowDialog) {
                            mWaitDialog.dismiss();
                        }
                        SimplexToast.show(mContext,"网络异常，无法获取新版本信息");
                        mCaller.call(false,null);
                    }

                    @Override
                    public void onNext(final Version version) {
                        AppContext.set(AppConfig.SPLASH_IMG_URL,version.getSplash_img_url());
                        int curVersionCode = 0;
                        if (curVersionCode < Integer.parseInt(version.getCode())) {
                            AlertDialog.Builder dialog = DialogHelper.getConfirmDialog(mContext, version.getMessage(), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mCaller.call(true,version);
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mCaller.call(false,version);
                                }
                            });
                            dialog.setTitle("发现新版本");
                            dialog.show();
                        }else{
                            if (mIsShowDialog) {
                                SimplexToast.show(mContext,"已经是最新版本了");
                            }
                            mCaller.call(false,version);
                        }
                    }
                });
//
    }

    public void setCaller(RequestPermissions caller) {
        this.mCaller = caller;
    }

    public interface RequestPermissions {
        void call(boolean isOk,Version version);
    }
}
