package com.lwc.naviblog.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TypeVo {

    private Integer id;
    private String typeName;
    private Integer blogCount;
}
