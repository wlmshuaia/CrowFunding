package com.tide.dao;

import com.tide.bean.Order;
import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    Order selectByPrimaryKey(Integer id);

    List<Order> selectAll();

    int updateByPrimaryKey(Order record);

    Integer getCount();
    List<Order> getOrderByStatus(Integer status) ;
    Order getOrderByTradeNo(String tradeno);
    int deleteOrderByTradeno(String tradeno);
}