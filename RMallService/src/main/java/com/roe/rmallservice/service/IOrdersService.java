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

    List<Orders> showOrdersByUserId(Long userId, int page, int pageNum);

    Integer ordersCountByUserId(Long userId);
}
