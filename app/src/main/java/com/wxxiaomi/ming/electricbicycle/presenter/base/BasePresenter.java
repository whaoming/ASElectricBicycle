package com.wxxiaomi.ming.electricbicycle.presenter.base;

import com.wxxiaomi.ming.electricbicycle.model.base.DAO;
import com.wxxiaomi.ming.electricbicycle.ui.view.base.BaseView;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by 12262 on 2016/5/29.
 */
public abstract class BasePresenter<V extends BaseView,B extends DAO>{
    protected V view;
    protected B model;
    public BasePresenter(V v) {
        this.view = v;
        try {
            model = getB();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private B getB() throws InstantiationException, IllegalAccessException {
        Type sType = getClass().getGenericSuperclass();
        Type[] generics = ((ParameterizedType) sType).getActualTypeArguments();
        @SuppressWarnings("unchecked")
        Class<B> mTClass = (Class<B>) (generics[1]);
        return mTClass.newInstance();
    }
}
