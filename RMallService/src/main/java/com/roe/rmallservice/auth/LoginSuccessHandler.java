package com.roe.rmallservice.auth;

import com.alibaba.fastjson.JSON;
import com.roe.rmallservice.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: HP
 * @Date: 2021/4/9 22:20
 * @Description:登录成功处理
 **/
@Component
@Slf4j
public class LoginSuccessHandler extends JSONAuthentication implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //以JSON格式返回对象
        log.info("用户登录成功{}",authentication.getPrincipal());

        this.WriteJSON(httpServletRequest,httpServletResponse,Result.ok(authentication.getPrincipal()));
    }
}
