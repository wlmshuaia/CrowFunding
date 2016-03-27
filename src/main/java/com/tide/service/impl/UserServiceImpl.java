package com.tide.service.impl;

import com.tide.bean.FocusUser;
import com.tide.bean.FocusUserProject;
import com.tide.bean.User;
import com.tide.dao.*;
import com.tide.service.ProjectService;
import com.tide.service.UserService;
import com.tide.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wengliemiao on 15/12/7.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper ;

    @Autowired
    private FocusUserMapper focusUserMapper;

    @Autowired
    private FocusUserProjectMapper focusUserProjectMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ProjectLabelMapper projectLabelMapper;

    @Autowired
    private LabelMapper labelMapper;

    @Autowired
    private ProjectService projectService;

    @Override
    public User getObjById(Integer id) {
        return this.userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> selectAll() {
        return this.userMapper.selectAll();
    }

    @Override
    public int delete(Integer id, HttpSession session) {
        return 0;
    }

    @Override
    public int getCount() {
        return this.userMapper.getCount();
    }

    @Override
    public List<User> getUserByType(String type) {
        List<User> uList;
        if(DataUtils.USER_TYPE_NORMAL.equals(type)) { // 普通用户
            uList = this.userMapper.getUserByType(DataUtils.I_TYPE_NORMAL);
        } else if(DataUtils.USER_TYPE_DESIGNER.equals(type)) { // 设计师类型
            uList = this.userMapper.getUserByType(DataUtils.I_TYPE_DESIGNER);
        } else { // 所有用户
            uList = this.userMapper.selectAll();
        }
        return uList;
    }

    @Override
    public List<User> getUserFocus(Integer uid) {
        List<User> uList = new ArrayList<>();
        List<FocusUser> fuList = this.focusUserMapper.getFocusUser(uid);
        for (FocusUser fu : fuList) {
            User u = this.userMapper.selectByPrimaryKey(fu.getFocusuid());
            uList.add(u);
        }
        return uList;
    }

    @Override
    public List<FocusUser> getFocusUser(Integer uid) {
        return this.focusUserMapper.getFocusUser(uid);
    }

    @Override
    public List<FocusUserProject> getFocusProject(Integer uid) {
        return this.focusUserProjectMapper.getFocusProject(uid);
    }

    @Override
    public int ifFocusUser(Integer uid, Integer focusid, String type) {
        FocusUser fu = new FocusUser();
        fu.setUserid(uid);
        fu.setFocusuid(focusid);

        if("focus".equals(type)) {
            return this.focusUserMapper.insert(fu);
        }
        return this.focusUserMapper.deleteFocusUserByUF(uid, focusid);
    }

    @Override
    public int ifFocusProject(Integer uid, Integer projectid, String type) {
        FocusUserProject fup = new FocusUserProject();

        fup.setUserid(uid);
        fup.setProjectid(projectid);

        if("focus".equals(type)) {
            return this.focusUserProjectMapper.insert(fup);
        }
        return this.focusUserProjectMapper.deleteFocusProjectByUP(uid, projectid);
    }

    @Override
    public int insert(User user) {
        return this.userMapper.insert(user);
    }

    @Override
    public User getUserByOpenid(String openid) {
        return this.userMapper.getUserByOpenid(openid);
    }

    @Override
    public String getOpenidById(Integer id) {
        return this.userMapper.getOpenidById(id);
    }

    @Override
    public int update(User user) {
        return this.userMapper.updateByPrimaryKey(user);
    }

    @Override
    public Map<String, Object> getDesignerInfo(Integer id, HttpSession session) {
        User user = this.userMapper.selectByPrimaryKey(id);
        return this.getDesignerInfo(user, session);
    }

    @Override
    public Map<String, Object> getDesignerInfo(User user, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        Integer pNum = this.projectMapper.getProjectNum(user.getId());
        Integer fNum = this.focusUserMapper.getFocuserNum(user.getId());
        List<String> lList = this.getUserLabels(user.getId());

        /**
         * 是否关注该设计师
         */
        Integer uid = Integer.parseInt(session.getAttribute("userid").toString());
        Integer iFocus = 0;
        if(this.focusUserMapper.getFocusUserByUF(uid, user.getId()) != null) {
            iFocus = 1;
        }

        map.put("info", user);
        map.put("projectnum", pNum);
        map.put("focusernum", fNum);
        map.put("labels", lList);
        map.put("ifFocus", iFocus);
        return map;
    }

    public List<String> getUserLabels(Integer userid) {
        List<String> lnList = new ArrayList<>();

        List<Integer> projectIdList = this.projectMapper.getProjectIdsByUserid(userid);
        for (Integer pid : projectIdList) {
            List<Integer> labelIdList = this.projectLabelMapper.getLabelIdList(pid);

            for (Integer lid : labelIdList) {
                String labelName = this.labelMapper.getLabelName(lid);
                if(!lnList.contains(labelName)) {
                    lnList.add(labelName);
                }
            }
        }

        return lnList;
    }

    @Override
    public Map<String, Object> getDesignerProjectsBase(User user, HttpSession session) {
        // 获取设计师信息
        Map<String, Object> designer = this.getDesignerInfo(user, session);

        // 获取项目列表
        List<Map<String, Object>> pMapList = this.projectService.getProjectBaseMapByUid(user.getId(), session);

        Map<String, Object> map = new HashMap<>();
        map.put("designer", designer);
        map.put("projects", pMapList);
        return map;
    }

    @Override
    public Map<String, Object> getDesignerProjectsDetail(User user, HttpSession session) {
        return null;
    }
}
