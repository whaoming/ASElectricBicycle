package com.wxxiaomi.ming.electricbicycle.ui.weight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.wxxiaomi.ming.common.net.cons.OptionType;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.db.bean.Option;

import java.util.List;


/**
 * Created by Administrator on 2016/12/6.
 */

public class OptionAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Option> list;
    private Context mContext;

    public OptionAdapter2(List<Option> feedList, Context context) {
        list = feedList;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_option, null);
        return new LatestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof LatestViewHolder) {
            LatestViewHolder holder = (LatestViewHolder) viewHolder;
            Option option = list.get(position);
            if(option!=null){
                int flag = option.type;
                switch (flag){
                    case OptionType.TOPIC_PUBLISH:
//
                        break;
                    case OptionType.TOPIC_COMMENT:
                        break;
                }
//            Log.i("wang
            }

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
