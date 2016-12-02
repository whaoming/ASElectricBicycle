package com.wxxiaomi.ming.electricbicycle.support.web;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.squareup.leakcanary.RefWatcher;
import com.wxxiaomi.ming.electricbicycle.EBApplication;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.support.web.builder.SimpleBuilder;
import com.wxxiaomi.ming.electricbicycle.support.web.builder.TabBuilder;
import com.wxxiaomi.ming.webmodule.Director;


public class TestWebActivity extends AppCompatActivity {

    Director director;
    private LinearLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_web);
        content = (LinearLayout) findViewById(R.id.content);
        boolean isMany = getIntent().getBooleanExtra("many",false);
        if(isMany){
            director = new Director(new TabBuilder(this));
        }else{
            director = new Director(new SimpleBuilder(this));
        }

        content.addView(director.construct());
        director.PageInit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        director.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = EBApplication.sRefWatcher;
        refWatcher.watch(this);
    }
}
