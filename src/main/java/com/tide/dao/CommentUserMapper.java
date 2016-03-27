package com.tide.dao;

import com.tide.bean.CommentUser;
import java.util.List;

public interface CommentUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CommentUser record);

    CommentUser selectByPrimaryKey(Integer id);

    List<CommentUser> selectAll();

    int updateByPrimaryKey(CommentUser record);

    int deleteCommentUser(Integer commentid, Integer uid);
    CommentUser getCommentUser(Integer commentid, Integer uid);
}
