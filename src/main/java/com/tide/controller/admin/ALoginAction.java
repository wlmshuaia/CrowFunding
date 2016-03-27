package com.tide.controller.admin;

import com.tide.bean.Admin;
import com.tide.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 后台登录
 * Created by wengliemiao on 15/11/30.
 */
@Controller
@RequestMapping(value = "/admin/Login")
public class ALoginAction {

    @Autowired
    private AdminService adminService ;

    /**
     * 显示登录界面
     * @return
     */
    @RequestMapping(value = "/loginD")
    public String login() {
        return "admin/login";
    }

    /**
     * 登录提交表单操作
     * @param adminname
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "/login")
    public String login(String adminname, String password, HttpSession session, HttpServletRequest request) {
        System.out.println(adminname+", "+password);
        Admin a = this.adminService.getObjByNP(adminname, password);

        // 登录失败
        if(a == null) {
            return "redirect:/admin/Login/loginD.do";
        }

        // 获取IP
        String loginIp = request.getRemoteAddr();
        // 获取时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String loginDate = sdf.format(new Date());
        a.setLastloginip(loginIp);
        a.setLastlogintime(loginDate);
        // 记录时间
        this.adminService.update(a);

        session.setAttribute("adminname", adminname);
        session.setAttribute("adminid", a.getId());
        return "redirect:/admin/Index/index.do";
    }

    /**
     * 注销用户
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("adminname");
        session.removeAttribute("adminid");
        return "admin/login" ;
    }
}
