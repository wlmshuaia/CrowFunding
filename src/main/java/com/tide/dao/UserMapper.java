package com.tide.dao;

import com.tide.bean.User;
import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);
    User getUserByOpenid(String openid);

    List<User> selectAll();
    List<User> getUserByType(Integer type);

    int updateByPrimaryKey(User record);

    Integer getCount();
    String getOpenidById(Integer id);
}