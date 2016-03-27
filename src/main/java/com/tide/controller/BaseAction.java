package com.tide.controller;

import com.tide.service.BaseService;
import com.tide.utils.DataUtils;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wengliemiao on 15/12/13.
 */
public class BaseAction {
    /**
     * 批量删除
     * @param key
     * @param baseService
     * @return
     */
    protected String batchDelete(String key, BaseService baseService, HttpSession session) {
        Integer res = 0;
        String[] aId = key.split(",");
        for (String sId : aId) {
            if(!"".equals(sId)) {
                if(baseService.delete(Integer.parseInt(sId), session) != 0) {
                    res = 1;
                }
            }
        }
        if(res != 0) {
            return "success";
        }
        return "批量删除失败";
    }

    /**
     * 获取分页相关属性
     * @param page
     * @param modelName
     * @param baseService
     * @param model
     * @return
     */
    protected Map<String, Object> getPageMap(Integer page, String modelName, BaseService baseService, Model model) {
        if(page == null) {
            page = 1;
        }

        Integer count = baseService.getCount();
        Integer totalPage = count % DataUtils.PAGE_NUM == 0 ? count / DataUtils.PAGE_NUM : (count/DataUtils.PAGE_NUM+1);

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("curr", page);
        pageMap.put("totalPage", totalPage);
        pageMap.put("model", modelName);
        model.addAttribute("pageMap", pageMap);
        return pageMap;
    }
}
