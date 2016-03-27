package com.tide.controller.front;

import com.github.pagehelper.PageHelper;
import com.tide.bean.Project;
import com.tide.bean.User;
import com.tide.service.ProjectService;
import com.tide.service.UserService;
import com.tide.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wengliemiao on 16/1/17.
 */
@Controller
@RequestMapping(value = "/front/Designer")
public class FDesignerAction {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService ;

    /**
     * 异步获取设计师列表
     * @return
     */
    @RequestMapping(value = "/getDesignerList")
    @ResponseBody
    public Object getDesignerList(@RequestBody Map<String, String> paramMap, HttpSession session) {
        List<Map<String, Object>> designerList = new ArrayList<>();

        Integer start = Integer.parseInt(paramMap.get("start"));
        start = start == null ? 1 : start;

        PageHelper.startPage(start, DataUtils.OBJ_NUM);
        List<User> userList = this.userService.getUserByType(DataUtils.USER_TYPE_DESIGNER);
        for (User u : userList) {
            Map<String, Object> map = this.userService.getDesignerProjectsBase(u, session);
            designerList.add(map);
        }

        return designerList;
    }

    /**
     * 获取设计师详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getDesignerInfo")
    @ResponseBody
    public Object getDesignerInfo(Integer id, HttpSession session) {
        return this.userService.getDesignerInfo(id, session);
    }

    /**
     * 获取设计师作品
     * @param map
     * @return
     */
    @RequestMapping(value = "/getDesignerProjects")
    @ResponseBody
    public Object getDesignerProjects(@RequestBody Map<String, String> map, HttpSession session) {
        List<Map<String, Object>> pMapList = new ArrayList<>();

        Integer id = Integer.parseInt(map.get("id"));
        Integer start = Integer.parseInt(map.get("start"));
        start = start == null ? 1 : start;

        PageHelper.startPage(start, DataUtils.OBJ_NUM);
        List<Project> pList = this.projectService.getProjectByUid(id);
        for (Project p : pList) {
            Map<String, Object> mTmp = this.projectService.getProjectInfo(p, session);
            pMapList.add(mTmp);
        }
        return pMapList;
    }
}
