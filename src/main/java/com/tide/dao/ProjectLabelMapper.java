package com.tide.dao;

import com.tide.bean.ProjectLabel;
import java.util.List;

public interface ProjectLabelMapper {
    int deleteByPrimaryKey(Integer id);
    int deleteByProjectId(Integer projectid);

    int insert(ProjectLabel record);

    ProjectLabel selectByPrimaryKey(Integer id);

    List<ProjectLabel> selectAll();
    List<ProjectLabel> getLabelList(Integer projectid);
    List<Integer> getLabelIdList(Integer projectid);
    List<ProjectLabel> getProjectList(Integer labelid);

    int updateByPrimaryKey(ProjectLabel record);
}