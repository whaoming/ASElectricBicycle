package com.wxxiaomi.ming.electricbicycle.api.service;


import com.wxxiaomi.ming.electricbicycle.dao.bean.Option;
import com.wxxiaomi.ming.electricbicycle.dao.bean.OptionLogs;
import com.wxxiaomi.ming.electricbicycle.dao.bean.User;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo2;
import com.wxxiaomi.ming.electricbicycle.dao.bean.UserLocatInfo;

import com.wxxiaomi.ming.electricbicycle.dao.common.Result;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
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
    @FormUrlEncoded
    @POST("android/user_updateuserfriends")
    Observable<Result<List<UserCommonInfo2>>> updateUserFriend2(@Field("friends") String friends);

    @FormUrlEncoded
    @POST("android/user_updateuserfriends")
    Observable<Result<List<UserCommonInfo2>>> updateUserFriend3(@Field("friends") String friends);

    @POST("android/user_updateuserfriends")
    Observable<Result<List<UserCommonInfo2>>> updateUserFriend(@Body Map<String,String> friends);

    @POST("android/user_updateuserinfo")
    Observable<Result<String>> updateUserInfo3(@Body UserCommonInfo2 userinfo);
    @FormUrlEncoded
    @POST("android/user_updateuserinfo")
    Observable<Result<String>> updateUserInfo(@FieldMap Map<String, String> options);
    @FormUrlEncoded
    @POST("android/user_updateuserinfo")
    Observable<Result<String>> updateUserInfo2(@Field("name") String name);

    @GET("android/user_longToken")
    Observable<Result<String>> getSToken(@Query("long_token")String long_token,@Query("phoneId")String phoneId);

    @GET("android/user_login")
    Observable<Result<User>> readBaidu(@Query("username") String username, @Query("password") String password,@Query("uniqueNum") String uniqueNum);

    @GET("ActionServlet?action=inituserinfo")
    Observable<Result<List<UserCommonInfo2>>> initUserInfo(@Query("username") String username, @Query("password") String password);

    @GET("android/user_infosbyems")
    Observable<Result<List<UserCommonInfo2>>> getUserListByEmList(@Query("emnamelist") String emnamelist);

    @GET("android/lbs_near")
    Observable<Result<List<UserLocatInfo>>> getNearByFromServer(@Query("userid") int userid, @Query("latitude") double latitude, @Query("longitude") double longitude);

    @GET("android/user_userinfobyname")
    Observable<Result<List<UserCommonInfo2>>> getUserCommonInfoByName(@Query("name") String name);

//    @GET("ActionServlet?action=register")
//    @GET("android/user_register")
//    Observable<Result<Register>> registerUser(@Query("username") String username, @Query("password") String password);

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

//    Observable<Response<>>
}
