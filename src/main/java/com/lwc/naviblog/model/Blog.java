package com.lwc.naviblog.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Blog {

    @TableId(type = IdType.AUTO)
    @NotNull(message = "博客Id不能为空", groups = {Update.class})
    private Integer id;

    @NotEmpty(message = "博客标题不能为空", groups = {Insert.class, Update.class})
    private String title;

    @NotEmpty(message = "博客内容不能为空", groups = {Insert.class, Update.class})
    private String content;

    @NotEmpty(message = "请指定博客首图",groups = {Insert.class, Update.class})
    private String firstPicture;

    @NotEmpty(message = "博客描述不能为空", groups = {Insert.class, Update.class})
    private String description;

    private String flag;

    @NotNull(message = "请指定博客分类", groups = {Insert.class, Update.class})
    private Integer typeId;

    private Integer views;
    private Boolean appreciation;
    private Boolean commentabled;
    private Boolean published;
    private Boolean recommend;
    private Boolean shareStatement;
    private Integer userId;
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    @TableField(exist = false)
    private Type type;

    @TableField(exist = false)
    private List<Tag> tags;

    @TableField(exist = false)
    private SysUser user;

    @TableField(exist = false)
    private List<Comment> comments;
}
