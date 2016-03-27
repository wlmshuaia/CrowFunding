package com.tide.service;

import com.tide.bean.Color;
import com.tide.bean.Size;

import java.util.List;
import java.util.Map;

/**
 * 商品属性管理
 * Created by wengliemiao on 15/12/11.
 */
public interface AttrService {
    // zc_color
    public void insertColor(Color color) ;
    public List<Color> selectColor() ;
    public void deleteColor(Integer id);
    public Color selectColorByName(String name);
    public Color selectColorById(Integer id);
    public String getColorname(Integer id);

    // zc_size
    public void insertSize(Size size) ;
    public List<Size> selectSize();
    public void deleteSize(Integer id) ;
    public String getSizename(Integer id);

    public Object getAttrById(Integer id, String type) ;
    public int updateAttr(Map<String, String> map);

}
