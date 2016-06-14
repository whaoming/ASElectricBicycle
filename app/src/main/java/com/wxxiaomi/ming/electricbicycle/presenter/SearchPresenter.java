package com.wxxiaomi.ming.electricbicycle.presenter;

import com.wxxiaomi.ming.electricbicycle.presenter.base.BasePre;

/**
 * Created by 12262 on 2016/6/9.
 */
public interface SearchPresenter extends BasePre{
    void initPoi();
    void searchBtnOnClick(String content);
    void onSearchTextChange(String content);
}
