package com.lwc.naviblog.service;

import com.lwc.naviblog.model.Tag;
import com.lwc.naviblog.vo.TagVo;

import java.util.List;

public interface TagService {
    Integer addTag(Tag tag);

    Integer updateById(Tag tag);

    Integer deleteById(Integer id);

    Tag selectById(Integer id);

    List<Tag> selectAll();

    Tag selectByName(String name);

    List<Tag> selectByBlogId(Integer blogId);

    List<TagVo> selectBlogCountGroupByTag(Integer size);
}
