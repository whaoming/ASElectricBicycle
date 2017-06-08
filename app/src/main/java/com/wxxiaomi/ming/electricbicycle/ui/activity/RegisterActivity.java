package com.wxxiaomi.ming.electricbicycle.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wxxiaomi.ming.common.util.TDevice;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.net.HttpMethods;
import com.wxxiaomi.ming.common.util.AppManager;
import com.wxxiaomi.ming.electricbicycle.db.bean.User;
import com.wxxiaomi.ming.electricbicycle.im.ImHelper1;
import com.wxxiaomi.ming.electricbicycle.manager.Account;
import com.wxxiaomi.ming.electricbicycle.common.rx.ProgressObserver;

import rx.Observable;
import rx.functions.Func1;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private Button btn_register;
    private TextView tv_login;

    private EditText et_username;
    private EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_register2);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("注册");
        setSupportActionBar(toolbar);
        tv_login = (TextView) findViewById(R.id.tv_login);
        tv_login.setOnClickListener(this);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_register:
                register();
                break;
        }
    }

    private void register() {
        String uniqueID = TDevice.getUniqueID(getApplicationContext());
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        HttpMethods.getInstance().registerUser(username, password, uniqueID)
                .flatMap(new Func1<User, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(User user) {
                        Account.updateUserCache(user);
                        return ImHelper1.getInstance().LoginFromEm(user.username, user.password);
                    }
                }).subscribe(new ProgressObserver<Boolean>(this) {
            @Override
            public void onNext(Boolean aBoolean) {
                AppManager.getAppManager().finishAllActivity(RegisterActivity.this.getClass());
                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                startActivity(intent);
                AppManager.getAppManager().finishActivity(RegisterActivity.this);
            }
        });
//                .subscribe(new ProgressObserver<User>(this) {
//                    @Override
//                    public void onNext(User user) {
//                        Log.i("wang","注册成功，user:"+user.toString());
//                        AccountHelper.updateUserCache(user);
//                        ImHelper1.getInstance().LoginFromEm(user.username, user.password).subscribe(new Action1<Boolean>() {
//                            @Override
//                            public void call(Boolean aBoolean) {
//
//                            }
//                        });
//                        AppManager.getAppManager().finishAllActivity(RegisterActivity.this.getClass());
//                        Intent intent = new Intent(RegisterActivity.this,HomeActivity.class);
//                        startActivity(intent);
//                        AppManager.getAppManager().finishActivity(RegisterActivity.this);
//
//                    }
//                });

    }

    @Override
    protected void onDestroy() {
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();

    }
}
