package com.lwc.naviblog.controller;

import com.lwc.naviblog.enums.StatusCode;
import com.lwc.naviblog.model.CommonResult;
import com.lwc.naviblog.utils.CommonResultUtil;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/auth")
    public CommonResult auth(Authentication authentication){
        return CommonResultUtil.build(StatusCode.SUCCESS,"success" , authentication);
    }
}
