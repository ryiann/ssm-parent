package com.ryan.enums;

/**
 * @author YoriChen
 * @date 2018/5/21
 */
public enum ResponseCode {
    /**
     * BASE Code
     */
    SYSTEM_ERROR("BASE_000000", "系统运行异常！"),
    PARAM_IS_NULL("BASE_000001", "请求参数为空！"),
    PARAM_ERROR("BASE_000002", "输入参数有误！"),

    /**
     * TOKEN Code
     */
    TOKEN_LIMIT_ERROR("TOKEN_000001", "限制调用"),
    TOKEN_EXPIRED("TOKEN_000002", "token 过期"),
    TOKEN_NO_AUTH("TOKEN_000003", "禁止访问"),
    TOKEN_NOT_FOUND("TOKEN_000004", "资源没找到"),
    TOKEN_INVALID_REQUEST("TOKEN_000005", "非法请求"),
    ;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;

    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}