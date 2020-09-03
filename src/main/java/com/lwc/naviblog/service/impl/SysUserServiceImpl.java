package com.lwc.naviblog.service.impl;

import com.lwc.naviblog.dao.SysUserDao;
import com.lwc.naviblog.model.SysUser;
import com.lwc.naviblog.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserDao userDao;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userDao.selectByUsername(s);
    }

    @Override
    @Transactional(readOnly = true)
    public SysUser selectById(Integer id) {
        return userDao.selectById(id);
    }
}
