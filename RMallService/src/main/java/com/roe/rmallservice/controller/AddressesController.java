package com.roe.rmallservice.controller;

import com.alibaba.fastjson.JSON;
import com.roe.rmallservice.entity.Addresses;
import com.roe.rmallservice.response.Result;
import com.roe.rmallservice.response.ResultCode;
import com.roe.rmallservice.service.IAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/3/7 09:30
 * @Description:对用户地址进行管理
 **/

@RestController
@RequestMapping("/address")
@Slf4j
public class AddressesController {

    @Autowired
    private IAddressService addressService;

    /**
     * 创建新的地址
     * @param address
     * @return
     */
    @RequestMapping(value = {"/add"},method = RequestMethod.POST)
    public String addAddressToUser(@RequestBody Addresses address){
        log.info("addAddressToUser{}");
        boolean isOk = addressService.addAddressToUser(address);
        if(!isOk){
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }

        return JSON.toJSONString(Result.ok("创建成功"));
    }

    /**
     * 修改地址
     * @param address
     * @return
     */
    @RequestMapping(value = {"/edit"},method = RequestMethod.POST)
    public String editAddressToUser(@RequestBody Addresses address){
        boolean isOk = addressService.editAddressToUser(address);
        if(!isOk){
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }

        return JSON.toJSONString(Result.ok("修改成功"));
    }

    /**
     * 展示该用户地址
     * @param userId
     * @return
     */
    @RequestMapping(value = {"/show"},method = RequestMethod.POST)
    public String showAddressByUserId(@RequestParam("userId") Long userId){
        List<Addresses> addresses = addressService.showAddressByUserIdNotDefault(userId);
        if (addresses == null) {
            return JSON.toJSONString(Result.error(ResultCode.ERROR));
        }
        return JSON.toJSONString(Result.ok(addresses));
    }
}
