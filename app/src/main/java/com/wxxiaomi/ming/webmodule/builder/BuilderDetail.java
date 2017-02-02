package com.wxxiaomi.ming.webmodule.builder;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;

/**
 * Created by Administrator on 2016/12/1.
 */

public abstract class BuilderDetail implements Builder {
    protected Context context;
    protected BridgeWebView mWebView;

    protected BuilderDetail(Context context){
        this.context = context;
    }

    @Override
    public ViewGroup buildView() {
        mWebView = new BridgeWebView(context);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        mWebView.setLayoutParams(p);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.setWebViewClient(new MyWebViewClient(mWebView));
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        return mWebView;
    }

    @Override
    public void initPageData() {
        String url = getTargetUrl();
        mWebView.loadUrl(url);
        String data = getComeData();
        mWebView.send(data==null?"":data, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                doJsInitData(data);
            }
        });
    }

    @Override
    public void registerMethod() {
        /**
         * 发送一个get请求
         */
        mWebView.registerHandler("sendGet", new BridgeHandler() {
            @Override
            public void handler(String data, final CallBackFunction function) {

                doGet(data, function);
            }
        });

        /**
         * 发送一个get请求
         */
        mWebView.registerHandler("sendPost", new BridgeHandler() {
            @Override
            public void handler(String data, final CallBackFunction function) {
                doPost(data, function);
            }
        });


        //跳转
        mWebView.registerHandler("forward", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                
                doForwardEvent(data,function);
            }
        });

        mWebView.registerHandler("dialog", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                doDialogEvent(data);
            }
        });

        mWebView.registerHandler("finish", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                doFinishEvent(data);
            }
        });

        mWebView.registerHandler("loadComplete", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
            }
        });

        mWebView.registerHandler("showLog", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                showLog(data);
            }
        });

        mWebView.registerHandler("takePic", new BridgeHandler() {
            @Override
            public void handler(String data, final CallBackFunction function) {
                doPictureTakeEvent(data, function);
            }
        });

        mWebView.registerHandler("sendUP", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                doSumbitEvent(data, function);
            }
        });
        registerOtherMethod();
    }

    protected abstract void doSumbitEvent(String data, CallBackFunction function);

    protected abstract void doPictureTakeEvent(String data, CallBackFunction function);

    protected abstract void registerOtherMethod();

    /**
     * 自定义的WebViewClient
     */
    protected class MyWebViewClient extends BridgeWebViewClient {
        public MyWebViewClient(BridgeWebView webView) {
            super(webView);
        }
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
//            Log.i("wang","url:"+url);
            if(url.contains("http://localhost")){
//            if(url.endsWith("")){
                return  doInterceptRequest(view, url);
            }else {
                return super.shouldInterceptRequest(view, url);
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//            Log.i("wang","request:"+request.toString());
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
//            Log.i("wang","onLoadResource,url:"+url);
            super.onLoadResource(view, url);
        }
    }

    protected abstract void doJsInitData(String data);

    protected abstract String getComeData();

    protected abstract String getTargetUrl();

    protected abstract WebResourceResponse doInterceptRequest(WebView view, String url);

    protected abstract void doForwardEvent(String data, CallBackFunction function);

    protected abstract void doDialogEvent(String data);

    protected abstract void doFinishEvent(String data);

    protected abstract void showLog(String data);

    protected abstract void doPost(String data, CallBackFunction function);

    protected abstract void doGet(String data, CallBackFunction function);
}
