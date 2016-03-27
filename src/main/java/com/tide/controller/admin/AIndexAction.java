package com.tide.controller.admin;

import com.tide.service.OrderService;
import com.tide.service.ProjectService;
import com.tide.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wengliemiao on 15/12/4.
 */
@Controller
@RequestMapping(value = "/admin/Index")
public class AIndexAction {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private OrderService orderService;

    /**
     * 后台首页
     * @param model
     * @return
     */
    @RequestMapping(value = "/index")
    public String index(Model model) {

        Integer uCount = this.userService.getCount();
        Integer pCount = this.projectService.getCount();
        Integer oCount = this.orderService.getCount();

        Map<String, Object> infoMap = new HashMap<>();
        infoMap.put("userCount", uCount);
        infoMap.put("projectCount", pCount);
        infoMap.put("orderCount", oCount);
        model.addAttribute("infoMap", infoMap);

        model.addAttribute("menuUrl", "admin/Index/index.do");
        return "admin/index";
    }
}
