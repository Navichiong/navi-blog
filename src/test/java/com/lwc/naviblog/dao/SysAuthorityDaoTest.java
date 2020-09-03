package com.lwc.naviblog.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SysAuthorityDaoTest {

    @Autowired
    private SysAuthorityDao authorityDao;

    @Test
    public void testFindAll(){
        authorityDao.selectList(null).forEach(System.out::println);
    }

}
