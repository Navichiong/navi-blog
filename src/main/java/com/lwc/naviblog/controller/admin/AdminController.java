package com.lwc.naviblog.controller.admin;

import com.lwc.naviblog.enums.StatusCode;
import com.lwc.naviblog.model.CommonResult;
import com.lwc.naviblog.model.SysUser;
import com.lwc.naviblog.service.SysUserService;
import com.lwc.naviblog.utils.CommonResultUtil;
import com.lwc.naviblog.vo.SysUserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Secured({"ROLE_ADMIN"})
public class AdminController {

    @Autowired
    private SysUserService userService;

    @GetMapping("/currentUser")
    public CommonResult currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            SysUserVo userVo = new SysUserVo();
            SysUser tmpUser = (SysUser) authentication.getPrincipal();
            SysUser sysUser = userService.selectById(tmpUser.getId());
            BeanUtils.copyProperties(sysUser, userVo);
            return CommonResultUtil.build(StatusCode.SUCCESS, "success", userVo);
        }
        return CommonResultUtil.build(StatusCode.UNAUTHORIZED, "你还未登录", null);
    }
}
