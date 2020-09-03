package com.lwc.naviblog.dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TagDaoTest {

    @Autowired
    private TagDao tagDao;

    @Test
    public void testFindAll() {
        tagDao.selectList(null).forEach(System.out::println);
    }

}
