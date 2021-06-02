package com.roe.rmallservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roe.rmallservice.entity.Addresses;
import com.roe.rmallservice.entity.DefaultAddress;

/**
 * @Auther: HP
 * @Date: 2021/6/1 10:23
 * @Description:
 **/
public interface DefaultAddressMapper extends BaseMapper<DefaultAddress> {
    Addresses getDefaultAddress(Long userId);
}
