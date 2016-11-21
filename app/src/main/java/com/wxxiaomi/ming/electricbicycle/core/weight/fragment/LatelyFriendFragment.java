//package com.wxxiaomi.ming.electricbicycle.core.weight.fragment;
//
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Map;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Pair;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//
//import com.hyphenate.chat.EMClient;
//import com.hyphenate.chat.EMConversation;
//import com.wxxiaomi.ming.electricbicycle.R;
//import com.wxxiaomi.ming.electricbicycle.core.weight.em.MyEaseConversationList;
//import com.wxxiaomi.ming.electricbicycle.core.weight.fragment.base.BaseFragment;
//
//
//public class LatelyFriendFragment extends BaseFragment {
//
//	private View view;
//	private MyEaseConversationList conversationListView;
//	protected List<EMConversation> conversationList = new ArrayList<EMConversation>();
//
//
//	@SuppressLint("InflateParams") @Override
//	public View initView(LayoutInflater inflater) {
//		view = inflater.inflate(R.layout.ease_fragment_conversation_list, null);
//		 conversationListView = (MyEaseConversationList) view.findViewById(R.id.list);
//		return view;
//	}
//
//	@Override
//	public void initData(Bundle savedInstanceState) {
//		 conversationList.addAll(loadConversationList());
//		 conversationListView.init(conversationList);
//		 conversationListView.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				EMConversation conversation = conversationListView.getItem(position);
//                String username = conversation.getUserName();
////                Intent intent = new Intent(getActivity(), ChatActivity.class);
////                intent.putExtra("userId", username);
////                startActivity(intent);
//			}
//		});
//
//	}
//	public void Refresh(){
//		conversationList.clear();
//        conversationList.addAll(loadConversationList());
//        if(conversationListView!=null){
//        	conversationListView.refresh();
//        }
//
//	}
//
//	@Override
//	public void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		if(conversationListView!=null){
//			Refresh();
//        }
//	}
//
//	 /**
//     * 获取会话列表
//     *
//     * @return
//                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        +    */
//    public List<EMConversation> loadConversationList(){
//        // 获取所有会话，包括陌生人
//        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
//        // 过滤掉messages size为0的conversation
//        /**
//         * 如果在排序过程中有新消息收到，lastMsgTime会发生变化
//         * 影响排序过程，Collection.sort会产生异常
//         * 保证Conversation在Sort过程中最后一条消息的时间不变
//         * 避免并发问题
//         */
//        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
//        synchronized (conversations) {
//            for (EMConversation conversation : conversations.values()) {
//                if (conversation.getAllMessages().size() != 0) {
//                    //if(conversation.getType() != EMConversationType.ChatRoom){
//                        sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
//                    //}
//                }
//            }
//        }
//        try {
//            // Internal is TimSort algorithm, has bug
//            sortConversationByLastChatTime(sortList);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        List<EMConversation> list = new ArrayList<EMConversation>();
//        for (Pair<Long, EMConversation> sortItem : sortList) {
//            list.add(sortItem.second);
//        }
//        return list;
//    }
//
//    /**
//     * 根据最后一条消息的时间排序
//     *
//     */
//    private void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
//        Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
//            @Override
//            public int compare(final Pair<Long, EMConversation> con1, final Pair<Long, EMConversation> con2) {
//
//                if (con1.first == con2.first) {
//                    return 0;
//                } else if (con2.first > con1.first) {
//                    return 1;
//                } else {
//                    return -1;
//                }
//            }
//
//        });
//    }
//
//
//}
