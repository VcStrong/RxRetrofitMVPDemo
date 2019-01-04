package com.dingtao.rrmmp.core.http;

import com.dingtao.rrmmp.bean.Banner;
import com.dingtao.rrmmp.bean.Circle;
import com.dingtao.rrmmp.bean.Result;
import com.dingtao.rrmmp.bean.UserInfo;
import com.dingtao.rrmmp.bean.shop.HomeList;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

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
    Observable<Result<UserInfo>> login(@Field("phone") String m, @Field("pwd") String p);

    /**
     * banner
     */
    @GET("commodity/v1/bannerShow")
    Observable<Result<List<Banner>>> bannerShow();

    /**
     * 首页商品列表
     */
    @GET("commodity/v1/commodityList")
    Observable<Result<HomeList>> commodityList();

    /**
     * 圈子
     */
    @GET("circle/v1/findCircleList")
    Observable<Result<List<Circle>>> findCircleList(
            @Header("userId") long userId,
            @Header("sessionId")String sessionId,
            @Query("page")int page,
            @Query("count")int count);

    /**
     * 我的足迹
     */
    @GET("commodity/verify/v1/browseList")
    Observable<Result<List<Banner>>> browseList(
            @Header("userId") String userId,
            @Header("sessionId")String sessionId,
            @Query("page")int page,
            @Query("count")int count);

    /**
     * 同步购物车数据
     */
    @PUT("order/verify/v1/syncShoppingCart")
    Observable<Result> syncShoppingCart(
            @Header("userId") String userId,
            @Header("sessionId")String sessionId,
            @Body String data);
}
