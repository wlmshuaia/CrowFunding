package com.tide.dao;

import com.tide.bean.Admin;

import java.util.List;

public interface AdminMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    Admin selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Admin record);

    List<Admin> selectAll();

    Admin getObjByNP(String name, String password);
}