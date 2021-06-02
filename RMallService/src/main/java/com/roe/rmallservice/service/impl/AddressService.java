package com.roe.rmallservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.roe.rmallservice.entity.Addresses;
import com.roe.rmallservice.entity.DefaultAddress;
import com.roe.rmallservice.mapper.AddressMapper;
import com.roe.rmallservice.service.IAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/6/1 10:05
 * @Description:
 **/

@Service
@Slf4j
public class AddressService implements IAddressService {
    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private DefaultAddressService defaultAddressService;

    @Override
    public boolean addAddressToUser(Addresses address) {
        int insert = addressMapper.insert(address);
        if (address.getIsDefault()) {
            DefaultAddress defaultAddress = new DefaultAddress();
            defaultAddress.setAddressId(address.getAddressId());
            defaultAddress.setUserId(address.getUserId());
            defaultAddressService.setDefaultAddress(defaultAddress);
        }
        return insert>0;
    }

    @Override
    public boolean editAddressToUser(Addresses address) {
        int i = addressMapper.updateById(address);
        if(address.getIsDefault()){
            DefaultAddress defaultAddress = new DefaultAddress();
            defaultAddress.setAddressId(address.getAddressId());
            defaultAddress.setUserId(address.getUserId());
            defaultAddressService.setDefaultAddress(defaultAddress);
        }
        return i>0;
    }

    @Override
    public List<Addresses> showAddressByUserId(Long userId) {
        QueryWrapper<Addresses> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);

        return addressMapper.selectList(wrapper);
    }

    @Override
    public List<Addresses> showAddressByUserIdNotDefault(Long userId) {


        return addressMapper.getNotDefaultAddresses(userId);
    }
}
