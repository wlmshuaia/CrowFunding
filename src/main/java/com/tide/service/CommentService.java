package com.tide.service;

import com.tide.bean.Comment;
import com.tide.bean.CommentUser;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by wengliemiao on 15/12/29.
 */
public interface CommentService extends BaseService {
    List<Map<String, Object>> selectAll();
    List<Map<String, Object>> getCommentByStatus(String status);
    List<Map<String, Object>> getDisplayCommentsByProjectId(Integer projectid, HttpSession session);
    int setCommonStatus(Integer id, Integer status) ;
    int getDisplayCommentCountByProjectId(Integer projectid);
    int insertComment(Comment comment);
    Comment getCommentById(Integer id);
    int updateComment(Comment comment);

    // comment_user
    int insertCommentUser(CommentUser commentUser);
//    int deleteCommentUser(CommentUser commentUser);
    int deleteCommentUser(Integer commentid, Integer uid);
    CommentUser getCommentUser(Integer commentid, Integer uid);
}
