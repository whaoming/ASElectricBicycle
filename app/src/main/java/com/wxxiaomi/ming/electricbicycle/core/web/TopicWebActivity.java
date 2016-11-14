package com.wxxiaomi.ming.electricbicycle.core.web;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.api.WebMethods;
import com.wxxiaomi.ming.electricbicycle.core.web.base.BaseWebActivity;

import rx.Observer;

public class TopicWebActivity extends BaseWebActivity {
    private Toolbar toolbar;


    @Override
    protected int initViewAndReutrnWebViewId(Bundle savedInstanceState) {
        setContentView(R.layout.activity_webview);
        return R.id.web_view;
    }

    @Override
    protected void initData() {
        toolbar = (Toolbar) this.findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mWebView.loadUrl("file:///android_asset/index.html");
        mWebView.registerHandler("listMoreTopic", new BridgeHandler() {
            @Override
            public void handler(final String data, final CallBackFunction function) {
                WebMethods.getInstance().listTopic(Integer.valueOf(data))
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onCompleted() {}

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



        mWebView.registerHandler("runTopicDetail", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //将data转化成UserInfo
                Log.i("wang","跳转到topicDetail的data="+data);
                Intent intent = new Intent(TopicWebActivity.this,TopicDetailWebActivty.class);
                intent.putExtra("initValue",data);
                startActivity(intent);
            }
        });

        mWebView.registerHandler("runTopicEdit", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //将data转化成UserInfo
                Log.i("wang","runTopicEdit->data="+data);
//                Intent intent = new Intent(TopicWebActivity.this,TestBaseActivity.class);
//                intent.putExtra("initValue",data);
//                startActivity(intent);
                Intent intent = new Intent(TopicWebActivity.this,TopicWebActivity.class);
                intent.putExtra("initValue",data);
                startActivity(intent);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_topic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
        }
        else if (id == R.id.personal) {
            Intent intent = new Intent(TopicWebActivity.this,TopicPersonalActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
