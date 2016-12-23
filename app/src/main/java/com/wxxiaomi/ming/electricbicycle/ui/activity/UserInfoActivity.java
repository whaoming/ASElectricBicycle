package com.wxxiaomi.ming.electricbicycle.ui.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.wxxiaomi.ming.electricbicycle.ConstantValue;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.api.HttpMethods;
import com.wxxiaomi.ming.electricbicycle.common.GlobalManager;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo2;
import com.wxxiaomi.ming.electricbicycle.dao.db.UserService;
import com.wxxiaomi.ming.electricbicycle.ui.fragment.InfoCardFragment;
import com.wxxiaomi.ming.electricbicycle.ui.fragment.InfoDetailFragment;
import com.wxxiaomi.ming.electricbicycle.ui.fragment.base.BaseFragment;
import com.wxxiaomi.ming.electricbicycle.ui.fragment.base.FragmentCallback;
import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter.IndexFragmentTabAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.schedulers.Schedulers;

/**
 * 用户信息展示页面
 * 进来需要判断是当前用户还是其他用户
 * 其他用户的话必须根据id从服务器获取信息
 */
public class UserInfoActivity extends AppCompatActivity implements FragmentCallback{
    //标识当前页面的信息是当前user还是其他用户user
    private boolean isMine;

    private List<Fragment> fragments;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private List<String> titles;
    private IndexFragmentTabAdapter fAdapter;
    private CollapsingToolbarLayout collapsing_toolbar;
    private OnUserLoadCompleteListner lis1;
    private OnUserLoadCompleteListner lis2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persoanl_latest);
        isMine = getIntent().getBooleanExtra(ConstantValue.RUNMINE,false);
        tabLayout = (TabLayout) findViewById(R.id.tab_FindFragment_title);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager = (ViewPager) findViewById(R.id.vp_FindFragment_pager);
        toolbar = (Toolbar) this.findViewById(R.id.toolbar1);
        toolbar.setTitle("wang");
        collapsing_toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsing_toolbar.setTitleEnabled(false);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragments = new ArrayList<Fragment>();
        InfoCardFragment infoCardFragment = new InfoCardFragment();
        lis1 = infoCardFragment;
        fragments.add(infoCardFragment);
        InfoDetailFragment infoDetailFragment = new InfoDetailFragment();
        lis2 = infoDetailFragment;
        fragments.add(infoDetailFragment);
        titles = new ArrayList<String>();
        titles.add("个人主页");
        titles.add("详细信息");
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(1)));
        fAdapter = new IndexFragmentTabAdapter(getSupportFragmentManager(),
                fragments, titles);
        viewPager.setAdapter(fAdapter);
        viewPager.requestDisallowInterceptTouchEvent(true);
        tabLayout.setupWithViewPager(viewPager);
        initData();
    }

    private void initData() {
        if(isMine){
            lis1.onUserInfoComplete(GlobalManager.getInstance().getUser().userCommonInfo);
            lis2.onUserInfoComplete(GlobalManager.getInstance().getUser().userCommonInfo);

        }else{
            int userid = getIntent().getIntExtra(ConstantValue.INTENT_USERID,0);
//            HttpMethods.getInstance().get
            UserService.getInstance().getUserInfoById(userid)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<UserCommonInfo2>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(UserCommonInfo2 userCommonInfo2) {
                            Log.i("wang","userCommonInfo2:"+userCommonInfo2);
                            lis1.onUserInfoComplete(userCommonInfo2);
                            lis2.onUserInfoComplete(userCommonInfo2);

                        }
                    });
        }
    }

    @Override
    public void onFragmentCallback(BaseFragment fragment, int id, Bundle args) {

    }

    public interface OnUserLoadCompleteListner{
        void onUserInfoComplete(UserCommonInfo2 userinfo);
    }

}
