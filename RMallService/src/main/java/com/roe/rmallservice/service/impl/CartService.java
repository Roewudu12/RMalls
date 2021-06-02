package com.roe.rmallservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.roe.rmallservice.entity.Cart;
import com.roe.rmallservice.mapper.CartsMapper;
import com.roe.rmallservice.service.ICartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/5/30 23:28
 * @Description:
 **/

@Service
@Slf4j
public class CartService implements ICartService {
    @Autowired
    private CartsMapper cartsMapper;

    @Override
    public boolean addToCart(Cart cart) {
        //首先判断相同型号的该服装是否已经存在
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("buyer_id",cart.getBuyerId());
        wrapper.eq("good_id",cart.getGoodId());
        wrapper.eq("good_choice",cart.getGoodChoice());
        int i;
        Cart exist = cartsMapper.selectOne(wrapper);
        if(exist!=null){
            //表示存在只需对数进行加加
            exist.setGoodNum(exist.getGoodNum()+cart.getGoodNum());
            i = cartsMapper.updateById(exist);

        }else {
            i = cartsMapper.insert(cart);
        }

        return i>0;
    }

    @Override
    public List<Cart> getAllCartByUserId(Long userId) {

        return cartsMapper.getAllCartsByUserId(userId);
    }

    @Override
    public List<Cart> getAllCartByIds(List<Long> ids) {
        return cartsMapper.selectBatchIds(ids);
    }

    @Override
    public boolean removeCartsByIds(List<Long> ids) {
        int i = cartsMapper.deleteBatchIds(ids);
        return i>0;
    }
}
