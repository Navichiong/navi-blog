package com.lwc.naviblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lwc.naviblog.model.SysUser;

public interface SysUserDao extends BaseMapper<SysUser> {
    SysUser selectByUsername(String username);
}
