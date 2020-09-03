package com.lwc.naviblog.service;

import com.lwc.naviblog.model.SysUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SysUserService extends UserDetailsService {
    SysUser selectById(Integer id);
}
