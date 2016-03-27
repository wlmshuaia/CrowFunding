package com.tide.service;

import com.tide.bean.Order;

import java.util.List;

/**
 * Created by wengliemiao on 15/12/23.
 */
public interface OrderService extends BaseService {
    List<Order> selectAll() ;
    List<Order> getOrderByStatus(String status);

    Order getObjById(Integer id);
    Order getOrderByTradeNo(String tradeno);

    int updateOrder(Order order) ;

    int insertOrder(Order order);

    int deleteOrder(int id);
    int deleteOrderByTradeno(String tradeno);
}
