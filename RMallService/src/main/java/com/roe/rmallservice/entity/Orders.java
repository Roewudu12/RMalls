package com.roe.rmallservice.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Auther: HP
 * @Date: 2021/3/12 15:54
 * @Description:
 **/
@Data
public class Orders {
    @TableId(type = IdType.ASSIGN_ID)
    @JSONField(serializeUsing= ToStringSerializer.class)//解决Long型前端丢失精度问题
    private Long orderId;
    @JSONField(serializeUsing= ToStringSerializer.class)//解决Long型前端丢失精度问题
    private Long goodId;
    @JSONField(serializeUsing= ToStringSerializer.class)//解决Long型前端丢失精度问题
    private Long buyerId;
    @JSONField(serializeUsing= ToStringSerializer.class)//解决Long型前端丢失精度问题
    private Long sellerId;
    @JSONField(serializeUsing= ToStringSerializer.class)//解决Long型前端丢失精度问题
    private Long addressId;


    private Float orderPrice;
    private Integer orderStatus;
    private Integer goodNum;
    private String goodChoice;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;



    @TableField(exist = false)
    private Users buyer;//买家
    @TableField(exist = false)
    private Addresses buyerAddress;//买家地址
    @TableField(exist = false)
    private Goods good;//商品

}
