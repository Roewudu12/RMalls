package com.roe.rmallservice.service;

import com.roe.rmallservice.entity.Addresses;

import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/6/1 10:05
 * @Description:
 **/
public interface IAddressService {
    boolean addAddressToUser(Addresses address);

    boolean editAddressToUser(Addresses address);

    List<Addresses> showAddressByUserId(Long userId);

    List<Addresses> showAddressByUserIdNotDefault(Long userId);
}
