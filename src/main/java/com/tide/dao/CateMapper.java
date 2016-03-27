package com.tide.dao;

import com.tide.bean.Cate;
import java.util.List;

public interface CateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cate record);

    Cate selectByPrimaryKey(Integer id);

    List<Cate> selectAll();

    List<Cate> selectCateByPid(Integer pid);

    int updateByPrimaryKey(Cate record);

    Cate getCateById(Integer id);
}