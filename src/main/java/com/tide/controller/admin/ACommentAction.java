package com.tide.controller.admin;

import com.github.pagehelper.PageHelper;
import com.tide.controller.BaseAction;
import com.tide.service.CommentService;
import com.tide.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by wengliemiao on 15/12/29.
 */
@Controller
@RequestMapping(value = "/admin/Comment")
public class ACommentAction extends BaseAction {

    @Autowired
    private CommentService commentService;

    /**
     * 项目热议列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/selectAll")
    public String selectAll(String status, Integer page, Model model) {
        Map<String, Object> pageMap = this.getPageMap(page, "Page", commentService, model);
        PageHelper.startPage(Integer.parseInt(pageMap.get("curr").toString()), DataUtils.PAGE_NUM);

        List<Map<String, Object>> commList = this.commentService.getCommentByStatus(status);
        model.addAttribute("commentList", commList);
        return "admin/comment/commentList";
    }

    /**
     * 删除评论
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteComment")
    @ResponseBody
    public String deleteComment(Integer id, HttpSession session) {
        if(this.commentService.delete(id, session) != 0) {
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
        return this.batchDelete(key, commentService, session);
    }

    /**
     * 通过评论
     * @param id
     * @return
     */
    @RequestMapping(value = "/setCommonPass")
    @ResponseBody
    public String setCommonPass(Integer id) {
        if(this.commentService.setCommonStatus(id, DataUtils.I_C_STATUS_ALREADY_PASS) != 0){
            return "success";
        }
        return "操作失败!";
    }

    /**
     * 否决评论
     * @param id
     * @return
     */
    @RequestMapping(value = "/setCommonAgainst")
    @ResponseBody
    public String setCommonAgainst(Integer id) {
        if(this.commentService.setCommonStatus(id, DataUtils.I_C_STATUS_ALREADY_AGAINST) != 0){
            return "success";
        }
        return "操作失败!";
    }
}
