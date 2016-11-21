package com.wxxiaomi.ming.electricbicycle.core.presenter;


import com.wxxiaomi.ming.electricbicycle.core.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.core.base.BaseView;


/**
 * Created by 12262 on 2016/6/9.
 */
public interface SearchPresenter<V extends BaseView> extends BasePre<V> {
    void searchBtnOnClick(String content);
    void onSearchTextChange(String content);
}
