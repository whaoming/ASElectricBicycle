package com.wxxiaomi.ming.electricbicycle.ui.presenter.base;

import com.wxxiaomi.ming.electricbicycle.ui.activity.base.BaseView;

/**
 * Created by 12262 on 2016/10/18.
 * 要想父类帮子类初始化view，就要传入mview类型
 */
public abstract class BasePreImpl<V extends BaseView> implements BasePre<V> {
    public V mView;
    public abstract void init();

    @Override
    public void onViewResume(){
    }
    @Override
    public void onViewDestory(){

    }
    @Override
    public void attach(V mview) {
        this.mView = mview;
    }
}
