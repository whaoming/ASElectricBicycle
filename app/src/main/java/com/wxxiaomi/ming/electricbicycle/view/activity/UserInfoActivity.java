package com.wxxiaomi.ming.electricbicycle.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.bean.User;
import com.wxxiaomi.ming.electricbicycle.dao.impl.UserDaoImpl;
import com.wxxiaomi.ming.electricbicycle.view.activity.base.BaseActivity;
import com.wxxiaomi.ming.electricbicycle.view.custom.EditableDialog;


public class UserInfoActivity extends BaseActivity {

    private Toolbar toolbar;
    private User.UserCommonInfo userInfo;
    private FloatingActionButton btn_add;
    private UserDaoImpl impl;
    private boolean isMyFriendFlag;

    @SuppressWarnings("deprecation")
    @Override
    protected void initView() {
        setContentView(R.layout.activity_userinfo);
        userInfo = (User.UserCommonInfo) getIntent().getBundleExtra("value").get("userInfo");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // 标题的文字需在setSupportActionBar之前，不然会无效
        toolbar.setTitle(userInfo.name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); // 设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_add = (FloatingActionButton) findViewById(R.id.btn_add);

        btn_add.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        // TODO Auto-generated method stub
//		EMClient.getInstance().
        impl = new UserDaoImpl(ct);
        isMyFriendFlag = impl.isMyFriend(userInfo.emname);
        if (isMyFriendFlag) {
            //已經是好友關係
            btn_add.setImageDrawable(getResources().getDrawable(R.mipmap.ic_mode_edit_black_18dp));
        } else {
            btn_add.setImageDrawable(getResources().getDrawable(R.mipmap.ic_common_add_press));
        }
        Log.i("wang", "当前用户:" + EMClient.getInstance().getCurrentUser());


    }

    @Override
    protected void processClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                ///添加好友按钮

                if (isMyFriendFlag) {
//					Intent intent = new Intent(ct,);
                    Intent intent = new Intent(ct, ChatActivity2.class);
                    intent.putExtra("userId", userInfo.emname);
                    startActivity(intent);
                } else {
                        EditableDialog dialog = new EditableDialog(ct).builder()
                                .setHint("理由")
                                .setTitle("添加好友")
                                .setOnPositiveButtonClick(new EditableDialog.PositiveButtonOnClick() {
                                    @Override
                                    public void onClick(DialogInterface dialog,String content) {
                                        try {
                                            EMClient.getInstance().contactManager().addContact(userInfo.emname, content);
                                            dialog.dismiss();
                                            showErrorDialog("已發送好友申請");
                                        } catch (HyphenateException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                            Log.i("wang", "添加好友出错");
                                        }
                                    }
                                })
                                ;
                        dialog.show();
                       //
                        //Hyphenate

                }


                break;

            default:
                break;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
