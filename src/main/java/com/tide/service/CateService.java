package com.tide.service;

import com.tide.bean.Cate;

import java.util.List;

/**
 * 分类管理
 * Created by wengliemiao on 15/12/11.
 */
public interface CateService {
    List<Cate> selectCateByPid(Integer pid) ;
    List<Cate> getCateByCateid(Integer cateid);
    List<Cate> selectAll();
    int insertCate(Cate cate) ;
    void deleteCate(Integer id);
    Cate getCateById(Integer id);
    Cate getFirstCateById(Cate cate);
}
