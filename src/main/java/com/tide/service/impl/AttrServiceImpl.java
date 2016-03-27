package com.tide.service.impl;

import com.tide.bean.Color;
import com.tide.bean.Size;
import com.tide.dao.ColorMapper;
import com.tide.dao.SizeMapper;
import com.tide.service.AttrService;
import com.tide.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wengliemiao on 15/12/11.
 */
@Service("attrService")
public class AttrServiceImpl implements AttrService {
    @Autowired
    private ColorMapper colorMapper;

    @Autowired
    private SizeMapper sizeMapper;

    @Override
    public List<Color> selectColor() {
        return this.colorMapper.selectAll();
    }

    @Override
    public String getColorname(Integer id) {
        return this.colorMapper.getColorname(id);
    }

    @Override
    public String getSizename(Integer id) {
        return this.sizeMapper.getSizename(id);
    }

    @Override
    public Color selectColorById(Integer id) {
        return this.colorMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteColor(Integer id) {
        this.colorMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Color selectColorByName(String name) {
        return this.colorMapper.selectByName(name);
    }

    /**
     * 获取属性: 颜色或尺码
     *
     * @param id
     * @param type
     * @return
     */
    public Object getAttrById(Integer id, String type) {
        if (DataUtils.ATTR_COLOR.equals(type)) {
            return this.colorMapper.selectByPrimaryKey(id);
        } else if (DataUtils.ATTR_SIZE.equals(type)) {
            return this.sizeMapper.selectByPrimaryKey(id);
        }
        return null;
    }

    /**
     * 更新属性
     * @param map
     * @return
     */
    public int updateAttr(Map<String, String> map) {
        Integer id = Integer.parseInt(map.get("id").toString());
        String name = map.get("name").toString();
        String type = map.get("type").toString();

        if(DataUtils.ATTR_COLOR.equals(type)) {
            String colorcard = map.get("colorcard").toString() ;
            Color c = new Color() ;
            c.setId(id);
            c.setColorname(name);
            c.setColorcard(colorcard);
            return this.colorMapper.updateByPrimaryKey(c);
        } else if(DataUtils.ATTR_SIZE.equals(type)) {
            Size s = new Size() ;
            s.setId(id);
            s.setSizename(name);
            return this.sizeMapper.updateByPrimaryKey(s);
        }
        return 0;
    }

    @Override
    public List<Size> selectSize() {
        return this.sizeMapper.selectAll();
    }

    @Override
    public void deleteSize(Integer id) {
        this.sizeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insertColor(Color color) {
        this.colorMapper.insert(color);
    }

    @Override
    public void insertSize(Size size) {
        this.sizeMapper.insert(size);
    }

}
