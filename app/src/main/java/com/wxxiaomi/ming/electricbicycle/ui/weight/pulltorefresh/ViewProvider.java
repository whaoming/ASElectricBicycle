package com.wxxiaomi.ming.electricbicycle.ui.weight.pulltorefresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wxxiaomi.ming.electricbicycle.R;

/**
 * Created by Mr.W on 2017/2/3.
 * E-maiil：122627018@qq.com
 * github：https://github.com/whaoming
 * Todo: 负责制造数据状态的view，比如空数据，网络错误等等
 */
public class ViewProvider {

    public static View makeNetWorkErrorView(Context context, ViewGroup viewGroup,View.OnClickListener listener){
        View view = LayoutInflater.from(context).inflate(R.layout.view_listview_net_err, viewGroup, false);
        view.findViewById(R.id.btn_reload).setOnClickListener(listener);
        return view;
    }
    public static View makeNetWorkErrorView(Context context, ViewGroup viewGroup,String content,View.OnClickListener listener){
        View view = LayoutInflater.from(context).inflate(R.layout.view_listview_net_err, viewGroup, false);
        view.findViewById(R.id.btn_reload).setOnClickListener(listener);
        ((TextView)view.findViewById(R.id.tv_err)).setText(content);
        return view;
    }

    /**
     * 附近的人数量为0的view
     * @param context
     * @param viewGroup
     * @param listener
     * @return
     */
    public static View makeNoOneView(Context context, ViewGroup viewGroup,View.OnClickListener listener){
        View view = LayoutInflater.from(context).inflate(R.layout.view_listview_near_no_one, viewGroup, false);
        view.findViewById(R.id.btn_reload).setOnClickListener(listener);
        return view;
    }
}
