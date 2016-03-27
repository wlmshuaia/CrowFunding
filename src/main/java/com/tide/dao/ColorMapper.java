package com.tide.dao;

import com.tide.bean.Color;
import java.util.List;

public interface ColorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Color record);

    Color selectByPrimaryKey(Integer id);
    Color selectByName(String name);

    List<Color> selectAll();

    int updateByPrimaryKey(Color record);

    String getColorname(Integer id);
}