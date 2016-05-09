package com.wxxiaomi.ming.chatwidget.weidgt;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.wxxiaomi.ming.chatwidget.R;
import com.wxxiaomi.ming.chatwidget.bean.CoustomEmojiGroupEntity;
import com.wxxiaomi.ming.chatwidget.bean.Emojicon;
import com.wxxiaomi.ming.chatwidget.emojicon.EaseEmojiconIndicatorView;
import com.wxxiaomi.ming.chatwidget.emojicon.EaseEmojiconPagerView;
import com.wxxiaomi.ming.chatwidget.emojicon.EaseEmojiconScrollTabBar;


/**
 * Created by 12262 on 2016/5/1.
 */
public class EaseEmojiconMenu extends LinearLayout {
    protected EaseEmojiconMenuListener listener;
    private int emojiconColumns;
//    private int bigEmojiconColumns;
//    private final int defaultBigColumns = 4;
    private final int defaultColumns = 7;
    private EaseEmojiconScrollTabBar tabBar;
    private EaseEmojiconIndicatorView indicatorView;
    private EaseEmojiconPagerView pagerView;

    private List<CoustomEmojiGroupEntity> emojiconGroupList = new ArrayList<CoustomEmojiGroupEntity>();

    public EaseEmojiconMenu(Context context) {
        super(context);
        init(context, null);

    }

    public EaseEmojiconMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);

    }
    public EaseEmojiconMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        LayoutInflater.from(context).inflate(R.layout.weidgt_emojicon, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EaseEmojiconMenu);
        emojiconColumns = ta.getInt(R.styleable.EaseEmojiconMenu_emojiconColumns, defaultColumns);
//        bigEmojiconColumns = ta.getInt(R.styleable.EaseEmojiconMenu_bigEmojiconRows, defaultBigColumns);
        ta.recycle();
        pagerView = (EaseEmojiconPagerView) findViewById(R.id.pager_view);
        indicatorView = (EaseEmojiconIndicatorView) findViewById(R.id.indicator_view);
        tabBar = (EaseEmojiconScrollTabBar) findViewById(R.id.tab_bar);

    }

    /**
     * 把每组表情数据添加到emojiconGroupList中,并设置头部icon
     * 
     * @param groupEntities  多组表情数据
     */
    public void init(List<CoustomEmojiGroupEntity> groupEntities){
        if(groupEntities == null || groupEntities.size() == 0){
            return;
        }
        for(CoustomEmojiGroupEntity groupEntity : groupEntities){
            emojiconGroupList.add(groupEntity);
            tabBar.addTab(groupEntity.getIcon());
        }

        pagerView.setPagerViewListener(new EmojiconPagerViewListener());
        pagerView.init(emojiconGroupList, emojiconColumns,4);

        tabBar.setTabBarItemClickListener(new EaseEmojiconScrollTabBar.EaseScrollTabBarItemClickListener() {

            @Override
            public void onItemClick(int position) {
                pagerView.setGroupPostion(position);
            }
        });

    }

    /**
     * 添加表情组
     * @param groupEntity
     */
    public void addEmojiconGroup(CoustomEmojiGroupEntity groupEntity){
        emojiconGroupList.add(groupEntity);
        pagerView.addEmojiconGroup(groupEntity, true);
        tabBar.addTab(groupEntity.getIcon());
    }

    /**
     * 添加一系列表情组
     * @param groupEntitieList
     */
    public void addEmojiconGroup(List<CoustomEmojiGroupEntity> groupEntitieList){
        for(int i= 0; i < groupEntitieList.size(); i++){
            CoustomEmojiGroupEntity groupEntity = groupEntitieList.get(i);
            emojiconGroupList.add(groupEntity);
            pagerView.addEmojiconGroup(groupEntity, i == groupEntitieList.size()-1 ? true : false);
            tabBar.addTab(groupEntity.getIcon());
        }

    }

    /**
     * 移除表情组
     * @param position
     */
    public void removeEmojiconGroup(int position){
        emojiconGroupList.remove(position);
        pagerView.removeEmojiconGroup(position);
        tabBar.removeTab(position);
    }

    public void setTabBarVisibility(boolean isVisible){
        if(!isVisible){
            tabBar.setVisibility(View.GONE);
        }else{
            tabBar.setVisibility(View.VISIBLE);
        }
    }


    private class EmojiconPagerViewListener implements EaseEmojiconPagerView.EaseEmojiconPagerViewListener {

        @Override
        public void onPagerViewInited(int groupMaxPageSize, int firstGroupPageSize) {
            indicatorView.init(groupMaxPageSize);
            indicatorView.updateIndicator(firstGroupPageSize);
            tabBar.selectedTo(0);
        }

        @Override
        public void onGroupPositionChanged(int groupPosition, int pagerSizeOfGroup) {
            indicatorView.updateIndicator(pagerSizeOfGroup);
            tabBar.selectedTo(groupPosition);
        }

        @Override
        public void onGroupInnerPagePostionChanged(int oldPosition, int newPosition) {
            indicatorView.selectTo(oldPosition, newPosition);
        }

        @Override
        public void onGroupPagePostionChangedTo(int position) {
            indicatorView.selectTo(position);
        }

        @Override
        public void onGroupMaxPageSizeChanged(int maxCount) {
            indicatorView.updateIndicator(maxCount);
        }

        @Override
        public void onDeleteImageClicked() {
            if(listener != null){
                listener.onDeleteImageClicked();
            }
        }

        @Override
        public void onExpressionClicked(Emojicon emojicon) {
            if(listener != null){
                listener.onExpressionClicked(emojicon);
            }
        }

    }




    /**
     * 设置回调监听
     * @param listener
     */
    public void setEmojiconMenuListener(EaseEmojiconMenuListener listener){
        this.listener = listener;
    }

    public interface EaseEmojiconMenuListener{
        /**
         * 表情被点击
         * @param emojicon
         */
        void onExpressionClicked(Emojicon emojicon);
        /**
         * 删除按钮被点击
         */
        void onDeleteImageClicked();
    }
}
