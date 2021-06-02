package com.roe.rmallservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;


/**
 * @Auther: HP
 * @Date: 2021/3/12 15:47
 * @Description:
 **/
@Data
public class FootPrint {
    @TableId(type = IdType.ASSIGN_ID)
    private String footId;
    private String userId;
    private Date time;
    private String goodId;
}
