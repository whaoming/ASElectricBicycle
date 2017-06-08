package com.wxxiaomi.ming.electricbicycle.net;

import android.util.Log;

import com.wxxiaomi.ming.common.net.RetrofitHelper;
import com.wxxiaomi.ming.common.util.TDevice;
import com.wxxiaomi.ming.electricbicycle.ConstantValue;
import com.wxxiaomi.ming.electricbicycle.EBApplication;
import com.wxxiaomi.ming.electricbicycle.db.bean.format.LoginResponseBean;
import com.wxxiaomi.ming.electricbicycle.net.interceptor.ErrorInterceptor;
import com.wxxiaomi.ming.electricbicycle.net.interceptor.ResultParseInterceptor;
import com.wxxiaomi.ming.electricbicycle.net.interceptor.TokenGetInterceptor;
import com.wxxiaomi.ming.electricbicycle.net.provider.TokenProvider;
import com.wxxiaomi.ming.electricbicycle.net.service.ApiService;
import com.wxxiaomi.ming.electricbicycle.manager.update.Version;
import com.wxxiaomi.ming.electricbicycle.manager.Account;
import com.wxxiaomi.ming.electricbicycle.db.bean.Option;
import com.wxxiaomi.ming.electricbicycle.db.bean.User;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserLocatInfo;

import com.wxxiaomi.ming.electricbicycle.db.bean.format.FootPrintGet;
import com.wxxiaomi.ming.electricbicycle.db.bean.format.UserInfo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.RequestBody;
import rx.Observable;
import rx.functions.Func1;

/**
* @author whaoming
* github：https://github.com/whaoming
* created at 2017/2/14 15:59
* Description:
*/
public class HttpMethods {
    private ApiService demoService;

    //构造方法私有
    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        Map<String,String> headers = new HashMap<>();
        List<Interceptor> is = new ArrayList<>();
        is.add(new TokenGetInterceptor(headers));
        RetrofitHelper.getInstance().init(ConstantValue.SERVER_URL,is);
        demoService = RetrofitHelper.getInstance().createService(ApiService.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }



    public Observable<Version> checkUpdate(){
        return demoService.checkUpdate();
    }

    public Observable<String> publishFootPrint(String content,String picture,String locat_tag,double lat,double lng){
//        return demoService.publishFootPrint(content, picture, locat_tag, lat, lng)
//                .flatMap(new Direct<String>(new TokenProviderImpl()));
        return  Direct2.create( demoService.publishFootPrint(content, picture, locat_tag, lat, lng),new TokenProviderImpl());
    }

    public Observable<FootPrintGet> getUserFootPrint(int userid){
//        return demoService.listUserFootPrint(userid)
//                .flatMap(new Direct<FootPrintGet>(new TokenProviderImpl()));
        return  Direct2.create( demoService.listUserFootPrint(userid),new TokenProviderImpl());
    }

    public Observable<String> upLoadUserCover(String path){
        String params = "coverPath="+path;
//        return demoService.upLoadUserCover(params)
//                .flatMap(new Direct<String>(new TokenProviderImpl()));
        return  Direct2.create(demoService.upLoadUserCover(params),new TokenProviderImpl());
    }

    public Observable<UserInfo> getUserInfoAndOption(int userid){
//        return demoService.getUserInfoAndOption(userid)
//                .flatMap(new Direct<UserInfo>(new TokenProviderImpl()));
        return  Direct2.create(demoService.getUserInfoAndOption(userid),new TokenProviderImpl());
    }


    public Observable<UserCommonInfo> getUserInfoById(int userid){
//        return demoService.getUserInfoById(userid)
//                .flatMap(new Direct<UserCommonInfo>(new TokenProviderImpl()));
        return  Direct2.create(demoService.getUserInfoById(userid),new TokenProviderImpl());
    }

    public Observable<String> updateUserInfo(Map<String,String> pars){
        String params = "";
        for(Map.Entry<String,String> item : pars.entrySet()){
            params+= item.getKey()+"="+item.getValue()+"&";
        }

//        return demoService.updateUserInfo(params)
//                .flatMap(new Direct<String>(new TokenProviderImpl()));
        return  Direct2.create(demoService.updateUserInfo(params),new TokenProviderImpl());
    }


