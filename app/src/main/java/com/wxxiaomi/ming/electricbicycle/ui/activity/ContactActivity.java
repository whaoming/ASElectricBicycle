package com.wxxiaomi.ming.electricbicycle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.squareup.leakcanary.RefWatcher;
import com.wxxiaomi.ming.electricbicycle.EBApplication;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.ui.activity.base.BaseActivity;
import com.wxxiaomi.ming.electricbicycle.bridge.easemob.ui.ContactListFragment;
import com.wxxiaomi.ming.electricbicycle.bridge.easemob.ui.ConversationListFragment;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.ContactPresenter;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.impl.ContactPresenterImpl;
import com.wxxiaomi.ming.electricbicycle.ui.activity.view.ContactView;
import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter.IndexFragmentTabAdapter;
import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter.NewFriendAddItemAdapter;
import com.wxxiaomi.ming.electricbicycle.ui.fragment.base.BaseFragment;
import com.wxxiaomi.ming.electricbicycle.ui.fragment.base.FragmentCallback;
import com.wxxiaomi.ming.electricbicycle.service.GlobalManager;
import com.wxxiaomi.ming.electricbicycle.ui.weight.custom.MsgActionProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 12262 on 2016/6/9.
 */
public class ContactActivity extends BaseActivity<ContactView,ContactPresenter> implements ContactView<ContactPresenter>,FragmentCallback {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentStatePagerAdapter fAdapter;
    private List<Fragment> list_fragment;
    private List<String> list_title;
    private ConversationListFragment demoFragment;
    private ContactListFragment contactFragment;

//    private DrawerLayout mDrawerLayout;
//    private RecyclerView mRecyclerView;
    private LinearLayout drawer;
    private LinearLayoutManager mLayoutManager;

    private RelativeLayout drawer_ll;
    private MsgActionProvider mActionProvider;
    private MsgActionProvider mActionProvider2;

    @Override
    protected void initView(Bundle savedInstanceState) {

        setContentView(R.layout.activity_contact2);
        tabLayout = (TabLayout) findViewById(R.id.tab_FindFragment_title);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager = (ViewPager) findViewById(R.id.vp_FindFragment_pager);
        toolbar = (Toolbar) this.findViewById(R.id.toolbar1);
        toolbar.setTitle(GlobalManager.getInstance().getUser().userCommonInfo.nickname);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);

//        drawer = (LinearLayout) findViewById(R.id.drawer);
//        drawer.setOnClickListener(this);
//        drawer_ll = (RelativeLayout)drawer.findViewById(R.id.drawer_ll);
//        drawer_ll.setOnClickListener(this);
//        mRecyclerView = (RecyclerView) drawer.findViewById(R.id.list);
        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        list_fragment = new ArrayList<Fragment>();
        contactFragment = new ContactListFragment();
        demoFragment = new ConversationListFragment();
        list_fragment.add(demoFragment);
        list_fragment.add(contactFragment);
        list_title = new ArrayList<String>();
        list_title.add("最近联系人");
        list_title.add("我的好友");
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(1)));
        fAdapter = new IndexFragmentTabAdapter(getSupportFragmentManager(),
                list_fragment, list_title);
        viewPager.setAdapter(fAdapter);
        viewPager.requestDisallowInterceptTouchEvent(true);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public ContactPresenter getPresenter() {
        return new ContactPresenterImpl();
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.drawer_ll:
                presenter.onAddFriendBtnClick();
                break;
        }
    }

    @Override
    public void updateUnReadMsg(int count) {
        Log.i("wang","更新邀请信息："+count);
        if (mActionProvider!=null) {
            mActionProvider.setBadge(count);
        }
    }

    @Override
    public void setInviteListAdapter(NewFriendAddItemAdapter adapter) {
//        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void runActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(this,clazz);
        if(bundle!=null){
            intent.putExtra("value",bundle);
        }
        startActivity(intent);
    }

    @Override
    public void refershChildUI() {
        demoFragment.refresh();
    }

    @Override
    public void onFragmentCallback(BaseFragment fragment, int id, Bundle args) {
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_msg_notify, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_invite:
                break;
            case R.id.menu_search:
                presenter.onAddFriendBtnClick();
                break;
            case R.id.menu_mine:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = EBApplication.sRefWatcher;
        refWatcher.watch(this);
    }
}
