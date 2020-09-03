package com.lwc.naviblog.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lwc.naviblog.enums.StatusCode;
import com.lwc.naviblog.model.CommonResult;
import com.lwc.naviblog.model.Blog;
import com.lwc.naviblog.model.SysUser;
import com.lwc.naviblog.model.valid.Insert;
import com.lwc.naviblog.model.valid.Update;
import com.lwc.naviblog.service.BlogService;
import com.lwc.naviblog.utils.CommonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/blog")
public class AdminBlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/list")
    public CommonResult list(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                             @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false, defaultValue = "5") Integer navigatePages,
                             Blog blog) {
        PageHelper.startPage(pageNum, pageSize, "update_time desc");
        List<Blog> blogs = blogService.selectByConditions(blog);
        PageInfo<Blog> listPageInfo = new PageInfo<>(blogs, navigatePages);
        return CommonResultUtil.build(StatusCode.SUCCESS, "success", listPageInfo);
    }

    /**
     * 添加博客
     *
     * @param blog 博客实体
     * @return Json描述类
     */
    @PostMapping("/addBlog")
    public CommonResult addBlog(@RequestBody @Validated(Insert.class) Blog blog, Authentication authentication) {
        // 查询是否存在该博客
        Blog b = blogService.selectByTitle(blog.getTitle());
        if (b != null) {
            // 存在则不添加
            return CommonResultUtil.build(StatusCode.ERROR, "博客标题重复，请换一个标题", null);
        }

        // 设置user_id
        SysUser sysUser = (SysUser) authentication.getPrincipal();
        blog.setUserId(sysUser.getId());
        // 设置查看次数
        blog.setViews(0);

        return blogService.addBlog(blog) > 0 ?
                CommonResultUtil.build(StatusCode.SUCCESS, "添加博客成功", null) :
                CommonResultUtil.build(StatusCode.ERROR, "添加博客失败", null);
    }

    @GetMapping("/selectById/{id}")
    public CommonResult selectById(@PathVariable("id") Integer id) {
        Blog blog = blogService.selectById(id);
        return blog != null ? CommonResultUtil.build(StatusCode.SUCCESS, "success", blog) :
                CommonResultUtil.build(StatusCode.ERROR, "error", null);
    }

    @PutMapping("/updateById")
    public CommonResult updateById(@RequestBody @Validated(Update.class) Blog blog) {
        Blog b  = blogService.selectById(blog.getId());
        if ( b == null) {
            // 不存在则修改失败
            return CommonResultUtil.build(StatusCode.ERROR, blog.getId() + "号博客不存在", null);
        }

        b = blogService.selectByTitle(blog.getTitle());
        if (b != null && !b.getId().equals(blog.getId())) {
            // 存在则不修改
            return CommonResultUtil.build(StatusCode.ERROR, "博客标题重复，修改失败", null);
        }
        return blogService.updateById(blog) > 0 ?
                CommonResultUtil.build(StatusCode.SUCCESS, "success", null) :
                CommonResultUtil.build(StatusCode.ERROR, "error", null);
    }

    @DeleteMapping("/deleteById/{id}")
    public CommonResult updateById(@PathVariable("id") Integer id) {
        Blog blog = blogService.selectById(id);
        if (blog == null) {
            // 不存在则不删除
            return CommonResultUtil.build(StatusCode.ERROR, id + "号博客不存在", null);
        }
        return blogService.deleteById(id) > 0 ?
                CommonResultUtil.build(StatusCode.SUCCESS, "success", null) :
                CommonResultUtil.build(StatusCode.ERROR, "error", null);
    }
}
