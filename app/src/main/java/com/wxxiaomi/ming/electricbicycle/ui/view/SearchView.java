package com.wxxiaomi.ming.electricbicycle.ui.view;

import com.wxxiaomi.ming.electricbicycle.ui.view.base.BaseView;
import com.wxxiaomi.ming.electricbicycle.view.adapter.PoiSearchResultAdapter;

/**
 * Created by 12262 on 2016/6/9.
 */
public interface SearchView extends BaseView {
    void setListAdapter(PoiSearchResultAdapter sugAdapter);
    void runRoutePlanAct();
    void setNoResult(boolean flag);
}
