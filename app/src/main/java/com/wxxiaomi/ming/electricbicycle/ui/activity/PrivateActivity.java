package com.wxxiaomi.ming.electricbicycle.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.wxxiaomi.ming.electricbicycle.R;

public class PrivateActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private RelativeLayout rl_collect;
    private RelativeLayout rl_album;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal2);
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("编辑个人信息");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); // 设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rl_collect = (RelativeLayout) findViewById(R.id.rl_collect);
        rl_album = (RelativeLayout) findViewById(R.id.rl_album);
        rl_album.setOnClickListener(this);
        rl_collect.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }
}
