package com.lwc.naviblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lwc.naviblog.model.Type;
import com.lwc.naviblog.vo.TypeVo;

import java.util.List;

public interface TypeDao extends BaseMapper<Type> {
    List<TypeVo> selectBlogCountGroupByType(Integer size);
}
