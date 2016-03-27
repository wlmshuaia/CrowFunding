package com.tide.wechat;

/**
 * 订单查询输入对象
 * Created by wengliemiao on 16/3/3.
 */
public class WxPayOrderQuery extends WxPayDataBase {

//    private String appId;
//    private String mchId;
//    private String transactionId;
//    private String outTradeNo;
//    private String noncestr;

    public String getAppId() {
        return this.values.get("appid");
    }

    public void setAppId(String appId) {
        this.values.put("appid", appId);
    }

    public boolean isAppidSet() {
        return this.values.containsKey("appid");
    }

    public String getMchId() {
        return this.values.get("mch_id");
    }

    public void setMchId(String mchId) {
        this.values.put("mch_id", mchId);
    }

    public boolean isMchidSet() {
        return this.values.containsKey("mch_id");
    }

    public String getTransactionId() {
        return this.values.get("transaction_id");
    }

    public void setTransactionId(String transactionId) {
        this.values.put("transaction_id", transactionId);
    }

    public boolean isTransactionidSet() {
        return this.values.containsKey("transaction_id");
    }

    public String getOutTradeNo() {
        return this.values.get("out_trade_no");
    }

    public void setOutTradeNo(String outTradeNo) {
        this.values.put("out_trade_no", outTradeNo);
    }

    public boolean isOutTradeNoSet() {
        return this.values.containsKey("out_trade_no");
    }

    public String getNoncestr() {
        return this.values.get("nonce_str");
    }

    public void setNoncestr(String nonceStr) {
        this.values.put("nonce_str", nonceStr);
    }

    public boolean isNoncestrSet() {
        return this.values.containsKey("nonce_str");
    }

}
