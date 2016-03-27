package com.tide.dao;

import com.tide.bean.Label;
import java.util.List;

public interface LabelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Label record);
    int getCount();

    Label selectByPrimaryKey(Integer id);
    String getLabelName(Integer id);

    List<Label> selectAll();
    List<Label> selectAllDisplay();

    int updateByPrimaryKey(Label record);
}