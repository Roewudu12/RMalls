package com.roe.rmallservice.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Auther: HP
 * @Date: 2021/3/12 15:43
 * @Description:
 **/
@Data
public class Category {
    @TableId(type = IdType.ASSIGN_ID)
    @JSONField(serializeUsing= ToStringSerializer.class)//解决Long型前端丢失精度问题
    private Long cateId;
    private String cateName;
    private String cateFid;
}
