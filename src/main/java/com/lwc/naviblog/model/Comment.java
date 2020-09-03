package com.lwc.naviblog.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lwc.naviblog.model.valid.Insert;
import com.lwc.naviblog.model.valid.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Comment {

    private Integer id;
    @NotEmpty(message = "昵称不能为空",groups = {Insert.class, Update.class})
    private String nickname;
    @NotEmpty(message = "邮箱不能为空",groups = {Insert.class, Update.class})
    private String email;
    @NotEmpty(message = "评论不能为空",groups = {Insert.class, Update.class})
    private String content;
    @TableField(fill = FieldFill.INSERT)
    private String avatar;
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    @NotNull(message = "博客ID不能为空",groups = {Insert.class, Update.class})
    private Integer blogId;
    private Integer parentCommentId;

    private boolean adminComment;

    @TableField(exist = false)
    private Blog blog;

    @TableField(exist = false)
    private List<Comment> replyComments;

    @TableField(exist = false)
    private Comment parentComment;
}
