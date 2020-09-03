package com.lwc.naviblog.service;

import com.lwc.naviblog.model.Blog;

import java.util.List;
import java.util.Map;

public interface BlogService {

    Integer addBlog(Blog blog);

    Integer updateById(Blog blog);

    Integer deleteById(Integer id);

    Blog selectById(Integer id);

    List<Blog> selectByTypeId(Integer typeId);

    Integer updateTypeIdById(Integer blogId, Integer typeId);

    List<Blog> selectByConditions(Blog blog);

    Blog selectByTitle(String title);

    List<Blog> selectByRecommendAndPublished(Boolean recommend, Boolean published);

    List<Blog> topBlogBySize(Integer size);

    List<Blog> globalSearch(Blog blog);

    List<Blog> selectByTagId(Integer tagId);

    Map<String,List<Blog>> blogArchive();

    Integer countAll();
}
