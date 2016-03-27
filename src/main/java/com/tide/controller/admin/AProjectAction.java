package com.tide.controller.admin;

import com.github.pagehelper.PageHelper;
import com.tide.bean.*;
import com.tide.controller.BaseAction;
import com.tide.service.LabelService;
import com.tide.service.ProductService;
import com.tide.service.ProjectService;
import com.tide.service.UserService;
import com.tide.utils.DataUtils;
import com.tide.utils.DateUtils;
import com.tide.wechat.WxPayApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wengliemiao on 15/12/14.
 */
@Controller
@RequestMapping(value = "/admin/Project")
public class AProjectAction extends BaseAction {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private LabelService labelService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    /**
     * 获取所有项目
     * @param model
     * @return
     */
    @RequestMapping(value = "/selectAll")
    public String selectAll(String type, String status, Integer page, HttpSession session, Model model) {
        Map<String, Object> pageMap = this.getPageMap(page, "Project", projectService, model);
        PageHelper.startPage(Integer.parseInt(pageMap.get("curr").toString()), DataUtils.PAGE_NUM);

        List<Project> pList;
        if(type != null) {
            pList = this.projectService.getProjectByType(type);
        } else {
            pList = this.projectService.getProjectByStatus(status);
        }

        List<Map<String, Object>> projectList = new ArrayList<>();
        for(Project project : pList) {
            Map<String, Object> tmpMap = this.projectService.getProjectBaseInfo(project, session);

            User user = this.userService.getObjById(project.getUserid());
            Integer proid = this.projectService.getProIdByProjectId(project.getId());
            Product product = this.productService.getObjById(proid);
            tmpMap.put("user", user);
            tmpMap.put("product", product);

            projectList.add(tmpMap);
        }

        model.addAttribute("projectList", projectList);
        return "admin/project/projectList";
    }

    /**
     * 获取项目详细信息
     * @param id
     * @param session
     * @return map
     */
    @RequestMapping(value = "/getProjectInfoById")
    @ResponseBody
    public Object getProjectInfoById(Integer id, HttpSession session) {
        Map<String, Object> projectMap = new HashMap<>();

        // project
        Project project = this.projectService.getObjById(id);
        // project_designimg
        List<ProjectDesignimg> pdList = this.projectService.getDesignimgList(id);
        // project_label
        List<ProjectLabel> plList = this.projectService.getProjectLabelList(id);
        // product
        Integer proid = this.projectService.getProIdByProjectId(project.getId());
        Product product = this.productService.getObjById(proid);
        // product_color
        List<ProductColor> pcList = this.productService.getColorByProId(product.getId());

        // user
        User user = this.userService.getObjById(project.getUserid());

        // label
        List<Label> labelList = new ArrayList<>();
        for(ProjectLabel pl : plList) {
            Label label = this.labelService.getObjById(pl.getLabelid());
            labelList.add(label);
        }

        projectMap.put("project", project);
        projectMap.put("product", product);
        projectMap.put("pcList", pcList);
        projectMap.put("user", user);
        projectMap.put("pdList", pdList);
        projectMap.put("labelList", labelList);
        return projectMap;
    }

    /**
     * 操作项目
     * @param projectid // 项目id
     * @param type      // 类型: pass, unpass
     * @return
     */
    @RequestMapping(value = "/projectHandler")
    @ResponseBody
    public String projectHandler(Integer projectid, String type, HttpServletRequest request, HttpSession session) {
        Project project = this.projectService.getObjById(projectid);
        String openid = this.userService.getOpenidById(project.getUserid());
        String url = DataUtils.WEB_PHONE_HOMPAGE;

        if(DataUtils.PROJECT_STATUS_PASS.equals(type)) {
            project.setStatus(DataUtils.I_PROJECT_CROWFUNDING);

            url += "/front/Project/projectInfo.do?id=" + projectid;
            // 通过微信发送项目通过消息
            String sCompany = DataUtils.getProjectType(project.getType());
            WxPayApi.sendProjectStartMsg(openid, url, project.getTitle(), project.getTargetnum() + " " + sCompany, project.getEnddate());

            // 修改项目状态
            if(this.projectService.update(project) != 0){
                return "success";
            }
        } else if(DataUtils.PROJECT_STATUS_AGAINST.equals(type)) {
            project.setStatus(DataUtils.I_PROJECT_AGAINST);

            // 通过微信发送项目审核失败消息
            String reason = "不符合发布作品条例";
            WxPayApi.sendExamineFailMsg(openid, url, project.getTitle(), reason, DateUtils.getCurrentDate2());

            // 删除项目
            if(this.projectService.delete(projectid, session) != 0){
                return "success";
            }
        }
        return "审核项目失败!";
    }

    /**
     * 删除项目
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "/deleteProject")
    @ResponseBody
    public String deleteProject(Integer id, HttpSession session) {
        if(this.projectService.delete(id, session) != 0){
            return "success" ;
        }
        return "删除项目失败!";
    }

    /**
     * 批量删除项目
     * @param key
     * @return
     */
    @RequestMapping(value = "/batchDelete")
    @ResponseBody
    public String batchDelete(String key, HttpSession session) {
        return this.batchDelete(key, projectService, session);
    }

    /**
     * 更新项目
     * @param project
     * @return
     */
    @RequestMapping(value = "/updateProject")
    @ResponseBody
    public String updateProject(@ModelAttribute("project") Project project) {

        return "";
    }
}
