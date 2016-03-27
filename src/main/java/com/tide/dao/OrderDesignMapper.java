package com.tide.dao;

import com.tide.bean.OrderDesign;
import java.util.List;

public interface OrderDesignMapper {
    int deleteByPrimaryKey(Integer id);
    Integer deleteByTradeNo(String tradeno);
    int insert(OrderDesign record);

    OrderDesign selectByPrimaryKey(Integer id);

    List<OrderDesign> selectAll();

    int updateByPrimaryKey(OrderDesign record);
}