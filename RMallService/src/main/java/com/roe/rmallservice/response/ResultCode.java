package com.roe.rmallservice.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 枚举类，请求返回参数
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    //登陆
    LOGIN_SUCCESS(200,"登录成功"),
    LOGIN_FAIL(500,"登录失败"),
    ERROR_NOT_LOGIN(1212,"未登陆"),
    NO_PERMISSION(1213,"无权限"),

    //操作
    SUCCESS(201,"操作成功"),
    ERROR(501,"操作失败");



    private Integer code;
    private String message;


}
