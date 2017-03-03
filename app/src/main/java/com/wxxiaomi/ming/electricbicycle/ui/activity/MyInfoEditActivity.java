package com.wxxiaomi.ming.electricbicycle.ui.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.wxxiaomi.ming.common.base.AppContext;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.common.weight.DialogHelper;
import com.wxxiaomi.ming.electricbicycle.manager.Account;
import com.wxxiaomi.ming.electricbicycle.manager.UserFunctionProvider;
import com.wxxiaomi.ming.electricbicycle.common.rx.ProgressObserver;
import com.wxxiaomi.ming.electricbicycle.manager.ShowerProvider;
import com.wxxiaomi.ming.electricbicycle.common.aliyun.OssEngine;
import com.wxxiaomi.ming.electricbicycle.common.photoget.PhotoTakeUtil;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MyInfoEditActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private ImageView userHead;
    private PhotoTakeUtil util;
    private String tmpHeadUrl;
    private EditText et_nickname;
    private EditText et_description;
    private EditText et_city;

    String head = null;
    String name = null;
    String description = null;
    String city = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info_edit);
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("编辑个人信息");
        et_nickname  = (EditText) findViewById(R.id.et_nickname);
        et_description = (EditText) findViewById(R.id.et_description);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); // 设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userHead = (ImageView) findViewById(R.id.userHead);
        userHead.setOnClickListener(this);
        ShowerProvider.showHead(this,userHead, Account.getAccountInfo().avatar);
        util = new PhotoTakeUtil(this);
        et_nickname.setText(Account.getAccountInfo().nickname);
        et_description.setText(Account.getAccountInfo().description);
        et_city = (EditText) findViewById(R.id.et_city);
        et_city.setText(Account.getAccountInfo().city);
        et_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals(Account.getAccountInfo().city)){
                    city = editable.toString();
                }else{
                    city = null;
                }

            }
        });
        et_description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals(Account.getAccountInfo().description)){
                    description = editable.toString();
                }else{
                    description = null;
                }
            }
        });
        et_nickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals(Account.getAccountInfo().nickname)){
                    name = editable.toString();
                }else{
                    name = null;
                }
            }
        });

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

        if(tmpHeadUrl!=null){

        }
        if(tmpHeadUrl==null&&name==null&&description==null&&city==null){
            finish();
        }
        if(!TextUtils.isEmpty(tmpHeadUrl)){
            head = tmpHeadUrl;
        }
        if(name==null){
            name = Account.getAccountInfo().nickname;
        }
        if (description==null) {
            description = Account.getAccountInfo().description;
        }

        if(city==null){
            city = Account.getAccountInfo().city;
        }

        UserFunctionProvider.getInstance().updateUserInfo(name,head,description,city)
                .subscribeOn(Schedulers.io())
                .subscribe(new ProgressObserver<Integer>(this) {
                    @Override
                    public void onNext(Integer integer) {
                        finish();
                    }
                });
    }

    private void onHeadClick() {
//        new Action1<String>() {
//            @Override
//            public void call(String s) {
//                tmpHeadUrl = s;
//                Glide.with(MyInfoEditActivity.this).load(s).into(userHead);
//            }
//        };
        final ProgressDialog progressDialog = DialogHelper.getProgressDialog(MyInfoEditActivity.this, "正在上传..", false);
        util.takePhotoCut()
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> strings) {
                        Log.i("wang","strings.get(0):"+strings.get(0));
                        progressDialog.show();
                        OssEngine.getInstance().initOssEngine(MyInfoEditActivity.this.getApplicationContext());
                        return UserFunctionProvider.getInstance().upLoadImgToOss(strings.get(0));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onStart() {

                        super.onStart();
                    }

                    @Override
                    public void onCompleted() {
//                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        AppContext.showToast("上传头像失败");
                    }

                    @Override
                    public void onNext(String s) {
//                        Log.i("wang","有结果了,s:"+s);
                        tmpHeadUrl = s;
                        Glide.with(MyInfoEditActivity.this).load(s).into(new GlideDrawableImageViewTarget(userHead){
                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                                super.onResourceReady(resource, animation);
                                progressDialog.dismiss();
                            }
                        });
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

//    class MyTextChangListener implements TextWatcher {
//
//        @Override
//        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable editable) {
//
//        }
//    }

}
