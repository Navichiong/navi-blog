package com.lwc.naviblog.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lwc.naviblog.model.Blog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
public class BlogDaoTest {

    @Autowired
    private BlogDao blogDao;

    @Test
    public void testFindAll() {
        blogDao.selectList(null).forEach(System.out::println);
    }

    @Test
    public void testLambdaSql() {
        Blog blog = new Blog().setTitle("123");
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        List<Blog> blogs = blogDao.selectList(wrapper
                .eq("recommend", true)
                .eq("published", true)
                .orderByDesc("update_time")
                .and(!StringUtils.isEmpty(blog.getTitle()) || !StringUtils.isEmpty(blog.getContent()),
                        obj ->
                                obj.like("title", blog.getTitle())
                                        .or()
                                        .like("content", blog.getContent()))
        );
        System.out.println(blogs);
    }

    @Test
    public void testSelectByTagId(){
        blogDao.selectByTagId(9).forEach(System.out::println);
    }

    @Test
    public void testYears(){
        blogDao.selectYears().forEach(System.out::println);
    }

    @Test
    public void testSelectByYear(){
        blogDao.selectByYear("2020").forEach(System.out::println);

        System.out.println(blogDao.selectCount(null));
    }

}
