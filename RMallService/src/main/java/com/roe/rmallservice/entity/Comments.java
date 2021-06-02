package com.roe.rmallservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Auther: HP
 * @Date: 2021/3/12 15:44
 * @Description:
 **/
@Data
public class Comments {
    @TableId(type = IdType.ASSIGN_ID)
    private String commentId;
    private String UserFromId;
    private String UserToId;
    private String goodId;
    private String commentTime;
}
