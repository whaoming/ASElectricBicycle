package com.wxxiaomi.ming.electricbicycle.core.weight.em;//package com.wxxiaomi.electricbicycle.view.em;
//
//
//import com.hyphenate.chat.EMClient;
//import com.hyphenate.chat.EMConversation;
//import com.hyphenate.chat.EMConversation.EMConversationType;
//import com.hyphenate.easeui.adapter.EaseMessageAdapter;
//import com.hyphenate.easeui.utils.EaseCommonUtils;
//import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
//import com.wxxiaomi.electricbicycle.R;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.drawable.Drawable;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.RelativeLayout;
//
//public class MyEaseChatMessageList extends LinearLayout{
//
//	private Context context;
//	private RecyclerView listView;
//	private String toChatUsername;
//	private EMConversation conversation;
//	private ChatRowItemAdapter messageAdapter;
//	private LinearLayoutManager mLayoutManager;
//	
//	  protected boolean showUserNick;
//	    protected boolean showAvatar;
//	    protected Drawable myBubbleBg;
//	    protected Drawable otherBuddleBg;
//
//	public MyEaseChatMessageList(Context context, AttributeSet attrs, int defStyle) {
//        this(context, attrs);
//    }
//
//    public MyEaseChatMessageList(Context context, AttributeSet attrs) {
//    	super(context, attrs);
////    	parseStyle(context, attrs);
//    	init(context);
//    }
//    protected void parseStyle(Context context, AttributeSet attrs) {
//        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EaseChatMessageList);
//        showAvatar = ta.getBoolean(R.styleable.EaseChatMessageList_msgListShowUserAvatar, true);
//        myBubbleBg = ta.getDrawable(R.styleable.EaseChatMessageList_msgListMyBubbleBackground);
//        otherBuddleBg = ta.getDrawable(R.styleable.EaseChatMessageList_msgListMyBubbleBackground);
//        showUserNick = ta.getBoolean(R.styleable.EaseChatMessageList_msgListShowUserNick, false);
//        ta.recycle();
//    }
//
//    public MyEaseChatMessageList(Context context) {
//        super(context);
//        init(context);
//    }
//
//    private void init(Context context){
//        this.context = context;
//        LayoutInflater.from(context).inflate(R.layout.activity_chat_message_list, this);
////        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.chat_swipe_layout);
//        listView = (RecyclerView) findViewById(R.id.list);
//        mLayoutManager = new LinearLayoutManager(context);
//        listView.setLayoutManager(mLayoutManager);
////        listView.setItemAnimator(new DefaultItemAnimator());
//    }
//    /**
//     * 获取listview
//     * @return
//     */
//	public RecyclerView getListView() {
//		return listView;
//	} 
//    
//    /**
//     * init widget
//     * @param toChatUsername
//     * @param chatType
//     * @param customChatRowProvider
//     */
//    public void init(String toChatUsername, int chatType, EaseCustomChatRowProvider customChatRowProvider) {
//        this.toChatUsername = toChatUsername;
//        
//        conversation = EMClient.getInstance().chatManager().getConversation(toChatUsername, EMConversationType.Chat, true);
//        messageAdapter = new ChatRowItemAdapter(context, toChatUsername, chatType, listView);
//        // 设置adapter显示消息
//        listView.setAdapter(messageAdapter);
//        
//        refreshSelectLast();
//    }
//    
//    /**
//     * 刷新列表，并且跳至最后一个item
//     */
//    public void refreshSelectLast(){
//        if (messageAdapter != null) {
////            messageAdapter.refreshSelectLast();
//        }
//    }
//
//}
