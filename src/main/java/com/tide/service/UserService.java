package com.tide.service;

import com.tide.bean.FocusUser;
import com.tide.bean.FocusUserProject;
import com.tide.bean.User;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by wengliemiao on 15/12/7.
 */
public interface UserService extends BaseService {
    List<User> selectAll() ;
    List<User> getUserByType(String type);
    User getObjById(Integer id);
    User getUserByOpenid(String openid);
    String getOpenidById(Integer id);
    Map<String, Object> getDesignerInfo(Integer id, HttpSession session);
    Map<String, Object> getDesignerInfo(User user, HttpSession session);
    Map<String, Object> getDesignerProjectsBase(User user, HttpSession session);
    Map<String, Object> getDesignerProjectsDetail(User user, HttpSession session);
    List<User> getUserFocus(Integer uid);
    List<String> getUserLabels(Integer userid);
    int insert(User user);
    int update(User user);

    // focus_user
    List<FocusUser> getFocusUser(Integer uid);
    int ifFocusUser(Integer uid, Integer focusid, String type);

    // focus_user_project
    List<FocusUserProject> getFocusProject(Integer uid);
    int ifFocusProject(Integer uid, Integer projectid, String type) ;
}
