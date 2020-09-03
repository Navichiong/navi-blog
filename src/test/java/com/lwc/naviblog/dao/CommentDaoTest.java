package com.lwc.naviblog.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CommentDaoTest {

    @Autowired
    private CommentDao commentDao;

    @Test
    public void testFindAll() {
        commentDao.selectList(null).forEach(System.out::println);
    }
}

