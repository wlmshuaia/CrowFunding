package com.tide.wechat;

/**
 * Created by wengliemiao on 16/2/29.
 */
public class JsApiPay extends WxPayDataBase {
//    private String timeStamp;// 时间戳
//    private String package;  // 订单详情扩展字符串: prepay_id=123456789
//    private String signType; // 签名算法，暂支持MD5
//    private String paySign;  // 签名方式
//    private String nonceStr;

    public void setNonceStr(String nonceStr) {
        this.values.put("nonceStr", nonceStr);
    }

    public void setPackage(String pack) {
        this.values.put("package", pack);
    }

    public void setAppId(String appId) {
        this.values.put("appId", appId);
    }

    public void setTimeStamp(String timeStamp) {
        this.values.put("timeStamp", timeStamp);
    }

    public void setSignType(String signType) {
        this.values.put("signType", signType);
    }

    public void setPaySign(String paySign) {
        this.values.put("paySign", paySign);
    }
}
