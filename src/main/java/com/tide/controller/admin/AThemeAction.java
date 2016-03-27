package com.tide.controller.admin;

import com.github.pagehelper.PageHelper;
import com.tide.bean.Label;
import com.tide.bean.Theme;
import com.tide.bean.ThemeLabel;
import com.tide.controller.BaseAction;
import com.tide.service.LabelService;
import com.tide.service.ThemeService;
import com.tide.utils.DataUtils;
import com.tide.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 *  主题管理
 * Created by wengliemiao on 15/12/17.
 */
@Controller
@RequestMapping(value = "/admin/Theme")
public class AThemeAction extends BaseAction {

    @Autowired
    private ThemeService themeService;

    @Autowired
    private LabelService labelService ;

    /**
     * 主题列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/selectAll")
    public String selectAll(Integer page, Model model) {
        Map<String, Object> pageMap = this.getPageMap(page, "Theme", themeService, model);
        PageHelper.startPage(Integer.parseInt(pageMap.get("curr").toString()), DataUtils.PAGE_NUM);

        List<Theme> tList = this.themeService.selectAll();
        model.addAttribute("themeList", tList);
        return "admin/theme/themeList";
    }

    /**
     * 添加主题页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/addTheme")
    public String addTheme(Model model) {
        List<Label> labelList = this.labelService.selectAllDisplay();
        model.addAttribute("labelList", labelList);
        return "admin/theme/addTheme";
    }

    /**
     * 添加主题表单处理
     * @param theme
     * @param label
     * @param post
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/addThemeHandle")
    public String addThemeHandle(@ModelAttribute("theme") Theme theme,
                                 String[] label,
                                 MultipartFile post,
                                 HttpSession session,
                                 Model model) {

        if(!post.isEmpty()) {
            // 存储图片
            String postUrl = FileUtils.saveFile(post, session);
            theme.setPosterurl(postUrl);
            this.themeService.insert(theme);
        } else {
            model.addAttribute("errorMsg", "海报为空!");
            return "admin/error";
        }

        if(label != null) {
            for (String l : label) {
                ThemeLabel tl = new ThemeLabel();
                tl.setThemeid(theme.getId());
                tl.setLabelid(Integer.parseInt(l));
                this.themeService.insertThemeLabel(tl);
            }
        } else {
            model.addAttribute("errorMsg", "主题标签为空!");
            return "admin/error";
        }

        return "redirect:/admin/Theme/selectAll.do";
    }

    /**
     * 获取主题信息: 返回 map 类型(基本信息+标签)
     * @param id
     * @return
     */
    @RequestMapping(value = "/getThemeInfoById")
    @ResponseBody
    public Object getThemeInfoById(Integer id) {
        return this.themeService.getObjInfoById(id);
    }

    /**
     * 更新主题
     * @param theme
     * @param labels
     * @param poster
     * @return
     */
    @RequestMapping(value = "/updateTheme")
    @ResponseBody
    public String updateTheme(@ModelAttribute Theme theme,
                              String[] labels,
                              @RequestParam("poster") MultipartFile poster,
                              HttpSession session) {

        // 旧 Theme 对象
        Theme oldTheme = this.themeService.getObjById(theme.getId());

        // 拼接 theme
        if(!poster.isEmpty()) {
            String posterurl = FileUtils.saveFile(poster, session);
            FileUtils.deleteFile(FileUtils.getFileRealPath(session, oldTheme.getPosterurl()));
            theme.setPosterurl(posterurl);
        } else {
            theme.setPosterurl(oldTheme.getPosterurl());
        }

        // 保存 theme
        if(this.themeService.update(theme) != 0) {

            // 旧 标签
            List<ThemeLabel> oldLabels = this.themeService.getByThemeId(theme.getId());

            for(String l : labels) { // 保存 theme_label
                Boolean bHasLabel = false;
                for(ThemeLabel tmpTl : oldLabels) {
                    if(tmpTl.getLabelid().toString().equals(l)) {
                        oldLabels.remove(tmpTl);
                        bHasLabel = true;
                        break ;
                    }
                }
                // 旧标签未包含则插入
                if(bHasLabel == false) {
                    ThemeLabel tl = new ThemeLabel();
                    tl.setLabelid(Integer.parseInt(l));
                    tl.setThemeid(theme.getId());
                    this.themeService.insertThemeLabel(tl);
                }
            }

            // 删除多余的标签
            for(ThemeLabel tmpTl : oldLabels) {
                this.themeService.deleteThemeLabel(tmpTl.getId());
            }

        } else {
            return "更新主题出错!";
        }

        return "success";
    }

    /**
     * 删除主题
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteTheme")
    @ResponseBody
    public String delete(Integer id, HttpSession session) {
        if(this.themeService.delete(id, session) != 0) {
            return "success";
        }
        return "删除失败";
    }

    /**
     * 批量删除
     * @param key
     * @return
     */
    @RequestMapping(value = "/batchDelete")
    @ResponseBody
    public String batchDelete(String key, HttpSession session) {
        return this.batchDelete(key, themeService, session);
    }
}
