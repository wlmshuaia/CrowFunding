package com.tide.controller.admin;

import com.github.pagehelper.PageHelper;
import com.tide.bean.Label;
import com.tide.controller.BaseAction;
import com.tide.service.LabelService;
import com.tide.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by wengliemiao on 15/12/12.
 */
@Controller
@RequestMapping(value = "/admin/Label")
public class ALabelAction extends BaseAction {

    @Autowired
    private LabelService labelService;

    /**
     * 返回所有标签: 普通格式
     * @param model
     * @return
     */
    @RequestMapping(value = "/selectAll")
    public String selectAll(Integer page, Model model) {
        Map<String, Object> pageMap = this.getPageMap(page, "Label", labelService, model);
        PageHelper.startPage(Integer.parseInt(pageMap.get("curr").toString()), DataUtils.PAGE_NUM);

        List<Label> lList = this.labelService.selectAll();
        model.addAttribute("labelList", lList);
        return "admin/project/labelList";
    }

    /**
     * 返回所有标签: json 格式
     * @return
     */
    @RequestMapping(value = "/selectAllJson")
    @ResponseBody
    public Object selectAllJson() {
        return this.labelService.selectAll();
    }

    /**
     * 添加标签
     * @param map
     * @return
     */
    @RequestMapping(value = "/addLabel")
    @ResponseBody
    public String addLabel(@RequestBody Map<String, String> map) {
        Label label = new Label() ;
        if(map.get("labelname") != null) {
            label.setLabelname(map.get("labelname").toString());
        } else {
            return "标签名称为空";
        }
        if(map.get("status") != null) {
            label.setStatus(Integer.parseInt(map.get("status").toString()));
        } else {
            return "标签状态";
        }
        label.setType(0);
        this.labelService.insert(label);
        return "success";
    }

    /**
     * 根据主键id获取标签对象
     * @param id
     * @return
     */
    @RequestMapping(value = "/getObjById")
    @ResponseBody
    public Object getObjById(Integer id) {
        Label label = this.labelService.getObjById(id);
        if(label != null) {
            return label;
        }
        return "该标签不存在" ;
    }

    /**
     * 更新标签
     * @param label
     * @return
     */
    @RequestMapping(value = "/updateLabel")
    @ResponseBody
    public String updateLabel(@RequestBody Label label) {
        if(this.labelService.update(label) != 0){
            return "success";
        }
        return "更新标签失败!";
    }

    /**
     * 删除标签
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteLabel")
    @ResponseBody
    public String deleteLabel(Integer id, HttpSession session) {
        if(this.labelService.delete(id, session) != 0) {
            return "success";
        }
        return "删除失败!";
    }

    /**
     * 批量删除
     * @param key
     * @return
     */
    @RequestMapping(value = "/batchDelete")
    @ResponseBody
    public String batchDelete(String key, HttpSession session) {
        return this.batchDelete(key, labelService, session);
    }

}
