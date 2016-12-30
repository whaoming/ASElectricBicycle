package com.wxxiaomi.ming.electricbicycle.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.v7.app.AlertDialog;

import com.wxxiaomi.ming.electricbicycle.R;

/**
 * Created by Administrator on 2016/12/16.
 */

public class SettingFragment extends PreferenceFragment {
    AlertDialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (getActivity().getString(R.string.setting_logout).equals(preference.getKey())) {
            //退出登陆
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            logout();
                        }
                    })
                    .setNegativeButton("取消",null)
                    .setTitle("提示")
                    .setMessage("确定退出当前账号吗")
                    .create();
            builder.show();
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    private void logout() {
//        UserFunctionProvider.getInstance().l
    }
}
