package com.tide.wechat;

/**
 * 提交退款输入对象
 * Created by wengliemiao on 16/3/8.
 */
public class WxPayRefund extends WxPayDataBase {
//    private String appId;
//    private String mchId;
//    private String deviceInfo;
//    private String nonceStr;
//    private String sign;
//    private String transactionId;
//    private String outTradeNo;
//    private String outRefundNo;
//    private String totalFee;
//    private String refundFee;
//    private String refundFeeType;
//    private String opUserId;

    public String getAppId() {
        return this.values.get("appid");
    }

    public void setAppId(String appId) {
        this.values.put("appid", appId);
    }

    public String getMchId() {
        return this.values.get("mch_id");
    }

    public void setMchId(String mchId) {
        this.values.put("mch_id", mchId);
    }

    public String getDeviceInfo() {
        return this.values.get("device_info");
    }

    public void setDeviceInfo(String deviceInfo) {
        this.values.put("device_info", deviceInfo);
    }

    public String getNoncestr() {
        return this.values.get("nonce_str");
    }

    public void setNoncestr(String nonceStr) {
        this.values.put("nonce_str", nonceStr);
    }

    @Override
    public String getSign() {
        return this.values.get("sign");
    }

    public void setSign(String sign) {
        this.values.put("sign", sign);
    }

    public String getTransactionId() {
        return this.values.get("transaction_id");
    }

    public void setTransactionId(String transactionId) {
        this.values.put("transaction_id", transactionId);
    }

    public boolean isTransactionIdSet() {
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

    public String getOutRefundNo() {
        return this.values.get("out_refund_no");
    }

    public void setOutRefundNo(String outRefundNo) {
        this.values.put("out_refund_no", outRefundNo);
    }

    public boolean isOutRefundNoSet() {
        return this.values.containsKey("out_refund_no");
    }

    public String getTotalFee() {
        return this.values.get("total_fee");
    }

    public void setTotalFee(String totalFee) {
        this.values.put("total_fee", totalFee);
    }

    public boolean isTotalFeeSet() {
        return this.values.containsKey("total_fee");
    }

    public String getRefundFee() {
        return this.values.get("refund_fee");
    }

    public void setRefundFee(String refundFee) {
        this.values.put("refund_fee", refundFee);
    }

    public boolean isRefundFeeSet() {
        return this.values.containsKey("refund_fee");
    }

    public String getRefundFeeType() {
        return this.values.get("mch_id");
    }

    public void setRefundFeeType(String refundFeeType) {
        this.values.put("refund_fee_type", refundFeeType);
    }

    public String getOpUserId() {
        return this.values.get("op_user_id");
    }

    public void setOpUserId(String opUserId) {
        this.values.put("op_user_id", opUserId);
    }

    public boolean isOpUserIdSet() {
        return this.values.containsKey("op_user_id");
    }
}
