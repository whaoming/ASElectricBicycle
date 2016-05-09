package com.wxxiaomi.ming.chatwidget.weidgt;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.wxxiaomi.ming.chatwidget.R;
import com.wxxiaomi.ming.chatwidget.util.DensityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 扩展栏,用于放置item
 * Created by 12262 on 2016/5/1.
 */
public class ChatExtendMenu extends GridView {
    protected Context context;
    private List<ChatMenuItemModel> itemModels = new ArrayList<ChatMenuItemModel>();

    public ChatExtendMenu(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    public ChatExtendMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ChatExtendMenu(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs){
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MingDemo);
        int numColumns = ta.getInt(R.styleable.MingDemo_numColumns, 4);
        ta.recycle();
        setNumColumns(numColumns);
        setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        setGravity(Gravity.CENTER_VERTICAL);
        setVerticalSpacing(DensityUtils.dip2px(context, 8));
    }

    /**
     * 初始化
     */
    public void init(int[] itemStrings,int[] itemdrawables,int[] itemIds,EaseChatExtendMenuItemClickListener listener){
        this.listener = listener;
        for (int i = 0; i < itemStrings.length; i++) {
            registerMenuItem(itemStrings[i], itemdrawables[i],
                    itemIds[i],null);
        }
        setAdapter(new ItemAdapter(context, itemModels));
    }


    /**
     * 注册menu item
     * 
     * @param name
     *            item名字
     * @param drawableRes
     *            item背景
     * @param itemId
     *             id
     * @param listener
     *            item点击事件
     */
    public void registerMenuItem(String name, int drawableRes, int itemId, EaseChatExtendMenuItemClickListener listener) {
        ChatMenuItemModel item = new ChatMenuItemModel();
        item.name = name;
        item.image = drawableRes;
        item.id = itemId;
        item.clickListener = listener;
        itemModels.add(item);
    }

    /**
     * 注册menu item
     * 
     * @param nameRes
     *            item名字的resource id
     * @param drawableRes
     *            item背景
     * @param itemId
     *             id
     * @param listener
     *            item点击事件
     */
    public void registerMenuItem(int nameRes, int drawableRes, int itemId, EaseChatExtendMenuItemClickListener listener) {
        registerMenuItem(context.getString(nameRes), drawableRes, itemId, listener);
    }


    /**
     * GirdView适配器adapter
     * @author 12262
     *
     */
    private class ItemAdapter extends ArrayAdapter<ChatMenuItemModel> {

        private Context context;

        public ItemAdapter(Context context, List<ChatMenuItemModel> objects) {
            super(context, 0, objects);
            this.context = context;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ChatMenuItem menuItem = null;
            if(convertView == null){
                convertView = new ChatMenuItem(context);
            }
            menuItem = (ChatMenuItem) convertView;
            menuItem.setImage(getItem(position).image);
            menuItem.setText(getItem(position).name);
            menuItem.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
//                    if(getItem(position).clickListener != null){
                        listener.onClick(position,getItem(position).id, v);
//                    }
                }
            });
            return convertView;
        }


    }

private EaseChatExtendMenuItemClickListener listener;
    /**
     * 点击监听的接口
     * @author 12262
     *
     */
    public interface EaseChatExtendMenuItemClickListener{
        void onClick(int position,int itemId, View view);
    }


    /**
     * 一个item对应的bean类
     * @author 12262
     *
     */

    
    
    /**
     * 一个item view对应的bean类
     * 类似与chatRow
     * @author 12262
     *
     */
    class ChatMenuItem extends LinearLayout {
        private ImageView imageView;
        private TextView textView;

        public ChatMenuItem(Context context, AttributeSet attrs, int defStyle) {
            this(context, attrs);
        }

        public ChatMenuItem(Context context, AttributeSet attrs) {
            super(context, attrs);
            init(context, attrs);
        }

        public ChatMenuItem(Context context) {
            super(context);
            init(context, null);
        }

        private void init(Context context, AttributeSet attrs) {
            LayoutInflater.from(context).inflate(R.layout.ming_chat_menu_item, this);
            imageView = (ImageView) findViewById(R.id.image);
            textView = (TextView) findViewById(R.id.text);
        }

        public void setImage(int resid) {
            imageView.setBackgroundResource(resid);
        }

        public void setText(int resid) {
            textView.setText(resid);
        }

        public void setText(String text) {
            textView.setText(text);
        }
    }


    public class ChatMenuItemModel{
        String name;
        int image;
        int id;
        EaseChatExtendMenuItemClickListener clickListener;


    }
}
