package com.tide.dao;

import com.tide.bean.Project;

import java.util.List;

public interface ProjectMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(Project record);
    Project selectByPrimaryKey(Integer id);
    List<Project> selectAll();
    int updateByPrimaryKey(Project record);


    int getProjectStatus(Integer id); // 获取项目状态
    int getProjectNum(Integer userid);
    List<Integer> getProjectIdsByUserid(Integer userid);

    List<Project> getProjectDisplay();
    List<Project> getProjectByStatus(Integer status);
    List<Project> getProjectByUid(Integer uid);
    List<Project> getProjectAddByStatus(Integer uid, Integer status);
    List<Project> getProjectByBackNumDesc();
    List<Project> getProjectByBackNumDescDisplay();
    List<Project> getProjectBySearch(String content);
    List<Project> getProjectByType(Integer type);

    Integer getCount();

}