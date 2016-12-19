package com.wxxiaomi.ming.electricbicycle.support.easemob.ui;

import android.content.Context;
import android.util.Log;
import android.widget.BaseAdapter;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowBigExpression;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowFile;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowImage;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowLocation;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowText;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowVideo;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowVoice;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;

/**
 * Created by Administrator on 2016/12/16.
 */

public class MyChatRowProvider implements EaseCustomChatRowProvider {
    private Context ct;
    private static final int HANDLER_MESSAGE_REFRESH_LIST = 0;
    private static final int HANDLER_MESSAGE_SELECT_LAST = 1;
    private static final int HANDLER_MESSAGE_SEEK_TO = 2;

    private static final int MESSAGE_TYPE_RECV_TXT = 0;
    private static final int MESSAGE_TYPE_SENT_TXT = 1;
    private static final int MESSAGE_TYPE_SENT_IMAGE = 2;
    private static final int MESSAGE_TYPE_SENT_LOCATION = 3;
    private static final int MESSAGE_TYPE_RECV_LOCATION = 4;
    private static final int MESSAGE_TYPE_RECV_IMAGE = 5;
    private static final int MESSAGE_TYPE_SENT_VOICE = 6;
    private static final int MESSAGE_TYPE_RECV_VOICE = 7;
    private static final int MESSAGE_TYPE_SENT_VIDEO = 8;
    private static final int MESSAGE_TYPE_RECV_VIDEO = 9;
    private static final int MESSAGE_TYPE_SENT_FILE = 10;
    private static final int MESSAGE_TYPE_RECV_FILE = 11;
    private static final int MESSAGE_TYPE_SENT_EXPRESSION = 12;
    private static final int MESSAGE_TYPE_RECV_EXPRESSION = 13;
    public MyChatRowProvider(Context context){

        this.ct = context;
    }

    @Override
    public int getCustomChatRowTypeCount() {
        return 14;
    }

    @Override
    public int getCustomChatRowType(EMMessage message) {
        if (message.getType() == EMMessage.Type.TXT) {
            if(message.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_BIG_EXPRESSION, false)){
                return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_EXPRESSION : MESSAGE_TYPE_SENT_EXPRESSION;
            }
            return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_TXT : MESSAGE_TYPE_SENT_TXT;
        }
        if (message.getType() == EMMessage.Type.IMAGE) {
            return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_IMAGE : MESSAGE_TYPE_SENT_IMAGE;

        }
        if (message.getType() == EMMessage.Type.LOCATION) {
            return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_LOCATION : MESSAGE_TYPE_SENT_LOCATION;
        }
        if (message.getType() == EMMessage.Type.VOICE) {
            return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE : MESSAGE_TYPE_SENT_VOICE;
        }
        if (message.getType() == EMMessage.Type.VIDEO) {
            return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO : MESSAGE_TYPE_SENT_VIDEO;
        }
        if (message.getType() == EMMessage.Type.FILE) {
            return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_FILE : MESSAGE_TYPE_SENT_FILE;
        }
        return -1;// invalid
    }

    @Override
    public EaseChatRow getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {
        EaseChatRow chatRow = null;
        switch (message.getType()) {
            case TXT:
                if(message.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_BIG_EXPRESSION, false)){
                    chatRow = new EaseChatRowBigExpression(ct, message, position, adapter);
                }else{
                    chatRow = new MyChatRowText(ct, message, position, adapter);
                }
                break;
            case LOCATION:
                chatRow = new EaseChatRowLocation(ct, message, position, adapter);
                break;
            case FILE:
                chatRow = new EaseChatRowFile(ct, message, position, adapter);
                break;
            case IMAGE:
                chatRow = new EaseChatRowImage(ct, message, position, adapter);
                break;
            case VOICE:
                chatRow = new EaseChatRowVoice(ct, message, position, adapter);
                break;
            case VIDEO:
                chatRow = new EaseChatRowVideo(ct, message, position, adapter);
                break;
            default:
                break;
        }

        return chatRow;
    }
}
