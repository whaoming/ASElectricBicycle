package com.wxxiaomi.ming.electricbicycle.core.web;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.core.weight.adapter.IndexFragmentTabAdapter;

import java.util.ArrayList;
import java.util.List;

public class TopicPersonalActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentStatePagerAdapter fAdapter;
    private List<Fragment> list_fragment;
    private List<String> list_title;

    /**
     * 我的帖子页面
     */
    private WebFragment TopicItemFragment;
    /**
     * 我的消息页面
     */
    private WebFragment TopicMsgFragment;

    private TestWebFragment testFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("wang","进入TopicPersonalActivity->oncreate");
        setContentView(R.layout.activity_topic_personal);
        tabLayout = (TabLayout) findViewById(R.id.tab_FindFragment_title);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager = (ViewPager) findViewById(R.id.vp_FindFragment_pager);
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list_fragment = new ArrayList<Fragment>();
        TopicItemFragment = new WebFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url","file:///android_asset/myitem.html");
        TopicItemFragment.setArguments(bundle);
        TopicMsgFragment = new WebFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("url","file:///android_asset/info.html");
        TopicMsgFragment.setArguments(bundle2);
        testFragment = new TestWebFragment();
        list_fragment.add(TopicItemFragment);
        list_fragment.add(TopicMsgFragment);
        list_title = new ArrayList<String>();
        list_title.add("我的帖子");
        list_title.add("我的消息");
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(1)));
        fAdapter = new IndexFragmentTabAdapter(getSupportFragmentManager(),
                list_fragment, list_title);
        viewPager.setAdapter(fAdapter);
        viewPager.requestDisallowInterceptTouchEvent(true);
        tabLayout.setupWithViewPager(viewPager);
    }


}
