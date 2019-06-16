package com.dingtao.common.core;

import com.dingtao.common.core.exception.ApiException;

/**
 * @author dingtao
 * @date 2018/12/30 10:30
 * qq:1940870847
 */
public interface DataCall<T> {

    void success(T data,Object...args);
    void fail(ApiException data, Object...args);

}
