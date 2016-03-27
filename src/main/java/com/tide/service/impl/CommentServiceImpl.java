package com.tide.service.impl;

import com.tide.bean.Comment;
import com.tide.bean.CommentUser;
import com.tide.bean.Project;
import com.tide.bean.User;
import com.tide.dao.CommentMapper;
import com.tide.dao.CommentUserMapper;
import com.tide.dao.ProjectMapper;
import com.tide.dao.UserMapper;
import com.tide.service.CommentService;
import com.tide.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wengliemiao on 15/12/29.
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentUserMapper commentUserMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Map<String, Object>> selectAll() {
        List<Comment> cList = this.commentMapper.selectAll();
        return getCommentMap(cList);
    }

    @Override
    public int delete(Integer id, HttpSession session) {
        return this.commentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Map<String, Object>> getCommentByStatus(String status) {
        List<Comment> cList;
        Integer iStatus;

        if(DataUtils.C_STATUS_WAIT_EXAME.equals(status)) { // 待审核
            iStatus = DataUtils.I_C_STATUS_WAIT_EXAME;
        } else if(DataUtils.C_STATUS_ALREADY_PASS.equals(status)) { // 已通过
            iStatus = DataUtils.I_C_STATUS_ALREADY_PASS;
        } else if(DataUtils.C_STATUS_ALREADY_AGAINST.equals(status)) { // 已否决
            iStatus = DataUtils.I_C_STATUS_ALREADY_AGAINST;
        } else { // 全部评论
            iStatus = -1;
        }

        if(iStatus == -1) {
            cList = this.commentMapper.selectAll();
        } else {
            cList = this.commentMapper.getCommentByStatus(iStatus);
        }
        return getCommentMap(cList);
    }

    /**
     * 将评论封装成map类型
     * @param cList
     * @return
     */
    public List<Map<String, Object>> getCommentMap(List<Comment> cList){
        List<Map<String, Object>> commList = new ArrayList<>();
        for (Comment c : cList) {
            Project p = this.projectMapper.selectByPrimaryKey(c.getProjectid());
            User u = this.userMapper.selectByPrimaryKey(c.getUserid());

            Map<String, Object> map = new HashMap<>();
            map.put("comment", c);
            map.put("project", p);
            map.put("user", u);
            commList.add(map);
        }
        return commList;
    }

    @Override
    public int setCommonStatus(Integer id, Integer status) {
        return this.commentMapper.setCommonStatus(id, status);
    }

    @Override
    public int getCount() {
        return this.commentMapper.getCount();
    }

    @Override
    public int deleteCommentUser(Integer commentid, Integer uid) {
        return this.commentUserMapper.deleteCommentUser(commentid, uid);
    }

    @Override
    public int updateComment(Comment comment) {
        return this.commentMapper.updateByPrimaryKey(comment);
    }

    @Override
    public int getDisplayCommentCountByProjectId(Integer projectid) {
        return this.commentMapper.getCommentCountByIdAndStatus(projectid, DataUtils.I_C_STATUS_ALREADY_PASS);
    }

    @Override
    public int insertComment(Comment comment) {
        return this.commentMapper.insert(comment);
    }

    @Override
    public Comment getCommentById(Integer id) {
        return this.commentMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insertCommentUser(CommentUser commentUser) {
        return this.commentUserMapper.insert(commentUser);
    }

    @Override
    public List<Map<String, Object>> getDisplayCommentsByProjectId(Integer projectid, HttpSession session) {
        Integer uid = Integer.parseInt(session.getAttribute("userid").toString());
//        Integer uid = 1;

        List<Map<String, Object>> cMapList = new ArrayList<>();

        List<Comment> cList = this.commentMapper.getCommentsByIdAndStatus(projectid, DataUtils.I_C_STATUS_ALREADY_PASS);
        for (Comment c : cList) {
            User u = this.userMapper.selectByPrimaryKey(c.getUserid());

            Map<String, Object> map = new HashMap<>();
            map.put("comment", c);
            map.put("user", u);
            if(this.getCommentUser(c.getId(), uid) != null) {
                map.put("ifZan", "1");
            } else {
                map.put("ifZan", "0");
            }

            cMapList.add(map);
        }

        return cMapList;
    }

    @Override
    public CommentUser getCommentUser(Integer commentid, Integer uid) {
        return this.commentUserMapper.getCommentUser(commentid, uid);
    }
}
