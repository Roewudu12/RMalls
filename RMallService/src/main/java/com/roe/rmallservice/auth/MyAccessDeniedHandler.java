package com.roe.rmallservice.auth;

import com.roe.rmallservice.response.Result;
import com.roe.rmallservice.response.ResultCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: HP
 * @Date: 2021/5/27 11:51
 * @Description: 自定义权限不足返回
 **/
@Component
public class MyAccessDeniedHandler extends JSONAuthentication implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        Result result = Result.error(ResultCode.NO_PERMISSION);
        this.WriteJSON(httpServletRequest,httpServletResponse,result);
    }
}
