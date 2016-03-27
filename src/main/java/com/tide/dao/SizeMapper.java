package com.tide.dao;

import com.tide.bean.Size;
import java.util.List;

public interface SizeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Size record);

    Size selectByPrimaryKey(Integer id);

    List<Size> selectAll();

    int updateByPrimaryKey(Size record);

    String getSizename(Integer id);
}