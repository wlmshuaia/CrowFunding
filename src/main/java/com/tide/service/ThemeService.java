package com.tide.service;

import com.tide.bean.Theme;
import com.tide.bean.ThemeLabel;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by wengliemiao on 15/12/17.
 */
public interface ThemeService extends BaseService {
    // theme
    List<Theme> selectAll();
    List<Theme> selectAllDisplay();
    List<Theme> getThemeLimit(Integer start);
    List<Map<String, Object>> getThemeMapList(HttpSession session);
    List<Map<String, Object>> getThemeProjectByThemeid(Integer themeid, Integer start, HttpSession session);

    int insert(Theme theme);
    int update(Theme theme);
    Theme getObjById(Integer id);
    Object getObjInfoById(Integer id);

    // theme_label
    int insertThemeLabel(ThemeLabel tl);
    int updateThemeLabel(ThemeLabel tl);
    List<ThemeLabel> getByThemeId(Integer themeid);
    ThemeLabel getByThemeLabelId(Integer themeid, Integer labelid);
    int deleteThemeLabel(Integer id);
}
