package com.tide.dao;

import com.tide.bean.Comment;
import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);
    int deleteByProjectId(Integer projectid);

    int insert(Comment record);
    int getCount();
    int getCommentCountByIdAndStatus(Integer projectid, Integer status);

    Comment selectByPrimaryKey(Integer id);

    List<Comment> selectAll();
    List<Comment> getCommentByStatus(Integer status);
    List<Comment> getCommentsByIdAndStatus(Integer projectid, Integer status);
    int setCommonStatus(Integer id, Integer status);

    int updateByPrimaryKey(Comment record);
}