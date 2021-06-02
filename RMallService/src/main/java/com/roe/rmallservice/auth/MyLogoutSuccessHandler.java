package com.roe.rmallservice.auth;

import com.roe.rmallservice.response.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: HP
 * @Date: 2021/5/27 12:12
 * @Description: 注销成功的Handler
 **/

@Component
public class MyLogoutSuccessHandler extends JSONAuthentication implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Result ok = Result.ok("注销成功");
        this.WriteJSON(httpServletRequest,httpServletResponse,ok);
    }
}
