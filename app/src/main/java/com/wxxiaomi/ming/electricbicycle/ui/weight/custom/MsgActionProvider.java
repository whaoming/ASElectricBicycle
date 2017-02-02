package com.wxxiaomi.ming.electricbicycle.ui.weight.custom;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wxxiaomi.ming.electricbicycle.R;


/**
 * Created by Administrator on 2016/12/15.
 */

public class MsgActionProvider extends ActionProvider {
    private ImageView mIvIcon;
    private TextView mTvBadge;

    // 用来记录是哪个View的点击，这样外部可以用一个Listener接受多个menu的点击。
    private int clickWhat;
    private OnClickListener onClickListener;

    /**
     * Creates a new instance.
     *
     * @param context Context for accessing resources.
     */
    public MsgActionProvider(Context context) {
        super(context);
    }

    @Override
    public View onCreateActionView() {
        int size = getContext().getResources().getDimensionPixelSize(
                android.support.design.R.dimen.abc_action_bar_default_height_material);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(size, size);
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.view_action_message, null, false);

        view.setLayoutParams(layoutParams);
        mIvIcon = (ImageView) view.findViewById(R.id.iv_icon);
        mTvBadge = (TextView) view.findViewById(R.id.tv_badge);
        view.setOnClickListener(onViewClickListener);
        return view;
    }

    // 点击处理。
    private View.OnClickListener onViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onClickListener != null)
                onClickListener.onClick(clickWhat);
        }
    };

    // 外部设置监听。
    public void setOnClickListener(int what, OnClickListener onClickListener) {
        this.clickWhat = what;
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int what);
    }

    // 设置图标。
    public void setIcon(@DrawableRes int icon) {
        mIvIcon.setImageResource(icon);
    }

    // 设置显示的数字。
    public void setBadge(int i) {
        if(i==0){
            mTvBadge.setVisibility(View.GONE);
        }else{
            mTvBadge.setVisibility(View.VISIBLE);
            mTvBadge.setText(Integer.toString(i));
        }

    }

    // 设置文字。
    public void setTextInt(@StringRes int i) {
        mTvBadge.setText(i);
    }

    // 设置显示的文字。
    public void setText(CharSequence i) {
        mTvBadge.setText(i);
    }
}
