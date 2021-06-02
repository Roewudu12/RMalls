package com.roe.rmallservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.roe.rmallservice.entity.Addresses;
import com.roe.rmallservice.entity.DefaultAddress;
import com.roe.rmallservice.mapper.DefaultAddressMapper;
import com.roe.rmallservice.service.IDefaultAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: HP
 * @Date: 2021/6/1 10:28
 * @Description:
 **/

@Service
@Slf4j
public class DefaultAddressService implements IDefaultAddressService {
    @Autowired
    private DefaultAddressMapper defaultAddressMapper;


    /**
     * 设置默认地址，一个用户id对应一个地址
     * @param defaultAddress
     * @return
     */
    @Override
    public boolean setDefaultAddress(DefaultAddress defaultAddress) {

        int flag;
        flag = defaultAddressMapper.updateById(defaultAddress);
        if (flag==0){
            defaultAddressMapper.insert(defaultAddress);
        }
        return flag>0;
    }

    @Override
    public Addresses getDefaultAddress(Long userId) {

        Addresses address = defaultAddressMapper.getDefaultAddress(userId);
        return address;
    }
}
