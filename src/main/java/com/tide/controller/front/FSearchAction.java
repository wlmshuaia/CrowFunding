package com.tide.controller.front;

import com.github.pagehelper.PageHelper;
import com.tide.bean.Cate;
import com.tide.bean.Label;
import com.tide.service.CateService;
import com.tide.service.LabelService;
import com.tide.service.ProjectService;
import com.tide.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wengliemiao on 16/1/6.
 */
@Controller
@RequestMapping(value = "/front/Search")
public class FSearchAction {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private LabelService labelService;

    @Autowired
    private CateService cateService;

    /**
     * 根据不同类型筛选数据
     * @param map
     * @param session
     * @return
     */
    @RequestMapping(value = "/getByType")
    @ResponseBody
    public Object getByType(@RequestBody Map<String, String> map, HttpSession session) {
        Integer start = Integer.parseInt(map.get("start"));
        String type = map.get("type");
        if(start == null) {
            start = 1;
        }

        if(type != null) {
            PageHelper.startPage(start, DataUtils.INRO_PROJECT_NUM);
            return this.projectService.getProjectByType(type, session);
        }
        return "error";
    }

    /**
     * 获取热门标签, 分类
     * @return
     */
    @RequestMapping(value = "/getHot")
    @ResponseBody
    public Object getHot() {
        Map<String, Object> map = new HashMap<>();

        // 分类: 选择所有一级分类
        //List<Cate> cateList = this.cateService.selectAll();
        List<Cate> cateList = this.cateService.selectCateByPid(0);

        // 热门
        List<Label> labelList = this.labelService.selectAll();

        map.put("hotLabels", labelList);
        map.put("hotCates", cateList);
        return map;
    }

    /**
     * 搜索
     * @return
     */
    @RequestMapping(value = "/toSearch")
    @ResponseBody
    public Object toSearch(@RequestBody Map<String, String> map, HttpSession session) {
        String content = map.get("content");
        Integer start = Integer.parseInt(map.get("start"));
        PageHelper.startPage(start, DataUtils.INRO_PROJECT_NUM);
        return this.projectService.getProjectBySearch(content, session);
    }

    /**
     * 根据标签筛选项目
     * @param map
     * @param session
     * @return
     */
    @RequestMapping(value = "/searchByLabel")
    @ResponseBody
    public Object searchByLabel(@RequestBody Map<String, String> map, HttpSession session) {
        Integer start = Integer.parseInt(map.get("start"));
        String labelid = map.get("labelid");
        if(start == null) {
            start = 1;
        }

        if(labelid != null) {
            PageHelper.startPage(start, DataUtils.INRO_PROJECT_NUM);
            return this.projectService.getProjectByLabelId(Integer.parseInt(labelid), session);
        }
        return "error";
    }

    /**
     * 根据分类筛选项目
     * @param map
     * @param session
     * @return
     */
    @RequestMapping(value = "/searchByCate")
    @ResponseBody
    public Object searchByCate(@RequestBody Map<String, String> map, HttpSession session) {
        Integer start = Integer.parseInt(map.get("start"));
        String cateid = map.get("cateid");
        if(start == null) {
            start = 1;
        }

        if(cateid != null) {
            PageHelper.startPage(start, DataUtils.INRO_PROJECT_NUM);
            return this.projectService.getProjectByCateId(Integer.parseInt(cateid), session);
//            return this.projectService.getProjectByCateId(Integer.parseInt(cateid), start, session);
        }
        return "error";
    }
}



