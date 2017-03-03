package com.wxxiaomi.ming.electricbicycle.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wxxiaomi.ming.common.base.AppContext;
import com.wxxiaomi.ming.common.cache.CacheManager;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.common.util.AppManager;
import com.wxxiaomi.ming.electricbicycle.manager.update.CheckUpdateManager;
import com.wxxiaomi.ming.common.weight.DialogHelper;
import com.wxxiaomi.ming.electricbicycle.manager.update.Version;
import com.wxxiaomi.ming.electricbicycle.manager.Account;
import com.wxxiaomi.ming.electricbicycle.ui.activity.LoginActivity;

/**
 * Created by Administrator on 2016/12/16.
 */

public class SettingFragment extends PreferenceFragment {
    View root;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        Preference bike_cache = getPreferenceManager().findPreference("bike_cache");
        bike_cache.setSummary(CacheManager.getCacheSize());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = super.onCreateView(inflater,container,savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (getActivity().getString(R.string.setting_logout).equals(preference.getKey())) {
//            //退出登陆
            DialogHelper.getConfirmDialog(getActivity(),
                    "提示",
                    "确定退出当前账号吗",
                    "确定",
                    "取消",
                    true,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            logout();
                        }
                    }).show();
        } else if ("check_update".equals(preference.getKey())) {
            CheckUpdateManager manager = new CheckUpdateManager(getActivity(), true);
            manager.setCaller(new CheckUpdateManager.RequestPermissions() {
                @Override
                public void call(boolean isOk, Version version) {

                }
            });
            manager.checkUpdate();
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    private void logout() {
        try {
            Account.logout(root, new Runnable() {
                @Override
                public void run() {
                    AppManager.getAppManager().finishAllActivity(getActivity().getClass());
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(intent);
                    AppContext.showToastShort("退出登陆成功");
                    AppManager.getAppManager().finishActivity(getActivity());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            ;
        }
//
    }
}
