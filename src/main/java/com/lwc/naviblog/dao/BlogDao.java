package com.lwc.naviblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lwc.naviblog.model.Blog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 博客Dao
 */
public interface BlogDao extends BaseMapper<Blog> {
    Integer updateTypeIdById(@Param("blogId") Integer blogId, @Param("typeId") Integer typeId);

    List<Blog> topBlogBySzie(Integer size);

    List<Blog> selectByTagId(Integer tagId);

    List<String> selectYears();

    List<Blog> selectByYear(String year);
}
