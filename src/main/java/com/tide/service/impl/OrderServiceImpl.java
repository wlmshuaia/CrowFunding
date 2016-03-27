package com.tide.service.impl;

import com.tide.bean.Order;
import com.tide.dao.OrderDesignMapper;
import com.tide.dao.OrderMapper;
import com.tide.dao.ProjectBackerMapper;
import com.tide.dao.ProjectMapper;
import com.tide.service.OrderService;
import com.tide.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by wengliemiao on 15/12/23.
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper ;

    @Autowired
    private OrderDesignMapper orderDesignMapper;

    @Autowired
    private ProjectBackerMapper projectBackerMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public List<Order> selectAll() {
        return orderMapper.selectAll();
    }

    @Override
    public Order getObjById(Integer id) {
        return this.orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateOrder(Order order) {
        return this.orderMapper.updateByPrimaryKey(order);
    }

    @Override
    public int getCount() {
        return this.orderMapper.getCount();
    }

    @Override
    public int delete(Integer id, HttpSession session) {
        Order order = this.orderMapper.selectByPrimaryKey(id);

        // 删除order_design
        this.orderDesignMapper.deleteByTradeNo(order.getTradeno());

//        // 删除project_backer
//        this.projectBackerMapper.deleteByTradeNo(order.getTradeno());

        // 删除order
        this.orderMapper.deleteByPrimaryKey(id);

        return 1;
    }

    @Override
    public List<Order> getOrderByStatus(String status) {
        List<Order> oList;
        Integer iStatus;

        if(DataUtils.O_STATUS_WAIT_DELIVER.equals(status)) { // 待发货
            iStatus = DataUtils.O_WAIT_DELIVER;
        } else if(DataUtils.O_STATUS_WAIT_TAKE.equals(status)) { // 待收货
            iStatus = DataUtils.O_WAIT_TAKE;
        } else if(DataUtils.O_STATUS_ALREADY_TAKE.equals(status)) { // 已收货
            iStatus = DataUtils.O_ALREADY_TAKE;
        } else if(DataUtils.O_STATUS_REFUNDING.equals(status)) { // 退款中
            iStatus = DataUtils.O_ALREADY_TAKE;
        } else if(DataUtils.O_STATUS_REFUNDING_SUCCESS.equals(status)) { // 退款成功
            iStatus = DataUtils.O_ALREADY_TAKE;
        } else if(DataUtils.O_STATUS_REFUNDING_FAIL.equals(status)) { // 退款失败
            iStatus = DataUtils.O_ALREADY_TAKE;
        } else { // 全部商品
            iStatus = -1;
        }

        if(iStatus == -1) {
            oList = this.orderMapper.selectAll();
        } else {
            oList = this.orderMapper.getOrderByStatus(iStatus);
        }

        return oList;
    }

    @Override
    public int insertOrder(Order order) {
        return this.orderMapper.insert(order);
    }

    @Override
    public Order getOrderByTradeNo(String tradeno) {
        return this.orderMapper.getOrderByTradeNo(tradeno);
    }

    @Override
    public int deleteOrder(int id) {
        return this.orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteOrderByTradeno(String tradeno) {
        return this.orderMapper.deleteOrderByTradeno(tradeno);
    }
}
