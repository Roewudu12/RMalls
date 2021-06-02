package com.roe.rmallservice.auth;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Auther: HP
 * @Date: 2021/4/9 22:35
 * @Description:封装向前端写JSON代码
 **/

@Slf4j
public abstract class JSONAuthentication {
    protected void WriteJSON(HttpServletRequest request, HttpServletResponse response,Object data) throws IOException, ServletException{
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin","http://localhost:8080");
        response.setHeader("Access-Control-Allow-Method","POST,GET");

        //输出JSON
        PrintWriter out = response.getWriter();
        out.write(JSON.toJSONString(data));
        out.flush();
        out.close();
    }
}
