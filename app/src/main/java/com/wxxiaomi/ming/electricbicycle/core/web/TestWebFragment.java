package com.wxxiaomi.ming.electricbicycle.core.web;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.support.GlobalManager;


/**
 * Created by 12262 on 2016/11/10.
 */

public class TestWebFragment extends Fragment {

    protected BridgeWebView mWebView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_simple_webview,container,false);
        mWebView = (BridgeWebView) view.findViewById(R.id.web_view);
        mWebView.setWebViewClient(new MyWebViewClient(mWebView));
        mWebView.loadUrl("file:///android_asset/myitem.html");
        /**
         * 显示loading dialog
         */
        mWebView.registerHandler("showLoading", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("wang","showLoading");
            }
        });

        /**
         * 关闭loading dialog
         */
        mWebView.registerHandler("closeLoading", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("wang","closeLoading");
            }
        });

        /**
         * 发送一个get请求
         */
        mWebView.registerHandler("sendGet", new BridgeHandler() {
            @Override
            public void handler(String data, final CallBackFunction function) {
                Log.i("wang","sendget");
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
                Log.i("wang","js->log:"+data);
            }
        });

        /**
         * 初始化js
         */
        mWebView.callHandler("bridgeInit", "", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {}
        });
        mWebView.send("hello");
        return view;
    }

    protected class MyWebViewClient extends BridgeWebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
        public MyWebViewClient(BridgeWebView webView) {
            super(webView);
        }
    }
}
