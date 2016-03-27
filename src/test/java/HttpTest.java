import com.tide.utils.HttpUtils;
import com.tide.wechat.WxPayDataBase;
import com.tide.wechat.WxPayApi;
import org.junit.Test;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by wengliemiao on 16/2/29.
 */
public class HttpTest extends BaseSpring {

    @Test
    public void getOpenidTest() {
        WxPayApi wTools = new WxPayApi();
        String redirectUrl = "http://www.zm-college.com/Pay/getOpenid.do";
        String url = wTools.createOuthUrlForCode(redirectUrl);

        String html = HttpUtils.getResponseHtml(url);
        System.out.println(html);
    }

    @Test
    public void postXmlTest() {
        String openid = "oKVZ5wN_WyvG1DxfvIXaQwLaMPMw";

        WxPayDataBase wdTools = new WxPayDataBase();
//        wdTools.setBody("jsapi 支付测试哈哈哈");
        wdTools.setBody("jsapi");
        wdTools.setAttach("test");
        wdTools.setOut_trade_no("2012329620021"); // 订单号
        wdTools.setTotal_fee("1"); // 价格
        wdTools.setNotify_url("http://www.zm-college.com/Pay/jsapiNotify.do"); // 回调地址
        wdTools.setTrade_type("JSAPI");
        wdTools.setOpenid(openid);
        wdTools.setSpbill_create_ip("121.43.60.126"); // 终端 ip


        WxPayApi wTools = new WxPayApi() ;
        Map<String, String> orderMap = wTools.unifiedOrder(wdTools);

        System.out.println("统一下单支付单信息");
        System.out.println(orderMap);
        for (Iterator iter = orderMap.keySet().iterator(); iter.hasNext();) {
            String key = iter.next().toString();
            System.out.println(key + ": " + orderMap.get(key));
        }


        String sJsapiParam = wTools.getJsApiParams(orderMap);
        System.out.println(sJsapiParam);
    }

    @Test
    public void fromXmlTest() {
        String xml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg><appid><![CDATA[wx950c13ed59c4faab]]></appid><mch_id><![CDATA[1310974601]]></mch_id><nonce_str><![CDATA[Z57MLsetEzQNZ3Ue]]></nonce_str><sign><![CDATA[B49D9361D953397E6FB22E68E1A5772A]]></sign><result_code><![CDATA[SUCCESS]]></result_code><prepay_id><![CDATA[wx2016022912072411a32104c20985263865]]></prepay_id><trade_type><![CDATA[JSAPI]]></trade_type></xml>";
        WxPayDataBase wdTools = new WxPayDataBase();
        Map<String, String> vMap = wdTools.fromXml(xml);

        for (Iterator iter = vMap.keySet().iterator(); iter.hasNext();) {
            String key = iter.next().toString();
            System.out.println(key + ": " + vMap.get(key));
        }
    }

}
