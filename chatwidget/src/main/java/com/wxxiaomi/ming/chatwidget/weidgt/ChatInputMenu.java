package com.wxxiaomi.ming.chatwidget.weidgt;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import com.wxxiaomi.ming.chatwidget.R;
import com.wxxiaomi.ming.chatwidget.bean.Emojicon;
import com.wxxiaomi.ming.chatwidget.bean.CoustomEmojiGroupEntity;
import com.wxxiaomi.ming.chatwidget.bean.DefaultEmojiconDatas;
import com.wxxiaomi.ming.chatwidget.emojicon.util.SmileUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 整个控件的总和
 * Created by 12262 on 2016/5/1.
 */
public class ChatInputMenu extends LinearLayout {

    FrameLayout primaryMenuContainer, emojiconMenuContainer;
    // 住控件
    protected EaseChatPrimaryMenu chatPrimaryMenu;
    // 表情栏
    protected EaseEmojiconMenu emojiconMenu;
    // 扩展栏
    protected ChatExtendMenu chatExtendMenu;
    protected FrameLayout chatExtendMenuContainer;
    protected LayoutInflater layoutInflater;

    private Handler handler = new Handler();
    private ChatInputMenuListener listener;
    private Context context;
    private boolean inited;

    public ChatInputMenu(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    public ChatInputMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ChatInputMenu(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.weidgt_menu, this);
        primaryMenuContainer = (FrameLayout) findViewById(R.id.primary_menu_container);
        emojiconMenuContainer = (FrameLayout) findViewById(R.id.emojicon_menu_container);
        chatExtendMenuContainer = (FrameLayout) findViewById(R.id.extend_menu_container);
        // 扩展按钮栏
        chatExtendMenu = (ChatExtendMenu) findViewById(R.id.extend_menu);
    }

    /**
     * init view 此方法需放在registerExtendMenuItem后面及setCustomEmojiconMenu，
     * //	 * @param emojiconGroupList
     * 表情组类别，传null使用easeui默认的表情
     */
//	public void init(List<CoustomEmojiGroupEntity> emojiconGroupList) {
//		if (inited) {
//			return;
//		}
//		// 主按钮菜单栏,没有自定义的用默认的
////		if (chatPrimaryMenu == null) {
//			chatPrimaryMenu = (EaseChatPrimaryMenu) layoutInflater.inflate(
//					R.layout.weidgt_menu_primary, null);
////		}
//		//输入栏添加到页面上去
//		primaryMenuContainer.addView(chatPrimaryMenu);
//
//		// 表情栏，没有自定义的用默认的
////		if (emojiconMenu == null) {
//			emojiconMenu = (EaseEmojiconMenu) layoutInflater.inflate(
//					R.layout.weidgt_menu_emojicon_menu, null);
//			if (emojiconGroupList == null) {
//				emojiconGroupList = new ArrayList<CoustomEmojiGroupEntity>();
//				emojiconGroupList.add(new CoustomEmojiGroupEntity(
//						R.mipmap.ee_1, Arrays.asList(DefaultEmojiconDatas
//								.getData())));
//			}
//			((EaseEmojiconMenu) emojiconMenu).init(emojiconGroupList);
////		}
//		//表情栏添加到主页面
//		emojiconMenuContainer.addView(emojiconMenu);
//
//		processChatMenu();
//		// 初始化extendmenu
//		chatExtendMenu.init();
//
//		inited = true;
//	}
    public void init(CoustomEmojiGroupEntity groupEntity, int[] itemStrings, int[] itemdrawables, int[] itemIds, final ChatInputMenuListener listener) {
        this.listener = listener;
        if (inited) {
            return;
        }
        chatPrimaryMenu = (EaseChatPrimaryMenu) layoutInflater.inflate(
                R.layout.weidgt_menu_primary, null);
        primaryMenuContainer.addView(chatPrimaryMenu);
        emojiconMenu = (EaseEmojiconMenu) layoutInflater.inflate(
                R.layout.weidgt_menu_emojicon_menu, null);
        ArrayList<CoustomEmojiGroupEntity> emojiconGroupList = new ArrayList<CoustomEmojiGroupEntity>();
        emojiconGroupList.add(new CoustomEmojiGroupEntity(
                R.mipmap.ee_1, Arrays.asList(DefaultEmojiconDatas
                .getData())));
        emojiconMenu.init(emojiconGroupList);
        emojiconMenu.addEmojiconGroup(groupEntity);
        /*表情栏添加到主页面*/
        emojiconMenuContainer.addView(emojiconMenu);
        processChatMenu();
        chatExtendMenu.init(itemStrings,itemdrawables,itemIds,new ChatExtendMenu.EaseChatExtendMenuItemClickListener(){

            @Override
            public void onClick(int position, int itemId, View view) {
                listener.onCustomItemClick(position);
            }
        });

    }




//
//    public EaseChatPrimaryMenu getPrimaryMenu() {
//        return chatPrimaryMenu;
//    }
//
//    public ChatExtendMenu getExtendMenu() {
//        return chatExtendMenu;
//    }
//
//    public EaseEmojiconMenu getEmojiconMenu() {
//        return emojiconMenu;
//    }
//
//    /**
//     * 注册扩展菜单的item
//     *
//     * @param name        item名字
//     * @param drawableRes item背景
//     * @param itemId      id
//     * @param listener    item点击事件
//     */
//    public void registerExtendMenuItem(String name, int drawableRes,
//                                       int itemId,
//                                       ChatExtendMenu.EaseChatExtendMenuItemClickListener listener) {
//        chatExtendMenu.registerMenuItem(name, drawableRes, itemId, listener);
//    }
//
//    /**
//     * 注册扩展菜单的item
//     * <p>
//     * item名字
//     *
//     * @param drawableRes item背景
//     * @param itemId      id
//     * @param listener    item点击事件
//     */
//    public void registerExtendMenuItem(int nameRes, int drawableRes,
//                                       int itemId,
//                                       ChatExtendMenu.EaseChatExtendMenuItemClickListener listener) {
//        chatExtendMenu.registerMenuItem(nameRes, drawableRes, itemId, listener);
//    }

