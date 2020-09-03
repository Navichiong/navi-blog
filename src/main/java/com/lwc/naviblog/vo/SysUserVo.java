package com.lwc.naviblog.vo;

import com.lwc.naviblog.model.Blog;
import com.lwc.naviblog.model.SysRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SysUserVo {
    private Integer id;
    private String username;
    private String email;
    private String avatar;
    private Integer type;
    private String nickname;
    private Integer status;
    private String wechat;
    private Date createTime;
    private Date updateTime;
    private List<SysRole> roles;
    private List<Blog> blogs;
}
