package com.lwc.naviblog.handler;

import com.lwc.naviblog.enums.StatusCode;
import com.lwc.naviblog.utils.CommonResultUtil;
import com.lwc.naviblog.utils.JsonResponseUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BlogLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

//        JsonResponseUtil.writeObject(CommonResultUtil.ok("注销成功！", null), response);
        JsonResponseUtil.writeObject(CommonResultUtil.build(StatusCode.SUCCESS, "注销成功", null), response);
    }
}
