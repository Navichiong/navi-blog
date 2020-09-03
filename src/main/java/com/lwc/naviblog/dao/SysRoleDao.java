package com.lwc.naviblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lwc.naviblog.model.SysRole;

import java.util.Set;

public interface SysRoleDao extends BaseMapper<SysRole> {

    Set<SysRole> selectByUserId(Integer userId);
}
