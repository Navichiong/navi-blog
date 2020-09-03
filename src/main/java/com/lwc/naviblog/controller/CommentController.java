package com.lwc.naviblog.controller;

import com.lwc.naviblog.enums.StatusCode;
import com.lwc.naviblog.model.Blog;
import com.lwc.naviblog.model.Comment;
import com.lwc.naviblog.model.CommonResult;
import com.lwc.naviblog.model.SysUser;
import com.lwc.naviblog.model.valid.Insert;
import com.lwc.naviblog.service.BlogService;
import com.lwc.naviblog.service.CommentService;
import com.lwc.naviblog.utils.CommonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Value("${server.servlet.context-path}")
    private String rootPath;

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @PostMapping("/addComment")
    public CommonResult addComment(@RequestBody @Validated(Insert.class) Comment comment, Authentication authentication) {

        // 查询该博客是否存在
        Blog blog = blogService.selectById(comment.getBlogId());
        if (blog == null) {
            return CommonResultUtil.build(StatusCode.ERROR, comment.getBlogId() + "号博客不存在", null);
        }

        //是否是管理员
        if (authentication != null) {
            SysUser user = (SysUser) authentication.getPrincipal();
            comment.setNickname(user.getNickname());
            comment.setEmail(user.getEmail());
            comment.setAdminComment(true);
            comment.setAvatar(rootPath + "/images/user.jpg");
        }

        // 登出之后有些页面还不知道。。。。
        if(comment.isAdminComment()){
            comment.setAvatar(rootPath + "/images/user.jpg");
        }

        Integer pId = comment.getParentCommentId();
        if (pId != null && pId.equals(-1)) {
            comment.setParentCommentId(null);
        }
        Integer integer = commentService.addComment(comment);
        return integer != null && integer > 0 ?
                CommonResultUtil.build(StatusCode.SUCCESS, "success", null) :
                CommonResultUtil.build(StatusCode.ERROR, "error", null);
    }

    @GetMapping("/selectByBlogId/{blogId}")
    public CommonResult selectByBlogId(@PathVariable("blogId") Integer blogId) {

        List<Comment> comments = commentService.selectByBlogId(blogId);
        return !CollectionUtils.isEmpty(comments) ?
                CommonResultUtil.build(StatusCode.SUCCESS, "success", comments) :
                CommonResultUtil.build(StatusCode.ERROR, "error", null);
    }

    @GetMapping("/isAdmin")
    public CommonResult auth(Authentication authentication) {
        //是否是管理员
        if (authentication != null) {
            SysUser user = (SysUser) authentication.getPrincipal();
            user.setPassword(null);
            return CommonResultUtil.build(StatusCode.SUCCESS, "success", user);
        }
        return CommonResultUtil.build(StatusCode.ERROR, "error", null);
    }
}