    public Observable<LoginResponseBean> login(String username, String password,String num) {
        String params = "username="+username+"&password="+password+"&uniqueNum="+num;
//        return demoService.readBaidu(params)
////                .map((new ResultParseInterceptor<User>()))
////        .onErrorResumeNext(new ErrorInterceptor<User>());
//                .flatMap(new Direct<User>(new TokenProviderImpl()));
        return  Direct2.create(demoService.readBaidu(params),new TokenProviderImpl());
//                .map(new Func1<LoginResponseBean, User>() {
//                    @Override
//                    public User call(LoginResponseBean loginResponseBean) {
//                        Log.i("wang","loginResponseBean:"+loginResponseBean);
//                        return loginResponseBean.user;
//                    }
//                });
    }

    public Observable<List<UserCommonInfo>> getUserListByEmList(List<String> emnamelist) {
        String temp = "";
        for (String e : emnamelist) {
            temp += e + "<>";
        }
        return  Direct2.create( demoService.getUserListByEmList(temp),new TokenProviderImpl());
    }

    public Observable<List<UserCommonInfo>> getUserCommonInfo2ByEmname(String emname) {
        emname = emname + "<>";
       return  Direct2.create(demoService.getUserListByEmList(emname),new TokenProviderImpl());
    }

    public Observable<List<UserLocatInfo>> getNearByFromServer(int userid, double latitude, double longitude) {
        return Direct2.create(demoService.getNearByFromServer(latitude, longitude),new TokenProviderImpl());
    }

    public Observable<List<UserCommonInfo>> getUserCommonInfo2ByName(String name) {
        return Direct2.create(demoService.getUserCommonInfoByName(name),new TokenProviderImpl());

    }

    public Observable<User> registerUser(String username, String password,String num) {
//        Log.i("wang","HttpMethods->registerUser");
        String params = "username="+username+"&password="+password+"&uniqueNum="+num;
//        return demoService.registerUser(params)
//                .flatMap(new Direct<User>(new TokenProviderImpl()));
        return Direct2.create(demoService.registerUser(params),new TokenProviderImpl());
    }

    public Observable<String> upLoadHead(String fileName, RequestBody imgs){
//        return demoService.uploadImage(fileName,imgs)
//                .flatMap(new Direct<String>(new TokenProviderImpl()));
        return Direct2.create(demoService.uploadImage(fileName,imgs),new TokenProviderImpl());
    }


    public Observable<List<Option>> getOption(int userid){
//        return demoService.listOption(userid)
//                .flatMap(new Direct<List<Option>>(new TokenProviderImpl()));
        return Direct2.create(demoService.listOption(userid),new TokenProviderImpl());

    }

    /**
     * 向服务器发送长token，获取短token
     * 还要发送设备唯一id
     * @return
     */
//    public Observable<String> Token_Long2Short(){
////        String uniqueID = TDevice.getUniqueID(EBApplication.applicationContext);
////        String long_token = AccountHelper.getLongCookie();
////        return demoService.getSToken(long_token,uniqueID)
////                .flatMap(new Direct<String>(Token_Long2Short()));
//        return null;
//    }




    public Observable<List<UserCommonInfo>> updateuserFriend2(String friends){
//        return demoService.updateUserFriend3(friends)
//                .flatMap(new Direct<List<UserCommonInfo>>(new TokenProviderImpl()));
        return Direct2.create(demoService.updateUserFriend3(friends),new TokenProviderImpl());
    }


    /**
     * 当token过期时的处理操作
     */
    public class TokenProviderImpl implements TokenProvider{
        @Override
        public Observable<String> getToken() {
            String uniqueID = TDevice.getUniqueID(EBApplication.applicationContext);
        String long_token = Account.getLongCookie();
        return demoService.getSToken(long_token,uniqueID)
                .map(new ResultParseInterceptor<String>())
                .onErrorResumeNext(new ErrorInterceptor<String>());
        }
    }
}
