package com.tide.controller.front;

import com.github.pagehelper.PageHelper;
import com.tide.bean.Project;
import com.tide.bean.User;
import com.tide.service.ProjectService;
import com.tide.service.UserService;
import com.tide.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户操作
 * Created by wengliemiao on 16/1/9.
 */
@Controller
@RequestMapping(value = "/front/User")

public class FUserAction {
    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService ;

    /**
     * 个人中心
     * @return
     */
    @RequestMapping(value = "/personal")
    public String personal(Model model, HttpSession session) {
        // 用户id, 从微信端获取
        Integer uid = 1;
        if(session.getAttribute("userid") != null) {
            uid = Integer.parseInt(session.getAttribute("userid").toString());
        }
        List<Project> pList = this.projectService.getProjectByUid(uid);

        List<Map<String, Object>> projectList = new ArrayList<>();
        if(pList != null) {
            for (Project p : pList){
                Map<String, Object> pMap = this.projectService.getProjectInfo(p, session);
                projectList.add(pMap);
            }
        }

        model.addAttribute("projectList", projectList);
        return "front/user/index";
    }

    /**
     * 根据不同的条件筛选项目(个人中心)
     * @param map
     * @return
     */
    @RequestMapping(value = "/getInfoByJson")
    @ResponseBody
    public Object getInfoByJson(@RequestBody Map<String, String> map, HttpSession session) {
        // 从微信公众号获取用户id
        Integer uid = 1;
        Object oUid = session.getAttribute("userid");
        if(oUid != null) {
            uid = Integer.parseInt(oUid.toString());
        }

        // 分页数据
        Integer start = Integer.parseInt(map.get("start"));
        start = start == null ? 1 : start ;

        Object oType = map.get("type");
        if(oType != null && DataUtils.S_PERSON_TYPE_DESIGNER.equals(oType.toString())) { // 关注的设计师
            List<Map<String, Object>> uMapList = new ArrayList<>();

            PageHelper.startPage(start, DataUtils.OBJ_NUM);
            List<User> uList = this.userService.getUserFocus(uid);
            for (User u : uList) {
                uMapList.add(this.userService.getDesignerProjectsBase(u, session));
            }
            return uMapList;
        } else {    // 项目
            Object headerObj = map.get("header");
            if(headerObj != null) {
                String sHeader = headerObj.toString();
                List<Project> pList = new ArrayList<>();

                PageHelper.startPage(start, DataUtils.INRO_PROJECT_NUM);
                if(DataUtils.S_PERSON_HEADER_RELEASE.equals(sHeader)) { // 我发布的
                    pList = this.projectService.getProjectAddByStatus(uid, map.get("status"));
                } else if(DataUtils.S_PERSON_HEADER_BACK.equals(sHeader)) { // 我支持的
                    pList = this.projectService.getProjectBackByStatus(uid, map.get("status"));
                } else if(DataUtils.S_PERSON_HEADER_FOCUS.equals(sHeader)) { // 我关注的: 项目
                    pList = this.projectService.getProjectFocusByStatus(uid, map.get("status"));
                }

                List<Map<String, Object>> projectList = new ArrayList<>();
                if(pList != null) {
                    for (Project p : pList){
                        Map<String, Object> pMap = this.projectService.getProjectInfo(p, session);
                        projectList.add(pMap);
                    }
                }
                return projectList;
            }
        }
        return "success";
    }

    /**
     * 关注设计师
     * @return
     */
    @RequestMapping(value = "/ifFocusUser")
    @ResponseBody
    public String focusUser(@RequestBody Map<String, String> map, HttpSession session) {
        Integer uid = Integer.parseInt(session.getAttribute("userid").toString());
        if(this.userService.ifFocusUser(uid, Integer.parseInt(map.get("id")), map.get("type")) != 0) {
            return "success";
        }
        return "fail";
    }

    /**
     * 关注项目
     * @return
     */
    @RequestMapping(value = "/ifFocusProject")
    @ResponseBody
    public String focusProject(@RequestBody Map<String, String> map, HttpSession session) {
        Integer uid = Integer.parseInt(session.getAttribute("userid").toString());
        if(this.userService.ifFocusProject(uid, Integer.parseInt(map.get("id")), map.get("type")) != 0) {
            return "success";
        }
        return "fail";
    }

}
