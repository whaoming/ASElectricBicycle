package com.wxxiaomi.ming.electricbicycle.core.ui.presenter;


import com.wxxiaomi.ming.electricbicycle.core.ui.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.core.ui.base.BaseView;


/**
 * Created by 12262 on 2016/6/9.
 */
public interface SearchPresenter<V extends BaseView> extends BasePre<V> {
    void searchBtnOnClick(String content);
    void onSearchTextChange(String content);
}
