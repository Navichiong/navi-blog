package com.lwc.naviblog.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lwc.naviblog.model.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class SysUserDaoTest {

    @Autowired
    private SysUserDao userDao;

    @Test
    public void testSelectAll() {
        userDao.selectList(null).forEach(System.out::println);
    }

    @Test
    public void testSelectById() {
        SysUser sysUser = userDao.selectByUsername("admin");
        System.out.println(sysUser);
    }

    @Test
    public void testInsert() {
        SysUser sysUser = new SysUser()
                .setUsername("666")
                .setPassword("123");
        userDao.insert(sysUser);
    }

    @Test
    public void testUpdate() {
        SysUser sysUser = new SysUser()
                .setUsername("777")
                .setId(3);
        userDao.updateById(sysUser);
    }

}
