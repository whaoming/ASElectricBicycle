package com.wxxiaomi.ming.electricbicycle.core.web;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wxxiaomi.ming.electricbicycle.api.WebMethods;
import com.wxxiaomi.ming.electricbicycle.service.cache.CacheEngine;
import com.wxxiaomi.ming.electricbicycle.support.util.ParsMakeUtil;
import com.wxxiaomi.ming.webmodule.action.dialog.AlertAction;
import com.wxxiaomi.ming.webmodule.action.dialog.DialogACtion;
import com.wxxiaomi.ming.webmodule.action.dialog.DialogTypeAdapter;
import com.wxxiaomi.ming.webmodule.action.dialog.LoadingAction;
import com.wxxiaomi.ming.webmodule.action.forward.ForwardAction;
import com.wxxiaomi.ming.webmodule.action.forward.ForwardTypeAdapter;
import com.wxxiaomi.ming.webmodule.action.forward.H5ACtion;
import com.wxxiaomi.ming.webmodule.action.forward.ManyH5Action;
import com.wxxiaomi.ming.webmodule.action.forward.NativeAction;
import com.wxxiaomi.ming.webmodule.builder.BuilderDetail;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/12/1.
 */

public abstract class ComBuildImpl extends BuilderDetail {

    protected ProgressDialog dialog;
    protected AlertDialog alertDialog;

    Map<Integer,String> list = new HashMap<>();
    protected ComBuildImpl(Context context) {
        super(context);
        dialog = new ProgressDialog(context);
        dialog.setTitle("请等待");//设置标题
        dialog.setMessage("正在加载");
    }

    @Override
    public void doPost(String data, final CallBackFunction function) {
        doPost(data)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        function.onCallBack(s);
                    }
                });
    }

    @Override
    public String getTargetUrl() {
        return ((AppCompatActivity)context).getIntent().getStringExtra("url");
    }

    @Override
    public void doFinishEvent(String data) {
        Map<String, String> map = ParsMakeUtil.string2Map(data);
        if(map.get("isReturn")!=null&&map.get("isReturn").equals("true")){
            String pars = map.get("data");
            Intent intent = new Intent();
            intent.putExtra("value",pars);
            ((Activity)context).setResult(1,intent);
            ((Activity)context).finish();
        }else{
            ((Activity)context).finish();
        }
    }

    @Override
    public WebResourceResponse doInterceptRequest(WebView view, String url) {
        String path = "";
        final WebResourceResponse[] response = {null};
        path = url.replace("http://localhost","");
        Log.i("wang","path:"+path);
        CacheEngine.getInstance().getImage(path)
                .subscribe(new Action1<byte[]>() {
                    @Override
                    public void call(byte[] bytes) {
                        response[0] = new WebResourceResponse("image/png", "UTF-8", new ByteArrayInputStream(bytes));
                    }
                });
        return  response[0];
    }

    @Override
    public String getComeData() {
        return ((Activity)context).getIntent().getStringExtra("data");
    }

    @Override
    public void showLog(String data) {
        Log.i("wang","(js)"+data);
    }

    @Override
    public void doForwardEvent(String data, CallBackFunction function) {
        Gson gson = new GsonBuilder().registerTypeAdapter(ForwardAction.class, new ForwardTypeAdapter()).create();
        ForwardAction forwardAction = gson.fromJson(data, ForwardAction.class);
        if(forwardAction instanceof H5ACtion){
            H5ACtion action = (H5ACtion) forwardAction;
            Log.i("wang","action:"+action);
            Intent intent = new Intent(context,context.getClass());
            intent.putExtra("many",false);
            if(action.data!=""){
                intent.putExtra("data",action.data);
            }
            if(action.page!=null){
                intent.putExtra("url",action.page);
            }
            if(action.isReturn) {
                String callBack = action.callBack;
                list.put(1,callBack);
                ((Activity)context).startActivityForResult(intent, 1);
            }else{
                context.startActivity(intent);
            }
        }else if(forwardAction instanceof NativeAction){
            NativeAction action = (NativeAction) forwardAction;
            if(action.page.equals("UserInfoAct")){
//                Intent intent = new Intent(this, UserInfoAct.class);
//                intent.putExtra("value",action.data);
//                startActivity(intent);
            }
        }else if(forwardAction instanceof ManyH5Action){
            ManyH5Action action = (ManyH5Action) forwardAction;
            Log.i("wang","action:"+action.toString());
            Intent intent = new Intent(context, TestWebActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("action",action);
            intent.putExtra("value",bundle);
            intent.putExtra("many",true);
            context.startActivity(intent);
        }
    }

    @Override
    public void doGet(String data, final CallBackFunction function) {
        doGet(data)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        function.onCallBack(s);
                    }
                });
    }


    /**
     * 解析url
     *
     * @param data js
     * @return s
     */
    private Observable<String> doGet(String data) {
        Map<String, String> pars = ParsMakeUtil.string2Map(data);
        String url = pars.get("url");
        pars.remove("url");
        return WebMethods.getInstance().sendget(url, pars);
    }

    /**
     * 向服务器post一段json数据
     */
    private Observable<String> doPost(String data) {
        Map<String, String> pars = ParsMakeUtil.string2Map(data);
        String url = pars.get("url");
        pars.remove("url");
        return WebMethods.getInstance().sendPost(url, pars);
    }

    @Override
    protected void doPictureTakeEvent(String data, CallBackFunction function) {

    }

    @Override
    protected void registerOtherMethod() {

    }

    @Override
    protected void doJsInitData(String data) {

    }

    @Override
    protected void doDialogEvent(String data) {
        Gson gson = new GsonBuilder().registerTypeAdapter(DialogACtion.class, new DialogTypeAdapter()).create();
        DialogACtion dialogAction = gson.fromJson(data, DialogACtion.class);
        if(dialogAction instanceof LoadingAction){
            LoadingAction action = (LoadingAction)dialogAction;
            String title = action.title;
            String content = action.content;
            dialog.setTitle(title);
            dialog.setMessage(content);
            dialog.show();
        }else if(dialogAction instanceof AlertAction){
            final AlertAction action = (AlertAction)dialogAction;
            Log.i("wang","AlertAction.toString()="+action.toString());
            if(!action.okCallback.equals("")){
                alertDialog = new AlertDialog.Builder(context)
                        .setCancelable(false)
                        .setNegativeButton(action.cancelMsg, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton(action.okMsg, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mWebView.callHandler(action.okCallback,"",null);
                            }
                        }).create();
            }else{
                alertDialog = new AlertDialog.Builder(context)
                        .setPositiveButton(action.okMsg,null).create();

            }
            alertDialog.setTitle(action.title);
            alertDialog.setMessage(action.content);
            alertDialog.show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(data!=null) {
                String pars = data.getStringExtra("value");
                if (!pars.equals("")) {
                    String callback = list.get(1);
                    list.remove(String.valueOf(callback));
                    mWebView.callHandler(callback, pars, null);
                }
            }
        }
    }

    @Override
    protected void doSumbitEvent(String data, CallBackFunction function) {

    }
}
