package com.roe.rmallservice.auth;

import com.alibaba.fastjson.JSON;
import com.roe.rmallservice.response.Result;
import com.roe.rmallservice.response.ResultCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: HP
 * @Date: 2021/4/9 22:22
 * @Description: 登陆失败处理器
 **/
@Component
public class LoginFailHandler extends JSONAuthentication implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //以JSON格式返回对象
        Result result = Result.fail();
        this.WriteJSON(httpServletRequest,httpServletResponse,result);
    }
}
