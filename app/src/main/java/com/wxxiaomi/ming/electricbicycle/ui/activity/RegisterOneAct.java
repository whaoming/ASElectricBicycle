//package com.wxxiaomi.ming.electricbicycle.core.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.design.widget.TextInputLayout;
//import android.support.v7.widget.Toolbar;
//import android.view.View;
//import android.widget.Button;
//
//import com.wxxiaomi.ming.electricbicycle.R;
//import com.wxxiaomi.ming.electricbicycle.core.activity.base.BaseActivity;
//import com.wxxiaomi.ming.electricbicycle.core.presenter.RegisterOnePresenter;
//import com.wxxiaomi.ming.electricbicycle.core.presenter.impl.RegisterOnePresenterImpl;
//import com.wxxiaomi.ming.electricbicycle.core.activity.view.RegisterOneView;
//import com.wxxiaomi.ming.electricbicycle.common.util.MyUtils;
//
///**
// * Created by 12262 on 2016/6/25.
// * 注册页面
// */
//public class RegisterOneAct extends BaseActivity<RegisterOneView,RegisterOnePresenter> implements RegisterOneView<RegisterOnePresenter>{
//
//    private TextInputLayout til_username;
//    private TextInputLayout til_password;
//    /**
//     * 输入验证码之后确认按钮
//     */
//    private Button btn_ok;
//    private Toolbar toolbar;
//
//    @Override
//    protected void initView(Bundle savedInstanceState) {
//        setContentView(R.layout.activity_register_one);
//        til_username = (TextInputLayout) findViewById(R.id.til_username);
//        til_password = (TextInputLayout) findViewById(R.id.til_password);
//
//        btn_ok = (Button) findViewById(R.id.btn_ok);
//        btn_ok.setOnClickListener(this);
//        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
////        til_username.getEditText().addTextChangedListener(new TextWatcher() {
////
////            @Override
////            public void onTextChanged(CharSequence s, int start, int before, int count) {
////                til_username.setError("");
////                til_username.setEnabled(false);
////
////            }
////
////            @Override
////            public void beforeTextChanged(CharSequence s, int start, int count,
////                                          int after) {
////                // TODO Auto-generated method stub
////
////            }
////
////            @Override
////            public void afterTextChanged(Editable s) {
////                // TODO Auto-generated method stub
////
////            }
////        });
////        til_password.getEditText().addTextChangedListener(new TextWatcher() {
////
////            @Override
////            public void onTextChanged(CharSequence s, int start, int before, int count) {
////                til_password.setError("");
////                til_password.setEnabled(false);
////
////            }
////
////            @Override
////            public void beforeTextChanged(CharSequence s, int start, int count,
////                                          int after) {
////                // TODO Auto-generated method stub
////
////            }
////
////            @Override
////            public void afterTextChanged(Editable s) {
////                // TODO Auto-generated method stub
////
////            }
////        });
//    }
//
//    @Override
//    public RegisterOnePresenter getPresenter() {
//        return new RegisterOnePresenterImpl();
//    }
//
//    private boolean checkFormat(TextInputLayout strLayout) {
//        String str = strLayout.getEditText().getText().toString().trim();
//        if ("".equals(str)) {
//            strLayout.setError("不能为空");
//            return false;
//        } else if (str.contains(" ")) {
//            strLayout.setError("出现非法字符");
//            return false;
//        } else if (MyUtils.checkChainse(str)) {
//            strLayout.setError("不能包含中文");
//            return false;
//        } else if (str.length() < 6) {
//            strLayout.setError("长度小于6");
//            return false;
//        } else {
//            strLayout.setEnabled(false);
//            return true;
//        }
//    }
//
//
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_ok:
//                presenter.onLoginClick(til_username.getEditText().getText().toString().trim()
//                ,til_password.getEditText().getText().toString().trim());
//                break;
//            case R.id.btn_debug:
//
//                break;
//            default:
//                break;
//        }
//    }
//
//    @Override
//    public Intent getIntentData() {
//        return getIntent();
//    }
//}