    protected void processChatMenu() {
        // 发送消息栏
        chatPrimaryMenu
                .setChatPrimaryMenuListener(new EaseChatPrimaryMenu.EaseChatPrimaryMenuListener() {

                    @Override
                    public void onSendBtnClicked(String content) {
                        if (listener != null)
                            listener.onSendMessage(content);
                    }

                    @Override
                    public void onToggleVoiceBtnClicked() {
                        hideExtendMenuContainer();
                    }

                    @Override
                    public void onToggleExtendClicked() {
                        toggleMore();
                    }

                    @Override
                    public void onToggleEmojiconClicked() {
                        toggleEmojicon();
                    }

                    @Override
                    public void onEditTextClicked() {
                        hideExtendMenuContainer();
                    }

                    @Override
                    public boolean onPressToSpeakBtnTouch(View v,
                                                          MotionEvent event) {
                        if (listener != null) {
                            return listener.onPressToSpeakBtnTouch(v, event);
                        }
                        return false;
                    }
                });

        emojiconMenu
                .setEmojiconMenuListener(new EaseEmojiconMenu.EaseEmojiconMenuListener() {

                    @Override
                    public void onExpressionClicked(Emojicon emojicon) {
                        if (emojicon.getType() != Emojicon.Type.BIG_EXPRESSION) {
                            if (emojicon.getEmojiText() != null) {
                                chatPrimaryMenu.onEmojiconInputEvent(SmileUtils
                                        .getSmiledText(context,
                                                emojicon.getEmojiText()));
                            }
                        } else {
                            if (listener != null) {
                                listener.onBigExpressionClicked(emojicon);
                            }
                        }
                    }

                    @Override
                    public void onDeleteImageClicked() {
                        chatPrimaryMenu.onEmojiconDeleteEvent();
                    }
                });

    }

    /**
     * 显示或隐藏图标按钮页
     */
    protected void toggleMore() {
        if (chatExtendMenuContainer.getVisibility() == View.GONE) {
            hideKeyboard();
            handler.postDelayed(new Runnable() {
                public void run() {
                    chatExtendMenuContainer.setVisibility(View.VISIBLE);
                    chatExtendMenu.setVisibility(View.VISIBLE);
                    emojiconMenu.setVisibility(View.GONE);
                }
            }, 50);
        } else {
            if (emojiconMenu.getVisibility() == View.VISIBLE) {
                emojiconMenu.setVisibility(View.GONE);
                chatExtendMenu.setVisibility(View.VISIBLE);
            } else {
                chatExtendMenuContainer.setVisibility(View.GONE);
            }

        }

    }

    /**
     * 显示或隐藏表情页
     */
    protected void toggleEmojicon() {
        if (chatExtendMenuContainer.getVisibility() == View.GONE) {
            hideKeyboard();
            handler.postDelayed(new Runnable() {
                public void run() {
                    chatExtendMenuContainer.setVisibility(View.VISIBLE);
                    chatExtendMenu.setVisibility(View.GONE);
                    emojiconMenu.setVisibility(View.VISIBLE);
                }
            }, 50);
        } else {
            if (emojiconMenu.getVisibility() == View.VISIBLE) {
                chatExtendMenuContainer.setVisibility(View.GONE);
                emojiconMenu.setVisibility(View.GONE);
            } else {
                chatExtendMenu.setVisibility(View.GONE);
                emojiconMenu.setVisibility(View.VISIBLE);
            }

        }
    }

    /**
     * 隐藏软键盘
     */
    private void hideKeyboard() {
        chatPrimaryMenu.hideKeyboard();
    }

    /**
     * 隐藏整个扩展按钮栏(包括表情栏)
     */
    public void hideExtendMenuContainer() {
        chatExtendMenu.setVisibility(View.GONE);
        emojiconMenu.setVisibility(View.GONE);
        chatExtendMenuContainer.setVisibility(View.GONE);
        chatPrimaryMenu.onExtendMenuContainerHide();
    }

    /**
     * 系统返回键被按时调用此方法
     *
     * @return 返回false表示返回键时扩展菜单栏时打开状态，true则表示按返回键时扩展栏是关闭状态<br/>
     * 如果返回时打开状态状态，会先关闭扩展栏再返回值
     */
    public boolean onBackPressed() {
        if (chatExtendMenuContainer.getVisibility() == View.VISIBLE) {
            hideExtendMenuContainer();
            return false;
        } else {
            return true;
        }

    }

    //public void setChatInputMenuListener(ChatInputMenuListener listener) {
//		this.listener = listener;
//	}

    public interface ChatInputMenuListener {
        /**
         * 发送消息按钮点击
         *
         * @param content 文本内容
         */
        void onSendMessage(String content);

        /**
         * 大表情被点击
         *
         * @param emojicon
         */
        void onBigExpressionClicked(Emojicon emojicon);

        /**
         * 长按说话按钮touch事件
         *
         * @param v
         * @param event
         * @return
         */
        boolean onPressToSpeakBtnTouch(View v, MotionEvent event);


        void onCustomItemClick(int position);
    }
}
