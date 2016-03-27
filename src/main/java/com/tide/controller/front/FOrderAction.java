package com.tide.controller.front;

import com.tide.bean.Product;
import com.tide.bean.Project;
import com.tide.service.OrderService;
import com.tide.service.ProductService;
import com.tide.service.ProjectService;
import com.tide.utils.RandomUtils;
import com.tide.wechat.WxPayApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wengliemiao on 15/12/22.
 */
@Controller
@RequestMapping(value = "/front/Order")
public class FOrderAction {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    /**
     * 用户订单列表
     * @return
     */
    @RequestMapping(value = "/index")
    public String order(Integer projectid,
                        Integer num,
                        Double price,
                        String size,
                        HttpSession session,
                        Model model) {

        if(size == null || price == null) {
            model.addAttribute("errorMsg", "订单数据出错");
            return "front/error/error";
        }

        int uid = 1;
        if(session.getAttribute("userid") != null) {
            uid = Integer.parseInt(session.getAttribute("userid").toString());
        }

        Map<String, Object> pMap = this.projectService.getProjectBaseInfo(projectid, session);
        Project project = (Project) pMap.get("project");
        Integer proid = this.projectService.getProIdByProjectId(project.getId());
        Product product = this.productService.getObjById(proid);

        Map<String, Object> orderMap = new HashMap<>();
        orderMap.put("projectMap", pMap);
        orderMap.put("product", product);
        orderMap.put("num", num);
        orderMap.put("price", price);
        orderMap.put("size", size);
        orderMap.put("out_trade_no", RandomUtils.createProjectNo(uid));
        session.setAttribute("orderMap", orderMap);

        // 拼接获取 openid 地址
        WxPayApi wTools = new WxPayApi();
        String redirectUrl = "http://www.zm-college.com/Pay/getOpenid.do";
        String url = wTools.createOuthUrlForCode(redirectUrl);
        return "redirect:"+url;

//        return "front/order/index";
    }

    /**
     * 删除订单相关数据
     * @param outTradeNo
     * @return
     */
    @RequestMapping(value = "/deleteOrder")
    @ResponseBody
    public String deleteOrder(String outTradeNo) {
        // 1. 删除 zc_order
        if(this.orderService.deleteOrderByTradeno(outTradeNo) > 0) {
            // 2. 删除 zc_project_backer
            if(this.projectService.deleteProjectBackerByTradeno(outTradeNo) > 0) {
                return "success";
            }
            return "delete project_backer error";
        }
        return "delete order error";
    }


    /**
     * 添加购物车接口
     * @return
     */
    @RequestMapping(value = "/addToCart")
    @ResponseBody
    public String addToCart(HttpServletRequest request,
                            HttpServletResponse response) {
        response.setContentType("text/plain");
        String callbackFunName = request.getParameter("callbackparam");//得到js函数名称
//        try {
//            response.getWriter().write(callbackFunName + "([ { name:\"添加到购物车\"}])"); //返回jsonp数据
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return "add to cart";
//        return "添加到购物车";
    }

    /**
     * 添加订单接口
     * @return
     */
    @RequestMapping(value = "/addOrder")
    @ResponseBody
    public String addOrder(HttpServletResponse response) {
        return "add order";
    }
}
