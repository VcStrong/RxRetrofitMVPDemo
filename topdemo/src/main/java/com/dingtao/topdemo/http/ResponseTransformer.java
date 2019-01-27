package com.dingtao.topdemo.http;

import com.dingtao.topdemo.bean.Result;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

public class ResponseTransformer {

    public static <T> ObservableTransformer<Result<T>, T> handleResult() {
        return new ObservableTransformer<Result<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<Result<T>> upstream) {
                return upstream
                        .onErrorResumeNext(new ErrorResumeFunction<T>())
                        .flatMap(new ResultFunction<T>());
            }
        };
    }


    /**
     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends Result<T>>> {

        @Override
        public ObservableSource<? extends Result<T>> apply(Throwable throwable) throws Exception {
            return Observable.error(CustomException.handleException(throwable));
        }
    }

    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     *
     * @param <T>
     */
    private static class ResultFunction<T> implements Function<Result<T>, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(Result<T> tResult) throws Exception {
            String code = tResult.getStatus();
            String message = tResult.getMessage();
            if (code.equals("0000")) {
                return Observable.just(tResult.getResult());
            } else {
                return Observable.error(new ApiException(code, message));
            }
        }
    }
}