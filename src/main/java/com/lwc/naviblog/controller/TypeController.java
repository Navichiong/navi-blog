package com.lwc.naviblog.controller;

import com.lwc.naviblog.enums.StatusCode;
import com.lwc.naviblog.model.CommonResult;
import com.lwc.naviblog.service.TypeService;
import com.lwc.naviblog.utils.CommonResultUtil;
import com.lwc.naviblog.vo.TypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/type")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/topType")
    public CommonResult topType(@RequestParam(required = false, defaultValue = "5") Integer size) {
        // 负数代表查询全部
        if (size < 0) {
            size = null;
        }
        List<TypeVo> typeVos = typeService.selectBlogCountGroupByType(size);
        return !CollectionUtils.isEmpty(typeVos) ?
                CommonResultUtil.build(StatusCode.SUCCESS, "success", typeVos) :
                CommonResultUtil.build(StatusCode.ERROR, "error", null);

    }
}
