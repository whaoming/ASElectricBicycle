package com.wxxiaomi.ming.electricbicycle.ui.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.api.HttpMethods;
import com.wxxiaomi.ming.electricbicycle.support.rx.SampleProgressObserver;
import com.wxxiaomi.ming.electricbicycle.service.ShowerProvider;
import com.wxxiaomi.ming.electricbicycle.bridge.aliyun.OssEngine;
import com.wxxiaomi.ming.electricbicycle.service.LocatProvider;
import com.wxxiaomi.ming.electricbicycle.bridge.img.PhotoTakeUtil;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

public class FootPublishActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private Button btn_up;
    private TextView tv_locat;
    private ImageView iv;
    private FloatingActionButton btn_action;
    private PhotoTakeUtil util;
    private String imgPath;
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foot_publish);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle("留下足迹");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); // 设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_up = (Button) findViewById(R.id.btn_up);
        btn_up.setOnClickListener(this);
        et = (EditText) findViewById(R.id.et);
        tv_locat = (TextView) findViewById(R.id.tv_locat);
        tv_locat.setText(LocatProvider.getInstance().getLocatTag());
        iv = (ImageView) findViewById(R.id.iv);
        iv.setOnClickListener(this);
        btn_action = (FloatingActionButton) findViewById(R.id.btn_action);
        btn_action.setOnClickListener(this);
        util = new PhotoTakeUtil(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_action:
                final String content = et.getText().toString().trim();
                final String locat_tag = LocatProvider.getInstance().getLocatTag();
                final double lat = LocatProvider.getInstance().getLatitude();
                final double lng = LocatProvider.getInstance().getLongitude();
                OssEngine.getInstance().uploadImageWithoutName(imgPath)
                        .flatMap(new Func1<String, Observable<String>>() {
                            @Override
                            public Observable<String> call(String s) {
                                return HttpMethods.getInstance().publishFootPrint(content,s,locat_tag,lat,lng);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SampleProgressObserver<String>(FootPublishActivity.this) {

                    @Override
                    public void onNext(String s) {
//                        showMsg(s);
                        finish();
                    }
                });
                break;
            case R.id.iv:
            case R.id.btn_up:
                util.takePhoto()
                        .subscribe(new Action1<List<String>>() {
                            @Override
                            public void call(List<String> strings) {
                                Log.i("wang","path:"+strings.get(0));
                                imgPath = strings.get(0);

                                ShowerProvider.showNormalImage(FootPublishActivity.this,iv,imgPath);
                                iv.setVisibility(View.VISIBLE);
                                btn_up.setVisibility(View.GONE);
                            }
                        });
                break;

        }
    }
}
