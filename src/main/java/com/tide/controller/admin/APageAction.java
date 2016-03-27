package com.tide.controller.admin;

import com.github.pagehelper.PageHelper;
import com.tide.bean.Page;
import com.tide.controller.BaseAction;
import com.tide.service.PageService;
import com.tide.utils.DataUtils;
import com.tide.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by wengliemiao on 15/12/20.
 */
@Controller
@RequestMapping(value = "/admin/Page")
public class APageAction extends BaseAction {

    @Autowired
    private PageService pageService;

    /**
     * 首页轮播图
     * @param model
     * @return
     */
    @RequestMapping(value = "/selectAll")
    public String selectAll(Integer page, Model model) {
        Map<String, Object> pageMap = this.getPageMap(page, "Page", pageService, model);
        PageHelper.startPage(Integer.parseInt(pageMap.get("curr").toString()), DataUtils.PAGE_NUM);

        List<Page> pageList = this.pageService.selectAll();
        model.addAttribute("pageList", pageList);
        return "admin/page/pageList";
    }

    /**
     * 添加首页
     * @param page
     * @param poster
     * @param session
     * @return
     */
    @RequestMapping(value = "/addPageHandle")
    public String addPageHandle(@ModelAttribute Page page,
                                MultipartFile poster,
                                HttpSession session) {
        if(!poster.isEmpty()) {
            String posturl = FileUtils.saveFile(poster, session);
            page.setPosterurl(posturl);

            this.pageService.insert(page);
        }

        return "redirect:/admin/Page/selectAll.do";
    }

    /**
     * 获取Page对象
     * @param id
     * @return
     */
    @RequestMapping(value = "/getObjById")
    @ResponseBody
    public Object getObjById(Integer id) {
        return this.pageService.getObjById(id);
    }

    /**
     * 更新Page对象
     * @param page
     * @param poster
     * @param session
     * @return
     */
    @RequestMapping(value = "/updatePage")
    @ResponseBody
    public String updatePage(@ModelAttribute Page page,
                             MultipartFile poster,
                             HttpSession session) {

        Page oldPage = this.pageService.getObjById(page.getId());

        if(!poster.isEmpty()) {
            // 保存新图片
            String posterurl = FileUtils.saveFile(poster, session);
            page.setPosterurl(posterurl);

            // 删除旧图片
            FileUtils.deleteFile(FileUtils.getFileRealPath(session, oldPage.getPosterurl()));
        } else {
            page.setPosterurl(oldPage.getPosterurl());
        }

        if(this.pageService.update(page) != 0) {
            return "success";
        }
        return "操作未更新!";
    }

    /**
     * 删除页面
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "/deletePage")
    @ResponseBody
    public String deletePage(Integer id, HttpSession session) {
        System.out.println(id);
        if(this.pageService.delete(id, session) != 0) {
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
        System.out.println("use baseAction");
        return this.batchDelete(key, pageService, session);
    }
}
