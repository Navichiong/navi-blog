package com.lwc.naviblog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lwc.naviblog.enums.StatusCode;
import com.lwc.naviblog.model.Blog;
import com.lwc.naviblog.model.CommonResult;
import com.lwc.naviblog.service.BlogService;
import com.lwc.naviblog.utils.CommonResultUtil;
import com.lwc.naviblog.utils.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/selectAll")
    public CommonResult index(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                              @RequestParam(required = false, defaultValue = "5") Integer navigatePages) {
        // 博客数据
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> blogs = blogService.selectByRecommendAndPublished(true, true);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs, navigatePages);
        return !ObjectUtils.isEmpty(blogPageInfo) ?
                CommonResultUtil.build(StatusCode.SUCCESS, "success", blogPageInfo) :
                CommonResultUtil.build(StatusCode.ERROR, "error", null);
    }

    @GetMapping("/topBlog")
    public CommonResult topType(@RequestParam(required = false, defaultValue = "5") Integer size) {
        List<Blog> blogs = blogService.topBlogBySize(size);
        return !CollectionUtils.isEmpty(blogs) ?
                CommonResultUtil.build(StatusCode.SUCCESS, "success", blogs) :
                CommonResultUtil.build(StatusCode.ERROR, "error", null);
    }

    @GetMapping("/globalSearch")
    public CommonResult globalSearch(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                     @RequestParam(required = false, defaultValue = "5") Integer navigatePages,
                                     Blog blog) {
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> blogs = blogService.globalSearch(blog);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs, navigatePages);
        return !ObjectUtils.isEmpty(blogPageInfo) ?
                CommonResultUtil.build(StatusCode.SUCCESS, "success", blogPageInfo) :
                CommonResultUtil.build(StatusCode.ERROR, "error", null);

    }

    @GetMapping("/selectById/{id}")
    public CommonResult selectById(@PathVariable("id") Integer id) {
        Blog blog = blogService.selectById(id);
        if (ObjectUtils.isEmpty(blog)) {
            return CommonResultUtil.build(StatusCode.ERROR, "error", null);
        }

        // 更新博客的浏览次数
        Integer views = blog.getViews();
        if (views == null) {
            views = 1;
        } else {
            views = views + 1;
        }
        blog.setViews(views);
        Integer row = blogService.updateById(blog);

        // 注意要在更新之后再格式化MarkDown格式，否则数据库的也变成html格式
        String s = MarkdownUtils.markdownToHtmlExtensions(blog.getContent());
        blog.setContent(s);

        return row != null && row > 0 ?
                CommonResultUtil.build(StatusCode.SUCCESS, "success", blog) :
                CommonResultUtil.build(StatusCode.ERROR, "error", null);
    }

    @GetMapping("/selectByTypeId/{typeId}")
    public CommonResult selectByTypeId(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                       @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                       @RequestParam(required = false, defaultValue = "5") Integer navigatePages,
                                       @PathVariable("typeId") Integer typeId) {
        Map<String, Object> map = new ConcurrentHashMap<>();
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> blogs = blogService.selectByTypeId(typeId);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs, navigatePages);
        map.put("blogInfo", blogPageInfo);
        map.put("activeType", typeId);
        return !ObjectUtils.isEmpty(blogs) ?
                CommonResultUtil.build(StatusCode.SUCCESS, "success", map) :
                CommonResultUtil.build(StatusCode.ERROR, "error", typeId);
    }

    @GetMapping("/selectByTagId/{tagId}")
    public CommonResult selectByTagId(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                      @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                      @RequestParam(required = false, defaultValue = "5") Integer navigatePages,
                                      @PathVariable("tagId") Integer tagId) {

        Map<String, Object> map = new ConcurrentHashMap<>();

        PageHelper.startPage(pageNum, pageSize);
        List<Blog> blogs = blogService.selectByTagId(tagId);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs, navigatePages);
        map.put("blogInfo", blogPageInfo);

        map.put("activeTag", tagId);
        return !ObjectUtils.isEmpty(blogs) ?
                CommonResultUtil.build(StatusCode.SUCCESS, "success", map) :
                CommonResultUtil.build(StatusCode.ERROR, "error", tagId);
    }

    @GetMapping("/archive")
    public CommonResult blogArchive() {

        Map<String, Object> result = new ConcurrentHashMap<>();

        Map<String, List<Blog>> archive = blogService.blogArchive();
        result.put("archiveInfo", archive);

        Integer rows = blogService.countAll();
        result.put("blogCount", rows);

        return !CollectionUtils.isEmpty(result) ?
                CommonResultUtil.build(StatusCode.SUCCESS, "success", result) :
                CommonResultUtil.build(StatusCode.ERROR, "error", null);
    }
}
