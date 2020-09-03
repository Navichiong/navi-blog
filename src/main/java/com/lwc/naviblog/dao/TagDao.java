package com.lwc.naviblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lwc.naviblog.model.Tag;
import com.lwc.naviblog.vo.TagVo;

import java.util.List;

public interface TagDao extends BaseMapper<Tag> {

    List<Tag> selectByBlogId(Integer blogId);

    List<TagVo> selectBlogCountGroupByTag(Integer size);
}
