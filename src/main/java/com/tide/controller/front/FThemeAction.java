package com.tide.controller.front;

import com.tide.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by wengliemiao on 16/1/9.
 */
@Controller
@RequestMapping(value = "/front/Theme")
public class FThemeAction {

    @Autowired
    private ThemeService themeService ;

    /**
     * 主题列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/index")
    public String index(Model model) {
//        List<Theme> themeList = this.themeService.selectAllDisplay();
//        model.addAttribute("themeList", themeList);
        return "front/theme/index";
    }

    /**
     * 异步获取主题列表
     * @return
     */
    @RequestMapping(value = "/getThemeList")
    @ResponseBody
    public Object getThemeList(@RequestBody Map<String, String> map) {
        return this.themeService.getThemeLimit(Integer.parseInt(map.get("start")));
    }

    /**
     * 主题详情
     * @return
     */
    @RequestMapping(value = "/themeInfo")
    public String themeInfo(Integer id, Model model) {
        model.addAttribute("themeid", id);
        return "front/theme/themeInfo";
    }

    /**
     * 获取专题详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getThemeInfo")
    @ResponseBody
    public Object getThemeInfo(Integer id) {
        return this.themeService.getObjById(id);
    }

    /**
     * 获取专题项目列表
     * @param paramMap
     * @param session
     * @return
     */
    @RequestMapping(value = "/getThemeDetail")
    @ResponseBody
    public Object getThemeDetail(@RequestBody Map<String, String> paramMap, HttpSession session) {
        Integer start = Integer.parseInt(paramMap.get("start"));
        Integer id = Integer.parseInt(paramMap.get("id"));

        return this.themeService.getThemeProjectByThemeid(id, start, session);
    }
}
