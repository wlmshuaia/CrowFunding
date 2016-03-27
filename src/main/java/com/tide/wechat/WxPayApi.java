package com.tide.wechat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tide.utils.HttpUtils;
import com.tide.utils.PropertiesUtils;
import com.tide.wechat.token.TokenProxy;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 调用微信接口工具类
 * Created by wengliemiao on 16/1/16.
 */
@Component
public class WxPayApi {
    // json 操作
//    private ObjectMapper objectMapper = new ObjectMapper();
    private static ObjectMapper objectMapper = new ObjectMapper();

    // 读取配置文件
    private static PropertiesUtils propUtils = PropertiesUtils.instance();

    /**
     * 授权获取 access_token
     *
     * @param code
     * @return
     */
    public Map<String, Object> getAccessToken(String code) {
        String sGetAccessToken = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        sGetAccessToken = sGetAccessToken.replace("APPID", propUtils.readValue("APPID"));
        sGetAccessToken = sGetAccessToken.replace("SECRET", propUtils.readValue("SECRET"));
        sGetAccessToken = sGetAccessToken.replace("CODE", code);

        try {
            String sAccessJson = HttpUtils.getResponseJson(sGetAccessToken);
            return objectMapper.readValue(sAccessJson, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 授权刷新 access_token
     *
     * @param sRefreshToken
     * @return
     */
    public Map<String, Object> getRefreshToken(String sRefreshToken) {
        String sRefreshTokenUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
        sRefreshTokenUrl = sRefreshTokenUrl.replace("APPID", propUtils.readValue("APPID"));
        sRefreshTokenUrl = sRefreshTokenUrl.replace("REFRESH_TOKEN", sRefreshToken);

        try {
            String sRefreshJson = HttpUtils.getResponseJson(sRefreshTokenUrl);
            return objectMapper.readValue(sRefreshJson, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 授权获取用户信息
     *
     * @param sAccessToken
     * @param sOpenid
     * @return
     */
    public Map<String, Object> getUserInfo(String sAccessToken, String sOpenid) {
        String sGetUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        sGetUserInfoUrl = sGetUserInfoUrl.replace("ACCESS_TOKEN", sAccessToken);
        sGetUserInfoUrl = sGetUserInfoUrl.replace("OPENID", sOpenid);
        try {
            String sUserInfoJson = HttpUtils.getResponseJson(sGetUserInfoUrl);
            return objectMapper.readValue(sUserInfoJson, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 拼接获取 code 的链接地址
     *
     * @param redirectUrl
     * @return
     */
    public String createOuthUrlForCode(String redirectUrl) {
        StringBuffer url = new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?");
        url.append("appid=" + propUtils.readValue("APPID"));
        url.append("&redirect_uri=" + redirectUrl);
        url.append("&response_type=code");
        url.append("&scope=snsapi_base");
        url.append("&state=STATE#wechat_redirect");
        return url.toString();
    }

    /**
     * 拼接获取 openid 的地址
     *
     * @param code
     * @return
     */
    public String createOauthUrlForOpenid(String code) {
        StringBuffer url = new StringBuffer("https://api.weixin.qq.com/sns/oauth2/access_token?");
        url.append("appid=" + propUtils.readValue("APPID"));
        url.append("&secret=" + propUtils.readValue("SECRET"));
        url.append("&code=" + code);
        url.append("&grant_type=authorization_code");
        return url.toString();
    }


    /**
     * 调用 统一下单 API
     *
     * @return
     */
    public Map<String, String> unifiedOrder(WxPayDataBase wdTools) {
        // 1.拼接参数
        wdTools.setAppid(propUtils.readValue("APPID"));
        wdTools.setMch_id(propUtils.readValue("MCHID"));
        wdTools.setNonce_str(WxPayDataBase.getNonceStr());
        wdTools.setSign();

        // 2.转化为 xml 格式
        String xml = wdTools.toXml(wdTools.getValues());

        System.out.println("======================= xml 转化之前 ===========================");
        System.out.println(WxPayDataBase.formatXML(xml));
        System.out.println("======================= xml 转化之前 ===========================");
        System.out.println();
        try {
            xml = new String(xml.getBytes(), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        BasicNameValuePair bnv = new BasicNameValuePair("input_charset", "UTF-8");

        System.out.println("======================= xml 转化之后 ===========================");
        System.out.println(WxPayDataBase.formatXML(xml));
        System.out.println("======================= xml 转化之后 ===========================");
        System.out.println();

        // 3.调用 统一下单 API 接口
        String sUnifiedOrderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        String responseXml = HttpUtils.postXmlCurl(sUnifiedOrderUrl, xml);

        System.out.println("======================= resposnsexml ===========================");
        System.out.println(WxPayDataBase.formatXML(responseXml));
        System.out.println("======================= resposnsexml ===========================");
        System.out.println();

        // 4.xml 转化为 map
        Map<String, String> resMap = WxPayDataBase.init(responseXml);

        return resMap;
    }

    /**
     * 封装 JSAPI 参数
     *
     * @param paramMap
     * @return
     */
    public String getJsApiParams(Map<String, String> paramMap) {
        JsApiPay wJsapiTools = new JsApiPay();
        wJsapiTools.setAppId(propUtils.readValue("APPID"));
        wJsapiTools.setTimeStamp(new Date().getTime() + "");
        wJsapiTools.setNonceStr(WxPayDataBase.getNonceStr());
        wJsapiTools.setPackage("prepay_id=" + paramMap.get("prepay_id"));
        wJsapiTools.setSignType("MD5");
        wJsapiTools.setPaySign(wJsapiTools.makeSign());

        try {
            // convert map to json string
            return objectMapper.writeValueAsString(wJsapiTools.getValues());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取地址js参数
     *
     * @return 获取共享收货地址js函数需要的参数，json格式可以直接做参数使用
     */
    public String getEditAddressParameters(String accessToken, String url, HttpServletRequest request) {
        Map<String, String> data = new TreeMap<>();
        data.put("appid", propUtils.readValue("APPID"));

        // 拼接 url 字符串
        String sUrlParams = request.getQueryString();
        url += "?" + sUrlParams;
        data.put("url", url);

        data.put("timestamp", String.valueOf(new Date().getTime()));
        data.put("noncestr", "123456");
        data.put("accesstoken", accessToken);

        // 组成字符串形式
        WxPayDataBase wdTools = new WxPayDataBase();
        String sParams = wdTools.ToUrlParams(data);

        // 使用 SHA-1 签名算法
        String addrSign = WxPayDataBase.SHA1(sParams);

        // 拼接调用地址接口参数
        Map<String, String> afterData = new HashMap<>();
        afterData.put("addrSign", addrSign);
        afterData.put("signType", "sha1");
        afterData.put("scope", "jsapi_address");
        afterData.put("appId", propUtils.readValue("APPID"));
        afterData.put("timeStamp", data.get("timestamp"));
        afterData.put("nonceStr", data.get("noncestr"));

        try {
            return objectMapper.writeValueAsString(afterData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询订单, WxPayOrderQuery中out_trade_no、transaction_id至少填一个
     * appid、mchid、spbill_create_ip、nonce_str不需要填入
     * @param wpOrderQuery
     * @return
     */
    public static Map orderQuery(WxPayOrderQuery wpOrderQuery) {
        String url = "https://api.mch.weixin.qq.com/pay/orderquery";
        if(!wpOrderQuery.isOutTradeNoSet() && !wpOrderQuery.isTransactionidSet()) {
            try {
                throw new WxPayException("订单查询接口中，out_trade_no、transaction_id至少填一个！");
            } catch (WxPayException e) {
                e.printStackTrace();
            }
        }

        PropertiesUtils propUtils = PropertiesUtils.instance();
        wpOrderQuery.setAppId(propUtils.readValue("APPID"));
        wpOrderQuery.setMchId(propUtils.readValue("MCHID"));
        wpOrderQuery.setNoncestr(WxPayDataBase.getNonceStr());

        wpOrderQuery.setSign();

        String xml = wpOrderQuery.toXml(wpOrderQuery.getValues());

        String response = HttpUtils.postXmlCurl(url, xml);

        Map<String, String> result = WxPayDataBase.init(response);

        return result;
    }

    /**
     * 根据 access_token 拼接模板消息接口地址
     * @return
     */
    public static String getTemplageMsgUrl() {
        // 1. 获取 access_token
        String accessToken = TokenProxy.getAccessToken();

        if(accessToken == null || "".equals(accessToken)) {
            return "";
        }
        // 2. 拼接 接口地址
        return "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken;
    }

    /**
     * 发送模板消息接口: 订单支付成功模板
     * @return
     */
    public static String sendPaySuccessMsg(String openid, String url, String product, String price) {
        // 1. 拼接 模板消息接口地址
        String sendMsgUrl = getTemplageMsgUrl();

        // 2. 拼接 json格式参数
        String templateId = "xff7ICUoACv36J-CjP6lR_ukJOVBL1YK3NFpKFddcGY";
        String sJson = "{\n" +
                "           \"touser\":\""+ openid +"\",\n" +
                "           \"template_id\":\""+ templateId +"\",\n" +
                "           \"url\":\""+ url +"\",            \n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"恭喜您订单支付成功~\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"orderProductName\":{\n" +
                "                       \"value\":\""+ product +"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"orderMoneySum\": {\n" +
                "                       \"value\":\""+ price +"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"Remark\":{\n" +
                "                       \"value\":\"欢迎再次购买！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";

        // 3. 调用发送模板消息接口
        return HttpUtils.postJsonCurl(sendMsgUrl, sJson);
    }

    /**
     * 发送模板消息: 购买成功通知
     * @return
     */
    public static String sendBuySuccessMsg(String openid, String url, String name) {
        String templateId = "IDInhHgk2gUB9P7XzkSN54Xrlbqqc-LCkv9pkpJOUBA";
        return sendBuyMsg(openid, url, name, templateId, "我们将尽快安排发货。欢迎您再次购买~");
    }

    /**
     * 发送模板消息: 购买失败通知
     * @return
     */
    public static String sendBuyFailMsg(String openid, String url, String name) {
        String templateId = "avkhVqIwN3bl2pyHHxfRootKsn-imigMYIIY3vIwse4";
        return sendBuyMsg(openid, url, name, templateId, "我们将在24小时内为您退款。如有疑问请咨询微信客服。欢迎您再次购买~");
    }

    /**
     * 发送模板消息: 购买通知
     * @param openid
     * @param url
     * @param name
     * @param templateId
     * @param remark
     * @return
     */
    public static String sendBuyMsg(String openid, String url, String name, String templateId, String remark) {
        // 1. 拼接 模板消息接口地址
        String sendMsgUrl = getTemplageMsgUrl();

        // 2. 拼接 json格式参数
        String sJson = "{\n" +
                "           \"touser\":\""+ openid +"\",\n" +
                "           \"template_id\":\""+ templateId +"\",\n" +
                "           \"url\":\""+ url +"\",            \n" +
                "           \"data\":{\n" +
                "                   \"name\":{\n" +
                "                       \"value\":\""+ name +"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\""+ remark +"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";

        // 3. 调用发送模板消息接口
        return HttpUtils.postJsonCurl(sendMsgUrl, sJson);
    }

    /**
     * 发送模板消息: 退款通知
     * @param openid
     * @param url
     * @param reason    退款原因
     * @param refund    退款金额
     * @return
     */
    public static String sendRefundMsg(String openid, String url, String reason, String refund) {
        // 1. 拼接 模板消息接口地址
        String sendMsgUrl = getTemplageMsgUrl();

        // 2. 拼接 json格式参数
        String templateId = "bnnxDcQnOSlRa0nFFl3U_e8ZaQXdObPU8eKbYsvkcPA";
        String sJson = "{\n" +
                "           \"touser\":\""+ openid +"\",\n" +
                "           \"template_id\":\""+ templateId +"\",\n" +
                "           \"url\":\""+ url +"\",            \n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"已经进入退款流程~\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"reason\":{\n" +
                "                       \"value\":\""+ reason +"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"refund\": {\n" +
                "                       \"value\":\""+ refund +"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"欢迎再次购买！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";

        // 3. 调用发送模板消息接口
        return HttpUtils.postJsonCurl(sendMsgUrl, sJson);
    }

    /**
     * 发送模板消息: 商品发出通知
     * @param openid
     * @param url
     * @param delivername   快递公司
     * @param ordername     快递单号
     * @return
     */
    public static String sendProductDeliverMsg(String openid, String url, String delivername, String ordername) {
        // 1. 拼接 模板消息接口地址
        String sendMsgUrl = getTemplageMsgUrl();

        // 2. 拼接 json格式参数
        String templateId = "CVhBkny5EXK4dzQquB43D7mEiEQM7Q3KBn7d1Y3EaiY";
        String sJson = "{\n" +
                "           \"touser\":\""+ openid +"\",\n" +
                "           \"template_id\":\""+ templateId +"\",\n" +
                "           \"url\":\""+ url +"\",            \n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"宝贝已经在路上了~\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"delivername\":{\n" +
                "                       \"value\":\""+ delivername +"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"ordername\": {\n" +
                "                       \"value\":\""+ ordername +"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"欢迎再次购买！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";

        // 3. 调用发送模板消息接口
        return HttpUtils.postJsonCurl(sendMsgUrl, sJson);
    }

    /**
     * 发送模压消息: 项目未通过审核通知
     * @param openid
     * @param url
     * @param title
     * @param reason
     * @param date
     * @return
     */
    public static String sendExamineFailMsg(String openid, String url, String title, String reason, String date) {
        // 1. 拼接 模板消息接口地址
        String sendMsgUrl = getTemplageMsgUrl();

        // 2. 拼接 json格式参数
        String templateId = "pdxKMCJ0VVrtVoqHNeMu7crkBwAt4gBPfA_BVG1HOiY";
        String sJson = "{\n" +
                "           \"touser\":\""+ openid +"\",\n" +
                "           \"template_id\":\""+ templateId +"\",\n" +
                "           \"url\":\""+ url +"\",            \n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"项目审核失败\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\":{\n" +
                "                       \"value\":\""+ title +"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\":{\n" +
                "                       \"value\":\""+ reason +"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword3\": {\n" +
                "                       \"value\":\""+ date +"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"欢迎再次发布作品！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";

        // 3. 调用发送模板消息接口
        return HttpUtils.postJsonCurl(sendMsgUrl, sJson);
    }

    /**
     * 发送模板消息: 项目审核通过通知
     * @param openid
     * @param url
     * @param name
     * @param targetnum
     * @param enddate
     * @return
     */
    public static String sendProjectStartMsg(String openid, String url, String name, String targetnum, String enddate) {
        // 1. 拼接 模板消息接口地址
        String sendMsgUrl = getTemplageMsgUrl();

        // 2. 拼接 json格式参数
        String templateId = "KdfvKqb2Dg3ttPiAERIwD1mezMJI4F5cJhs5EX5dIjs";
        String sJson = "{\n" +
                "           \"touser\":\""+ openid +"\",\n" +
                "           \"template_id\":\""+ templateId +"\",\n" +
                "           \"url\":\""+ url +"\",            \n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"您好,您已成功发起项目~\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\":{\n" +
                "                       \"value\":\""+ name +"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\": {\n" +
                "                       \"value\":\""+ targetnum +"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword3\": { " +
                "                       \"value\":\""+ enddate +"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"欢迎再次发布作品！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";

        // 3. 调用发送模板消息接口
        return HttpUtils.postJsonCurl(sendMsgUrl, sJson);
    }

    /**
     * 评价通知
     * @param openid
     * @param url
     * @param username
     * @param content
     * @param date
     * @return
     */
    public static String sendCommentMsg(String openid, String url, String username, String content, String date) {
        // 1. 拼接 模板消息接口地址
        String sendMsgUrl = getTemplageMsgUrl();

        // 2. 拼接 json格式参数
        String templateId = "qfFoExGviNz7EwqSW35V8dbbvUFK6DGXMHzpIitv9P4";
        String sJson = "{\n" +
                "           \"touser\":\""+ openid +"\",\n" +
                "           \"template_id\":\""+ templateId +"\",\n" +
                "           \"url\":\""+ url +"\",            \n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\""+ username + "刚刚评价了你\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\":{\n" +
                "                       \"value\":\""+ content +"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\": {\n" +
                "                       \"value\":\""+ date +"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"点击查看详情。\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";

        // 3. 调用发送模板消息接口
        return HttpUtils.postJsonCurl(sendMsgUrl, sJson);
    }


    /**
     * 退款接口
     * @param wpRefund
     * @return
     * @throws Exception
     */
    public static Map<String, String> refund(WxPayRefund wpRefund) {
        String url = "https://api.mch.weixin.qq.com/secapi/pay/refund";

        wpRefund.setAppId(propUtils.readValue("APPID"));
        wpRefund.setMchId(propUtils.readValue("MCHID"));
        wpRefund.setNoncestr(WxPayRefund.getNonceStr());
        wpRefund.setSign();
        String xml = wpRefund.toXml(wpRefund.getValues());
        System.out.println("xml: " + xml);
        try {
            String response = HttpUtils.postXmlCurlSSL(url, xml);
            System.out.println("response: " + response);
            return wpRefund.init(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
