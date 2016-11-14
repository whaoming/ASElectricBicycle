package com.wxxiaomi.ming.electricbicycle.core.web.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wxxiaomi.ming.electricbicycle.api.WebMethods;
import com.wxxiaomi.ming.electricbicycle.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.core.ui.activity.UserInfoAct;
import com.wxxiaomi.ming.electricbicycle.support.GlobalManager;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Observer;

/**
 * Created by 12262 on 2016/11/8.
 * webview基类activity
 */

public abstract class BaseWebActivity extends AppCompatActivity {
    protected final int SHOW_LOADING_DIALOG = 1;
    protected final int CLOSE_LOADING_DIALOG = 2;
    protected final int SHOW_MSG_DIALOG = 3;
    protected final int CLOSE_MSG_DIALOG = 4;

    protected BridgeWebView mWebView;
    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_LOADING_DIALOG:
                    dialog.show();
                    ;
                    break;
                case CLOSE_LOADING_DIALOG:
                    dialog.dismiss();
                    break;
                case SHOW_MSG_DIALOG:
                    alertDialog.setMessage(message);
                    alertDialog.show();
                    break;
                case CLOSE_MSG_DIALOG:
                    break;
            }
        }
    };
    ProgressDialog dialog;
    AlertDialog alertDialog;
    private String message = "未知错误";

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
        alertDialog = new AlertDialog.Builder(this)
                .setPositiveButton("确定", null)
                .create();

        /**
         * 显示loading dialog
         */
        mWebView.registerHandler("showLoading", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                handler.sendEmptyMessage(SHOW_LOADING_DIALOG);
            }
        });

        /**
         * 关闭loading dialog
         */
        mWebView.registerHandler("closeLoading", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                handler.sendEmptyMessage(CLOSE_LOADING_DIALOG);
            }
        });


        /**
         * 关闭loading dialog
         */
        mWebView.registerHandler("finish", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                finish();
            }
        });

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
         * 点击头像事件
         */
        mWebView.registerHandler("onHeadClick", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //将data转化成UserInfo
                java.lang.reflect.Type type = new TypeToken<UserCommonInfo>() {
                }.getType();
                UserCommonInfo userCommonInfo = new Gson().fromJson(data, type);
                Bundle bundle = new Bundle();
                bundle.putSerializable("userInfo", userCommonInfo);
                Intent intent = new Intent(BaseWebActivity.this, UserInfoAct.class);
                intent.putExtra("value", bundle);
                startActivity(intent);
            }
        });

        /**
         * 获取用户id
         */
        mWebView.registerHandler("getUserId", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //将data转化成UserInfo
                int id = GlobalManager.getInstance().getUser().userCommonInfo.id;
                function.onCallBack(String.valueOf(id));
            }
        });

        /**
         * 显示js中的log
         */
        mWebView.registerHandler("showLog", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //将data转化成UserInfo
                Log.i("wang", "js->log:" + data);
            }
        });

        initData();
        String initValue = getIntent().getStringExtra("initValue");
        /**
         * 初始化js
         */
        mWebView.callHandler("bridgeInit", initValue, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
            }
        });
        mWebView.send("hello");
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

    /**
     * 自定义的WebViewClient
     */
    protected class MyWebViewClient extends BridgeWebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            super.onPageFinished(view, url);
        }


        public MyWebViewClient(BridgeWebView webView) {

            super(webView);
        }
    }

    protected abstract int initViewAndReutrnWebViewId(Bundle savedInstanceState);

    protected abstract void initData();

    /**
     * 对js传过来的String类型的参数进行解析成Map
     *
     * @param data
     * @return
     */
    protected Map<String, String> parseData(String data) {
        Map<String, String> datas = new HashMap<>();
        String[] split = data.split("&");
        for (String item : split) {
            datas.put(item.substring(0, item.indexOf("=")), item.substring(item.indexOf("=") + 1, item.length()));
        }
        return datas;
    }

    protected void showLoadingDialog() {
        handler.sendEmptyMessage(SHOW_LOADING_DIALOG);
    }

    protected void closeLoadingDialog() {
        handler.sendEmptyMessage(CLOSE_LOADING_DIALOG);
    }


    protected void showErrorDialog(String content) {
        message = content;
        handler.sendEmptyMessage(SHOW_MSG_DIALOG);
    }

}
