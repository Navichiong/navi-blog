package com.lwc.naviblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.lwc.naviblog.dao.BlogDao;
import com.lwc.naviblog.dao.BlogTagsDao;
import com.lwc.naviblog.model.Blog;
import com.lwc.naviblog.model.BlogTags;
import com.lwc.naviblog.model.SysUser;
import com.lwc.naviblog.model.Tag;
import com.lwc.naviblog.model.Type;
import com.lwc.naviblog.service.BlogService;
import com.lwc.naviblog.service.SysUserService;
import com.lwc.naviblog.service.TagService;
import com.lwc.naviblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogTagsDao blogTagsDao;

    @Autowired
    private SysUserService userService;

    @Override
    public Integer addBlog(Blog blog) {

        int i = blogDao.insert(blog);

        // 设置博客标签关联
        List<Tag> tags = blog.getTags();
        if (!CollectionUtils.isEmpty(tags)) {
            tags.forEach(tag -> {
                if (tag != null) {
                    blogTagsDao.insert(new BlogTags(null, blog.getId(), tag.getId()));
                }
            });
        }
        return i;
    }

    @Override
    public Integer updateById(Blog blog) {

        // 删除以前的博客标签关联;
        blogTagsDao.delete(new QueryWrapper<BlogTags>()
                .eq("blog_id", blog.getId()));

        // 设置博客标签关联
        List<Tag> tags = blog.getTags();
        if (!CollectionUtils.isEmpty(tags)) {
            tags.forEach(tag -> {
                if (tag != null) {
                    blogTagsDao.insert(new BlogTags(null, blog.getId(), tag.getId()));
                }
            });
        }

        return blogDao.updateById(blog);
    }

    @Override
    public Integer deleteById(Integer id) {
        // 删除博客-标签关联表
        blogTagsDao.delete(new QueryWrapper<BlogTags>()
                .eq("blog_id", id));
        // 删除博客表
        return blogDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Blog selectById(Integer id) {
        Blog blog = blogDao.selectById(id);
        if (blog == null) {
            return null;
        }
        // 查询对应的分类
        Type type = typeService.selectById(blog.getTypeId());
        blog.setType(type);
        //查询对应的标签
        List<Tag> tags = tagService.selectByBlogId(blog.getId());
        blog.setTags(tags);
        // 查询对应的SysUser
        SysUser sysUser = userService.selectById(blog.getUserId());
        sysUser.setPassword(null);
        blog.setUser(sysUser);
        return blog;
    }

    /**
     * 通过分类ID查询多个博客
     *
     * @param typeId 分类id
     * @return 博客List
     */
    @Transactional(readOnly = true)
    @Override
    public List<Blog> selectByTypeId(Integer typeId) {
        List<Blog> blogs = blogDao.selectList(new QueryWrapper<Blog>()
                .eq("type_id", typeId));

        blogs.forEach(blog -> {
            Type type = typeService.selectById(blog.getTypeId());
            blog.setType(type);

            SysUser sysUser = userService.selectById(blog.getUserId());
            blog.setUser(sysUser);
        });
        return blogs;
    }

    /**
     * 根据博客Id修改博客type_id
     *
     * @param blogId 博客ID
     * @param typeId 分类ID
     * @return 更新的行数
     */
    @Override
    public Integer updateTypeIdById(Integer blogId, Integer typeId) {
        return blogDao.updateTypeIdById(blogId, typeId);
    }

    /**
     * 根据多个条件查询博客
     *
     * @param blog 博客实体
     * @return 博客List
     */
    @Transactional(readOnly = true)
    @Override
    public List<Blog> selectByConditions(Blog blog) {

        // 按照条件查询多个博客
        List<Blog> blogs = blogDao.selectList(new QueryWrapper<Blog>()
                .eq(!StringUtils.isEmpty(blog.getTitle()), "title", blog.getTitle())
                .eq(!ObjectUtils.isEmpty(blog.getTypeId()), "type_id", blog.getTypeId())
                .eq(!ObjectUtils.isEmpty(blog.getRecommend()), "recommend", blog.getRecommend()));

        //把博客对应的type查出来
        blogs.forEach(b -> {
            if (b.getTypeId() != null) {
                Type type = typeService.selectById(b.getTypeId());
                b.setType(type);
            }
        });
        return blogs;
    }

    @Transactional(readOnly = true)
    @Override
    public Blog selectByTitle(String title) {
        return blogDao.selectOne(new QueryWrapper<Blog>()
                .eq("title", title));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Blog> selectByRecommendAndPublished(Boolean recommend, Boolean published) {
        List<Blog> blogs = blogDao.selectList(new QueryWrapper<Blog>()
                .eq("recommend", recommend)
                .eq("published", published)
                .orderByDesc("update_time"));


        blogs.forEach(blog -> {
            // 查出User对象
            SysUser sysUser = userService.selectById(blog.getUserId());
            sysUser.setPassword(null);
            blog.setUser(sysUser);

            // 查出Type对象
            Type type = typeService.selectById(blog.getTypeId());
            blog.setType(type);
        });

        return blogs;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Blog> topBlogBySize(Integer size) {
        return blogDao.topBlogBySzie(size);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Blog> globalSearch(Blog blog) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        List<Blog> blogs = blogDao.selectList(wrapper
                .eq("recommend", true)
                .eq("published", true)
                .orderByDesc("update_time")
                .and(!StringUtils.isEmpty(blog.getTitle()) || !StringUtils.isEmpty(blog.getContent()),
                        obj ->
                                obj.like("title", StringUtils.isEmpty(blog.getTitle()) ? blog.getContent() : blog.getTitle())
                                        .or()
                                        .like("content", StringUtils.isEmpty(blog.getContent()) ? blog.getTitle() : blog.getContent()))
        );

        blogs.forEach(b -> {
            // 查出User对象
            SysUser sysUser = userService.selectById(b.getUserId());
            sysUser.setPassword(null);
            b.setUser(sysUser);

            // 查出Type对象
            Type type = typeService.selectById(b.getTypeId());
            b.setType(type);
        });

        return blogs;
    }

    @Override
    public List<Blog> selectByTagId(Integer tagId) {

        List<Blog> blogs = blogDao.selectByTagId(tagId);
        blogs.forEach(b -> {
            // 查出User对象
            SysUser sysUser = userService.selectById(b.getUserId());
            sysUser.setPassword(null);
            b.setUser(sysUser);

            // 查出Type对象
            Type type = typeService.selectById(b.getTypeId());
            b.setType(type);

            // 查出Tags
            List<Tag> tags = tagService.selectByBlogId(b.getId());
            b.setTags(tags);
        });
        return blogs;
    }

    @Override
    public Map<String, List<Blog>> blogArchive() {

        Map<String, List<Blog>> map = new TreeMap<>((o1, o2) -> Integer.valueOf(o2) - Integer.valueOf(o1));
        List<String> years = blogDao.selectYears();
        years.forEach(year -> {
            List<Blog> blogs = blogDao.selectByYear(year);
            map.put(year, blogs);
        });
        return map;
    }

    @Override
    public Integer countAll() {
        return blogDao.selectCount(null);
    }
}
