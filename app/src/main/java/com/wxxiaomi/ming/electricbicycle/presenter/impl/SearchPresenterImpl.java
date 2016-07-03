package com.wxxiaomi.ming.electricbicycle.presenter.impl;

import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.wxxiaomi.ming.electricbicycle.GlobalParams;

import com.wxxiaomi.ming.electricbicycle.presenter.base.BasePresenterImpl;
import com.wxxiaomi.ming.electricbicycle.presenter.callback.SearchPresenter;
import com.wxxiaomi.ming.electricbicycle.ui.view.SearchView;
import com.wxxiaomi.ming.electricbicycle.view.adapter.PoiSearchResultAdapter;

/**
 * Created by 12262 on 2016/6/9.
 */
public class SearchPresenterImpl extends BasePresenterImpl<SearchView> implements SearchPresenter<SearchView>,OnGetSuggestionResultListener {

    private SuggestionSearch mSuggestionSearch = null;
    private PoiSearch mPoiSearch = null;
    /**
     * 搜索建议结果adapter
     */
    private PoiSearchResultAdapter sugAdapter;


    @Override
    public void initPoi() {
        mPoiSearch = PoiSearch.newInstance();
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);
        setPoiSearchListener();
    }

    private void setPoiSearchListener(){
        mPoiSearch
                .setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
                    @Override
                    public void onGetPoiResult(PoiResult result) {
                        if (result == null
                                || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
//                            showErrorDialog("未找到结果");
                        }
                        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                            GlobalParams.poiInf = result.getAllPoi().get(0);
                           mView.runRoutePlanAct();
                            return;
                        }
                        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
                            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
                        }
                    }

                    @Override
                    public void onGetPoiDetailResult(PoiDetailResult result) {
                        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
                            // 抱歉，未找到结果
//                            showErrorDialog("抱歉未找到结果");
                        } else {
//                            Log.i("wang",
//                                    "PoiSearchDemo.this, result.getName()  + result.getAddress()="
//                                            + result.getName() + ": "
//                                            + result.getAddress());
                        }

                    }
                });
    }

    @Override
    public void searchBtnOnClick(String content) {
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city("梅州")
                .keyword(content)
                .pageNum(1));
    }

    @Override
    public void onSearchTextChange(String content) {
        mSuggestionSearch
                .requestSuggestion((new SuggestionSearchOption())
                        .keyword(content).city("梅州"));
    }



    @Override
    public void onGetSuggestionResult(SuggestionResult suggestionResult) {
        sugAdapter.clear();
        if (suggestionResult == null || suggestionResult.getAllSuggestions() == null) {
            mView.setNoResult(true);
            return;
        }
        mView.setNoResult(false);
        for (SuggestionResult.SuggestionInfo info : suggestionResult.getAllSuggestions()) {
            if (info.key != null) {
                sugAdapter.addInfo(info);
            }
        }
        sugAdapter.notifyDataSetChanged();
    }
}
