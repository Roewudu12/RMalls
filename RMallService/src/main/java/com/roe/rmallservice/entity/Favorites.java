package com.roe.rmallservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Auther: HP
 * @Date: 2021/3/12 15:46
 * @Description:
 **/
@Data
public class Favorites {
    @TableId(type = IdType.ASSIGN_ID)
    private String favoriteId;
    private String goodId;
    private String userId;
}
