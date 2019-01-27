package com.dingtao.topdemo.presenter;

import com.dingtao.topdemo.http.ApiException;

/**
 * @author dingtao
 * @date 2019/1/14 15:28
 * qq:1940870847
 */
public interface DataCall<T> {
    void success(T data);
    void fail(ApiException e);
}
