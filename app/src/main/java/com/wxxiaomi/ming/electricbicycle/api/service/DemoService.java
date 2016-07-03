package com.wxxiaomi.ming.electricbicycle.api.service;


import com.wxxiaomi.ming.electricbicycle.bean.format.InitUserInfo;
import com.wxxiaomi.ming.electricbicycle.bean.format.Login;
import com.wxxiaomi.ming.electricbicycle.bean.format.NearByPerson;
import com.wxxiaomi.ming.electricbicycle.bean.format.Register;
import com.wxxiaomi.ming.electricbicycle.bean.format.common.Result;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 12262 on 2016/5/31.
 */
public interface DemoService {
    @GET("ActionServlet?action=login")
    Observable<Result<Login>> readBaidu(@Query("username") String username, @Query("password") String password);

    @GET("ActionServlet?action=inituserinfo")
    Observable<Result<InitUserInfo>> initUserInfo(@Query("username") String username, @Query("password") String password);

    @GET("ActionServlet?action=getuserinfolistbyemname")
    Observable<Result<InitUserInfo>> getUserListByEmList(@Query("emnamelist") String emnamelist);

    @GET("ActionServlet?action=getnearby")
    Observable<Result<NearByPerson>> getNearByFromServer(@Query("userid") int userid, @Query("latitude") double latitude, @Query("longitude") double longitude);

    @GET("ActionServlet?action=getuserinfobyname")
    Observable<Result<InitUserInfo>> getUserCommonInfoByName(@Query("name") String name);

    @GET("ActionServlet?action=register")
    Observable<Result<Register>> registerUser(@Query("username") String username, @Query("password") String password);
}
