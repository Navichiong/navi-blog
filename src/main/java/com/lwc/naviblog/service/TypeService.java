package com.lwc.naviblog.service;

import com.lwc.naviblog.model.Type;
import com.lwc.naviblog.vo.TypeVo;

import java.util.List;

public interface TypeService {

    Integer addType(Type type);

    Integer updateById(Type type);

    Integer deleteById(Integer id);

    Type selectById(Integer id);

    List<Type> selectAll();

    Type selectByName(String name);

    List<TypeVo> selectBlogCountGroupByType(Integer size);
}
