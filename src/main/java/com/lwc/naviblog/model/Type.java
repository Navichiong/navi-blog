package com.lwc.naviblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lwc.naviblog.model.valid.Insert;
import com.lwc.naviblog.model.valid.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Type {

    @TableId(type = IdType.AUTO)
    @NotNull(groups = {Update.class},message = "分类id能为空")
    private Integer id;
    @NotEmpty(groups = {Insert.class,Update.class},message = "分类名不能为空")
    private String typeName;

    @TableField(exist = false)
    private List<Blog> blogs ;
}
