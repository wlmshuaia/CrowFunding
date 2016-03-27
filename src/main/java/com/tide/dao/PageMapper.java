package com.tide.dao;

import com.tide.bean.Page;
import java.util.List;

public interface PageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Page record);
    int getCount();

    Page selectByPrimaryKey(Integer id);

    List<Page> selectAll();
    List<Page> selectAllDisplay();

    int updateByPrimaryKey(Page record);
}