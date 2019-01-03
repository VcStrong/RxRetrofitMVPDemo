package com.dingtao.rrmmp.core.exception;

import com.dingtao.rrmmp.bean.Result;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;
import retrofit2.Response;

/**
 * @author dingtao
 * @date 2019/1/2 7:11 PM
 * 封装了Retrofit+rxjava代码报错封装
 */
public class ResponseTransformer {

    public static <T> ObservableTransformer<T, T> handleResult() {

        return  new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {

                return upstream
                        .onErrorResumeNext(new ErrorResumeFunction<T>())
                        .flatMap(new ResponseFunction<T>());
            }
        };
    }


    /**
     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(Throwable throwable) throws Exception {
            return Observable.error(throwable);
        }
    }

    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     *
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<T, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(T tResponse) throws Exception {

                return Observable.just(tResponse);

        }
    }
}