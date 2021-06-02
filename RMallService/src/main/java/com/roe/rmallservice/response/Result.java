package com.roe.rmallservice.response;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: HP
 * @Date: 2021/4/9 21:42
 * @Description: 请求Json返回值
 **/
@Setter
@Getter
public class Result {
    //是否成功
    private Boolean isSuccess;
    //返回值
    private Integer code;
    //返回信息
    private String message;
    //返回数据
    private Object data;

    //构造方法私有化，保护属性
    private Result(){}

    public static Result ok(){
        Result result = new Result();
        result.setIsSuccess(true);
        result.setCode(ResultCode.LOGIN_SUCCESS.getCode());
        result.setMessage(ResultCode.LOGIN_SUCCESS.getMessage());
        return result;
    }

    public static Result ok(String message){
        Result result = new Result();
        result.setIsSuccess(true);
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(message);
        return result;
    }

    public static Result ok(Object object){
        Result result = new Result();
        result.setIsSuccess(true);
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        result.setData(object);
        return result;
    }


    public static Result fail(){
        Result result = new Result();
        result.setIsSuccess(false);
        result.setCode(ResultCode.LOGIN_FAIL.getCode());
        result.setMessage(ResultCode.LOGIN_FAIL.getMessage());
        return result;
    }

    public static Result error(ResultCode resultCode){
        Result result = new Result();
        result.setIsSuccess(false);
        result.setCode(resultCode.getCode());
        result.setMessage(resultCode.getMessage());
        return result;
    }


}
