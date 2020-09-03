package com.lwc.naviblog.handler;

import com.lwc.naviblog.enums.StatusCode;
import com.lwc.naviblog.utils.CommonResultUtil;
import com.lwc.naviblog.utils.JsonResponseUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BlogAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

//        JsonResponseUtil.writeObject(CommonResultUtil.ok("登录成功！", null), response);
        JsonResponseUtil.writeObject(CommonResultUtil.build(StatusCode.SUCCESS, "登陆成功！",null ), response);
    }
}
