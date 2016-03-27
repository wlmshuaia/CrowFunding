package com.tide.controller.admin;

import com.github.pagehelper.PageHelper;
import com.tide.bean.User;
import com.tide.controller.BaseAction;
import com.tide.service.UserService;
import com.tide.utils.DataUtils;
import com.tide.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by wengliemiao on 15/12/29.
 */
@Controller
@RequestMapping(value = "/admin/User")
public class AUserAction extends BaseAction {

    @Autowired
    private UserService userService;

    /**
     * 用户列表页
     * @param model
     * @return
     */
    @RequestMapping(value = "/selectAll")
    public String selectAll(String type, Integer page, Model model) {
        Map<String, Object> pageMap = this.getPageMap(page, "User", userService, model);
        PageHelper.startPage(Integer.parseInt(pageMap.get("curr").toString()), DataUtils.PAGE_NUM);

        List<User> uList = this.userService.getUserByType(type);
        model.addAttribute("userList", uList);
        return "admin/user/userList";
    }

    /**
     * 获取用户信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/getUserInfo")
    @ResponseBody
    public Object getUserInfo(Integer id) {
        return this.userService.getObjById(id);
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @RequestMapping(value = "/updateUser")
    @ResponseBody
    public String updateUser(@ModelAttribute User user,
                             @RequestParam("headimg") MultipartFile headimg,
                             HttpSession session) {

        if(headimg != null && headimg.getSize() > 0) {
            String sHeadUrl = FileUtils.saveFile(headimg, session);
            user.setHeadimgurl(sHeadUrl);
        }

        if(this.userService.update(user) != 0) {
            return "success";
        }
        return "修改失败";
    }
}
