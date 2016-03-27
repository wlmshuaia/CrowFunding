package com.tide.service;

import com.tide.bean.Label;

import java.util.List;

/**
 * Created by wengliemiao on 15/12/12.
 */
public interface LabelService extends BaseService{
    public List<Label> selectAll();
    public List<Label> selectAllDisplay();
    public List<Label> selectByLimit(Integer start, Integer size);
    public Label getObjById(Integer id);
    public int insert(Label label);
    public int update(Label label);
}
