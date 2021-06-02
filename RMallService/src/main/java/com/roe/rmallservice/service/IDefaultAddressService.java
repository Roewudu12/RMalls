package com.roe.rmallservice.service;

import com.roe.rmallservice.entity.Addresses;
import com.roe.rmallservice.entity.DefaultAddress;

/**
 * @Auther: HP
 * @Date: 2021/6/1 10:28
 * @Description:
 **/
public interface IDefaultAddressService {
    boolean setDefaultAddress(DefaultAddress defaultAddress);

    Addresses getDefaultAddress(Long userId);
}
