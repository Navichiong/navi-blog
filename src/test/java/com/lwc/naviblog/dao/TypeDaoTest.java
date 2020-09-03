package com.lwc.naviblog.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class TypeDaoTest {

    @Autowired
    private TypeDao typeDao;

    @Test
    public void testFindAll() {
        typeDao.selectList(null).forEach(System.out::println);
    }

    @Test
    public void testSelectBlogCountGroupByType(){
        typeDao.selectBlogCountGroupByType(1).forEach(System.out::println);
    }

}
