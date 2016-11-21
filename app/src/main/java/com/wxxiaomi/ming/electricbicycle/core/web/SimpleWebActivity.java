package com.wxxiaomi.ming.electricbicycle.core.web;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.core.web.action.ui.UiAction;
import com.wxxiaomi.ming.electricbicycle.core.web.base.BaseWebActivity;

/**
 * Created by 12262 on 2016/11/12.
 * 处理只有一个webview控件的公共activity
 */
public class SimpleWebActivity extends BaseWebActivity {
    private Toolbar toolbar1;
    private TextView tvTitle;
    private Button btnRight;

    @Override
    protected int initViewAndReutrnWebViewId(Bundle savedInstanceState) {
        setContentView(R.layout.activity_simple_webview);
        toolbar1 = (Toolbar) findViewById(R.id.toolbar);
        return R.id.web_view;
    }

    @Override
    protected void initWebView() {
        String url = getIntent().getStringExtra("url");
        mWebView.loadUrl(url);
    }

    @Override
    protected void initToolBar(String data) {
        tvTitle = (TextView) toolbar1.findViewById(R.id.tv_toolbar);
        btnRight = (Button) toolbar1.findViewById(R.id.btnRight);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(data!="") {
            UiAction uiAction = new Gson()
                    .fromJson(data, UiAction.class);
            parseUiAction(uiAction);
        }
    }

    private void parseUiAction(final UiAction uiAction) {
        toolbar1.setTitle(uiAction.title);
        if(uiAction.right.icon!="") {
            setToolbarRight("", getResource1(uiAction.right.icon), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mWebView.callHandler(uiAction.right.callback, "", null);
                }
            });
        }
    }

    protected void setToolbarRight(String text, @Nullable Integer icon, View.OnClickListener btnClick){
        if(text!=null)
        {
            btnRight.setVisibility(View.VISIBLE);
            btnRight.setText(text);
        }
        if (icon != null) {
            btnRight.setVisibility(View.VISIBLE);
            btnRight.setBackgroundResource(icon.intValue());
            ViewGroup.LayoutParams linearParams = btnRight.getLayoutParams();
//            linearParams.height= ToolUtils.dp2px(this,26);
//            linearParams.width=ToolUtils.dp2px(this,26);
            btnRight.setLayoutParams(linearParams);
        }
        btnRight.setOnClickListener(btnClick);
    }

    public int  getResource1(String imageName){
        Context ctx=getBaseContext();
        int resId = getResources().getIdentifier(imageName.split("\\.")[2], "mipmap", ctx.getPackageName());
        return resId;
    }
}
