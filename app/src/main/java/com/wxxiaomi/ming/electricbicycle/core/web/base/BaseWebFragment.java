package com.wxxiaomi.ming.electricbicycle.core.web.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.wxxiaomi.ming.electricbicycle.api.WebMethods;
import com.wxxiaomi.ming.electricbicycle.support.GlobalManager;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Observer;

/**
 * Created by 12262 on 2016/11/10.
 * webfragemnt的基类
 */

public abstract class BaseWebFragment extends Fragment{
    protected BridgeWebView mWebView;
    private ProgressDialog dialog;
    protected final int SHOW_LOADING_DIALOG = 1;
    protected final int CLOSE_LOADING_DIALOG = 2;
    protected final  int SHOW_MSG_DIALOG = 3;
    protected final int CLOSE_MSG_DIALOG = 4;
    protected Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SHOW_LOADING_DIALOG:
                    dialog.show();;
                    break;
                case CLOSE_LOADING_DIALOG:
                    dialog.dismiss();
                    break;
//                case SHOW_MSG_DIALOG:
//                    alertDialog.setMessage(message);
//                    alertDialog.show();
//                    break;
//                case CLOSE_MSG_DIALOG:
//                    break;
            }
        }
    };
    private String message = "未知错误";
    private Context ct;

    @Override
    public void onStart() {
        super.onStart();
        Log.i("wang","fragment-onstart()");
        mWebView.setWebViewClient(new MyWebViewClient(mWebView));
        dialog = new ProgressDialog(ct);
        dialog.setTitle("请等待");//设置标题
        dialog.setMessage("正在加载");
        //dialog.show();
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
                                Log.i("wang","native->sendGEt->result:"+s);
                                function.onCallBack(s);
                            }
                        });
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

        initData();
        mWebView.callHandler("bridgeInit", "", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {}
        });

    }

    /**
     * 解析url
     * @param data
     * @return
     */
    private Observable<String> parseGetRequest(String data) {
        Map<String, String> pars = parseData(data);
        String url = pars.get("url");
        pars.remove("url");
        return WebMethods.getInstance().sendget(url,pars);
    }

    /**
     * 对js传过来的String类型的参数进行解析成Map
     * @param data
     * @return
     */
    protected Map<String,String> parseData(String data){
        Map<String,String> datas = new HashMap<>();
        String[] split = data.split("&");
        for(String item : split){
            datas.put(item.substring(0,item.indexOf("=")),item.substring(item.indexOf("=")+1,item.length()));
        }
        return datas;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = initView(inflater);
        int webViewId = getWebViewId();
        mWebView = (BridgeWebView) view.findViewById(webViewId);
        return view;
    }

    protected abstract int getWebViewId();

    protected abstract View initView(LayoutInflater inflater);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ct = getActivity();
        super.onCreate(savedInstanceState);
    }

    /**
     * 自定义的WebViewClient
     */
    protected class MyWebViewClient extends BridgeWebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            if(dialog.isShowing()){
                dialog.dismiss();
            }
            super.onPageFinished(view, url);
        }
        public MyWebViewClient(BridgeWebView webView) {
            super(webView);
        }
    }

    protected abstract void initData();
}
