package com.wxxiaomi.ming.electricbicycle.bridge.web.builder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.bridge.web.TestFragment;
import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter.IndexFragmentTabAdapter;
import com.wxxiaomi.ming.webmodule.action.forward.ManyH5Action;
import com.wxxiaomi.ming.webmodule.builder.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/1.
 */

public class TabBuilder implements Builder {

    private Context context;
    private ManyH5Action action;
    private ViewGroup allView;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentStatePagerAdapter fAdapter;
    private List<Fragment> list_fragment;
    private List<String> list_title;
    private Toolbar toolbar;
    /**
     * 我的帖子页面
     */
    private TestFragment TopicItemFragment;
    /**
     * 我的消息页面
     */
    private TestFragment TopicMsgFragment;

    public TabBuilder(Context context){
        this.context = context;
    }

    @Override
    public ViewGroup buildView() {
        action = (ManyH5Action)((AppCompatActivity)context).getIntent().getBundleExtra("value").get("action");
        LayoutInflater lf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        allView = (ViewGroup) lf.inflate(R.layout.activity_topic_personal,null);
        tabLayout = (TabLayout) allView.findViewById(R.id.tab_FindFragment_title);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        toolbar = (Toolbar) allView.findViewById(R.id.toolbar);
        ((AppCompatActivity)context).setSupportActionBar(toolbar);
        ((AppCompatActivity)context).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)context).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(action.title);
        viewPager = (ViewPager) allView.findViewById(R.id.vp_FindFragment_pager);
        list_fragment = new ArrayList<Fragment>();
        TopicItemFragment = new TestFragment();

        Bundle bundle = new Bundle();
        bundle.putString("url",action.pages.get(0));
        TopicItemFragment.setArguments(bundle);

        TopicMsgFragment = new TestFragment();
        Bundle bundled = new Bundle();
        bundled.putString("url",action.pages.get(1));
        TopicMsgFragment.setArguments(bundled);

        list_fragment.add(TopicItemFragment);
        list_fragment.add(TopicMsgFragment);
        list_title = new ArrayList<String>();
        list_title.add(action.tabs.get(0));
        list_title.add(action.tabs.get(1));
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(1)));
        fAdapter = new IndexFragmentTabAdapter(((AppCompatActivity)context).getSupportFragmentManager(),
                list_fragment, list_title);
        viewPager.setAdapter(fAdapter);
        viewPager.requestDisallowInterceptTouchEvent(true);
        tabLayout.setupWithViewPager(viewPager);
        return allView;
    }

    @Override
    public void registerMethod() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


    @Override
    public void initPageData() {

    }
}
