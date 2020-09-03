package com.lwc.naviblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lwc.naviblog.dao.CommentDao;
import com.lwc.naviblog.model.Comment;
import com.lwc.naviblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    private List<Comment> replyComments = new LinkedList<>();

    @Override
    public Integer addComment(Comment comment) {
        return commentDao.insert(comment);
    }

    @Override
    public Integer updateById(Comment comment) {
        return null;
    }

    @Override
    public Integer deleteById(Integer id) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public Comment selectById(Integer id) {
        return commentDao.selectById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public synchronized List<Comment> selectByBlogId(Integer blogId) {

        //找出父节点
        List<Comment> comments = commentDao.selectList(new QueryWrapper<Comment>()
                .eq("blog_id", blogId)
                .isNull("parent_comment_id")
                .orderByDesc("create_time"));

        // 找出子集回复
        for (Comment comment : comments) {
            List<Comment> children = this.selectReplyCommentsByParentId(comment.getId());
            this.combineChildren(children);
            List<Comment> currentReplies = new LinkedList<>(this.replyComments);
            comment.setReplyComments(currentReplies);
            this.replyComments.clear();
        }

        return comments;
    }

    /**
     * 根据parent_commnet_id查询子回复
     *
     * @param parentId 父级评论ID
     * @return 以创建时间为逆序排列的子集回复列表
     */
    @Transactional(readOnly = true)
    @Override
    public List<Comment> selectReplyCommentsByParentId(Integer parentId) {
        return commentDao.selectList(new QueryWrapper<Comment>()
                .eq("parent_comment_id", parentId)
                .orderByDesc("create_time"));
    }

    private void combineChildren(List<Comment> comments) {
        if (!CollectionUtils.isEmpty(comments)) {
            for (Comment comment : comments) {
                // 先找出自己的父
                Comment parent = this.selectById(comment.getParentCommentId());
                comment.setParentComment(parent);
                replyComments.add(comment);
                // 再找出自己的子
                List<Comment> children2 = this.selectReplyCommentsByParentId(comment.getId());
                this.combineChildren(children2);
            }
        }
    }
}
