package com.tide.controller.front;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tide.bean.Order;
import com.tide.bean.Project;
import com.tide.bean.ProjectBacker;
import com.tide.service.OrderService;
import com.tide.service.ProductService;
import com.tide.service.ProjectService;
import com.tide.service.UserService;
import com.tide.utils.DataUtils;
import com.tide.utils.HttpUtils;
import com.tide.utils.PropertiesUtils;
import com.tide.utils.RandomUtils;
import com.tide.wechat.WxPayApi;
import com.tide.wechat.WxPayDataBase;
import com.tide.wechat.WxPayNotify;
import com.tide.wechat.token.TokenProxy;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wengliemiao on 16/2/25.
 */
@Controller
@RequestMapping(value = "/Pay")
public class FWCPay {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private WxPayDataBase wxPayDataBase;

    @Autowired
    private WxPayApi wxPayApi;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    /**
     * 获取 openid
     * @return
     */
    @RequestMapping(value = "/getOpenid")
    public String getOpenid(String code, HttpServletRequest request, Model model, HttpSession session) {
        if(code == null) {
            model.addAttribute("error", "code is null!");
        } else {
            //获取code码，以获取openid
            Map<String, String> openidMap = this.getOpenidFromMp(code);
            if(openidMap != null) {
                model.addAttribute("openid", openidMap.get("openid"));
                model.addAttribute("access_token", openidMap.get("access_token")); // 共享收货地址接口用
                model.addAttribute("url", request.getRequestURL());

                model.addAttribute("userid", session.getAttribute("userid"));
                model.addAttribute("username", session.getAttribute("username"));
            } else {
                model.addAttribute("errorMsg", "获取 openid 出错");
                return "front/error/error";
            }
        }
        return "front/order/index";
    }

