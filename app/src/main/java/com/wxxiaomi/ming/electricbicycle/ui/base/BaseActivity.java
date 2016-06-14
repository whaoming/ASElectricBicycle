package com.wxxiaomi.ming.electricbicycle.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wxxiaomi.ming.electricbicycle.Config;
import com.wxxiaomi.ming.electricbicycle.EBApplication;
import com.wxxiaomi.ming.electricbicycle.presenter.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.support.theme.ThemeConfig;
import com.wxxiaomi.ming.electricbicycle.support.utils.common.SPUtil;
import com.wxxiaomi.ming.electricbicycle.support.utils.common.SettingsUtil;
import com.wxxiaomi.ming.electricbicycle.ui.view.base.BaseView;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * Created by MummyDing on 16-1-29.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public abstract class BaseActivity<V extends BasePre> extends AppCompatActivity implements View.OnClickListener{

    protected V presenter;
    private int mLang = -1;
    protected void loadConfig() {
        SPUtil sp=new SPUtil(EBApplication.applicationContext);
        Config.themeSelected=sp.getIntSP(Config.SP_FILE_NAME,Config.THEME_SELECTED);
        this.setTheme(ThemeConfig.themeStyle[Config.themeSelected]);
        // Language
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

        presenter.onViewCreate();
        initData();
    }



    @Override
    protected void onDestroy() {
        presenter.onViewDestory();
        super.onDestroy();
    }



    protected abstract void initView(Bundle savedInstanceState);
    protected abstract void initData();
    protected abstract V initPre();

    public Context getContext() {
        return this;
    }


}
