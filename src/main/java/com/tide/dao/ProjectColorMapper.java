package com.tide.dao;

import com.tide.bean.ProjectColor;
import java.util.List;

public interface ProjectColorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectColor record);

    ProjectColor selectByPrimaryKey(Integer id);

    List<ProjectColor> selectAll();

    int updateByPrimaryKey(ProjectColor record);

    List<Integer> getProjectIdsByProid(Integer proid);
    List<Integer> getProIdByProjectId(Integer projectid);
    int deleteByProjectId(Integer projectid);
}