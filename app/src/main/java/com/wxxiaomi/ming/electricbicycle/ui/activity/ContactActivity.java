package com.wxxiaomi.ming.electricbicycle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.leakcanary.RefWatcher;
import com.wxxiaomi.ming.electricbicycle.EBApplication;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.ui.activity.base.BaseActivity;
import com.wxxiaomi.ming.electricbicycle.support.easemob.ui.ContactListFragment;
import com.wxxiaomi.ming.electricbicycle.support.easemob.ui.ConversationListFragment;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.ContactPresenter;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.impl.ContactPresenterImpl;
import com.wxxiaomi.ming.electricbicycle.ui.activity.view.ContactView;
import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter.IndexFragmentTabAdapter;
import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter.NewFriendAddItemAdapter;
import com.wxxiaomi.ming.electricbicycle.ui.fragment.base.BaseFragment;
import com.wxxiaomi.ming.electricbicycle.ui.fragment.base.FragmentCallback;
import com.wxxiaomi.ming.electricbicycle.common.GlobalManager;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

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

    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerView;
    private LinearLayout drawer;
    private LinearLayoutManager mLayoutManager;

//    private LatelyFriendFragment latelyFriendFragment;

    //	private EditText et_serach;
   // private MyFriendFragment myFriendFragment;
    private RelativeLayout drawer_ll;
    private ImageButton iv_contact;

    private TextView unread_msg;
    private TextView tv_title;

    @Override
    protected void initView(Bundle savedInstanceState) {

        setContentView(R.layout.activity_contact2);
        tabLayout = (TabLayout) findViewById(R.id.tab_FindFragment_title);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager = (ViewPager) findViewById(R.id.vp_FindFragment_pager);
        iv_contact = (ImageButton) findViewById(R.id.iv_contact);
        iv_contact.setOnClickListener(this);
        unread_msg = (TextView) findViewById(R.id.unread_msg);
        tv_title = (TextView) findViewById(R.id.tv_title);
        toolbar = (Toolbar) this.findViewById(R.id.toolbar1);
        tv_title.setText(GlobalManager.getInstance().getUser().userCommonInfo.name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);

        drawer = (LinearLayout) findViewById(R.id.drawer);
        drawer.setOnClickListener(this);
        drawer_ll = (RelativeLayout)drawer.findViewById(R.id.drawer_ll);
        drawer_ll.setOnClickListener(this);
        mRecyclerView = (RecyclerView) drawer.findViewById(R.id.list);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

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

//    public String sd(){
//        Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                return "asd";
//            }
//        });
//    }



    public void onClick(View v) {
        switch (v.getId()){
            case R.id.drawer_ll:
                presenter.onAddFriendBtnClick();
                break;
            case R.id.iv_contact:
                presenter.onDrawClick();
                mDrawerLayout.openDrawer(Gravity.RIGHT);
                break;


        }
    }


    @Override
    public void updateUnReadMsg(int count) {
        Log.i("wang","更新邀请信息："+count);
        if(count>0){
            unread_msg.setText(count+"s");
            unread_msg.setVisibility(View.VISIBLE);
        }else{
            unread_msg.setVisibility(View.GONE);
        }
    }

    @Override
    public void setInviteListAdapter(NewFriendAddItemAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
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
//        latelyFriendFragment.Refresh();
        //myFriendFragment.refersh();
        demoFragment.refresh();
    }


    @Override
    public void onFragmentCallback(BaseFragment fragment, int id, Bundle args) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = EBApplication.sRefWatcher;
        refWatcher.watch(this);
    }
}
