package com.dingtao.rrmmp.http;

import com.dingtao.rrmmp.bean.Result;

import java.io.File;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @author dingtao
 * @date 2018/12/28 10:00
 * qq:1940870847
 */
public interface IRequest {

    @FormUrlEncoded
    @POST("user/reg")
    Observable<Result> register(@Field("mobile") String m, @Field("password") String p);

}
