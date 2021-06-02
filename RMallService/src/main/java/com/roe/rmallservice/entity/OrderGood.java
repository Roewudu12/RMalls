package com.roe.rmallservice.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Auther: HP
 * @Date: 2021/5/24 21:11
 * @Description: 订单商品一对多映射表
 **/

@Data
public class OrderGood {

    @TableId
    @JSONField(serializeUsing= ToStringSerializer.class)//解决Long型前端丢失精度问题
    private Long id;
    @JSONField(serializeUsing= ToStringSerializer.class)//解决Long型前端丢失精度问题
    private Long orderId;
    @JSONField(serializeUsing= ToStringSerializer.class)//解决Long型前端丢失精度问题
    private Long goodId;
    private Integer num;
}
