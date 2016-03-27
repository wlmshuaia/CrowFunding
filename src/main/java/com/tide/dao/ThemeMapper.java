package com.tide.dao;

import com.tide.bean.Theme;
import java.util.List;

public interface ThemeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Theme record);
    int getCount();

    Theme selectByPrimaryKey(Integer id);

    List<Theme> selectAll();
    List<Theme> selectAllDisplay();

    int updateByPrimaryKey(Theme record);
}