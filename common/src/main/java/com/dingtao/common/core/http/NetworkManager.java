package com.dingtao.common.core.http;

import com.dingtao.common.bean.UserInfo;
import com.dingtao.common.core.WDApplication;
import com.dingtao.common.core.WDPresenter;
import com.dingtao.common.core.db.DaoMaster;
import com.dingtao.common.core.db.UserInfoDao;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author dingtao
 * @date 2018/12/28 10:07
 * qq:1940870847
 */
public class NetworkManager {

    private static Gson gson = new Gson();
    private static NetworkManager instance;

    //这个是模仿应用多模块采用不同的域名
    private Retrofit app_retrofit,baidu_retrofit;

    private NetworkManager(){
        init();
    }

    public static NetworkManager instance() {
        if (instance == null){
            instance = new NetworkManager();
        }
        return instance;
    }

    private void init(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//打印请求参数，请求结果

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        UserInfoDao userInfoDao = DaoMaster.newDevSession(WDApplication.getContext(),UserInfoDao.TABLENAME).getUserInfoDao();
//                        List<UserInfo> userInfos = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list();
//                        UserInfo userInfo = userInfos.get(0);//读取第一项
//                        Request request = chain.request().newBuilder()
//                                .addHeader("userId",userInfo.getUserId()+"")
//                                .addHeader("sessionId",userInfo.getSessionId())
//                                .build();
//                        return chain.proceed(request);
//                    }
//                })
                .connectTimeout(5,TimeUnit.SECONDS)
                .writeTimeout(5,TimeUnit.SECONDS)
                .readTimeout(5,TimeUnit.SECONDS)
                .build();

        app_retrofit = new Retrofit.Builder()
                .client(okHttpClient)
//                .baseUrl("http://169.254.101.220:8080/")//base_url:http+域名
//                .baseUrl("http://172.17.8.100/small/")//base_url:http+域名
                .baseUrl("http://mobile.bwstudent.com/small/")//base_url:http+域名
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用Rxjava对回调数据进行处理
                .addConverterFactory(GsonConverterFactory.create())//响应结果的解析器，包含gson，xml，protobuf
                .build();

        baidu_retrofit = new Retrofit.Builder()
                .client(okHttpClient)
//                .baseUrl("http://169.254.101.220:8080/")//base_url:http+域名
//                .baseUrl("http://172.17.8.100/small/")//base_url:http+域名
                .baseUrl("http://mobile.bwstudent.com/small/")//base_url:http+域名
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用Rxjava对回调数据进行处理
                .addConverterFactory(GsonConverterFactory.create())//响应结果的解析器，包含gson，xml，protobuf
                .build();
    }

    public <T> T create(int requestType ,final Class<T> service){
        if (requestType == WDPresenter.REQUEST_TYPE_SDK_BD){//如果请求百度SDK的接口
            return baidu_retrofit.create(service);
        }
        return app_retrofit.create(service);
    }

    /**
     * 把传递数据转为json格式，返回RequestBody
     *
     * @param keys   请求参数
     * @param values 数据
     * @return
     */
    public static RequestBody convertJsonBody(String[] keys, Object[] values) {
        JSONObject json = new JSONObject();

        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            Object value = values[i];
            try {//减小数据中某一项对于所有数据的影响
                json.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json.toString());
        return  body;
    }

    /**
     * 把传递数据转为json格式，返回RequestBody
     *
     * @param obj 数据
     * @return
     */
    public static RequestBody convertJsonBody(Object obj) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), gson.toJson(obj));
        return  body;
    }

}
