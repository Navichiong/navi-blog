package com.lwc.naviblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SysRole implements GrantedAuthority {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String roleName;
    private String description;
    @TableField(exist = false)
    private List<SysAuthority> authorities;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return this.roleName;
    }
}
