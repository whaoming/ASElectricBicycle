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

import com.wxxiaomi.ming.electricbicycle.common.util.AppManager;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.base.BasePre;

/**
 * Created by 12262 on 2016/10/18.
 * activity公共基类
 */
public abstract class BaseActivity<V,T extends BasePre> extends AppCompatActivity implements BaseView, View.OnClickListener{
    protected T presenter;
    protected AlertDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
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
        //DialogTheme
        if(cancelLis==null){
            dialog = new AlertDialog.Builder(this,android.R.style.Theme_Material_Light_Dialog_Alert)
                    .setCancelable(false)
                    .setPositiveButton(okk, okkLis)
                    .setTitle(title)
                    .setMessage(message)
                    .create();
        }else {
            dialog = new AlertDialog.Builder(this,android.R.style.Theme_Material_Light_Dialog_Alert)
                    .setCancelable(false)
                    .setNegativeButton(cancelMsg, cancelLis)
                    .setPositiveButton(okk, okkLis)
                    .setTitle(title)
                    .setMessage(message)
                    .create();
        }
    }
    @Override
    public void showDialog(){
        if(dialog!=null){
            dialog.show();
        }
    }
    @Override
    public void closeDialog(){
        if(dialog.isShowing()){
            dialog.dismiss();
        }
    }

    protected abstract void initView(Bundle savedInstanceState);
    public abstract T getPresenter();

    @Override
    public void runActivity(Class clazz, Bundle bundle, boolean isFinish) {
        Intent intent = new Intent(this,clazz);
        if(bundle!=null){
            intent.putExtra("value",bundle);
        }
        startActivity(intent);
        if(isFinish)
            finish();
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
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
