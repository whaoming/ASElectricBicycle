package com.wxxiaomi.ming.electricbicycle.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.wxxiaomi.ming.electricbicycle.Config;
import com.wxxiaomi.ming.electricbicycle.EBApplication;
import com.wxxiaomi.ming.electricbicycle.presenter.base.BasePresenter;
import com.wxxiaomi.ming.electricbicycle.support.theme.ThemeConfig;
import com.wxxiaomi.ming.electricbicycle.support.utils.common.SPUtil;
import com.wxxiaomi.ming.electricbicycle.support.utils.common.SettingsUtil;



/**
 * Created by MummyDing on 16-1-29.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public abstract class BaseMvpActivity<V,T extends BasePresenter<V>> extends AppCompatActivity implements View.OnClickListener{

    protected T presenter;
    private int mLang = -1;
    protected void loadConfig() {
        SPUtil sp=new SPUtil(EBApplication.applicationContext);
        Config.themeSelected=sp.getIntSP(Config.SP_FILE_NAME,Config.THEME_SELECTED);
        this.setTheme(ThemeConfig.themeStyle[Config.themeSelected]);
        mLang = SettingsUtil.getCurrentLanguage();
        if (mLang > -1) {
            SettingsUtil.changeLanguage(this, mLang);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        loadConfig();
        super.onCreate(savedInstanceState);
        presenter = initPre();
        initView(savedInstanceState);

            presenter.attach((V)this);


//        initData();
    }
    @Override
    protected void onResume() {
        presenter.onResume();
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        presenter.dettach();
        super.onDestroy();

    }

    protected abstract void initView(Bundle savedInstanceState);
    protected abstract T initPre();

    public Context getContext() {
        return this;
    }

    public void runActivity(Class clazz, Bundle bundle,boolean isFinish){
        Intent intent = new Intent(this,clazz);
        if(bundle!=null){
            intent.putExtra("value",bundle);
        }
        startActivity(intent);
        if(isFinish)
            finish();
    }


}
