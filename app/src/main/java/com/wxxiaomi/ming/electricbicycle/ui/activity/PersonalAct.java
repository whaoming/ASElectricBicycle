package com.wxxiaomi.ming.electricbicycle.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

//import com.jph.takephoto.permission.PermissionManager;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.ui.activity.base.BaseActivity;

import com.wxxiaomi.ming.electricbicycle.ui.presenter.PersonalPresenter;
//import com.wxxiaomi.ming.electricbicycle.core.presenter.impl.PersonalPreImpl;
import com.wxxiaomi.ming.electricbicycle.ui.presenter.impl.PersonalPreImpl;
import com.wxxiaomi.ming.electricbicycle.ui.activity.view.PersonaView;
import com.wxxiaomi.ming.electricbicycle.service.ShowerProvider;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pull2refreshreview.PullToRefreshRecyclerView;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pull2refreshreview.footer.DefaultLoadMoreView;

/**
 * Created by 12262 on 2016/6/21.
 * 个人页面
 */
public class PersonalAct extends BaseActivity<PersonaView, PersonalPresenter> implements PersonaView<PersonalPresenter> {

    private Toolbar toolbar1;
//    private FloatingActionButton btn_add;
    private PullToRefreshRecyclerView mRecyclerView;
    private CollapsingToolbarLayout collapsing_toolbar;
    private ImageView iv_my_head;
    private TextView tv_name;
    private ImageView iv_back;
;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_personal);
        // 标题的文字需在setSupportActionBar之前，不然会无效
//        btn_add = (FloatingActionButton) findViewById(R.id.btn_add);
//        btn_add.setOnClickListener(this);
//        btn_add.setVisibility(View.GONE);
        mRecyclerView = (PullToRefreshRecyclerView) findViewById(R.id.mRecyclerView);
        initRefreshView();
        toolbar1 = (Toolbar) findViewById(R.id.toolbar1);
        collapsing_toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar1.setTitle("");
        iv_my_head = (ImageView) findViewById(R.id.iv_avatvr);
        tv_name = (TextView) findViewById(R.id.tv_nick);
//        big_img = (RelativeLayout) findViewById(R.id.big_img);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        ShowerProvider.showHead(this,iv_my_head,"");
        setSupportActionBar(toolbar1);
        getSupportActionBar().setHomeButtonEnabled(true); // 设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initRefreshView() {
        DefaultLoadMoreView defaultLoadMoreView = new DefaultLoadMoreView(this, mRecyclerView.getRecyclerView());
        defaultLoadMoreView.setLoadmoreString("加载更多");
        defaultLoadMoreView.setLoadMorePadding(100);
        mRecyclerView.setSwipeEnable(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLoadMoreFooter(defaultLoadMoreView);

    }



    @Override
    public PersonalPresenter getPresenter() {
        return new PersonalPreImpl();
    }


    @Override
    public ImageView getHeadView(){
        return iv_my_head;
    }

    @Override
    public PullToRefreshRecyclerView getListView() {
        return mRecyclerView;
    }

    @Override
    public ImageView getBackImgContent() {
        return iv_back;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_add:
                ///添加好友按钮
//                presenter.onAddBtnClick();
                break;
            case R.id.iv_back:
                presenter.onBackImgClick();
                break;
        }
    }

    @Override
    public void setViewData(UserCommonInfo info) {
        collapsing_toolbar.setTitle(info.nickname);
        tv_name.setText(info.nickname);
    }

    @Override
    public void setBtnView(Drawable drawable) {
//        btn_add.setImageDrawable(drawable);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_act_personal, menu);//加载menu文件到布局
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit:
                presenter.onEditClick();
                break;
            case R.id.action_setting:
                presenter.onSettingClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
