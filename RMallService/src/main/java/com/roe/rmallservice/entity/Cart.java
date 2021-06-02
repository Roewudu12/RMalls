package com.roe.rmallservice.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Auther: HP
 * @Date: 2021/3/12 15:42
 * @Description:
 **/

@Data
public class Cart {
    @TableId
    @JSONField(serializeUsing= ToStringSerializer.class)//解决Long型前端丢失精度问题
    private String cartId;

    @JSONField(serializeUsing= ToStringSerializer.class)//解决Long型前端丢失精度问题
    private String buyerId;
    @JSONField(serializeUsing= ToStringSerializer.class)//解决Long型前端丢失精度问题
    private String goodId;
    private Integer goodNum;
    private String goodChoice;

    //一对一

    @TableField(exist = false)
    private Goods good;
}
