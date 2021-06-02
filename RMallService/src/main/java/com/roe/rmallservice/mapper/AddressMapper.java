package com.roe.rmallservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roe.rmallservice.entity.Addresses;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/5/17 16:29
 * @Description:
 **/
@Repository
public interface AddressMapper extends BaseMapper<Addresses> {
    List<Addresses> getNotDefaultAddresses(Long userId);
}
