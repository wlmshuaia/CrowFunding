package com.tide.controller.admin;

import com.github.pagehelper.PageHelper;
import com.tide.bean.Order;
import com.tide.bean.Project;
import com.tide.bean.ProjectBacker;
import com.tide.bean.User;
import com.tide.controller.BaseAction;
import com.tide.service.OrderService;
import com.tide.service.ProjectService;
import com.tide.service.UserService;
import com.tide.utils.DataUtils;
import com.tide.wechat.WxPayApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wengliemiao on 15/12/23.
 */
@Controller
@RequestMapping(value = "/admin/Order")
public class AOrderAction extends BaseAction {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    /**
     * 订单列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/selectAll")
    public String selectAll(String status, Integer page, Model model) {
        Map<String, Object> pageMap = this.getPageMap(page, "Order", orderService, model);
        PageHelper.startPage(Integer.parseInt(pageMap.get("curr").toString()), DataUtils.PAGE_NUM);

        List<Order> oList = this.orderService.getOrderByStatus(status);
        List<Map<String, Object>> orderList = new ArrayList<>();
        for (Order o : oList) {
            Map<String, Object> oMap = new HashMap<>();

            Project p = this.projectService.getProjectByTradeno(o.getTradeno());

            oMap.put("order", o);
            oMap.put("project", p);

            orderList.add(oMap);
        }

        model.addAttribute("orderList", orderList);
        return "admin/order/orderList";
    }

    /**
     * 获取订单信息: order, project, designimg
     * @param id
     * @return
     */
    @RequestMapping(value = "/getOrderInfo")
    @ResponseBody
    public Object getOrderInfo(Integer id, HttpSession session) {
        Order order = this.orderService.getObjById(id);
        Project p = this.projectService.getProjectByTradeno(order.getTradeno());
        ProjectBacker pb = this.projectService.getProjectBackerByTradeno(order.getTradeno());
        Map<String, Object> pMap = this.projectService.getProjectBaseInfo(p, session);
        pMap.put("order", order);
        pMap.put("pb", pb);
        return pMap;
    }

    /**
     * 填写发货单号
     * @param map
     * @return
     */
    @RequestMapping(value = "/fillExpress")
    @ResponseBody
    public String fillExpress(@RequestBody Map<String, Object> map) {
        if(map.containsKey("orderid") && !"".equals(map.get("orderid"))) {
            Order o = this.orderService.getObjById(Integer.parseInt(map.get("orderid").toString()));
            if(map.containsKey(("express")) && !"".equals(map.get("express"))) {
                o.setExpress(map.get("express").toString());
                if(map.containsKey("trackno") && !"".equals(map.get("trackno"))) {
                    o.setTrackno(map.get("trackno").toString());
                    o.setStatus(DataUtils.O_WAIT_TAKE);
                    if(this.orderService.updateOrder(o) != 0) { // 成功发货
                        // 发送已发货消息
                        User user = this.userService.getObjById(o.getUserid());
                        String openid = user.getOpenid();
                        Project project = this.projectService.getProjectByTradeno(o.getTradeno());
                        String url = DataUtils.WEB_PHONE_HOMPAGE + "/front/Project/projectInfo.do?id=" + project.getId();
                        WxPayApi.sendProductDeliverMsg(openid, url, map.get("express").toString(), map.get("trackno").toString());

                        return "success";
                    } else{
                        return "操作失败";
                    }
                }
                return "运单号码为空!";
            }
            return "快递公司为空!";
        }
        return "订单id为空!";
    }

    /**
     * 删除订单
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteOrder")
    @ResponseBody
    public String deleteOrder(Integer id, HttpSession session) {
        if(this.orderService.delete(id, session) != 0) {
            return "success";
        }
        return "删除失败";
    }

    /**
     * 批量删除订单
     * @param key
     * @return
     */
    @RequestMapping(value = "/batchDelete")
    @ResponseBody
    public String batchDelete(String key, HttpSession session) {
        return this.batchDelete(key, orderService, session);
    }

}
