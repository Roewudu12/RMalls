package com.roe.rmallservice.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Auther: HP
 * @Date: 2021/3/12 15:39
 * @Description:
 **/
@Data
public class Addresses {
    @TableId
    @JSONField(serializeUsing= ToStringSerializer.class)//解决Long型前端丢失精度问题
    private Long addressId;
    @JSONField(serializeUsing= ToStringSerializer.class)//解决Long型前端丢失精度问题
    private Long userId;
    private String name;
    private String phone;
    private String addressProvince;
    private String addressCity;
    private String addressDistrict;
    private String addressDetails;

    @TableField(exist = false)
    private Boolean isDefault; //是否是默认，用于接收前端传来的信息
}
