package com.wxxiaomi.ming.electricbicycle.ui.fragment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wxxiaomi.ming.electricbicycle.ConstantValue;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.dao.bean.Option;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo2;
import com.wxxiaomi.ming.electricbicycle.ui.activity.UserInfoActivity;
import com.wxxiaomi.ming.electricbicycle.ui.fragment.base.BaseFragment;
import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter.OptionAdapter2;
import com.wxxiaomi.ming.electricbicycle.ui.weight.adapter.OptionAdapter3;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pull2refreshreview.PullToRefreshRecyclerView;
import com.wxxiaomi.ming.electricbicycle.ui.weight.pull2refreshreview.footer.DefaultLoadMoreView;

import java.util.List;


/**
 * Created by Administrator on 2016/12/22.
 */

public class InfoCardFragment extends BaseFragment {
    private View view;
    private PullToRefreshRecyclerView mRecyclerView;
    private UserCommonInfo2 userinfo;
    private boolean isMine;
    private View header;


    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_info_card,null);
        mRecyclerView = (PullToRefreshRecyclerView) view.findViewById(R.id.mRecyclerView);
        initRefreshView();
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Log.i("wang","initData");
        dispatchCommand(5,null);
    }

//    @Override
//    public void onRefresh(boolean isRefresh) {
//        mRecyclerView.setRefreshing(isRefresh);
//    }

    private void initRefreshView() {
        mRecyclerView.setSwipeEnable(false);
        mRecyclerView.setRefreshing(true);
        DividerLine dividerLine = new DividerLine(DividerLine.VERTICAL);
        dividerLine.setSize(0);
        dividerLine.setColor(Color.parseColor("#f3f3f3"));
        mRecyclerView.getRecyclerView().addItemDecoration(dividerLine);
        DefaultLoadMoreView defaultLoadMoreView = new DefaultLoadMoreView(getActivity(), mRecyclerView.getRecyclerView());
        defaultLoadMoreView.setLoadmoreString("加载更多");
        defaultLoadMoreView.setLoadMorePadding(100);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setPagingableListener(new PullToRefreshRecyclerView.PagingableListener() {
            @Override
            public void onLoadMoreItems() {
                Log.i("wang", "onLoadMoreItems");
            }
        });
        mRecyclerView.setLoadMoreFooter(defaultLoadMoreView);
//
    }



    @Override
    public void receiveData(int flag,Bundle bundle) {
        switch (flag){
            case 1:
                userinfo = (UserCommonInfo2) bundle.getSerializable(ConstantValue.BUNDLE_USERINFO);
                isMine = bundle.getBoolean(ConstantValue.INTENT_ISMINE);
                adapterView();
                break;
            case 2:
                mRecyclerView.setRefreshing(false);
                List<Option> options = bundle.getParcelableArrayList(ConstantValue.BUNDLE_OPTIONS);
                OptionAdapter3 adapter = new OptionAdapter3(options,getActivity());
                mRecyclerView.setAdapter(adapter);

                break;
        }

    }

    /**
     * 当收到userinfo的时候匹配
     */
    private void adapterView() {
        Log.i("wang","adapterView");
        if(mRecyclerView!=null){
            mRecyclerView.setRefreshing(false);
            header = View.inflate(getActivity(),R.layout.header_infocard,null);
            mRecyclerView.addHeaderView(header);
        }

    }


    /**
     * 分隔线装饰
     *
     * @author youmingdot
     */
    public class DividerLine extends RecyclerView.ItemDecoration {
        /**
         * 水平方向
         */
        public static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;

        /**
         * 垂直方向
         */
        public static final int VERTICAL = LinearLayoutManager.VERTICAL;

        // 画笔
        private Paint paint;

        // 布局方向
        private int orientation;
        // 分割线颜色
        private int color;
        // 分割线尺寸
        private int size;

        public DividerLine() {
            this(VERTICAL);
        }

        public DividerLine(int orientation) {
            this.orientation = orientation;

            paint = new Paint();
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);

            if (orientation == VERTICAL) {
                drawHorizontal(c, parent);
            } else {
                drawVertical(c, parent);
            }
        }

        /**
         * 设置分割线颜色
         *
         * @param color 颜色
         */
        public void setColor(int color) {
            this.color = color;
            paint.setColor(color);
        }

        /**
         * 设置分割线尺寸
         *
         * @param size 尺寸
         */
        public void setSize(int size) {
            this.size = size;
        }

        // 绘制垂直分割线
        protected void drawVertical(Canvas c, RecyclerView parent) {
            final int top = parent.getPaddingTop();
            final int bottom = parent.getHeight() - parent.getPaddingBottom();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int left = child.getRight() + params.rightMargin;
                final int right = left + size;

                c.drawRect(left, top, right, bottom, paint);
            }
        }

        // 绘制水平分割线
        protected void drawHorizontal(Canvas c, RecyclerView parent) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + size;

                c.drawRect(left, top, right, bottom, paint);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(0, 0, 0, 30);
        }
    }
}
