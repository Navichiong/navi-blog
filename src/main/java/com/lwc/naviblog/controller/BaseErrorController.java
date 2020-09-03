package com.lwc.naviblog.controller;

import com.lwc.naviblog.enums.StatusCode;
import com.lwc.naviblog.model.CommonResult;
import com.lwc.naviblog.utils.CommonResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class BaseErrorController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    public CommonResult handleParamVerify(Exception e) {
        log.error("异常消息 --> " + e.getMessage());
        BindingResult br;
        if (e instanceof BindException) {
            br = ((BindException) e).getBindingResult();
        } else {
            br = ((MethodArgumentNotValidException) e).getBindingResult();
        }
        Map<String, String> map = new LinkedHashMap<>();
        br.getFieldErrors().forEach(fieldError -> map.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return CommonResultUtil.build(StatusCode.ILLEGAL, "非法传递参数", map);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CommonResult handleAccessException(AccessDeniedException e, HttpServletResponse response) {
        log.debug("权限异常 ==> " + e.getMessage());
        return CommonResultUtil.build(StatusCode.FORBIDDEN, "权限不足", null);
    }

    @ExceptionHandler(value = {Exception.class})
    public CommonResult handleCommonException(Exception e) {
        log.debug("异常消息 --> " + e.getMessage());
        return CommonResultUtil.build(StatusCode.ERROR, e.getMessage(), null);
    }

}
