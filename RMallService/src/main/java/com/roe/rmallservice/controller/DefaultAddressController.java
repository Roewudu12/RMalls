package com.roe.rmallservice.controller;

import com.alibaba.fastjson.JSON;
import com.roe.rmallservice.entity.Addresses;
import com.roe.rmallservice.entity.DefaultAddress;
import com.roe.rmallservice.response.Result;
import com.roe.rmallservice.response.ResultCode;
import com.roe.rmallservice.service.IDefaultAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: HP
 * @Date: 2021/6/1 10:25
 * @Description:
 **/

@Slf4j
@RestController
@RequestMapping("/defaultAdd")
public class DefaultAddressController {
    @Autowired
    private IDefaultAddressService defaultAddressService;

    /**
     * 设置默认地址
     * @param defaultAddress
     * @return
     */
    @RequestMapping(value = {"/set"},method = RequestMethod.POST)
    public String addDefaultAddress(@RequestBody DefaultAddress defaultAddress){
        boolean isOk = defaultAddressService.setDefaultAddress(defaultAddress);
        if (!isOk) {
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }

        return JSON.toJSONString(Result.ok("创建成功"));
    }

    /**
     * 获取默认地址
     * @param userId
     * @return
     */
    @RequestMapping(value = {"/get"},method = RequestMethod.POST)
    public String getDefaultAddress(@RequestParam("userId") Long userId){
        Addresses address = defaultAddressService.getDefaultAddress(userId);
        if (address ==null) {
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }

        return JSON.toJSONString(Result.ok(address));
    }

}
