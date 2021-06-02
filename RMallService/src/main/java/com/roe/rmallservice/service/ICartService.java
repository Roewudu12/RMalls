package com.roe.rmallservice.service;

import com.roe.rmallservice.entity.Cart;

import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/5/30 23:28
 * @Description:
 **/
public interface ICartService {
    boolean addToCart(Cart cart);

    List<Cart> getAllCartByUserId(Long userId);

    List<Cart> getAllCartByIds(List<Long> ids);

    boolean removeCartsByIds(List<Long> ids);
}
