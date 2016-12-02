package com.wxxiaomi.ming.electricbicycle.support.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.webmodule.Director;
import com.wxxiaomi.ming.electricbicycle.support.web.builder.FragBuilder;


/**
 * Created by Administrator on 2016/12/1.
 */

public class TestFragment extends Fragment {

    private Director director;;
    private LinearLayout content;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_test_web,null);
        content = (LinearLayout) view.findViewById(R.id.content);

        director = new Director(new FragBuilder(getActivity(),getArguments()));
        content.addView(director.construct());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        director.PageInit();
    }
}
