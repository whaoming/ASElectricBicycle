package com.wxxiaomi.ming.electricbicycle.dao.impl;

import android.util.Log;

import com.google.android.gms.common.api.Api;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.wxxiaomi.ming.electricbicycle.api.exception.ApiException;
import com.wxxiaomi.ming.electricbicycle.api.exception.ERROR;
import com.wxxiaomi.ming.electricbicycle.model.impl.EmEvent;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by 12262 on 2016/6/6.
 */
public class EmDaoImpl {
    private EmDaoImpl() {
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final EmDaoImpl INSTANCE = new EmDaoImpl();
    }

    //获取单例
    public static EmDaoImpl getInstance(){
        return SingletonHolder.INSTANCE;
    }


    public Observable<Boolean> LoginFromEm(final String username, final String password) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber) {
                EMClient.getInstance().login(username, password,
                        new EMCallBack() {
                            @Override
                            public void onSuccess() {
                                // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
                                EMClient.getInstance().groupManager().loadAllGroups();
                                EMClient.getInstance().chatManager()
                                        .loadAllConversations();
                                Log.i("wang", "登陆em服务器成功");
                                EMClient.getInstance().updateCurrentUserNick(
                                        username);
                                subscriber.onNext(true);
                            }

                            @Override
                            public void onProgress(int progress, String status) {
                                // Log.d(TAG, "login: onProgress");
                            }

                            @Override
                            public void onError(final int code, final String message) {
                                Log.i("wang", "login: onError: " + code + "---message="
                                        + message);
//                                closeLoading1Dialog();
//                                showErrorDialog("登陆聊天服务器失败，请尝试重新登陆");
                                ApiException apiException = new ApiException(null, code);

                                apiException.setDisplayMessage(message);
                                subscriber.onError(apiException);
                            }
                        });
            }
        });
    }

    public void addEmContactObserver2(){
//        EMClient.getInstance().contactManager()
//                .setContactListener(new EMContactListener() {
//                    @Override
//                    public void onContactAgreed(final String username) {
//                        subscriber.onNext(new EmEvent<String>(EmEvent.ONCONNTACTAGREE,username,""));
//                    }
//
//                    @Override
//                    public void onContactRefused(String username) {
//                        subscriber.onNext(new EmEvent<String>(EmEvent.ONCONTACTREFUSED,username,""));
//                    }
//
//                    @Override
//                    public void onContactInvited(final String username,
//                                                 final String reason) {
//                        Log.i("wang","EMClient.getInstance().contactManager()->onContactInvited");
//                        subscriber.onNext(new EmEvent<String>(EmEvent.ONCONTACTINVITED,username,reason));
//                    }
//
//                    @Override
//                    public void onContactDeleted(String username) {
//                        subscriber.onNext(new EmEvent<String>(EmEvent.ONCONTACTDELETED,username,""));
//                    }
//
//                    @Override
//                    public void onContactAdded(String username) {
//                        subscriber.onNext(new EmEvent<String>(EmEvent.ONCONTACTADDED,username,""));
//                    }
//                });
    }



    /**
     * 添加监听，不做任何处理
     * @return
     */
    public Observable<EmEvent<String>> addEmContactObserver(){
        return Observable.create(new Observable.OnSubscribe<EmEvent<String>>() {
            @Override
            public void call(final Subscriber<? super EmEvent<String>> subscriber) {
                EMClient.getInstance().contactManager()
                        .setContactListener(new EMContactListener() {
                            @Override
                            public void onContactAgreed(final String username) {
                                subscriber.onNext(new EmEvent<String>(EmEvent.ONCONNTACTAGREE,username,""));
                            }

                            @Override
                            public void onContactRefused(String username) {
                                subscriber.onNext(new EmEvent<String>(EmEvent.ONCONTACTREFUSED,username,""));
                            }

                            @Override
                            public void onContactInvited( String username,
                                                          String reason) {
                                Log.i("wang","EMClient.getInstance().contactManager()->onContactInvited");
                                subscriber.onNext(new EmEvent<String>(EmEvent.ONCONTACTINVITED,username,reason));
                            }

                            @Override
                            public void onContactDeleted(String username) {
                                subscriber.onNext(new EmEvent<String>(EmEvent.ONCONTACTDELETED,username,""));
                            }

                            @Override
                            public void onContactAdded(String username) {
                                subscriber.onNext(new EmEvent<String>(EmEvent.ONCONTACTADDED,username,""));
                            }
                        });

            }
        })
                ;
    }
    public interface demoLis<T>{
        void onNext(EmEvent<T> event);
    }
    private demoLis lis;
    public void setLis(demoLis lis){
        this.lis = lis;
    }

    public void init(){
        EMClient.getInstance().chatManager()
                .addMessageListener(new EMMessageListener() {
                    @Override
                    public void onMessageReceived(List<EMMessage> list) {
                        Log.i("wang","EmDaoImpl底层收到新消息");
                        lis.onNext(new EmEvent<List<EMMessage>>(EmEvent.onMessageReceived,"",list));
                    }

                    @Override
                    public void onCmdMessageReceived(List<EMMessage> list) {
                        lis.onNext(new EmEvent<List<EMMessage>>(EmEvent.onCmdMessageReceived,"",list));
                    }

                    @Override
                    public void onMessageReadAckReceived(List<EMMessage> list) {
                        lis.onNext(new EmEvent<List<EMMessage>>(EmEvent.onMessageReadAckReceived,"",list));
                    }

                    @Override
                    public void onMessageDeliveryAckReceived(List<EMMessage> list) {
                        lis.onNext(new EmEvent<List<EMMessage>>(EmEvent.onMessageDeliveryAckReceived,"",list));
                    }

                    @Override
                    public void onMessageChanged(EMMessage emMessage, Object o) {
                        lis.onNext(new EmEvent<List<EMMessage>>(EmEvent.onMessageChanged,"",null));
                    }
                });
    }


    /**
     * 添加消息监听
     * @return
     */
    public Observable<EmEvent<List<EMMessage>>> addMessageListener(){
        return Observable.create(new Observable.OnSubscribe<EmEvent<List<EMMessage>>>() {
            @Override
            public void call(final Subscriber<? super EmEvent<List<EMMessage>>> subscriber) {
                EMClient.getInstance().chatManager()
                        .addMessageListener(new EMMessageListener() {
                            @Override
                            public void onMessageReceived(List<EMMessage> list) {
                                Log.i("wang","EmDaoImpl底层收到新消息");
                                lis.onNext(new EmEvent<List<EMMessage>>(EmEvent.onMessageReceived,"",list));
                            }

                            @Override
                            public void onCmdMessageReceived(List<EMMessage> list) {
                                lis.onNext(new EmEvent<List<EMMessage>>(EmEvent.onCmdMessageReceived,"",list));
                            }

                            @Override
                            public void onMessageReadAckReceived(List<EMMessage> list) {
                                lis.onNext(new EmEvent<List<EMMessage>>(EmEvent.onMessageReadAckReceived,"",list));
                            }

                            @Override
                            public void onMessageDeliveryAckReceived(List<EMMessage> list) {
                                lis.onNext(new EmEvent<List<EMMessage>>(EmEvent.onMessageDeliveryAckReceived,"",list));
                            }

                            @Override
                            public void onMessageChanged(EMMessage emMessage, Object o) {
                                lis.onNext(new EmEvent<List<EMMessage>>(EmEvent.onMessageChanged,"",null));
                            }
                        });
            }
        });
    }

    /**
     * 从em服务器拉取好友列表
     * @return
     */
    public Observable<List<String>> getContactFromEm() {
        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                try {
                    List<String> allContactsFromServer = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    Log.i("wang", "从em服务器获取的好友个数:"+allContactsFromServer.size());
                    subscriber.onNext(allContactsFromServer);
                } catch (Exception e) {
                    ApiException apiEx = new ApiException(e, ERROR.EM_GETCONTACT_ERROR);
                    apiEx.setDisplayMessage("获取好友失败");
                    subscriber.onError(apiEx);
                }
            }
        });
    }

    public Observable<Boolean> agreeInvite(final String emname){
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try{
                    EMClient.getInstance().contactManager().acceptInvitation(emname);
                }catch (Exception e){
                    ApiException apiException = new ApiException(e, ERROR.EM_AGREEINVITE_ERROR);
                    apiException.setDisplayMessage("同意好友申请失败");
                    subscriber.onError(apiException);
                }
            }
        });
    }



}
