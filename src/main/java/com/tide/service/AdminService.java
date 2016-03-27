package com.tide.service;

import com.tide.bean.Admin;

import java.util.List;

/**
 * Created by wengliemiao on 15/11/29.
 */
public interface AdminService {
    public List<Admin> selectAll() ;
    public Admin getObjByNP(String name, String passowrd);
    public Admin getObjById(Integer id);
    public int update(Admin admin);
}

