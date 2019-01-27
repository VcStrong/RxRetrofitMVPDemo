package com.dingtao.topdemo.bean;

public class Result<T> {

    private String status; // 返回的code
    private T result; // 具体的数据结果
    private String message; // message 可用来返回接口的说明

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
