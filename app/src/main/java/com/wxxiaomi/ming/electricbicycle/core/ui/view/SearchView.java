package com.wxxiaomi.ming.electricbicycle.core.ui.view;

import com.wxxiaomi.ming.electricbicycle.core.ui.base.BasePre;
import com.wxxiaomi.ming.electricbicycle.core.ui.base.BaseView;


/**
 * Created by 12262 on 2016/6/9.
 */
public interface SearchView<T extends BasePre> extends BaseView<T> {
//    void setListAdapter(PoiSearchResultAdapter sugAdapter);
    void runRoutePlanAct();
    void setNoResult(boolean flag);
}
