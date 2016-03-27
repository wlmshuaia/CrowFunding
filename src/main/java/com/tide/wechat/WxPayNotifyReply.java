package com.tide.wechat;

/**
 * 回调基础类
 * Created by wengliemiao on 16/3/3.
 */
public class WxPayNotifyReply extends WxPayDataBase {
//    private String returnCode;  // 错误码
//    private String returnMsg;   // 错误信息

    public void setReturnCode(String returnCode) {
        this.values.put("return_code", returnCode);
    }

    public String getReturnCode() {
        return this.values.get("return_code");
    }

    public String getReturnMsg() {
        return this.values.get("return_msg");
    }

    public void setReturnMsg(String returnMsg) {
        this.values.put("return_msg", returnMsg);
    }

    /**
     * 设置返回参数
     * @param key
     * @param value
     */
    public void setData(String key, String value) {
        this.values.put(key, value);
    }
}
