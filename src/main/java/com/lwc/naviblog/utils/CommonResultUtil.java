package com.lwc.naviblog.utils;

import com.lwc.naviblog.enums.StatusCode;
import com.lwc.naviblog.model.CommonResult;
import org.springframework.util.StringUtils;

public class CommonResultUtil {

    private CommonResultUtil() {

    }

    public static <T> CommonResult<T> get() {
        return new CommonResult<>();
    }

    public static <T> CommonResult<T> build(StatusCode statusCode, String msg, T data) {
        return new CommonResult<>(statusCode.getCode(), msg, data);
    }

    /*public static <T> CommonResult<T> ok(String msg, T data) {
        if (StringUtils.isEmpty(msg)) {
            msg = "success";
        }
        return new CommonResult<>(200, msg, data);
    }

    public static <T> CommonResult<T> error(String msg, T data) {
        if (StringUtils.isEmpty(msg)) {
            msg = "error";
        }
        return new CommonResult<>(500, msg, data);
    }*/
}
