package com.dingtao.common.core.exception;

/**
 * @author dingtao
 * @date 2019/1/2 7:01 PM
 * 封装了自定义的异常，页面拿着我的异常做出友好提示
 */
public class ApiException extends Exception {
    private String code;//
    private String displayMessage;//提示的消息

    public ApiException(String code, String displayMessage) {
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public ApiException(String code, String message, String displayMessage) {
        super(message);
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }
}
