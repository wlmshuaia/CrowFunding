package com.tide.service.impl;

import com.tide.bean.Page;
import com.tide.dao.PageMapper;
import com.tide.service.PageService;
import com.tide.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by wengliemiao on 15/12/20.
 */
@Service("pageService")
public class PageServiceImpl implements PageService {

    @Autowired
    private PageMapper pageMapper;

    @Override
    public Page getObjById(Integer id) {
        return this.pageMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Page page) {
        return this.pageMapper.updateByPrimaryKey(page);
    }

    @Override
    public List<Page> selectAll() {
        return this.pageMapper.selectAll();
    }

    @Override
    public int insert(Page page) {
        return this.pageMapper.insert(page);
    }

    @Override
    public List<Page> selectAllDisplay() {
        return this.pageMapper.selectAllDisplay();
    }

    @Override
    public int delete(Integer id, HttpSession session) {
        Integer res = 0;

        // 删除图片
        Page page = null ;
        if((page = this.pageMapper.selectByPrimaryKey(id)) != null) {
            boolean flag = FileUtils.deleteFile(FileUtils.getFileRealPath(session, page.getPosterurl()));
            if(flag == false) {
                return res;
            }

            // 删除对象
            res = this.pageMapper.deleteByPrimaryKey(id);

            return res;
        }
        res = 1;
        return res;
    }

    @Override
    public int getCount() {
        return this.pageMapper.getCount();
    }
}
