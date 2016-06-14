package com.wxxiaomi.ming.electricbicycle.support.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by MummyDing on 16-2-3.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public abstract class BaseListAdapter<T,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected List<T> mItems;
    protected Context mContext;
    protected abstract void updateView();
    public BaseListAdapter(Context mContext, T model) {
        this.mContext = mContext;
    }
    public BaseListAdapter(Context mContext, List<T> modelList) {
        this.mContext = mContext;
    }
    @Override
    public int getItemCount() {
        if (mItems == null) return 0;
        return mItems.size();
    }

    protected T getItem(int position){
        return mItems.get(position);
    }
    public void newList(List<T> list){
        if(list == null){
            return;
        }
        if(mItems == null){
            mItems = new ArrayList<>();
        }else {
            mItems.clear();
        }

        mItems.addAll(list);
        updateView();
    }

    /**
     * 专门给CourseTableAdapter用的...
     * @param list
     */
//    public void addCourseInfoList(List<CourseInfoModel> list){
//    }

    /**
     * 专门给BookSearchAdapter 用的
     * @param keyword
     */
//    public void addKeyword(String keyword){
//
//    }

}
