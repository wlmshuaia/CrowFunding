package com.tide.wechat;

import com.tide.service.ProjectService;
import com.tide.service.impl.ProjectServiceImpl;

/**
 * Created by wengliemiao on 16/3/8.
 */
public class WTest {

    public static void main(String[] args) throws Exception {
//        WxPayRefund wxPayRefund = new WxPayRefund();
//        String outTradeNo = "1457422650261651032";
//        wxPayRefund.setOutTradeNo(outTradeNo);
//        wxPayRefund.setTotalFee("1");
//        wxPayRefund.setRefundFee("1");
//        wxPayRefund.setOutRefundNo(outTradeNo);
//        wxPayRefund.setOpUserId(PropertiesUtils.instance().readValue("MCHID"));
//
//        Map<String, String> resMap = WxPayApi.refund(wxPayRefund);
//        System.out.println(resMap);

        ProjectService projectService = new ProjectServiceImpl();
        Integer projectid = 68;
        // 1.1 发送众筹失败消息通知
        projectService.sendBuyFailMsgToBackers(projectid);

        // 1.2 退还款项给所有支持者
        projectService.refundToBackers(projectid);

    }

    public void test() {
        System.out.println(this.getClass().getResource("/").toString());
        System.out.println(com.tide.controller.front.FWCPay.class.getResource("/"));
    }
}
