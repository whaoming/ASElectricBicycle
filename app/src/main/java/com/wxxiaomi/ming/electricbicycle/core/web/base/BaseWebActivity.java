package com.wxxiaomi.ming.electricbicycle.core.web.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wxxiaomi.ming.electricbicycle.api.WebMethods;
import com.wxxiaomi.ming.electricbicycle.core.ui.activity.UserInfoAct;
import com.wxxiaomi.ming.electricbicycle.core.web.SimpleWebActivity;
import com.wxxiaomi.ming.electricbicycle.core.web.WebTabsActivity;
import com.wxxiaomi.ming.electricbicycle.core.web.action.dialog.AlertAction;
import com.wxxiaomi.ming.electricbicycle.core.web.action.dialog.DialogACtion;
import com.wxxiaomi.ming.electricbicycle.core.web.action.dialog.DialogTypeAdapter;
import com.wxxiaomi.ming.electricbicycle.core.web.action.dialog.LoadingAction;
import com.wxxiaomi.ming.electricbicycle.core.web.action.forward.ForwardAction;
import com.wxxiaomi.ming.electricbicycle.core.web.action.forward.ForwardTypeAdapter;
import com.wxxiaomi.ming.electricbicycle.core.web.action.forward.H5ACtion;
import com.wxxiaomi.ming.electricbicycle.core.web.action.forward.ManyH5Action;
import com.wxxiaomi.ming.electricbicycle.core.web.action.forward.NativeAction;
import com.wxxiaomi.ming.electricbicycle.support.GlobalManager;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Observer;

/**
 * Created by 12262 on 2016/11/12.
 * webview的基础activity，用于webview的初始化
 * 不能含有toolbar，toolbar的初始化应该是在simpleActivity中
 */

public abstract class BaseWebActivity extends AppCompatActivity {
    protected BridgeWebView mWebView;

    protected final int SHOW_LOADING_DIALOG = 1;
    protected final int CLOSE_LOADING_DIALOG = 2;
    protected final int SHOW_MSG_DIALOG = 3;
    protected final int CLOSE_MSG_DIALOG = 4;

