package com.wxxiaomi.ming.electricbicycle.bridge.easemob.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.util.EasyUtils;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.bridge.easemob.provider.MyChatRowProvider;

/**
 * chat activity，EaseChatFragment was used {@link #}
 *
 */
public class ChatActivity extends AppCompatActivity{
    public static ChatActivity activityInstance;
    private ChatFragment2 chatFragment;
    String toChatUsername;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_activity_chat);

        activityInstance = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //get user id or group id
        toChatUsername = getIntent().getExtras().getString("userId");
        toolbar.setTitle(toChatUsername);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); // 设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //use EaseChatFratFragment
        chatFragment = new ChatFragment2();
        //pass parameters to chat fragment
        chatFragment.setArguments(getIntent().getExtras());
        chatFragment.setChatFragmentListener(new EaseChatFragment.EaseChatFragmentHelper(){

            @Override
            public void onSetMessageAttributes(EMMessage message) {
                Log.i("wang","onSetMessageAttributes");
            }

            @Override
            public void onEnterToChatDetails() {
                Log.i("wang","onEnterToChatDetails");
            }

            @Override
            public void onAvatarClick(String username) {
                Log.i("wang","onAvatarClick,username:"+username);
            }

            @Override
            public void onAvatarLongClick(String username) {
                Log.i("wang","onAvatarLongClick,username:"+username);
            }

            @Override
            public boolean onMessageBubbleClick(EMMessage message) {
                return false;
            }

            @Override
            public void onMessageBubbleLongClick(EMMessage message) {
                Log.i("wang","onMessageBubbleLongClick");
            }

            @Override
            public boolean onExtendMenuItemClick(int itemId, View view) {
                return false;
            }

            @Override
            public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
                MyChatRowProvider myChatRowProvider = new MyChatRowProvider(ChatActivity.this);
                return myChatRowProvider;
            }
        });

        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
        if(getIntent().getBooleanExtra("isAdd",false)){
            chatFragment.addMsgQueue("我已经成功添加你为好友，让我们开始聊天吧！");
        }


    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
    	// make sure only one chat activity is opened
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }
    
    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
        if (EasyUtils.isSingleActivity(this)) {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
        }
    }
    
    public String getToChatUsername(){
        return toChatUsername;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
//        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_act_chat, menu);//加载menu文件到布局
        return true;
    }
}
