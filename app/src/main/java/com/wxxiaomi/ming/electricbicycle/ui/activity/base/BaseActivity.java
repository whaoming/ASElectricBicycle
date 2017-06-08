package com.wxxiaomi.ming.electricbicycle.ui.activity.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.wxxiaomi.ming.common.util.AppManager;

/**
 * Created by Mr.W on 2017/2/23.
 * E-mail：122627018@qq.com
 * Github：https://github.com/whaoming
 * TODO:作为所有activity的基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected AlertDialog dialog;
    protected abstract void initView(Bundle savedInstanceState);

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        AppManager.getAppManager().addActivity(this);
    }

    public void buildAlertDialog(String okk, DialogInterface.OnClickListener okkLis
            , String cancelMsg, DialogInterface.OnClickListener cancelLis
            , String title, String message){
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

    public void showDialog(){
        if(dialog!=null){
            dialog.show();
        }
    }
    public void closeDialog(){
        if(dialog.isShowing()){
            dialog.dismiss();
        }
    }

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
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
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

    /**
    * @author whaoming
    * github：https://github.com/whaoming
    * created at 2017/5/8 22:31
    * Description: 基类activity的接口
    */
    public interface BaseActivityInterface{
        /**
         * activity入口函数，负责做一些初始化操作
         * @param savedInstanceState
         * @param persistentState
         */
        void onCreate(Bundle savedInstanceState, PersistableBundle persistentState);

        /**
         * 负责ui的初始化操作
         * @param savedInstanceState
         */
        void initView(Bundle savedInstanceState);

        /**
         * 生命周期的结束函数，负责做一些资源的释放
         */
        void onDestroy();

        /**
         * 跳转函数
         * @param clazz
         * @param bundle
         * @param isFinish
         */
        void runActivity(Class clazz, Bundle bundle, boolean isFinish);
    }

}
