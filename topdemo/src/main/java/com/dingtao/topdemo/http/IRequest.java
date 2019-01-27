package com.dingtao.topdemo.http;

import com.dingtao.topdemo.bean.Banner;
import com.dingtao.topdemo.bean.Car;
import com.dingtao.topdemo.bean.Result;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author dingtao
 * @date 2019/1/14 14:31
 * qq:1940870847
 */
public interface IRequest {

    @FormUrlEncoded
    @POST("user/v1/login")
    Observable<Result> login(@Field("phone") String m, @Field("pwd") String p);


    /**
     * banner
     */
    @GET("commodity/v1/bannerShow")
    Observable<Result<List<Banner>>> bannerShow();
}
