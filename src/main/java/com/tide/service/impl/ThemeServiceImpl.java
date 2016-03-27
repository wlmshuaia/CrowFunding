package com.tide.service.impl;

import com.github.pagehelper.PageHelper;
import com.tide.bean.*;
import com.tide.dao.*;
import com.tide.service.ProjectService;
import com.tide.service.ThemeService;
import com.tide.utils.DataUtils;
import com.tide.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wengliemiao on 15/12/17.
 */
@Service("themeService")
public class ThemeServiceImpl implements ThemeService {

    @Autowired
    private ThemeMapper themeMapper;

    @Autowired
    private ThemeLabelMapper themeLabelMapper;

    @Autowired
    private LabelMapper labelMapper;

    @Autowired
    private ProjectService projectService;

//    @Autowired
//    private ProjectMapper projectMapper;

    @Autowired
    private ProjectLabelMapper projectLabelMapper;

    @Override
    public List<Theme> selectAll() {
        return this.themeMapper.selectAll();
    }

    @Override
    public List<Theme> selectAllDisplay() {
        return this.themeMapper.selectAllDisplay();
    }

    @Override
    public int insert(Theme theme) {
        return this.themeMapper.insert(theme);
    }

    @Override
    public int insertThemeLabel(ThemeLabel tl) {
        return themeLabelMapper.insert(tl);
    }

    @Override
    public Object getObjInfoById(Integer id) {
        Theme theme = this.themeMapper.selectByPrimaryKey(id);
        List<ThemeLabel> tlList = this.themeLabelMapper.getByThemeId(id);

        Map<String, Object> themeMap = new HashMap<>();

        List<Label> labelList = new ArrayList<>();
        for (ThemeLabel tl : tlList) {
            Label label = this.labelMapper.selectByPrimaryKey(tl.getLabelid());
            labelList.add(label);
        }
        themeMap.put("theme", theme);
        themeMap.put("labelList", labelList);
        return themeMap;
    }

    @Override
    public int updateThemeLabel(ThemeLabel tl) {
        return this.themeLabelMapper.updateByPrimaryKey(tl);
    }

    @Override
    public int update(Theme theme) {
        return this.themeMapper.updateByPrimaryKey(theme);
    }

    @Override
    public ThemeLabel getByThemeLabelId(Integer themeid, Integer labelid) {
        return this.themeLabelMapper.getByThemeLabelId(themeid, labelid);
    }

    @Override
    public int deleteThemeLabel(Integer id) {
        return this.themeLabelMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<ThemeLabel> getByThemeId(Integer themeid) {
        return this.themeLabelMapper.getByThemeId(themeid);
    }

    @Override
    public Theme getObjById(Integer id) {
        return this.themeMapper.selectByPrimaryKey(id);
    }

    @Override
    public int delete(Integer id, HttpSession session) {
        Integer res = 0;

        // 删除海报
        Theme theme = null;
        if((theme = this.themeMapper.selectByPrimaryKey(id)) != null) {
            boolean flag = FileUtils.deleteFile(FileUtils.getFileRealPath(session, theme.getPosterurl()));
            if(flag == false) {
                return res;
            }

            // 删除theme对象
            res = this.themeMapper.deleteByPrimaryKey(id);
        }
        res = 1;
        return res;
    }

    @Override
    public int getCount() {
        return this.themeMapper.getCount();
    }

    @Override
    public List<Map<String, Object>> getThemeMapList(HttpSession session) {
        List<Map<String, Object>> tMapList = new ArrayList<>();

        // 获取的主题数
        PageHelper.startPage(1, DataUtils.THEME_NUM_INDEX);

        List<Theme> tList = this.selectAllDisplay();
        for (Theme t : tList) {
            Map<String, Object> tMap = new HashMap<>();

            List<Map<String, Object>> pList = new ArrayList<>();

            Integer tid = t.getId();
            List<ThemeLabel> tlList = this.themeLabelMapper.getByThemeId(tid);
            for (ThemeLabel tl : tlList) {
                Integer lid = tl.getLabelid();

                // 每个主题获取的项目数
                PageHelper.startPage(1, DataUtils.THEME_PROJECT_NUM_INDEX);

                List<ProjectLabel> plList = this.projectLabelMapper.getProjectList(lid);
                for (ProjectLabel pl : plList) {
                    Project p = this.projectService.getObjById(pl.getProjectid());
                    if(p.getStatus() != DataUtils.I_PROJECT_WAIT_EXAME && p.getStatus() != DataUtils.I_PROJECT_AGAINST) {
                        Map<String, Object> map = this.projectService.getProjectInfo(p, session);
                        pList.add(map);
                    }
                }
            }

            tMap.put("theme", t);
            tMap.put("pMapList", pList);

            tMapList.add(tMap);
        }
        return tMapList;
    }
    
    @Override
    public List<Map<String, Object>> getThemeProjectByThemeid(Integer themeid, Integer start, HttpSession session) {
        List<Map<String, Object>> pList = new ArrayList<>();

        if(start == null) {
            start = 1;
        }

        PageHelper.startPage(start, DataUtils.INRO_PROJECT_NUM);
        List<ThemeLabel> tlList = this.getByThemeId(themeid);
        for (ThemeLabel tl : tlList) {
            PageHelper.startPage(1, DataUtils.INRO_PROJECT_NUM);
            List<ProjectLabel> plList = this.projectLabelMapper.getProjectList(tl.getLabelid());
            for (ProjectLabel pl : plList) {
                Project project = this.projectService.getObjById(pl.getProjectid());
//                Map<String, Object> map = this.projectService.getProjectInfo(pl.getProjectid(), session);
                if(project.getStatus() != DataUtils.I_C_STATUS_WAIT_EXAME && project.getStatus() != DataUtils.I_C_STATUS_ALREADY_AGAINST) {
                    Map<String, Object> map = this.projectService.getProjectInfo(project, session);
                    pList.add(map);
                }
            }
        }

        return pList;
    }

    @Override
    public List<Theme> getThemeLimit(Integer start) {
        if(start == null) {
            start = 1;
        }
        PageHelper.startPage(start, DataUtils.OBJ_NUM);
        return this.selectAllDisplay();
    }
}
