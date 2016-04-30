package com.wxxiaomi.ming.electricbicycle.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wxxiaomi.ming.electricbicycle.GlobalParams;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.bean.User;
import com.wxxiaomi.ming.electricbicycle.bean.format.InitUserInfo;
import com.wxxiaomi.ming.electricbicycle.bean.format.NearByPerson;
import com.wxxiaomi.ming.electricbicycle.bean.format.common.ReceiceData;
import com.wxxiaomi.ming.electricbicycle.engine.MapEngineImpl;
import com.wxxiaomi.ming.electricbicycle.engine.UserEngineImpl;
import com.wxxiaomi.ming.electricbicycle.engine.common.ResultByGetDataListener;
import com.wxxiaomi.ming.electricbicycle.view.activity.base.BaseActivity;
import com.wxxiaomi.ming.electricbicycle.view.adapter.NearFriendRecommendAdapter;
import com.wxxiaomi.ming.electricbicycle.view.adapter.NearFriendRecommendAdapter1;

import java.util.ArrayList;
import java.util.List;

public class FriendAddActivity extends BaseActivity {
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private Button btn_ok;
    private List<NearByPerson.UserLocatInfo> nearUserList;
    private NearFriendRecommendAdapter1 adapter ;
    private EditText et_username;
    private List<NearByPerson.UserLocatInfo> tempNearUserList;

    @Override
    protected void initView() {
    setContentView(R.layout.activity_friend_add);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView)findViewById(R.id.list);
        btn_ok = (Button)findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
        et_username = (EditText) findViewById(R.id.et_username);
        // 标题的文字需在setSupportActionBar之前，不然会无效
        toolbar.setTitle("添加好友");
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); // 设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initData() {
        tempNearUserList = new ArrayList<NearByPerson.UserLocatInfo>();
        adapter = new NearFriendRecommendAdapter1(ct,tempNearUserList);
//        y
        mRecyclerView.setAdapter(adapter);
        getNearFriend();
    }

    private void getNearFriend(){
        MapEngineImpl impl = new MapEngineImpl(ct);
        impl.getNearByFromServer1(GlobalParams.latitude, GlobalParams.longitude, new ResultByGetDataListener<NearByPerson>() {
            @Override
            public void success(ReceiceData<NearByPerson> result) {
                nearUserList = result.infos.userLocatList;
                processNearData(nearUserList);
            }

            @Override
            public void error(String error) {

            }
        });

    }

    private void processNearData(List<NearByPerson.UserLocatInfo> nearUserList){
//        adapter = new NearFriendRecommendAdapter(ct,nearUserList);
//        y
//        mRecyclerView.setAdapter(adapter);
        tempNearUserList.addAll(nearUserList);
//        tempNearUserList = nearUserList;
        adapter.setLoadingComplete();
    }


    @Override
    protected void processClick(View v) {
        switch (v.getId()){
            case R.id.btn_ok:
                //查找好友
                showLoading1Dialog("正在查詢");
                String name = et_username.getText().toString().trim();
                UserEngineImpl impl = new UserEngineImpl(ct);
                impl.getUserCommonInfoByName(name, new ResultByGetDataListener<InitUserInfo>() {
                    @Override
                    public void success(ReceiceData<InitUserInfo> result) {
                        List<User.UserCommonInfo> list = result.infos.friendList;
                        if(list.size() == 0){
                            closeLoading1Dialog();
                            showErrorDialog("此用戶不存在");
                        }else{
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("userInfo", result.infos.friendList.get(0));
                            Intent intent4 = new Intent(ct, UserInfoActivity.class);
                            intent4.putExtra("value", bundle);
                            closeLoading1Dialog();
                            startActivity(intent4);
                        }
                    }

                    @Override
                    public void error(String error) {

                    }
                });
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
