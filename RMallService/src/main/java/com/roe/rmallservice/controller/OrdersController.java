package com.roe.rmallservice.controller;

import com.alibaba.fastjson.JSON;
import com.roe.rmallservice.entity.Orders;
import com.roe.rmallservice.response.Result;
import com.roe.rmallservice.response.ResultCode;
import com.roe.rmallservice.service.IOrdersService;
import com.roe.rmallservice.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/3/7 09:12
 * @Description: 主要用户生成用户订单，修改用户订单操作
 **/

@RestController
@Slf4j
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    private IOrdersService ordersService;

    /**
     * 用户提交订单
     * @param orders
     * @return
     */
    @RequestMapping(value = {"/submit"},method = RequestMethod.POST)
    public String submitOrder(@RequestBody List<Orders> orders){
        boolean isOk = ordersService.submitOrder(orders);
        if (!isOk) {
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        return JSON.toJSONString(Result.ok("下单成功"));
    }

    /**
     * 分状态查看订单
     * @param userId
     * @param status
     * @param page
     * @return
     */
    @RequestMapping(value = {"/show/status"},method = RequestMethod.POST)
    public String showOrdersByType(@RequestParam("userId") Long userId, @RequestParam("status") int status,@RequestParam("page") int page){
        List<Orders> orders = ordersService.showOrdersByType(userId,status,page,Constants.PAGE_NUM);

        if (orders==null) {
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        return JSON.toJSONString(Result.ok(orders));
    }

    /**
     * 更新用户订单
     * @param order
     * @return
     */
    @RequestMapping(value = {"/update"},method = RequestMethod.POST)
    public String updateOrder(@RequestBody Orders order){
        boolean isOk = ordersService.updateOrder(order);
        if (!isOk) {
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        return JSON.toJSONString(Result.ok("更新成功"));
    }

    /**
     * 去付款用户订单
     * @param order
     * @return
     */
    @RequestMapping(value = {"/update/pay"},method = RequestMethod.POST)
    public String updatePayOrder(@RequestBody Orders order){
        boolean isOk = ordersService.updatePayOrder(order);
        if (!isOk) {
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        return JSON.toJSONString(Result.ok("更新成功"));
    }

    /**
     * 展示订单
     * @param page
     * @param userId
     * @param userAuthority
     * @return
     */
    @RequestMapping(value = {"/show/all"},method = RequestMethod.POST)
    public String showOrders(@RequestParam("page") int page,@RequestParam("userId") Long userId,@RequestParam("userAuthority") int userAuthority){
        List<Orders> orders;

        if (userAuthority==1){
            orders =  ordersService.showOrdersBySellerId(userId,page, Constants.PAGE_NUM);
        }else if(userAuthority==2){
            orders =  ordersService.showOrdersByBuyerId(userId,page, Constants.PAGE_NUM);
        }else {
            orders =  ordersService.showOrders(page, Constants.PAGE_NUM);
        }

        if (orders==null) {
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }

        return JSON.toJSONString(Result.ok(orders));
    }


    /**
     * 查看数据库中存储的订单数
     * @return
     */
    @RequestMapping(value = {"/count"},method = RequestMethod.POST)
    public String count(@RequestParam("userAuthority") int userAuthority,@RequestParam("userId") Long userId){
        Integer count;
        if(userAuthority==1){
            count = ordersService.ordersCountBySellerId(userId);
        }else if(userAuthority ==2){
            count = ordersService.ordersCountByBuyerId(userId);
        }else {
            count = ordersService.ordersCount();
        }
        if (count == null) {
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }

        return JSON.toJSONString(Result.ok(count));
    }

    /**
     * 查看数据库中存储的不同状态订单数
     * @return
     */
    @RequestMapping(value = {"/count/status"},method = RequestMethod.POST)
    public String count(@RequestParam("userAuthority") int userAuthority,@RequestParam("userId") Long userId,@RequestParam("status") int status){
        Integer count;
        if(userAuthority==1){
            count = ordersService.ordersCountBySellerIdAndStatus(userId,status);
        }else if(userAuthority==2){
            count = ordersService.ordersCountByBuyerIdAndStatus(userId,status);
        }else {
            count = ordersService.ordersCountByStatus(status);
        }
        if (count == null) {
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }

        return JSON.toJSONString(Result.ok(count));
    }

    @RequestMapping(value = {"/cancel"},method = RequestMethod.POST)
    public String cancelOrder(@RequestBody Orders order){
        log.info("取消的order{}",order);
        boolean isOk = ordersService.cancelOrder(order);
        if (!isOk) {
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        return JSON.toJSONString(Result.ok("取消成功"));
    }

}
