package com.lwc.naviblog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class BlogTags {

    private Integer id;
    private Integer blogId;
    private Integer tagId;
}
