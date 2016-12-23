package com.wxxiaomi.ming.electricbicycle.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wxxiaomi.ming.electricbicycle.ConstantValue;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo2;
import com.wxxiaomi.ming.electricbicycle.ui.activity.UserInfoActivity;
import com.wxxiaomi.ming.electricbicycle.ui.fragment.base.BaseFragment;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pull2refreshreview.PullToRefreshRecyclerView;


/**
 * Created by Administrator on 2016/12/22.
 */

public class InfoDetailFragment extends BaseFragment{
    private View view;
    private NestedScrollView srocllView;
    private TextView tv_locat;
    private TextView time_registe;
    private UserCommonInfo2 userinfo;
    private boolean isMine;

    @Override
    public void receiveData(int flag,Bundle bundle) {
        switch (flag) {
            case 1:
                userinfo = (UserCommonInfo2) bundle.getSerializable(ConstantValue.BUNDLE_USERINFO);
                isMine = bundle.getBoolean(ConstantValue.INTENT_ISMINE);
                tv_locat.setText(userinfo.city);
                time_registe.setText(userinfo.create_time);
                break;
        }
    }

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_info_detail,null);
        srocllView = (NestedScrollView) view.findViewById(R.id.srocllView);
        tv_locat = (TextView) view.findViewById(R.id.tv_locat);
        time_registe = (TextView) view.findViewById(R.id.time_registe);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        dispatchCommand(5,null);
    }

}
