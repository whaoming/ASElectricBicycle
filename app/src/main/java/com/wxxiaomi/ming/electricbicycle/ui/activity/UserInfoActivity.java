package com.wxxiaomi.ming.electricbicycle.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.wxxiaomi.ming.electricbicycle.ConstantValue;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.api.exp.ApiException;
import com.wxxiaomi.ming.electricbicycle.improve.im.ImHelper1;
import com.wxxiaomi.ming.electricbicycle.db.bean.Option;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.db.bean.format.UserInfo;
import com.wxxiaomi.ming.electricbicycle.service.AccountHelper;
import com.wxxiaomi.ming.electricbicycle.service.UserFunctionProvider;
import com.wxxiaomi.ming.electricbicycle.service.ShowerProvider;
import com.wxxiaomi.ming.electricbicycle.support.rx.ProgressObserver;
import com.wxxiaomi.ming.electricbicycle.support.rx.ToastObserver;
import com.wxxiaomi.ming.electricbicycle.ui.fragment.InfoCardFragment;
import com.wxxiaomi.ming.electricbicycle.ui.fragment.InfoDetailFragment;
import com.wxxiaomi.ming.electricbicycle.ui.fragment.base.BaseFragment;
import com.wxxiaomi.ming.electricbicycle.ui.fragment.base.FragmentCallback;
import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter.IndexFragmentTabAdapter;
import com.wxxiaomi.ming.electricbicycle.ui.weight.custom.EditableDialog;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 用户信息展示页面
 * 进来需要判断是当前用户还是其他用户
 * 其他用户的话必须根据id从服务器获取信息
 */
public class UserInfoActivity extends AppCompatActivity implements FragmentCallback {
    //标识当前页面的信息是当前user还是其他用户user
    private boolean isMine;

    private List<Fragment> fragments;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private List<String> titles;
    private IndexFragmentTabAdapter fAdapter;
    private CollapsingToolbarLayout collapsing_toolbar;
    private InfoCardFragment infoCardFragment;
    private InfoDetailFragment infoDetailFragment;
    private ImageView iv_avatar;
    private ImageView iv_back;
    private Bundle bundle;
    private UserCommonInfo targetUser;
    private boolean isFriend = false;
    private EditableDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persoanl_latest);
        bundle = getIntent().getBundleExtra("value");
        isMine = bundle.getBoolean(ConstantValue.INTENT_ISMINE, false);
        tabLayout = (TabLayout) findViewById(R.id.tab_FindFragment_title);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager = (ViewPager) findViewById(R.id.vp_FindFragment_pager);
        iv_avatar = (ImageView) findViewById(R.id.iv_avatar);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        toolbar = (Toolbar) this.findViewById(R.id.toolbar1);
        if (isMine) {
            //只有简单的字段
            toolbar.setTitle(AccountHelper.getAccountInfo().nickname);
        } else {
            targetUser = (UserCommonInfo) bundle.getSerializable(ConstantValue.INTENT_USERINFO);
            toolbar.setTitle(targetUser.nickname);
            if (ImHelper1.getInstance().isMyFriend(targetUser.emname)) {
                isFriend = true;
            }
        }
        collapsing_toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsing_toolbar.setTitleEnabled(false);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragments = new ArrayList<Fragment>();
        infoCardFragment = new InfoCardFragment();
        fragments.add(infoCardFragment);
        infoDetailFragment = new InfoDetailFragment();
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
        initCustomView();
        initDialog();
    }

    private void initDialog() {
        dialog = new EditableDialog(this).builder()
                .setHint("理由")
                .setTitle("添加好友")
                .setOnPositiveButtonClick(new EditableDialog.PositiveButtonOnClick() {
                    @Override
                    public void onClick(DialogInterface dialog, String content) {
                        ImHelper1.getInstance().addContact(targetUser.emname, content)
                                .subscribe(new ProgressObserver<Boolean>(UserInfoActivity.this) {
                                    @Override
                                    public void onNext(Boolean aBoolean) {
                                        showMsg("成功添加好友");
                                    }
                                });
                    }
                })
        ;
    }

    private void initCustomView() {
        if (!isMine) {
            ShowerProvider.showHead(this, iv_avatar, targetUser.avatar);
            if (targetUser.cover != null) {
                ShowerProvider.showNormalImage(this, iv_back, targetUser.cover);
            }
        }

    }

    private void initData() {
        if (isMine) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(ConstantValue.BUNDLE_USERINFO, AccountHelper.getAccountInfo());
            bundle.putBoolean(ConstantValue.INTENT_ISMINE, isMine);
            infoDetailFragment.receiveData(1, bundle);
            infoCardFragment.receiveData(1, bundle);
            UserFunctionProvider.getInstance().getUserOptions(AccountHelper.getAccountInfo().id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ToastObserver<List<Option>>(collapsing_toolbar) {
                        @Override
                        public void onNext(List<Option> options) {
                            ArrayList<Option> list = new ArrayList();
                            list.addAll(options);
                            Bundle bundle2 = new Bundle();
                            bundle2.putParcelableArrayList(ConstantValue.BUNDLE_OPTIONS, list);
                            infoCardFragment.receiveData(2, bundle2);
                        }

                        @Override
                        protected void onError(ApiException ex) {
                            super.onError(ex);
                        }
                    });
        } else {
//            final int userid = getIntent().getIntExtra(ConstantValue.INTENT_USERID,0);
            UserFunctionProvider.getInstance().getUserInfoAndOption(targetUser.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<UserInfo>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(UserInfo userInfo) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(ConstantValue.BUNDLE_USERINFO, userInfo.userCommonInfo);
                            bundle.putBoolean(ConstantValue.INTENT_ISMINE, isMine);

                            infoDetailFragment.receiveData(1, bundle);
                            infoCardFragment.receiveData(1, bundle);

                            bundle.putParcelableArrayList(ConstantValue.BUNDLE_OPTIONS, userInfo.options);
                            infoCardFragment.receiveData(2, bundle);
                        }
                    });

        }
    }

    private int count = 0;

    /**
     * 这个函数是用于fragment发送数据给activity
     *
     * @param fragment
     * @param id
     * @param args
     */
    @Override
    public void onFragmentCallback(BaseFragment fragment, int id, Bundle args) {
        Log.i("wang", "onFragmentCallback");
        if (id == 5) {
            count++;
            if (count == 2) {
                initData();
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //加载menu文件到布局
        if (isMine) {
            getMenuInflater().inflate(R.menu.menu_userinfo_mine, menu);
        } else {
            if (isFriend) {
                getMenuInflater().inflate(R.menu.menu_userinfo_friend, menu);
            } else {
                getMenuInflater().inflate(R.menu.menu_userinfo_other, menu);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_add:
                dialog.show();
                break;
            case R.id.action_edit:
                intent = new Intent(this, MyInfoEditActivity.class);
                startActivity(intent);
                break;
            case R.id.action_talk:
                intent = new Intent(this, MyInfoEditActivity.class);
                startActivity(intent);
                break;
            case R.id.action_share:
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                intent.putExtra(Intent.EXTRA_TEXT, "I have successfully share my message through my app");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent, getTitle()));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isMine) {
            toolbar.setTitle(AccountHelper.getAccountInfo().nickname);
            ShowerProvider.showHead(this, iv_avatar, AccountHelper.getAccountInfo().avatar);
            if (AccountHelper.getAccountInfo().cover != null) {
                ShowerProvider.showNormalImage(this, iv_back, AccountHelper.getAccountInfo().cover);
            }
        }
    }
}
