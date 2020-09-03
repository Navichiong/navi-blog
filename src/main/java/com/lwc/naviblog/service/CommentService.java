package com.lwc.naviblog.service;

import com.lwc.naviblog.model.Comment;

import java.util.List;

public interface CommentService {
    Integer addComment(Comment comment);

    Integer updateById(Comment comment);

    Integer deleteById(Integer id);

    Comment selectById(Integer id);

    List<Comment> selectByBlogId(Integer blogId);

    List<Comment> selectReplyCommentsByParentId(Integer parentId);
}
