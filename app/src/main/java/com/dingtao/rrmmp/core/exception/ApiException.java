package com.dingtao.rrmmp.core.exception;

/**
 * @author dingtao
 * @date 2019/1/2 7:01 PM
 * 封装了自定义的异常，页面拿着我的异常做出友好提示
 */
public class ApiException extends Exception {
    private int code;//
    private String displayMessage;//提示的消息

    public ApiException(int code, String displayMessage) {
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public ApiException(int code, String message, String displayMessage) {
        super(message);
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }
}
