package com.wxxiaomi.ming.electricbicycle.ui.activity.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.wxxiaomi.ming.common.util.AppManager;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.base.BasePre;

/**
* @author whaoming
* github：https://github.com/whaoming
* created at 2017/2/23 22:44
* TODO: 作为所有mvpactivity的基类
*/
public abstract class MvpActivity<V,T extends BasePre> extends BaseActivity implements BaseView, View.OnClickListener{
    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = getPresenter();
        initView(savedInstanceState);
        if(presenter!=null) {
            presenter.attach((V) this);
            presenter.init();
        }

    }

    @Override
    public void buildAlertDialog(String okk,DialogInterface.OnClickListener okkLis
            ,String cancelMsg, DialogInterface.OnClickListener cancelLis
            ,String title,String message){
        super.buildAlertDialog(okk, okkLis, cancelMsg, cancelLis, title, message);
    }
    @Override
    public void showDialog(){
       super.showDialog();
    }
    @Override
    public void closeDialog(){
        super.closeDialog();
    }


    public abstract T getPresenter();

    @Override
    public void runActivity(Class clazz, Bundle bundle, boolean isFinish) {
       super.runActivity(clazz, bundle, isFinish);
    }

    @Override
    protected void onResume() {
        if(presenter!=null) {
            presenter.onViewResume();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if(presenter!=null) {
            presenter.onViewDestory();
            presenter = null;
        }
        super.onDestroy();

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {

    }

}
