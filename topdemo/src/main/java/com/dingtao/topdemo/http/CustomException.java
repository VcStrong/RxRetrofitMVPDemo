package com.dingtao.topdemo.http;

import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class CustomException {

    /**
     * 未知错误
     */
    public static final String UNKNOWN = "1000";

    /**
     * 解析错误
     */
    public static final String PARSE_ERROR = "2000";

    /**
     * 网络错误
     */
    public static final String NETWORK_ERROR = "1002";

    /**
     * 协议错误
     */
    public static final String HTTP_ERROR = "1003";

    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //解析错误
            ex = new ApiException(PARSE_ERROR, e.getMessage());
            return ex;
        } else if (e instanceof ConnectException) {
            //网络错误
            ex = new ApiException(NETWORK_ERROR, e.getMessage());
            return ex;
        } else if (e instanceof UnknownHostException || e instanceof SocketTimeoutException) {
            //连接错误
            ex = new ApiException(NETWORK_ERROR, e.getMessage());
            return ex;
        }else if(e instanceof ApiException){
            ex = (ApiException) e;
            return ex;
        } else {
            //未知错误
            ex = new ApiException(UNKNOWN, e.getMessage());
            return ex;
        }
    }
}