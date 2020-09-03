package com.lwc.naviblog.enums;

public enum StatusCode {
    SUCCESS(200),ERROR(500),ILLEGAL(400) ,UNAUTHORIZED(401),FORBIDDEN(403);

    private Integer code;

    StatusCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "StatusCode{" +
                "code=" + code +
                '}';
    }
}
