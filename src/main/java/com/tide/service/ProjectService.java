package com.tide.service;

import com.tide.bean.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by wengliemiao on 15/12/12.
 */
public interface ProjectService extends BaseService {
    // project
    List<Project> selectAll();
    List<Project> getProjectByStatus(Integer status);
    List<Project> getProjectByStatus(String status);
    List<Map<String, Object>> getProjectByType(String type, HttpSession session) ;    // 前端根据项目类型搜索
    List<Map<String, Object>> getProjectByLabelId(Integer labelid, HttpSession session) ;    // 前端根据标签搜索
    List<Map<String, Object>> getProjectByCateId(Integer cateid, HttpSession session) ;    // 前端根据分类搜索
    List<Map<String, Object>> getProjectByCateId(Integer cateid, Integer start, HttpSession session) ;    // 前端根据分类搜索 test
//    int getProjectByTypeCount(String type);
    List<Map<String, Object>> getProjectBySearch(String content, HttpSession session); // 搜索项目
    List<Project> getProjectByUid(Integer uid);      // 根据用户id获取项目
    List<Project> getProjectAddByStatus(Integer uid, String status);  // 发布的项目
    List<Project> getProjectFocusByStatus(Integer uid, String status);// 关注的项目
    List<Project> getProjectBackByStatus(Integer uid, String status); // 支持的项目
    Map<String, Object> getProjectInfo(Project p, HttpSession session);
    Map<String, Object> getProjectInfo(Integer id, HttpSession session);
    List<Map<String, Object>> getProjectBaseMapByUid(Integer uid, HttpSession session);
    // 获取project基本信息: project, 主图
    Map<String, Object> getProjectBaseInfo(Integer id, HttpSession session);
    Map<String, Object> getProjectBaseInfo(Project p, HttpSession session);
    Project getObjById(Integer id);
    Project getProjectByTradeno(String tradeno);
    int insert(Project project);
    int update(Project project);
    String sendBuySuccessMsgToBackers(Integer projectid);
    String sendBuyFailMsgToBackers(Integer projectid);
    String refundToBackers(Integer projectid);
    List<Project> getProjectByType(String type);

    // project_designimg
    int insertProjectDesignimg(ProjectDesignimg pd);
    List<ProjectDesignimg> getDesignimgList(Integer projectid);

    // project_label
    int insertProjectLabel(ProjectLabel pl) ;
    List<Label> getLabelList(Integer projectid);
    List<ProjectLabel> getProjectLabelList(Integer projectid);

    // project_backer
    int getProjectBackerNum(Integer projectid);  // 支持者数
    int getProjectFundNum(Integer projectid);    // 筹集件数
    List<Map<String, Object>> getProjectBackerList(Integer projectid);
    int insertProjectBacker(ProjectBacker projectBacker);
    int deleteProjectBacker(Integer id);
    int deleteProjectBackerByTradeno(String tradeno);
    ProjectBacker getProjectBackerById(Integer id);
    ProjectBacker getProjectBackerByTradeno(String tradeno);
    int updateProjectBacker(ProjectBacker projectBacker);

    // project_color
    int insertProjectColor(ProjectColor projectColor);
    int getProIdByProjectId(Integer projectid);
}
