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
 * @Date: 2021/3/12 15:50
 * @Description:
 **/
@Data
public class Goods {
    @TableId(type = IdType.ASSIGN_ID)
    @JSONField(serializeUsing= ToStringSerializer.class)//解决Long型前端丢失精度问题
    private Long goodId;
    private String goodName;
    @JSONField(serializeUsing= ToStringSerializer.class)//解决Long型前端丢失精度问题
    private Long goodSellerId;
    private Integer goodSoldNum;
    private Integer goodRecommend;
    private Float goodPrice;
    private Integer goodStatus;
    private Integer goodStock;
    @JSONField(serializeUsing= ToStringSerializer.class)//解决Long型前端丢失精度问题
    private Long cateId;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    //多表查询
    @TableField(exist = false)
    private Users seller;//商家
    @TableField(exist = false)
    private Category cate;//类别
    @TableField(exist = false)
    private List<Images> detailImages;//商品详情图片

    @TableField(exist = false)
    private List<GoodChoice> goodChoices;//商品选项信息


}
