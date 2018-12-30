package com.dingtao.rrmmp.core.http;

import com.dingtao.rrmmp.bean.Result;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author dingtao
 * @date 2018/12/28 10:00
 * qq:1940870847
 */
public interface IRequest {

    @FormUrlEncoded
    @POST("user/v1/register")
    Observable<Result> register(@Field("phone") String m, @Field("pwd") String p);

    @FormUrlEncoded
    @POST("user/v1/login")
    Observable<Result> login(@Field("phone") String m, @Field("pwd") String p);
}
