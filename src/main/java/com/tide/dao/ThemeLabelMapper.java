package com.tide.dao;

import com.tide.bean.ThemeLabel;
import java.util.List;

public interface ThemeLabelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ThemeLabel record);

    ThemeLabel selectByPrimaryKey(Integer id);
    ThemeLabel getByThemeLabelId(Integer themeid, Integer labelid);

    List<ThemeLabel> selectAll();
    List<ThemeLabel> getByThemeId(Integer themeid);

    int updateByPrimaryKey(ThemeLabel record);
}