    ProgressDialog dialog;
    AlertDialog alertDialog;
    Map<Integer,String> list = new HashMap<Integer,String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        int webViewId = initViewAndReutrnWebViewId(savedInstanceState);
        mWebView = (BridgeWebView) findViewById(webViewId);
        mWebView.setWebViewClient(new MyWebViewClient(mWebView));
        dialog = new ProgressDialog(BaseWebActivity.this);
        dialog.setTitle("请等待");//设置标题
        dialog.setMessage("正在加载");
        dialog.show();
        initWebView();
        //这里send的应该别的activity带过来的数据
        String data = getIntent().getStringExtra("data");
        Log.i("wang","别的activity带过来的data="+data);
        mWebView.send(data==null?"":data, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.i("wang","从js返回回来的初始化toolbar的data="+data);
               initToolBar(data);
            }
        });
        initCommonMethod();

    }

    private void initCommonMethod() {
        /**
         * 发送一个get请求
         */
        mWebView.registerHandler("sendGet", new BridgeHandler() {
            @Override
            public void handler(String data, final CallBackFunction function) {
                parseGetRequest(data)
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
        });

        /**
         * 发送一个get请求
         */
        mWebView.registerHandler("sendPost", new BridgeHandler() {
            @Override
            public void handler(String data, final CallBackFunction function) {
                parsePostRequest(data)
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
        });

        //跳转
        mWebView.registerHandler("forward", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("wang","forward");
                Gson gson = new GsonBuilder().registerTypeAdapter(ForwardAction.class, new ForwardTypeAdapter()).create();
                ForwardAction forwardAction = gson.fromJson(data, ForwardAction.class);
                Log.i("wang","forwardAction="+forwardAction.toString());
                handleForward(forwardAction);
            }
        });

        mWebView.registerHandler("dialog", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("wang","dialog,data:"+data);
                Gson gson = new GsonBuilder().registerTypeAdapter(DialogACtion.class, new DialogTypeAdapter()).create();
                DialogACtion dialogAction = gson.fromJson(data, DialogACtion.class);
                Log.i("wang","dialogAction="+dialogAction.toString());
                handlerDialogAction(dialogAction);
            }
        });

        mWebView.registerHandler("getUser", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                String json = new Gson().toJson(GlobalManager.getInstance().getUser().userCommonInfo);
                function.onCallBack(json);
            }
        });

        mWebView.registerHandler("getUserId", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                int id = GlobalManager.getInstance().getUser().userCommonInfo.id;
                function.onCallBack(id+"");
            }
        });

        mWebView.registerHandler("finish", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Map<String, String> map = parseData(data);
                if(map.get("isReturn")!=null&&map.get("isReturn").equals("true")){
                    String pars = map.get("data");
                    Intent intent = new Intent();
                    intent.putExtra("value",pars);
                    setResult(1,intent);
                    finish();
                }else{
                    finish();
                }
            }
        });

        mWebView.registerHandler("loadComplete", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("wang","native->loadComplete");
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });

        mWebView.registerHandler("showLog", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("wang","js->showLog:"+data);

            }
        });

    }

    /**
     * 向服务器post一段json数据
     * @param data
     * @return
     */
    private Observable<String> parsePostRequest(String data) {
        Log.i("wang","parsePostRequest,data:"+data);
        Map<String, String> pars = parseData(data);
        String url = pars.get("url");
        pars.remove("url");
        return WebMethods.getInstance().sendPost(url, pars);
    }

    /**
     * 处理弹框事件
     * @param dialogAction
     */
    private void handlerDialogAction(DialogACtion dialogAction) {
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
            if(action.okCallback!=""){
                alertDialog = new AlertDialog.Builder(BaseWebActivity.this)
                        .setPositiveButton(action.okMsg, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mWebView.callHandler(action.okCallback,"",null);
                            }
                        }).create();
            }else{
                alertDialog = new AlertDialog.Builder(BaseWebActivity.this)
                        .setPositiveButton(action.okMsg,null).create();

            }
            alertDialog.setTitle(action.title);
            alertDialog.setMessage(action.content);
            alertDialog.show();
        }
    }

    /**
     * 解析url
     *
     * @param data
     * @return
     */
    private Observable<String> parseGetRequest(String data) {
        Map<String, String> pars = parseData(data);
        String url = pars.get("url");
        pars.remove("url");
        return WebMethods.getInstance().sendget(url, pars);
    }



    protected Map<String, String> parseData(String data) {
        Map<String, String> datas = new HashMap<>();
        String[] split = data.split("&");
        for (String item : split) {
            datas.put(item.substring(0, item.indexOf("=")), item.substring(item.indexOf("=") + 1, item.length()));
        }
        return datas;
    }

    /**
     * 自定义的WebViewClient
     */
    protected class MyWebViewClient extends BridgeWebViewClient {
        public MyWebViewClient(BridgeWebView webView) {
            super(webView);
        }
    }

    protected abstract int initViewAndReutrnWebViewId(Bundle savedInstanceState);
    protected abstract void initWebView();
    protected abstract void initToolBar(String data);

    /**
     * 处理跳转
     * @param forwardAction
     */
    private void handleForward(ForwardAction forwardAction) {
        //Log.i("wang","Thread.currentThread().getName()="+Thread.currentThread().getName());
        if(forwardAction instanceof H5ACtion){
            H5ACtion action = (H5ACtion) forwardAction;
            Intent intent = new Intent(BaseWebActivity.this,SimpleWebActivity.class);
            if(action.data!=""){
                intent.putExtra("data",action.data);
            }
            if(action.page!=null){
                intent.putExtra("url",action.page);
            }
            if(action.isReturn) {
                String callBack = action.callBack;
                list.put(1,callBack);
                startActivityForResult(intent, 1);
            }else{
                startActivity(intent);
            }
        }else if(forwardAction instanceof NativeAction){
            NativeAction action = (NativeAction) forwardAction;
            if(action.page.equals("UserInfoAct")){
                Intent intent = new Intent(this, UserInfoAct.class);
                intent.putExtra("value",action.data);
                startActivity(intent);
            }
        }else if(forwardAction instanceof ManyH5Action){
            ManyH5Action action = (ManyH5Action) forwardAction;
            Log.i("wang","action:"+action.toString());
            Intent intent = new Intent(BaseWebActivity.this, WebTabsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("action",action);
            intent.putExtra("value",bundle);
            startActivity(intent);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            Log.i("wang","startActivityForResult->requestCode="+requestCode);
            if(data!=null) {
                String pars = data.getStringExtra("value");
                Log.i("wang", "pars=" + pars);
                if (pars != "") {

                    String callback = list.get(1);
                    Log.i("wang", "callback=" + callback + "-value=" + pars);
                    list.remove(callback);
                    mWebView.callHandler(callback, pars, null);
                }
            }
        }
    }
}
