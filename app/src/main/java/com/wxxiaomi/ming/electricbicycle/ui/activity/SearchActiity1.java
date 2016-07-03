package com.wxxiaomi.ming.electricbicycle.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.presenter.callback.SearchPresenter;
import com.wxxiaomi.ming.electricbicycle.presenter.impl.SearchPresenterImpl;
import com.wxxiaomi.ming.electricbicycle.ui.base.BaseMvpActivity;
import com.wxxiaomi.ming.electricbicycle.ui.view.SearchView;
import com.wxxiaomi.ming.electricbicycle.view.activity.RoutePlanActivity;
import com.wxxiaomi.ming.electricbicycle.view.adapter.PoiSearchResultAdapter;

public class SearchActiity1 extends BaseMvpActivity<SearchView,SearchPresenter<SearchView>> implements SearchView{

    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private EditText et_serach;
    private TextView tv_noresult;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        mRecyclerView = (RecyclerView) findViewById(android.R.id.list);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置Item增加、移除动画
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // 标题的文字需在setSupportActionBar之前，不然会无效
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setTitle("");
        et_serach = (EditText) findViewById(R.id.et_serach);
        et_serach.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); // 设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_noresult = (TextView) findViewById(R.id.tv_noresult);
        et_serach.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() <= 0) {
                    return;
                }
//                mSuggestionSearch
//                        .requestSuggestion((new SuggestionSearchOption())
//                                .keyword(s.toString()).city("梅州"));
                presenter.onSearchTextChange(s.toString());

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });

        et_serach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    showLoading1Dialog("正在加载路线");
//                    mPoiSearch.searchInCity((new PoiCitySearchOption())
//                            .city("梅州")
//                            .keyword(et_serach.getText().toString().trim())
//                            .pageNum(1));
                    presenter.searchBtnOnClick(et_serach.getText().toString().trim());
                }
                return false;
            }
        });
    }


    @Override
    protected SearchPresenter initPre() {
        return new SearchPresenterImpl();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void setListAdapter(PoiSearchResultAdapter sugAdapter) {
        mRecyclerView.setAdapter(sugAdapter);
    }

    @Override
    public void runRoutePlanAct() {
        Intent intent = new Intent(this,
                RoutePlanActivity.class);
        startActivity(intent);
    }

    @Override
    public void setNoResult(boolean flag) {
        if(flag){
            tv_noresult.setVisibility(View.VISIBLE);
        }else{
            tv_noresult.setVisibility(View.GONE);
        }
    }
}
