package com.roe.rmallservice.service;

import com.roe.rmallservice.entity.Orders;
import com.roe.rmallservice.entity.Users;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


/**
 * @Auther: HP
 * @Date: 2021/4/9 09:25
 * @Description:
 **/

public interface IOrdersService {


    List<Orders> showOrders(int page, int pageNum);

    Integer ordersCount();

    boolean submitOrder(List<Orders> orders);

    List<Orders> showOrdersByType(Long userId, int status, int page, int pageNum);

    boolean updateOrder(Orders order);

    List<Orders> showOrdersBySellerId(Long userId, int page, int pageNum);

    List<Orders> showOrdersByBuyerId(Long userId, int page, int pageNum);

    Integer ordersCountBySellerId(Long userId);
    Integer ordersCountByBuyerId(Long userId);

    boolean updatePayOrder(Orders order);

    Integer ordersCountBySellerIdAndStatus(Long userId, int status);
    Integer ordersCountByBuyerIdAndStatus(Long userId, int status);

    Integer ordersCountByStatus(int status);

    boolean cancelOrder(Orders order);
}
