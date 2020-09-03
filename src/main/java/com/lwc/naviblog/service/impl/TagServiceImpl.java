package com.lwc.naviblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lwc.naviblog.dao.TagDao;
import com.lwc.naviblog.model.Tag;
import com.lwc.naviblog.service.TagService;
import com.lwc.naviblog.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    @Override
    public Integer addTag(Tag tag) {
        return tagDao.insert(tag);
    }

    @Override
    public Integer updateById(Tag tag) {
        return tagDao.updateById(tag);
    }

    @Override
    public Integer deleteById(Integer id) {
        //级联删除：博客标签关联表和标签表
        return tagDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Tag selectById(Integer id) {
        return tagDao.selectById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tag> selectAll() {
        return tagDao.selectList(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Tag selectByName(String name) {
        return tagDao.selectOne(new QueryWrapper<Tag>()
                .eq("name", name));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Tag> selectByBlogId(Integer blogId) {
        return tagDao.selectByBlogId(blogId);
    }

    /**
     * 根据tag分组查询对应的博客数量
     * @param size 限制的条数
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<TagVo> selectBlogCountGroupByTag(Integer size) {
        return tagDao.selectBlogCountGroupByTag(size);
    }
}
