package com.roe.rmallservice.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.roe.rmallservice.entity.Orders;
import org.springframework.stereotype.Repository;

/**
 * @Auther: HP
 * @Date: 2021/5/12 15:30
 * @Description:
 **/
@Repository
public interface OrdersMapper extends EasyBaseMapper<Orders> {

    IPage<Orders> showOrdersByType(Long userId, int status, Page<Orders> p);
}
