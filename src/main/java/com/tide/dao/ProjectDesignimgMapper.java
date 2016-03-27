package com.tide.dao;

import com.tide.bean.ProjectDesignimg;
import java.util.List;

public interface ProjectDesignimgMapper {
    int deleteByPrimaryKey(Integer id);
    int deleteByProjectId(Integer projectid);

    int insert(ProjectDesignimg record);

    ProjectDesignimg selectByPrimaryKey(Integer id);

    List<ProjectDesignimg> selectAll();
    List<ProjectDesignimg> getDesignimgList(Integer projectid);

    int updateByPrimaryKey(ProjectDesignimg record);
}