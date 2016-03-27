package com.tide.dao;

import com.tide.bean.ProjectBacker;
import java.util.List;

public interface ProjectBackerMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByProjectId(Integer projectid);
    Integer deleteByTradeNo(String tradeno);
    int getProjectBackerNum(Integer projectid); // 支持者数

    int insert(ProjectBacker record);

    ProjectBacker selectByPrimaryKey(Integer id);
    ProjectBacker getByTradeno(String tradeno);

    List<ProjectBacker> selectAll();
    List<ProjectBacker> getByProjectId(Integer projectid); // 支持者列表
    List<ProjectBacker> getByUserId(Integer uid);   // 获取用户支持过的项目

    int updateByPrimaryKey(ProjectBacker record);
}