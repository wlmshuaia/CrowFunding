package com.tide.dao;

import com.tide.bean.FocusUserProject;
import java.util.List;

public interface FocusUserProjectMapper {
    int deleteByPrimaryKey(Integer id);
    int deleteByProjectId(Integer projectid);

    int insert(FocusUserProject record);

    FocusUserProject selectByPrimaryKey(Integer id);
    FocusUserProject getFocusProjectByUP(Integer uid, Integer projectid);
    int deleteFocusProjectByUP(Integer uid, Integer projectid);

    List<FocusUserProject> selectAll();
    List<FocusUserProject> getFocusProject(Integer uid);

    int updateByPrimaryKey(FocusUserProject record);
}