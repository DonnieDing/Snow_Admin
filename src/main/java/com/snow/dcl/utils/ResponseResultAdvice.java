/**
 * Copyright (C), 2018-2021, Dcl_Snow
 * History:
 * <author>    <create>    <version>   <desc>
 * 作者姓名     修改时间        版本号    功能描述
 */
package com.snow.dcl.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @ClassName ResponesResultAdvice
 * (功能描述)
 * 统一返回的进一步处理
 * @Author Dcl_Snow
 * @Create 2021/8/16 11:32
 * @Version 1.0.0
 */
@RestControllerAdvice(basePackages = "com.snow.dcl.controller")
public class ResponseResultAdvice implements ResponseBodyAdvice {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        if (o instanceof String) {
            return objectMapper.writeValueAsString(ResponseResult.success().data(o));
        }
        if (o instanceof ResponseResult) {
            return o;
        }
        return ResponseResult.success().data(o);
    }
}
