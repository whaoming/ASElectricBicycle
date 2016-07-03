package com.wxxiaomi.ming.electricbicycle.presenter.callback;


import com.wxxiaomi.ming.electricbicycle.presenter.base.BasePresenter;

/**
 * Created by 12262 on 2016/6/9.
 */
public interface SearchPresenter<T> extends BasePresenter<T> {
    void initPoi();
    void searchBtnOnClick(String content);
    void onSearchTextChange(String content);
}
