package com.tide.wechat;

/**
 * 微信支付API异常类
 * Created by wengliemiao on 16/3/3.
 */
public class WxPayException extends Exception {

    public WxPayException(String msg) {
        super(msg);
    }

    public String errorMessage() {
        return this.getMessage();
    }

}
