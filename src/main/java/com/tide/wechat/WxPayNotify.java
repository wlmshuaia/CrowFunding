package com.tide.wechat;

import java.util.Map;

/**
 * 回调基础类
 * Created by wengliemiao on 16/3/3.
 */
public class WxPayNotify extends WxPayNotifyReply {

    public final void handle() {

    }

    /**
     * 查询订单
     * @param transactionId
     * @return
     */
    public static boolean checkOrder(String transactionId) {
        WxPayOrderQuery wpOrderQuery = new WxPayOrderQuery();
        wpOrderQuery.setTransactionId(transactionId);
        Map<String, String> result = WxPayApi.orderQuery(wpOrderQuery);

        if(result.containsKey("return_code") && "SUCCESS".equals(result.get("return_code"))
                && result.containsKey("result_code") && "SUCCESS".equals(result.get("result_code"))) {
            return true;
        }

        return false;
    }
}
