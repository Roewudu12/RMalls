package com.roe.rmallservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roe.rmallservice.entity.Goods;
import com.roe.rmallservice.entity.Orders;
import com.roe.rmallservice.entity.Users;
import com.roe.rmallservice.mapper.GoodsMapper;
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

    @Autowired
    private GoodsMapper goodsMapper;

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
        //插入成功后减少商品库存
        for (Orders order: orders) {
            Goods good = order.getGood();
            good.setGoodStock(good.getGoodStock()-1);
            goodsMapper.updateById(good);
        }

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
    public List<Orders> showOrdersBySellerId(Long userId, int page, int pageNum) {
        Page<Orders> p = new Page<>(page, pageNum);
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("seller_id",userId);
        return ordersMapper.selectPage(p,wrapper).getRecords();
    }

    @Override
    public List<Orders> showOrdersByBuyerId(Long userId, int page, int pageNum) {
        Page<Orders> p = new Page<>(page, pageNum);
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("buyer_id",userId);
        return ordersMapper.selectPage(p,wrapper).getRecords();
    }

    @Override
    public Integer ordersCountBySellerId(Long userId) {
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("seller_id",userId);
        return ordersMapper.selectCount(wrapper);
    }
    @Override
    public Integer ordersCountByBuyerId(Long userId) {
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("buyer_id",userId);
        return ordersMapper.selectCount(wrapper);
    }

    @Override
    public boolean updatePayOrder(Orders order) {
        int i = ordersMapper.updateById(order);
        //增加商品被购买数
        Goods good = order.getGood();
        if(good.getGoodSoldNum()==null){
            good.setGoodSoldNum(1);
        }else {
            good.setGoodSoldNum(good.getGoodSoldNum()+1);
        }

        goodsMapper.updateById(good);
        return i>0;
    }

    @Override
    public Integer ordersCountBySellerIdAndStatus(Long userId, int status) {
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("seller_id",userId);
        wrapper.eq("order_status",status);
        return ordersMapper.selectCount(wrapper);
    }

    @Override
    public Integer ordersCountByBuyerIdAndStatus(Long userId, int status) {
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("buyer_id",userId);
        wrapper.eq("order_status",status);
        return ordersMapper.selectCount(wrapper);
    }

    @Override
    public Integer ordersCountByStatus(int status) {
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("order_status",status);
        return ordersMapper.selectCount(wrapper);
    }

    @Override
    public boolean cancelOrder(Orders order) {
        Goods good = order.getGood();
        int i;
        if(order.getOrderStatus()==1){
            //未付款直接删除订单
            i = ordersMapper.deleteById(order.getOrderId());
        }else {
            //已付款
            if(good!=null && good.getGoodSoldNum()!=null){
                good.setGoodSoldNum(good.getGoodSoldNum()-1);
                good.setGoodStock(good.getGoodStock()+1);
                goodsMapper.updateById(good);
            }
            //将订单状态转为售后
            order.setOrderStatus(6);
            i = ordersMapper.updateById(order);
        }

        return i>0;
    }
}
