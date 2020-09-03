package com.lwc.naviblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lwc.naviblog.model.SysAuthority;

import java.util.Set;

public interface SysAuthorityDao extends BaseMapper<SysAuthority> {
    Set<SysAuthority> selectByRoleId(Integer roleId);
}
