package com.tide.dao;

import com.tide.bean.FocusUser;
import java.util.List;

public interface FocusUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FocusUser record);

    FocusUser selectByPrimaryKey(Integer id);
    FocusUser getFocusUserByUF(Integer uid, Integer focusuid);

    List<FocusUser> selectAll();
    List<FocusUser> getFocusUser(Integer uid);
    int getFocuserNum(Integer userid); // 关注ta的人数
    int deleteFocusUserByUF(Integer uid, Integer focuserid);

    int updateByPrimaryKey(FocusUser record);
}