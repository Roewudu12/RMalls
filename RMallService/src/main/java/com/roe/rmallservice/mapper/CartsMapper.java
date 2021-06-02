package com.roe.rmallservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roe.rmallservice.entity.Cart;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/5/17 16:31
 * @Description:
 **/
@Repository
public interface CartsMapper extends BaseMapper<Cart> {
    List<Cart> getAllCartsByUserId(Long userId);
}
