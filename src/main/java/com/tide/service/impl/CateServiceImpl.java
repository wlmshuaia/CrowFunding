package com.tide.service.impl;

import com.tide.bean.Cate;
import com.tide.dao.CateMapper;
import com.tide.service.CateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wengliemiao on 15/12/11.
 */
@Service("cateService")
public class CateServiceImpl implements CateService {
    @Autowired
    private CateMapper cateMapper;

    @Override
    public int insertCate(Cate cate) {
        return this.cateMapper.insert(cate);
    }

    /**
     * 递归删除分类
     * @param id
     */
    @Override
    public void deleteCate(Integer id) {
        this.cateMapper.deleteByPrimaryKey(id);

        List<Cate> cList = this.cateMapper.selectCateByPid(id) ;
        for (Cate c : cList) {
            this.deleteCate(c.getId());
        }
    }

    @Override
    public List<Cate> selectAll() {
        return this.cateMapper.selectAll();
    }

    @Override
    public List<Cate> getCateByCateid(Integer cateid) {
        List<Cate> cateList = new ArrayList<>();
        cateList.add(this.cateMapper.selectByPrimaryKey(cateid));

        List<Cate> cList = this.selectCateByPid(cateid) ;

        for (Cate c : cList) {
            cateList.addAll(this.getCateByCateid(c.getId()));
        }

        return cateList;
    }

    @Override
    public List<Cate> selectCateByPid(Integer pid) {
        return this.cateMapper.selectCateByPid(pid);
    }

    @Override
    public Cate getCateById(Integer id) {
        return this.cateMapper.getCateById(id);
    }

    @Override
    public Cate getFirstCateById(Cate cate) {
        if(cate.getPid() == 0) {
            return cate;
        }
        Cate c = this.getCateById(cate.getPid());
        return getFirstCateById(c);
    }
}
