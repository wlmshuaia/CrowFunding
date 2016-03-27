package com.tide.service.impl;

import com.tide.bean.Label;
import com.tide.dao.LabelMapper;
import com.tide.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by wengliemiao on 15/12/12.
 */
@Service("labelService")
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelMapper labelMapper;

    @Override
    public List<Label> selectAllDisplay() {
        return this.labelMapper.selectAllDisplay();
    }

    @Override
    public List<Label> selectAll() {
        return this.labelMapper.selectAll();
    }

    @Override
    public List<Label> selectByLimit(Integer start, Integer size) {
        return null;
    }

    @Override
    public Label getObjById(Integer id) {
        return this.labelMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insert(Label label) {
        return this.labelMapper.insert(label);
    }

    @Override
    public int delete(Integer id, HttpSession session) {
        return this.labelMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Label label) {
        return this.labelMapper.updateByPrimaryKey(label);
    }

    @Override
    public int getCount() {
        return this.labelMapper.getCount();
    }
}
