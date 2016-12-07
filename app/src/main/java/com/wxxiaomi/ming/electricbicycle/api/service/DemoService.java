package com.wxxiaomi.ming.electricbicycle.api.service;


import com.wxxiaomi.ming.electricbicycle.dao.bean.Option;
import com.wxxiaomi.ming.electricbicycle.dao.bean.OptionLogs;
import com.wxxiaomi.ming.electricbicycle.dao.bean.format.InitUserInfo;
import com.wxxiaomi.ming.electricbicycle.dao.bean.format.Login;
import com.wxxiaomi.ming.electricbicycle.dao.bean.format.NearByPerson;
import com.wxxiaomi.ming.electricbicycle.dao.bean.format.Register;
import com.wxxiaomi.ming.electricbicycle.dao.bean.format.common.Result;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by 12262 on 2016/5/31.
 */
public interface DemoService {
//    @GET("ActionServlet?action=login")

    @GET("android/user_login")
    Observable<Result<Login>> readBaidu(@Query("username") String username, @Query("password") String password);

    @GET("ActionServlet?action=inituserinfo")
    Observable<Result<InitUserInfo>> initUserInfo(@Query("username") String username, @Query("password") String password);

//    @GET("ActionServlet?action=getuserinfolistbyemname")
    @GET("android/user_infosbyems")
    Observable<Result<InitUserInfo>> getUserListByEmList(@Query("emnamelist") String emnamelist);

//    @GET("ActionServlet?action=getnearby")

    @GET("android/lbs_near")
    Observable<Result<NearByPerson>> getNearByFromServer(@Query("userid") int userid, @Query("latitude") double latitude, @Query("longitude") double longitude);

//    @GET("ActionServlet?action=userinfobyname")
    @GET("android/user_userinfobyname")
    Observable<Result<InitUserInfo>> getUserCommonInfoByName(@Query("name") String name);

//    @GET("ActionServlet?action=register")
    @GET("android/user_register")
    Observable<Result<Register>> registerUser(@Query("username") String username, @Query("password") String password);

    @GET("android/user_optionlog")
    Observable<Result<List<OptionLogs>>> optionLogs(@Query("userid") int userid);

    /**
     *
     * 上传一张图片
     * @param imgs
     * @return
     */
    @Multipart
    @POST("android/up_head")
    Observable<Result<String>> uploadImage(@Query("userid") String userid,
                             @Part("file\"; fileName=\"demo.jpg\"") RequestBody imgs);

    @GET("android/topic_list")
    Observable<String> listTopic(@Query("start") int start);

    @GET("android/user_optionlog")
    Observable<Result<List<Option>>> listOption(@Query("userid") int userid);



    @GET
    Observable<String> sendGet(@Url String url, @QueryMap Map<String,String> options);

    @FormUrlEncoded
    @POST
    Observable<String> sendPost(@Url String url, @FieldMap Map<String, String> options);
}
