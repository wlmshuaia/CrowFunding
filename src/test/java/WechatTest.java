import com.fasterxml.jackson.databind.ObjectMapper;
import com.tide.bean.User;
import com.tide.service.ProjectService;
import com.tide.service.UserService;
import com.tide.utils.DataUtils;
import com.tide.utils.DateUtils;
import com.tide.utils.FileUtils;
import com.tide.utils.PropertiesUtils;
import com.tide.wechat.WxPayApi;
import com.tide.wechat.WxPayDataBase;
import com.tide.wechat.WxPayNotify;
import com.tide.wechat.WxPayOrderQuery;
import com.tide.wechat.token.TokenProxy;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wengliemiao on 16/1/15.
 */
public class WechatTest extends BaseSpring {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Test
    public void getUserInfoTest() {
//        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx764d16c3043fedcb&redirect_uri=http://zmkj.arriveguide.com/oauth2.jsp&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
//        0414ee0a157905032ba09f34633bd0e0
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx764d16c3043fedcb&secret=2f531514127d116333540269880233f8&code=011c3ddccfcb47ffc5d5367198e8a93k&grant_type=authorization_code";
//        String res = HttpUtils.getResponseHtml(url);
//        System.out.println(res);
//
//        String res = HttpUtils.getResponseJson(url);
//        System.out.println(res);

        String sJson = "{\"access_token\":\"OezXcEiiBSKSxW0eoylIeP-tAo3n2SqFgcQrZilgmSPI6PWSrYS0LubVsy3EGiLul7ED0jsK9hMlH0RWzgK8R2bz8rcq0mCs6wHZLHXMkdk4tTimgjnP3k2LNk51elsaNI2FNchPCQYOLz9k8hKDGA\",\"expires_in\":7200,\"refresh_token\":\"OezXcEiiBSKSxW0eoylIeP-tAo3n2SqFgcQrZilgmSPI6PWSrYS0LubVsy3EGiLuuo3Q200Ca7PlPrm40WVTWnXxXqdHUhX-b93fQR15CFQ9wMhWsvmm3O-zECs6UfWW7QKGs1oLr-zqrC1aZif0UQ\",\"openid\":\"oPj28vitkJlUH4t2OBqRv1b6xPns\",\"scope\":\"snsapi_userinfo\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = null;
        try {
            map = objectMapper.readValue(sJson, Map.class);
            System.out.println(map.get("access_token"));
            System.out.println(map.get("refresh_token"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sha1Test() {
        PropertiesUtils propUtils = new PropertiesUtils();
        Map<String, String> data = new TreeMap<>();
        data.put("appid", "wx950c13ed59c4faab");
        data.put("url", "http://www.zm-college.com/Pay/getOpenid.do?code=0113d682d1bbe52a7738a367ee952f1N&state=STATE");
        data.put("timestamp", "1456912314338");
        data.put("noncestr", "123456");
        data.put("accesstoken", "OezXcEiiBSKSxW0eoylIeANIw3RXO5Mj1k9B6m2FJRTlUIhPSV625573F7Jp1CU0iS_VvMOPcDdRQ8YCYlPJTNei_RcqBqzPXTp-G8tfn1uDxL4RZzD8OeekEDN-CbJV81QLUFhEtLrdF3LYDiezOg");

        WxPayDataBase wdTools = new WxPayDataBase();
//        String sParams = wdTools.ToUrlParams(data);
//        sParams = "accesstoken=OezXcEiiBSKSxW0eoylIeANIw3RXO5Mj1k9B6m2FJRTlUIhPSV625573F7Jp1CU0iS_VvMOPcDdRQ8YCYlPJTNei_RcqBqzPXTp-G8tfn1uDxL4RZzD8OeekEDN-CbJV81QLUFhEtLrdF3LYDiezOg&appid=wx950c13ed59c4faab&noncestr=123456&timestamp=1456912314338&url=http://open.weixin.qq.com/?code=CODE&state=STATE";
        String sParams = "accesstoken=OezXcEiiBSKSxW0eoylIeBFk1b8VbNtfWALJ5g6aMgZHaqZwK4euEskSn78Qd5pLsfQtuMdgmhajVM5QDm24W8X3tJ18kz5mhmkUcI3RoLm7qGgh1cEnCHejWQo8s5L3VvsFAdawhFxUuLmgh5FRA&appid=wx17ef1eaef46752cb&noncestr=123456&timestamp=1384841012&url=http://open.weixin.qq.com/?code=CODE&state=STATE";

        System.out.println(sParams);

        // 使用 SHA-1 签名算法
        String addrSign = WxPayDataBase.SHA1(sParams);

        System.out.println(addrSign);

    }

    @Test
    public void toUrlParam() {
        PropertiesUtils propUtils = new PropertiesUtils();
        Map<String, String> data = new TreeMap<>();
        data.put("appid", propUtils.readValue("APPID"));
//        String url = request.getScheme() + "://" + request.getServerName() + "/front/Order/index.do";
        // 拼接 url 字符串
        String sUrlParams = "code=001e854c80021a8ff811a55262fd5c60&state=STATE";
        data.put("url", "http://www.zm-college.com/Pay/getOpenid.do?" + sUrlParams);

        data.put("timestamp", String.valueOf(new Date().getTime()));
        data.put("noncestr", "123456");
        String accessToken = "OezXcEiiBSKSxW0eoylIeANIw3RXO5Mj1k9B6m2FJRTlUIhPSV625573F7Jp1CU0pxxkqEaw0u5Sd0WEvG8BFpfI_HaKhxvKTJl3osjf9koB0qfBowf5eflWiXl-MbkZme1fqLCVmRtu96ffzxarOg";
        data.put("accesstoken", accessToken);

        WxPayDataBase wdTools = new WxPayDataBase();
        String sParams = wdTools.ToUrlParams(data);

        System.out.println(sParams);

        System.out.println(WxPayDataBase.SHA1(sParams));

    }

    @Test
    public void accessTokenTest() {
        String token = TokenProxy.getAccessToken();
        System.out.println(token);
    }

    @Test
    public void ticketTest() {
        String ticket = TokenProxy.getJsApiTicket();
        System.out.println(ticket);
    }

    @Test
    public void checkOrder() {
        WxPayOrderQuery wpOrderQuery = new WxPayOrderQuery();
        wpOrderQuery.setTransactionId("1008090402201603043728232984");
        Map<String, String> map = WxPayApi.orderQuery(wpOrderQuery);

        System.out.println(map.get("return_code"));
        System.out.println(map.get("return_msg"));
        System.out.println(map.get("openid"));
        System.out.println(map.get("trade_type"));
        System.out.println(map.get("trade_state"));
        System.out.println(map.get("bank_type"));
        System.out.println(map.get("total_fee"));

        System.out.println(WxPayNotify.checkOrder("1008090402201603043728232984"));
    }

    @Test
    public void sendMsg() {
        String openid = "oKVZ5wN_WyvG1DxfvIXaQwLaMPMw";
        String url = "http://www.zm-college.com/front/Project/projectInfo.do?id=155";
        String product = "支付订单号为 20160305114159783832";
        String price =  "54元";

//        String res = WxPayApi.sendPaySuccessMsg(openid, url, product, price);
//        String res = WxPayApi.sendBuySuccessMsg(openid, url, "订单号:123");
//        String res = WxPayApi.sendBuyFailMsg(openid, url, "订单号:123");
//        String res = WxPayApi.sendRefundMsg(openid, url, "项目目标未达成", "23");
//        String res = WxPayApi.sendProductDeliverMsg(openid, url, "圆通速递", "12312312312321");

//        String res = WxPayApi.sendProjectStartMsg(openid, url, "发布项目测试", "40 件", "2016-03-22 15:48:00");
        String res = WxPayApi.sendExamineFailMsg(openid, url, "发布项目测试", "不符合发布作品条例", "2016-03-07 16:24:00");
        System.out.println("Res: " + res);
    }

    @Test
    public void formatXml() throws Exception {
        String xml = "<xml><sign><![CDATA[E9E1A7B4CFF80286EC557B1607407948]]></sign><mch_id><![CDATA[1310974601]]></mch_id><refund_fee><![CDATA[1]]></refund_fee><op_user_id><![CDATA[1310974601]]></op_user_id><total_fee><![CDATA[1]]></total_fee><appid><![CDATA[wx950c13ed59c4faab]]></appid><nonce_str><![CDATA[cum7nyz8rlnhk8xrpu11bznjz3ji7yes]]></nonce_str><out_refund_no><![CDATA[20160307222553478132]]></out_refund_no><out_trade_no><![CDATA[20160307222553478132]]></out_trade_no></xml>";
        System.out.println(WxPayDataBase.formatXML(xml));

        WxPayDataBase.printXml(xml);
    }

    @Test
    public void refundTest() {
        Integer projectid = 68;
//        List<Map<String, Object>> userList = this.projectService.getProjectBackerList(projectid);
//        System.out.println(userList.size());

        // 1.1 发送众筹失败消息通知
        this.projectService.sendBuyFailMsgToBackers(projectid);

        // 1.2 退还款项给所有支持者
        this.projectService.refundToBackers(projectid);
    }

    @Test
    public void downloadImg() {
        String mediaId = "yibG1Ra8Qk3-vwoxKXaKX1rGHMW9WbSWMo5t7yCuwVJfEQmIUQrfhqHRhPP5kv-m";

        String filePath = FileUtils.downloadImgFromWechat(mediaId);
        System.out.println(filePath);
    }

    @Test
    public void commentMsgTest() {
        Integer uid = 32;
        User user = this.userService.getObjById(uid);
        String url = DataUtils.WEB_PHONE_HOMPAGE + "/front/Project/projectInfo.do?id=155";
        String res = WxPayApi.sendCommentMsg(user.getOpenid(), url, "翁列苗", "不错不错", DateUtils.getCurrentDate2());
        System.out.println(res);
    }

}
