package com.lwc.naviblog.handler;

import com.lwc.naviblog.enums.StatusCode;
import com.lwc.naviblog.utils.CommonResultUtil;
import com.lwc.naviblog.utils.JsonResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BlogAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

        JsonResponseUtil.writeObject(CommonResultUtil.build(StatusCode.ERROR, "用户名或密码错误!", null), response);
//        JsonResponseUtil.writeObject(CommonResultUtil.error("用户名或密码错误！", null), response);
    }
}
