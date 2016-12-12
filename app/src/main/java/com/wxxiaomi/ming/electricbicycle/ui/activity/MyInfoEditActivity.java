package com.wxxiaomi.ming.electricbicycle.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.common.GlobalManager;
import com.wxxiaomi.ming.electricbicycle.dao.db.UserService;
import com.wxxiaomi.ming.electricbicycle.support.aliyun.OssEngine;
import com.wxxiaomi.ming.electricbicycle.support.img.PhotoTakeUtil;
import com.wxxiaomi.ming.electricbicycle.support.myglide.ImgShower;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

public class MyInfoEditActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private ImageView userHead;
    private PhotoTakeUtil util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info_edit);
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("编辑个人信息");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); // 设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userHead = (ImageView) findViewById(R.id.userHead);
        userHead.setOnClickListener(this);
        ImgShower.showHead(this,userHead, GlobalManager.getInstance().getUser().userCommonInfo.head);
        util = new PhotoTakeUtil(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_act_info_edit, menu);//加载menu文件到布局
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_sumbit:
                onSumbitBtnClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 提交按钮点击事件
     */
    private void onSumbitBtnClick() {

    }

    private void onHeadClick() {
        util.takePhotoCut()
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> strings) {
                        OssEngine.getInstance().initOssEngine(MyInfoEditActivity.this.getApplicationContext());
                        return UserService.getInstance().upLoadHeadImg(strings.get(0));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Glide.with(MyInfoEditActivity.this).load(s).into(userHead);
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.userHead:
                onHeadClick();
                break;
        }
    }
}
