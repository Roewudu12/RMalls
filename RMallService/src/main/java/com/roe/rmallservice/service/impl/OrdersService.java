package com.roe.rmallservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roe.rmallservice.entity.Orders;
import com.roe.rmallservice.entity.Users;
import com.roe.rmallservice.mapper.OrdersMapper;
import com.roe.rmallservice.service.IOrdersService;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/4/9 09:25
 * @Description:
 **/

@Service
public class OrdersService implements IOrdersService {
    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    public List<Orders> showOrders(int page, int pageNum) {
        Page<Orders> p = new Page<>(page, pageNum);

        return ordersMapper.selectPage(p,null).getRecords();
    }

    @Override
    public Integer ordersCount() {
        return ordersMapper.selectCount(null);
    }

    @Override
    public boolean submitOrder(List<Orders> orders) {
        int insert = ordersMapper.insertBatchSomeColumn(orders);
        return insert>0;
    }

    @Override
    public List<Orders> showOrdersByType(Long userId, int status, int page, int pageNum) {
        Page<Orders> p = new Page<>(page, pageNum);

        return ordersMapper.showOrdersByType(userId,status,p).getRecords();
    }

    @Override
    public boolean updateOrder(Orders order) {
        int i = ordersMapper.updateById(order);
        return i>0;
    }

    @Override
    public List<Orders> showOrdersByUserId(Long userId, int page, int pageNum) {
        Page<Orders> p = new Page<>(page, pageNum);
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("seller_id",userId);
        return ordersMapper.selectPage(p,wrapper).getRecords();
    }

    @Override
    public Integer ordersCountByUserId(Long userId) {
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("seller_id",userId);
        return ordersMapper.selectCount(wrapper);
    }
}
