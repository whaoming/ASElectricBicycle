package com.wxxiaomi.ming.electricbicycle.ui.activity.base;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by 12262 on 2016/10/18.
 *
 * 初级视图接口
 */
public interface BaseView {

    void runActivity(Class clazz, Bundle bundle, boolean isFinish);
//    T getPresenter();
    Context getContext();
    void buildAlertDialog(String okk,DialogInterface.OnClickListener okkLis
            ,String cancelMsg, DialogInterface.OnClickListener cancelLis
            ,String title,String message);
    void showDialog();
    void closeDialog();

}
