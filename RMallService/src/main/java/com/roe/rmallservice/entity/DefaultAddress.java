package com.roe.rmallservice.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Auther: HP
 * @Date: 2021/6/1 10:22
 * @Description:
 **/
@Data
public class DefaultAddress {
    @TableId
    @JSONField(serializeUsing= ToStringSerializer.class)//解决Long型前端丢失精度问题
    private Long userId;
    @JSONField(serializeUsing= ToStringSerializer.class)//解决Long型前端丢失精度问题
    private Long addressId;
}
