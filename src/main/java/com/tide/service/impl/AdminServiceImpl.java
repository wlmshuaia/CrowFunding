package com.tide.service.impl;

import com.tide.bean.Admin;
import com.tide.dao.AdminMapper;
import com.tide.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wengliemiao on 15/12/3.
 */
@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper ;

    @Override
    public List<Admin> selectAll() {
        return this.adminMapper.selectAll();
    }

    @Override
    public Admin getObjByNP(String name, String passowrd) {
        return this.adminMapper.getObjByNP(name, passowrd);
    }

    @Override
    public Admin getObjById(Integer id) {
        return this.adminMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Admin admin) {
        return this.adminMapper.updateByPrimaryKey(admin);
    }
}
