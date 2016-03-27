package com.tide.controller.admin;

import com.tide.bean.Admin;
import com.tide.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wengliemiao on 15/12/29.
 */
@Controller
@RequestMapping(value = "/admin/Admin/")
public class AAdminAction {

    @Autowired
    private AdminService adminService;

    /**
     * 修改个人档案
     * @param adminid
     * @param model
     * @return
     */
    @RequestMapping(value = "/personal")
    public String personal(Integer adminid, Model model) {
        Admin admin = this.adminService.getObjById(adminid);
        model.addAttribute("admin", admin);
        return "admin/index/personal";
    }

    @RequestMapping(value = "/updateAdmin")
    public String updateAdmin(Admin admin) {
        return "redirect:/admin/Index/index.do";
    }
}
