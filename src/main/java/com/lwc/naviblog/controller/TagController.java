package com.lwc.naviblog.controller;

import com.lwc.naviblog.enums.StatusCode;
import com.lwc.naviblog.model.CommonResult;
import com.lwc.naviblog.service.TagService;
import com.lwc.naviblog.utils.CommonResultUtil;
import com.lwc.naviblog.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/topTag")
    public CommonResult topType(@RequestParam(required = false, defaultValue = "5") Integer size) {
        if (size < 0) {
            size = null;
        }
        List<TagVo> tagVos = tagService.selectBlogCountGroupByTag(size);
        return !CollectionUtils.isEmpty(tagVos) ?
                CommonResultUtil.build(StatusCode.SUCCESS, "success", tagVos) :
                CommonResultUtil.build(StatusCode.ERROR, "error", null);

    }
}
