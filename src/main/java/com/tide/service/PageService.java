package com.tide.service;

import com.tide.bean.Page;

import java.util.List;

/**
 * Created by wengliemiao on 15/12/20.
 */
public interface PageService extends BaseService {
    public List<Page> selectAll();
    public List<Page> selectAllDisplay();
    public int insert(Page page);
    public int update(Page page);
    public Page getObjById(Integer id);
}
