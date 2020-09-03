package com.lwc.naviblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lwc.naviblog.dao.TypeDao;
import com.lwc.naviblog.model.Blog;
import com.lwc.naviblog.model.Type;
import com.lwc.naviblog.service.BlogService;
import com.lwc.naviblog.service.TypeService;
import com.lwc.naviblog.vo.TypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeDao typeDao;

    @Autowired
    private BlogService blogService;

    @Override
    public Integer addType(Type type) {
        return typeDao.insert(type);
    }

    @Override
    public Integer updateById(Type type) {
        return typeDao.updateById(type);
    }

    @Override
    public Integer deleteById(Integer id) {
        // 把与被删除的分类相关的blog的type_id置空
        List<Blog> blogs = blogService.selectByTypeId(id);
        blogs.forEach(blog -> blogService.updateTypeIdById(blog.getId(), null));
        return typeDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Type selectById(Integer id) {
        return typeDao.selectById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Type> selectAll() {
        return typeDao.selectList(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Type selectByName(String name) {
        return typeDao.selectOne(new QueryWrapper<Type>()
                .eq("type_name", name));
    }

    /**
     * 根据type分组查询对应的blog数量，并按照blog数量逆袭排序
     *
     * @param size 限制的条数
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<TypeVo> selectBlogCountGroupByType(Integer size) {
        return typeDao.selectBlogCountGroupByType(size);
    }
}
