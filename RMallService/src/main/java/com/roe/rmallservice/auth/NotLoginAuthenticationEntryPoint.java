package com.roe.rmallservice.auth;

import com.roe.rmallservice.response.Result;
import com.roe.rmallservice.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: HP
 * @Date: 2021/4/9 22:44
 * @Description:用户未登录处理
 **/

@Component
@Slf4j
public class NotLoginAuthenticationEntryPoint extends JSONAuthentication implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        log.info("用户未登录");
        this.WriteJSON(httpServletRequest,httpServletResponse,Result.error(ResultCode.ERROR_NOT_LOGIN));
    }
}
