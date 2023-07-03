/**
 * Copyright (C), 2018-2021, Dcl_Snow
 * History:
 * <author>    <create>    <version>   <desc>
 * 作者姓名     修改时间        版本号    功能描述
 */
package com.snow.dcl.utils;

/**
 * @ClassName HttpCodeEnum
 * (功能描述)
 * 接口返回状态码
 * @Author Dcl_Snow
 * @Create 2021/8/16 10:57
 * @Version 1.0.0
 */
public enum HttpCodeEnum {

    // 特殊段
    SUCCESS("00000", "操作成功"),
    FAIL("50000", "服务器错误"),

    // code规则定义：
    // A段：错误来源于用户
    // B段：错误来源于系统服务
    // C段：错误来源于第三方服务

    UNAUTHORIZED("A0401", "请进行登录认证"),
    FORBIDDEN("A0403", "权限不足，请联系管理员"),
    // B服务端错误段
    // 参数校验
    PARAMETER_VALIDATED_FAIL("B0400", "参数校验失败"),
    DELETE_FAIL("B0450", "删除失败");

    String code;
    String message;

    HttpCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
