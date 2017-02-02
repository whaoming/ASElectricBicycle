//package com.wxxiaomi.ming.electricbicycle.core.activity;
//
//import android.os.Bundle;
//import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.OvershootInterpolator;
//import android.view.animation.TranslateAnimation;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.wxxiaomi.ming.electricbicycle.R;
//import com.wxxiaomi.ming.electricbicycle.core.activity.base.BaseActivity;
//import com.wxxiaomi.ming.electricbicycle.core.presenter.WelcomePre;
//import com.wxxiaomi.ming.electricbicycle.core.presenter.impl.WelcomePresenterImpl;
//import com.wxxiaomi.ming.electricbicycle.core.activity.view.WelcomeView;
//
//public class WelcomeActivity extends BaseActivity<WelcomeView, WelcomePre> implements WelcomeView<WelcomePre> {
//
//    private LinearLayout ll_scan;
//    private LinearLayout ll_login;
//    private TranslateAnimation mShowAction;
//    private TextView tv_load;
//    private LinearLayout ll_towbtn_view;
//
//    @Override
//    protected void initView(Bundle savedInstanceState) {
//        setContentView(R.layout.activity_welcome2);
//        ll_scan = (LinearLayout) findViewById(R.id.ll_scan);
//        ll_login = (LinearLayout) findViewById(R.id.ll_login);
//        tv_load = (TextView) findViewById(R.id.tv_load);
//        ll_scan.setOnClickListener(this);
//        ll_login.setOnClickListener(this);
//        ll_towbtn_view = (LinearLayout) findViewById(R.id.ll_towbtn_view);
//    }
//
//    @Override
//    public WelcomePre getPresenter() {
//        return new WelcomePresenterImpl();
//    }
//
//    @Override
//    public void showBtn() {
//        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f,
//                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
//                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
//        mShowAction.setDuration(1000);
//        mShowAction.setInterpolator(new OvershootInterpolator());
//        ll_towbtn_view.clearAnimation();
//        ll_towbtn_view.setAnimation(mShowAction);
//        ll_towbtn_view.startAnimation(mShowAction);
//        ll_towbtn_view.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void onClick(View v) {
//        super.onClick(v);
//        switch (v.getId()) {
//            case R.id.ll_scan:
//                presenter.onRegisterClick();
//                break;
//            case R.id.ll_login:
//                presenter.onLoginClick();
//                break;
//        }
//    }
//}
