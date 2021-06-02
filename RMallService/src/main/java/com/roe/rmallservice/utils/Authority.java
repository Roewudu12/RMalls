package com.roe.rmallservice.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Authority {

    BUYER(1,"商家"),
    SELLER(2,"买家"),
    MANAGER(3,"管理员");

    private Integer flag;
    private String auth;
}
