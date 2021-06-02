package com.roe.rmallservice.controller;

import com.alibaba.fastjson.JSON;
import com.roe.rmallservice.entity.Cart;
import com.roe.rmallservice.response.Result;
import com.roe.rmallservice.response.ResultCode;
import com.roe.rmallservice.service.ICartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/3/7 09:11
 * @Description: 主要用于存储管理用户购物车的操作
 **/

@RestController
@Slf4j
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ICartService cartService;

    /**
     * 插入购物车
     * @param cart
     * @return
     */
    @RequestMapping(value = {"/insert"},method = RequestMethod.POST)
    public String addToCart(@RequestBody Cart cart){
        boolean isOk = cartService.addToCart(cart);
        if(!isOk){
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        return JSON.toJSONString(Result.ok("插入成功"));
    }

    /**
     * 显示某买家的购物车
     * @param userId
     * @return
     */
    @RequestMapping(value = {"/show/buyer"},method = RequestMethod.POST)
    public String showAllCartByUserId(@RequestParam("userId") Long userId){
        List<Cart> carts = cartService.getAllCartByUserId(userId);
        if(carts==null){
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        return JSON.toJSONString(Result.ok(carts));
    }

    /**
     * 根据ids显示购物车信息
     * @param ids
     * @return
     */
    @RequestMapping(value = {"show/ids"},method = RequestMethod.POST)
    public String showAllCartByIds(@RequestBody List<Long> ids){
        List<Cart> carts = cartService.getAllCartByIds(ids);
        if(carts==null){
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        return JSON.toJSONString(Result.ok(carts));
    }


    @RequestMapping(value = {"/remove"},method = RequestMethod.POST)
    public String removeCartsByIds(@RequestBody List<Long> ids){
        boolean isOk = cartService.removeCartsByIds(ids);

        if(!isOk){
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        return JSON.toJSONString(Result.ok("删除成功"));
    }
}