    /**
     * 根据 code 获取 openid
     * @param code
     * @return
     */
    public Map<String, String> getOpenidFromMp(String code) {
//        WxPayApi wTools = new WxPayApi();

        // 拼接获取 openid 链接地址
        String sGetOpenidUrl = wxPayApi.createOauthUrlForOpenid(code);

        // 获取 json 字符串返回结果
        String sAccessJson = HttpUtils.getResponseJson(sGetOpenidUrl);

        // json 操作类
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(sAccessJson, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 调用 统一下单API 获取 JSAPI 相关参数
     * @param paramMap
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/getJsapiParams")
    @ResponseBody
    public Object getJsapiParams(@RequestBody Map<String, String> paramMap, HttpServletRequest request, HttpSession session) {
//        System.out.println(" paramMap: " + paramMap);

        String sJsapiParam = "";
        if(!paramMap.containsKey("openid") || paramMap.get("openid") == null) {
            return "error: 获取JSAPI参数出错: openid 为空";
        }

        if(!paramMap.containsKey("totalPrice") || paramMap.get("totalPrice") == null) {
            return "error: 获取JSAPI参数出错: 不能获取订单价格";
        }

        if(session.getAttribute("orderMap") == null) {
            return "error: 获取JSAPI参数出错: 订单信息为空";
        }

        String openid = paramMap.get("openid");
        Float totalPrice = Float.parseFloat(paramMap.get("totalPrice"));
        String size = paramMap.get("size");

//        System.out.println("openid: " + openid + ", totalPrice: " + totalPrice);

        // 1.拼接订单相关参数
        Map<String, Object> orderMap = (Map<String, Object>) session.getAttribute("orderMap");

        // 1.1 保存订单相关属性
        // 1.1.1 存储订单相关属性到数据库
        // 生成订单号
        String out_trade_no = RandomUtils.createOrderNo(Integer.parseInt(session.getAttribute("userid").toString()));
        Integer userid = 1;
        if(session.getAttribute("userid") != null) {
            userid = Integer.parseInt(session.getAttribute("userid").toString());
        }
        Order order = new Order();
        order.setTradeno(out_trade_no);
        order.setPrice(totalPrice);
        order.setUserid(userid);
        order.setUsername(paramMap.get("username"));
        order.setPhone(paramMap.get("phone"));
        order.setProvince(paramMap.get("province"));
        order.setCity(paramMap.get("city"));
        order.setCounties(paramMap.get("counties"));
        order.setAddress(paramMap.get("address"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sOrderDate = sdf.format(new Date());
        order.setOrderdate(sOrderDate);
        order.setStatus(DataUtils.O_WAIT_PAY);
        this.orderService.insertOrder(order);

        // 1.1.2 存储 zc_project_backer 相关属性
        ProjectBacker projectBacker = new ProjectBacker();
        projectBacker.setProjectid(Integer.parseInt(paramMap.get("projectid")));
        projectBacker.setUserid(userid);
        projectBacker.setTradeno(out_trade_no);
        projectBacker.setNum(Integer.parseInt(paramMap.get("num")));
        projectBacker.setBackdate(sOrderDate);
        projectBacker.setSize(size);
        this.projectService.insertProjectBacker(projectBacker);

        // 1.2 拼接调用 统一下单API 所需相关参数
        String sBody = "";
        if(orderMap.containsKey("projectMap")) {
            Map<String, Object> projectMap = (Map<String, Object>) orderMap.get("projectMap");
            Project project = (Project) projectMap.get("project");
            sBody = project.getTitle();
        } else {
//            sBody = "杭州优布季语电子商务有限公司";
            sBody = "众梦空间-商品支付";
        }
        try {
            sBody = new String(sBody.getBytes(), "UTF-8"); // 转换编码为 UTF-8 格式
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 订单金额: 以分为单位
        int total_fee = 1;
        total_fee = (int) (100 * totalPrice);

        wxPayDataBase.setBody(sBody);
        wxPayDataBase.setOut_trade_no(out_trade_no); // 订单号
        wxPayDataBase.setTotal_fee(total_fee + ""); // 价格
        String sNotifyUrl = request.getScheme() + "://" + request.getServerName() + "/Pay/jsapiNotify.do";
        wxPayDataBase.setNotify_url(sNotifyUrl); // 回调地址
        wxPayDataBase.setTrade_type("JSAPI");
        wxPayDataBase.setOpenid(openid);
        wxPayDataBase.setSpbill_create_ip(request.getRemoteAddr()); // 终端 ip
        wxPayDataBase.setInput_charset("UTF-8");

        // 2.调用 统一下单API, 获取 预支付交易会话标识: prepay_id
        Map<String, String> unifiedOrderMap = wxPayApi.unifiedOrder(wxPayDataBase); // 调用统一下单 API

        Map<String, String> resMap = new HashMap<>();
        if("FAIL".equals(unifiedOrderMap.get("return_code"))) { // 返回错误信息
            resMap.put("error", unifiedOrderMap.get("return_msg"));
            return resMap;
        }
        // 3.将 map 转为 json 字符串
        sJsapiParam = wxPayApi.getJsApiParams(unifiedOrderMap);

        System.out.println("sJsapiParam: " + sJsapiParam);

        resMap.put("jsapiParam", sJsapiParam);
        resMap.put("outTradeNo", out_trade_no);
        return resMap;
    }

    /**
     * 测试action
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/uriTest")
    public String uriTest(HttpServletRequest request, Model model, HttpSession session) {
        String serverPath = session.getServletContext().getRealPath("");
        System.out.println(serverPath);
        return "front/error/error";
    }

    /**
     * 异步通知回调 url
     * @return
     */
    @RequestMapping(value = "/jsapiNotify")
    @ResponseBody
    public String jsapiNotify(InputStream is) {
//        System.out.println("jsapiNotify...");

        String paramXml = HttpUtils.getDataFromInputstream(is);
//        System.out.println("paramXml: " + paramXml);

        Map<String, String> map = this.wxPayDataBase.fromXml(paramXml);

        Logger logger = Logger.getLogger(FWCPay.class);
        logger.info("return_code: " + map.get("return_code") + ", result_code: " + map.get("result_code"));

        String return_code;
        String return_msg;

        // 获取回调项内容
        if("SUCCESS".equals(map.get("return_code"))) { // 状态码
//            System.out.println("step check 11111111");

            String resultCode = map.get("result_code");
            if("SUCCESS".equals(resultCode)) {
//                System.out.println("step check 2222222");
                // 签名验证
//                String nonceStr = map.get("nonce_str");
//                WxPayDataBase wpDataBase = new WxPayDataBase();
//                wpDataBase.setAppid(PropertiesUtils.instance().readValue("APPID"));
//
//                String makeSign = ;
//                String sign = map.get("sign") ;
//                String openid = map.get("openid");
//                if(sign.equals()) {
//
//                }

                // 查询订单
                String transactionId = map.get("transaction_id");
                String outTradeNo = map.get("out_trade_no");

                System.out.println("transaction_id: " + transactionId + ", out_trade_no: " + outTradeNo);

                if(WxPayNotify.checkOrder(transactionId)) { // 四步操作
//                    System.out.println("step check 333333333");
                    return_code = "SUCCESS";
                    return_msg = "OK";

                    // 0.1 从数据库中获取数据

                    // 0.2 从微信服务器获取数据
                    int price = Integer.parseInt(map.get("total_fee")); // 总金额: 以分为单位

                    // 1.zc_order 更新数据
                    Order order = this.orderService.getOrderByTradeNo(outTradeNo);
                    if(order == null) {
//                        System.out.println("step check 4444444555555555");
                        return "order is null";
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String sOrderDate = sdf.format(new Date()); // 支付成功日期
                    order.setPaytype(DataUtils.PAY_TYPE_WECHAT);
                    order.setTransactionid(transactionId);
                    order.setStatus(DataUtils.O_WAIT_DELIVER);
                    order.setOrderdate(sOrderDate);

                    System.out.println("order: " + order);
                    if(orderService.getOrderByTradeNo(outTradeNo).getStatus() == DataUtils.O_WAIT_PAY) { // 防止有多条回调通知
                        orderService.updateOrder(order);
                    } else {
                        return "duplicate jsapi notify";
                    }

                    // 2. zc_project_backer 更新数据
                    ProjectBacker projectBacker = this.projectService.getProjectBackerByTradeno(outTradeNo);
                    projectBacker.setBackdate(order.getOrderdate());

                    System.out.println("projectBacker: " + projectBacker);

                    this.projectService.updateProjectBacker(projectBacker);

                    // 3. 修改 zc_project 的 backernum 字段; 并判断项目是否众筹成功
                    Project project = this.projectService.getObjById(projectBacker.getProjectid());

                    // 3.1 修改 zc_project 表
                    Integer backerNum = project.getBackernum(); // 支持者数量
                    backerNum = backerNum == null ? 0 : backerNum;
                    backerNum ++;

                    Integer fundNum = project.getFundnum(); // 筹集的数量
                    fundNum = fundNum == null ? 0 : fundNum;
                    fundNum += projectBacker.getNum();

                    project.setBackernum(backerNum);
                    project.setFundnum(fundNum);
                    this.projectService.update(project);

                    // 3.2 判断是否众筹成功
//                    Integer fundNum = this.projectService.getProjectFundNum(project.getId()); // 已筹集的数量
                    if(fundNum >= project.getTargetnum()) { // 众筹成功
                        // 3.1 设置 zc_project 的 status 字段为已达成
                        project.setStatus(DataUtils.I_PROJECT_SUCCESS);

                        // 3.2 发送项目达成信息给项目发布者 以及所有的支持者
//                        this.sendBuySuccessMsgToBackers(project.getId());
                        this.projectService.sendBuySuccessMsgToBackers(project.getId());
                    }

                    // 4.发送支付成功消息给用户
                    String openid = this.userService.getOpenidById(order.getUserid());
                    String url = DataUtils.WEB_PHONE_HOMPAGE + "/front/Project/projectInfo.do?id=" + project.getId();
                    StringBuffer product = new StringBuffer("项目名称: " + project.getTitle() + ", ");
                    product.append("项目编号: " + project.getProjectno() + ", ");
                    product.append("订单编号: " + outTradeNo + ", ");
                    product.append("支持数量: " + projectBacker.getNum());
                    wxPayApi.sendPaySuccessMsg(openid, url, product.toString(), order.getPrice() + "");
                } else {
//                    System.out.println("step check 3333334444444444444");
                    return_code = "FAIL";
                    return_msg = "error when query the order";
                }
            } else {
//                System.out.println("step check 4444444444");
                return_code = map.get("err_code");
                return_msg = map.get("err_code_des");
            }
        } else { // 出错
//            System.out.println("step check 5555555555");
            return_code = "FAIL";
            return_msg = map.get("return_msg");
        }

//        System.out.println("step check 6666666666666");

        // 返回数据到微信服务器
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("return_code", return_code);
        returnMap.put("return_msg", return_msg);

        System.out.println("returnMap: " + returnMap);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(returnMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取 config 相关参数 -- 调用 jssdk
     * @param url
     * @return
     */
    @RequestMapping(value = "/getConfigParams")
    @ResponseBody
    public Map<String, String> getConfigParams(String url) {
        Map<String, String> pMap = new TreeMap<>();
        pMap.put("timestamp", new Date().getTime() + "");
        pMap.put("noncestr", WxPayDataBase.getNonceStr());
        pMap.put("jsapi_ticket", TokenProxy.getJsApiTicket());
        pMap.put("url", url);

        String sParams = wxPayDataBase.ToUrlParams(pMap);

        String sign = WxPayDataBase.SHA1(sParams);

        Map<String, String> afterMap = new HashMap<>();
        afterMap.put("appid", PropertiesUtils.instance().readValue("APPID"));
        afterMap.put("sign", sign);
        afterMap.put("timestamp", pMap.get("timestamp"));
        afterMap.put("noncestr", pMap.get("noncestr"));
        return afterMap;
    }
}


